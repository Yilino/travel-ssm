package cn.itcast.service;

import cn.itcast.domain.User;

public interface UserService {
    /**
     * 注册
     * @param user
     * @return
     */
    boolean register(User user);

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    User login(String username, String password);
}
