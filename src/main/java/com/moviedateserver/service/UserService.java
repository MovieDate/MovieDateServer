package com.moviedateserver.service;

import com.moviedateserver.entity.User;

import java.util.Date;
import java.util.List;

/**
 * Created by wunaifu on 2017/8/8.
 * 定义方法，只需要定义，实现在对应的*ServiceImpl.java里实现，这就是spring做的工作
 */
public interface UserService {

    /**登录
     * 通过 phone和password 来登录 User
     * @param phone
     * @param password
     */
    int loginByPhonePsw(String phone,String password);

    /**
     * 注册会用到
     * 通过 phone和password 来添加 User
     *
     * @param phone
     * @param password
     * @return 插入的行数
     */
    int addUserByPhonePsw(String phone, String password,String name,int gender);

    /**
     * 通过phone删除User
     *
     * @param phone
     * @return 删除成功返回1，失败返回0
     */
    int deleteUserByPhone(String phone);

    /**
     * 通过phone和password更新User密码，新密码不能和旧密码一样
     *
     * @param phone
     * @param password
     * @return 更新成功返回1，失败返回0
     */
    int updatePswByPhonePsw(String phone, String password);

     /*
    *通过id修改手机号码
    * @param phone
    * @param id
    * */
     int updatePhoneById(String phone,int id);



    /**
     * 通过phone检验旧密码是否正确
     *
     * @param phone
     * @param oldPassword
     * @return 正确返回1，失败返回0
     */
    int checkPassword(String phone, String oldPassword);

    /**
     * 通过phone查询User信息
     *
     * @param phone
     * @return 查找成功返回User，没有则null
     */
    User findUserByPhone(String phone);
    User findUserById(int id);

    /**
     * 查找所有User，并按年龄降序排序好
     *
     * @return
     */
    List<User> findAllUserDESC();


    /**
     * 通过phone完善用户表
     */
    int updataUser(String phone,String name,String nickname, int gender, int age, String habit, String birthday, String job, String address, String weight, String height, String xingZuo, String signature);

    /*搜索好友*/
    List<User> findUser(String details);
}