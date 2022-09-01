package com.dinsaren.bbuappserver.security.services;

import com.dinsaren.bbuappserver.constants.Constants;
import com.dinsaren.bbuappserver.models.User;
import com.dinsaren.bbuappserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user;
    Optional<User> userName = userRepository.findByUsernameAndStatus(username, Constants.ACTIVE_STATUS);
    if(userName.isPresent()){
      user = userName.get();
    }else{
      user = userRepository.findByPhoneAndStatus(username, Constants.ACTIVE_STATUS)
              .orElseThrow(() -> new UsernameNotFoundException("User Not Found with phone: " + username));
    }

    return UserDetailsImpl.build(user);
  }

}
