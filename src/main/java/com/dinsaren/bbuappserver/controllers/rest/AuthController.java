package com.dinsaren.bbuappserver.controllers.rest;

import com.dinsaren.bbuappserver.constants.Constants;
import com.dinsaren.bbuappserver.exception.TokenRefreshException;
import com.dinsaren.bbuappserver.models.RefreshToken;
import com.dinsaren.bbuappserver.models.Role;
import com.dinsaren.bbuappserver.models.User;
import com.dinsaren.bbuappserver.models.UserRole;
import com.dinsaren.bbuappserver.payload.request.LogOutReq;
import com.dinsaren.bbuappserver.payload.request.LoginReq;
import com.dinsaren.bbuappserver.payload.request.RegisterReq;
import com.dinsaren.bbuappserver.payload.request.TokenRefreshReq;
import com.dinsaren.bbuappserver.payload.response.JwtRes;
import com.dinsaren.bbuappserver.payload.response.MessageRes;
import com.dinsaren.bbuappserver.payload.response.TokenRefreshRes;
import com.dinsaren.bbuappserver.repository.RoleRepository;
import com.dinsaren.bbuappserver.repository.UserRepository;
import com.dinsaren.bbuappserver.security.jwt.JwtUtils;
import com.dinsaren.bbuappserver.security.services.RefreshTokenService;
import com.dinsaren.bbuappserver.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/oauth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    RefreshTokenService refreshTokenService;
    @PostMapping("/token")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginReq req) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(req.getPhone(), req.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(userDetails);
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        return ResponseEntity.ok(new JwtRes(jwt, refreshToken.getToken(), userDetails.getId(),
                userDetails.getUsername(), userDetails.getEmail(), roles));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterReq req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageRes("Error: Username is already taken!",null));
        }
        if (userRepository.existsByEmail(req.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageRes("Error: Email is already in use!",null));
        }
        if (userRepository.existsByPhone(req.getPhone())) {
            return ResponseEntity.badRequest().body(new MessageRes("Error: Phone is already in use!",null));
        }
        // Create new user's account
        User user = new User(req.getUsername(), req.getEmail(),
                encoder.encode(req.getPassword()), req.getPhone());
//        Set<String> strRoles = req.getRole();
        Set<Role> roles = new HashSet<>();
//        if (strRoles == null) {
        Role userRole = roleRepository.findByName(UserRole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
//        } else {
//            strRoles.forEach(role -> {
//                switch (role) {
//                    case "admin":
//                        Role adminRole = roleRepository.findByName(UserRole.ROLE_ADMIN)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(adminRole);
//                        break;
//                    case "mod":
//                        Role modRole = roleRepository.findByName(UserRole.ROLE_MODERATOR)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(modRole);
//                        break;
//                    default:
//                        Role userRole = roleRepository.findByName(UserRole.ROLE_USER)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(userRole);
//                }
//            });
//        }

        user.setRoles(roles);
        user.setStatus(Constants.ACTIVE_STATUS);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageRes("User registered successfully!",null));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshReq request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new TokenRefreshRes(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@RequestBody LogOutReq req) {
        refreshTokenService.deleteByUserId(req.getUserId());
        return ResponseEntity.ok(new MessageRes("Log out successful!",null));
    }

}
