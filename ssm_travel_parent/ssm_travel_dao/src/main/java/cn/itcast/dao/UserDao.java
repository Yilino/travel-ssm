package cn.itcast.dao;

import cn.itcast.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserDao {
    /**
     * 查询所有用户
     * @return
     */
    List<User> findAll();


    /**
     * 根据用户名查询用户
      * @param username
     * @return
     */
    List<User> findUserByName(String username);

    /**
     * 保存用户
     * @param user
     */
    void saveUser(User user);

    /**
     * 用户登录
      * @param username
     * @param password
     * @return
     */
    User login(@Param("username") String username, @Param("password") String password);
}
