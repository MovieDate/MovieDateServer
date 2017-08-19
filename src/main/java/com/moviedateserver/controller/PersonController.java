package com.moviedateserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.moviedateserver.entity.Friend;
import com.moviedateserver.entity.Person;
import com.moviedateserver.service.PersonService;
import com.moviedateserver.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/personController")
public class PersonController {

    @Autowired
    private PersonService personService;

    /**
     * 添加约影人信息
     * 是否添加？
     * 1、persomList==null,未添加
     * 2、persomList！=null,已添加
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "addPersonByPostId")
    public String addPersonByPostId(HttpServletRequest request, HttpServletResponse response)
            throws IOException{


        PrintWriter out =null;
        out = response.getWriter();

        String spostId= request.getParameter("postId");
        int postId =Integer.parseInt(spostId);
        String sstartPersonId =request.getParameter("startPersonId");
        int startPersonId =Integer.parseInt(sstartPersonId);;
        String sbyPersonId= request.getParameter("byPersonId");
        int byPersonId =Integer.parseInt(sbyPersonId);
        //如果需要的时间是当前系统的时间，时间不用从APP那边获取，都是后台这边统一处理，
        // 这样时间才不会因为网络延迟或者每个客户端的系统时间不同而出现时间计算标准不一样
        String personTime = TimeUtil.dateToString(new Date());

        System.out.println("postId="+spostId+" startPersonId="+sstartPersonId+"byPersonId="+sbyPersonId+ "addTime="+personTime);

        List<Person> personList=personService.findPersonByPostId(postId);

        //是否收藏？ collectList==null,未收藏，添加收藏记录  collectList！=null,已收藏，取消收藏（删除收藏记录）

        if (personList == null || personList.size() == 0) {

            int addFlag = personService.addPersonByPostId(postId, startPersonId, byPersonId,personTime);
            if (addFlag == 1) {
                System.out.println("添加成功");

                out.print("add_success");
            }
        } else {

        }

        out.flush();
        out.close();

        return  null;
    }

    /**
     * 查找已添加约影人信息
     * 1、friendList==null,未添加
     * 2、friendList！=null,已添加，展示查找内容
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/findPersonByPostId")
    public String findPersonByPostId(HttpServletRequest request,HttpServletResponse response)throws IOException{

        PrintWriter out=null;
        out = response.getWriter();

        String spostId= request.getParameter("postId");
        int postId =Integer.parseInt(spostId);


        List<Person> personList=personService.findPersonByPostId(postId);
        out =response.getWriter();
        if (personList != null && personList.size() > 0) {
            JSONObject jsonObject = new JSONObject();
            String userJson = jsonObject.toJSONString(personList);

            System.out.println("person====" + personList);
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
