package com.moviedateserver.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moviedateserver.entity.User;
import com.moviedateserver.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by wunaifu on 2017/8/8.
 */
//@Component
    @Controller
@RequestMapping("/userController")
//tomcat配置好的项目地址是 + controller配好的RequestMapping + controller里面的接口方法配置好的RequestMapping
//    就是网络请求时的地址，即访问controller中某方法的网络地址URL
//    例如：http://localhost:8080/userController/allUser --》就访问了findAllUserDESC（）方法
public class UserController {

    //这里写的每个方法都要注释好是干什么的，有复杂的逻辑处理关系的也要注释好，便于别人读懂你的代码

    //注入Service实现类依赖，可注入多个Service依赖
    @Autowired
    private UserService userService;

//    @Autowired
//    private PostService postService;

    /**
     * 查找User表里的所有用户数据，并按年龄降序的方式排序好
     * 这个方法里没有请求的参数
     * @param response
     * @return
     * @throws IOException
     * method = RequestMethod.GET  get方式的请求，不写method则表示get和post方法请求都可以
     */
    @RequestMapping(value = "/allUser")
    public String findAllUserDESC(HttpServletResponse response) throws IOException {

        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取列表页
        List<User> userList = userService.findAllUserDESC();
        if (userList != null && userList.size() > 0) {
            //将List转换成json数据
            JSONArray jsonArray = new JSONArray();
            for (User user : userList) {
                JSONObject jsonObj = (JSONObject) JSON.toJSON(user);
                jsonArray.add(jsonObj);
            }

            System.out.println("userList===="+userList);
            System.out.println("jsonArry===="+jsonArray);
            //获取到数据不为空时，向APP传输UserList的json数据
            out.print(jsonArray.toString());

        }else {
            //获取到数据为空时，向APP传输没有找到数据的信号
            out.print("nodata");
        }

        out.flush();
        out.close();
        return null;//这里返回空就行
    }

    /**
     * 通过phone查找用户的信息（可用于查看用户信息功能的请求接口）
     * @param request
     * @param response
     * @return
     * @throws IOException
     * 这些参数就是APP那边请求的参数  HttpServletResponse是用来返回数据的，不是APP那边请求的参数
     */
    //@ResponseBody
    @RequestMapping(value = "/findUserByPhone")
    public String findUserByPhone(HttpServletRequest request, HttpServletResponse response)
            throws IOException{

//        response.setContentType("text/html;charset=utf-8");
//        request.setCharacterEncoding("UTF-8");

        //request.getParameter("phone")就是APP端传过来的请求参数
        String phone = request.getParameter("phone");

        User user = new User();
        user = userService.findUserByPhone(phone);
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (user != null) {
            //将User转换成json数据
            JSONObject jsonObject = new JSONObject();
            String userJson = jsonObject.toJSONString(user);

            System.out.println("user====" + user);
            System.out.println("userJson====" + userJson);

            //获取到的数据传过去APP端
            out.print(userJson);
        } else {
            //获取到数据为空时，向APP传输没有找到数据的信号
            out.print("nodata");
        }

        out.flush();
        out.close();
        return null;
    }

}
