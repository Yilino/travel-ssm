package cn.itcast.service.impl;

import cn.itcast.dao.UserDao;
import cn.itcast.domain.User;
import cn.itcast.service.UserService;
import cn.itcast.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    /**
     * 注册
     * @param user
     * @return
     */
    @Override
    public boolean register(User user) {
        //根据用户名查询
        List<User> userList = userDao.findUserByName(user.getUsername());
        System.out.println(userList);
        for (User user1 : userList) {
            System.out.println(user1);
        }
        if (userList!=null&&userList.size()>0){
            //用户名已存在
            return false;
        }
        user.setStatus("N");
//        user.setCode(UUID.randomUUID().toString().replace("-",""));
        user.setCode(UUIDUtils.getUuid());
        userDao.saveUser(user);
        return true;
    }

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @Override
    public User login(String username, String password) {

        return userDao.login(username,password);
    }
}
