package com.moviedateserver.dao;

import com.moviedateserver.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by wunaifu on 2017/8/8.
 * 定义一些通过什么参数进行增删改查的方法，只需要定义，实现在对应的*Dao.xml里实现，这就是mybatis做的工作
 */
public interface UserDao {//添加UserDao的test时，选中类名UserDao，右键go to->test-创建->选择Junit4,选择添加的测试方法，finish

    /**登录
     * 通过 phone和password 来登录 User
     * @param phone
     * @param password
     */
    User loginByPhonePsw(@Param("phone") String phone,@Param("password") String password);


    /**注册会用到
     * 通过 phone和password 来添加 User
     * @param phone
     * @param password
     * @return 插入的行数
     */
    int addUserByPhonePsw(@Param("phone") String phone,@Param("password") String password,@Param("name")String name,@Param("gender")int gender);

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

    /*
    *通过id修改手机号码
    * @param phone
    * @param id
    * */
    int updatePhoneById(@Param("phone")String phone,@Param("id")int id);

    /**
     * 通过phone检验旧密码是否正确
     * @param phone
     * @param password
     * @return 正确返回user，失败返回null
     */
    User checkPassword(@Param("phone") String phone, @Param("password")String password);

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

    /*
    * 完善资料时，输入全部信息
    * */
    int updateUser(@Param("phone")String phone,@Param("name") String name,@Param("nickname") String nickname, @Param("gender")int gender, @Param("age")int age,
                   @Param("habit")String habit, @Param("birthday")String birthday, @Param("job")String job,
                   @Param("address")String address, @Param("weight")String weight, @Param("height")String height,
                   @Param("xingZuo")String xingZuo, @Param("signature")String signature);

}
