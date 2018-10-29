package cn.itcast.controller;

import cn.itcast.domain.Category;
import cn.itcast.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @RequestMapping("/findAll")
    public List findAll() {
        List<Category> list = categoryService.findAll();
        return list;
    }
}
