package cn.itcast.dao;

import cn.itcast.domain.Category;

import java.util.List;

public interface CategoryDao {

    /**
     * 查询所有
     * @return
     */
    List<Category> findAll();
}
