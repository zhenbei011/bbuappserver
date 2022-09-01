package com.dinsaren.bbuappserver.controllers.rest;

import com.dinsaren.bbuappserver.models.Category;
import com.dinsaren.bbuappserver.payload.response.MessageRes;
import com.dinsaren.bbuappserver.security.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/app/category")
public class CategoryController {
    private final CategoryService categoryService;
    private MessageRes messageRes;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public ResponseEntity<MessageRes> getAllCategory(){
        messageRes = new MessageRes();
        messageRes.setData(categoryService.findAll());
        messageRes.setMessage("SUC-000");
        return new ResponseEntity<>(messageRes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageRes> getById(@PathVariable("id") Integer id){
        messageRes = new MessageRes();
        messageRes.setData(categoryService.findById(id));
        messageRes.setMessage("SUC-000");
        return new ResponseEntity<>(messageRes, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<MessageRes> create(@RequestBody Category req){
        categoryService.save(req);
        messageRes = new MessageRes();
        messageRes.setMessage("Create Success");
        return new ResponseEntity<>(messageRes, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<MessageRes> update(@RequestBody Category req){
        messageRes = new MessageRes();
        if(null != categoryService.findById(req.getId())) {
            messageRes.setMessage("Create Success");
            categoryService.save(req);
        }else{
            messageRes.setMessage("Create Filed");
        }

        return new ResponseEntity<>(messageRes, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<MessageRes> delete(@RequestBody Category req){
        messageRes = new MessageRes();
        if(null != categoryService.findById(req.getId())) {
            messageRes.setMessage("Create Success");
            categoryService.delete(req);
        }else{
            messageRes.setMessage("Create Filed");
        }

        return new ResponseEntity<>(messageRes, HttpStatus.OK);
    }

}
