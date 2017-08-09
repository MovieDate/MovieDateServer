package com.moviedateserver.service.impl;

import com.moviedateserver.dao.UserDao;
import com.moviedateserver.entity.User;
import com.moviedateserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wunaifu on 2017/8/8.
 */
@Service
public class UserServiceImpl implements UserService{

    //注入Dao实现类依赖
    //    @Resource
    @Autowired
    private UserDao userDao;

    //也可以注入别的dao,例如对应post_tab表的PostDao，这样就可以根据需求整合多表的增删改查

//    @Resource
//    @Autowired
//    private UserDao userDao;

    /**注册会用到
     * 通过 phone和password 来添加 User
     * @param phone
     * @param password
     * @return 插入的行数
     */
    public int addUserByPhonePsw(String phone, String password) {
        return userDao.addUserByPhonePsw(phone,password);
    }

    /**
     * 通过phone删除User
     * @param phone
     * @return 删除成功返回1，失败返回0
     */
    public int deleteUserByPhone(String phone) {
        return userDao.deleteUserByPhone(phone);
    }

    /**
     * 通过phone和password更新User密码，新密码不能和旧密码一样
     * @param phone
     * @param password
     * @return 更新成功返回1，失败返回0
     */
    public int updatePswByPhonePsw(String phone, String password) {
        return userDao.updatePswByPhonePsw(phone,password);
    }

    /**
     * 通过phone查询User信息
     * @param phone
     * @return 查找成功返回User，没有则null
     */
    public User findUserByPhone(String phone) {
        return userDao.findUserByPhone(phone);
    }

    /**
     * 查找所有User，并按年龄降序排序好
     * @return
     */
    public List<User> findAllUserDESC() {
        return userDao.findAllUserDESC();
    }
}
