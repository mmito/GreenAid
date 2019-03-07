package app.services;

import app.models.Category;
import app.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl {

    @Autowired
    CategoryRepository categoryRepository;

    public Category findById(long id) {
        return categoryRepository.findById(id);
    }


}
