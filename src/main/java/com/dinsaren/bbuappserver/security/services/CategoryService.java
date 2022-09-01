package com.dinsaren.bbuappserver.security.services;

import com.dinsaren.bbuappserver.constants.Constants;
import com.dinsaren.bbuappserver.models.Category;
import com.dinsaren.bbuappserver.repository.CategoryRepository;
import org.aspectj.apache.bcel.classfile.Constant;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll(){
        return categoryRepository.findAllByStatus(Constants.ACTIVE_STATUS);
    }

    public void save(Category req){
        req.setStatus(Constants.ACTIVE_STATUS);
        categoryRepository.save(req);
    }

    public void delete(Category req){
        req.setStatus(Constants.DELETE_STATUS);
        categoryRepository.save(req);
    }

    public Category findById(Integer id){
        return categoryRepository.findByIdAndStatus(id, Constants.ACTIVE_STATUS).get();
    }

}
