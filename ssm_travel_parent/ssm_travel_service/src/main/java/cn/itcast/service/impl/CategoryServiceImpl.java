package cn.itcast.service.impl;

import cn.itcast.dao.CategoryDao;
import cn.itcast.domain.Category;
import cn.itcast.service.CategoryService;
import cn.itcast.utils.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDao categoryDao;

    /**
     * 查询所有分类
     *
     * @return
     */
    @Override
    public List<Category> findAll() {
        Jedis jedis = null;
        try {
            jedis = JedisUtil.getJedis();
        } catch (Exception e) {
            e.printStackTrace();
            List<Category> all = categoryDao.findAll();
            return all;
        }
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);

        List<Category> list = null;
        if (categorys == null || categorys.size() == 0) {
            list = categoryDao.findAll();
            for (Category category : list) {
                jedis.zadd("category", category.getCid(), category.getCname()); } } else {
            list = new ArrayList<Category>();
            for (Tuple tuple : categorys) {
                Category category = new Category();
                category.setCname(tuple.getElement());
                category.setCid((int) tuple.getScore());
                list.add(category); } }
                JedisUtil.close(jedis);
        return list;

    }
}
