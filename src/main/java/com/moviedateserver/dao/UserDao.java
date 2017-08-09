package com.moviedateserver.dao;

import com.moviedateserver.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by wunaifu on 2017/8/8.
 * 定义一些通过什么参数进行增删改查的方法，只需要定义，实现在对应的*Dao.xml里实现，这就是mybatis做的工作
 */
public interface UserDao {//添加UserDao的test时，选中类名UserDao，右键go to->test-创建->选择Junit4,选择添加的测试方法，finish

    /**注册会用到
     * 通过 phone和password 来添加 User
     * @param phone
     * @param password
     * @return 插入的行数
     */
    int addUserByPhonePsw(@Param("phone") String phone,@Param("password") String password);

    /**
     * 通过phone删除User
     * @param phone
     * @return 删除成功返回1，失败返回0
     */
    int deleteUserByPhone(String phone);

    /**
     * 通过phone和password更新User密码，新密码不能和旧密码一样
     * @param phone
     * @param password
     * @return 更新成功返回1，失败返回0
     */
    int updatePswByPhonePsw(@Param("phone") String phone, @Param("password")String password);

    /**
     * 通过phone查询User信息
     * @param phone
     * @return 查找成功返回User，没有则null
     */
    User findUserByPhone(String phone);

    /**
     * 查找所有User，并按年龄降序排序好
     * @return
     */
    List<User> findAllUserDESC();

}
