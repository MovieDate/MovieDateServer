package com.moviedateserver.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moviedateserver.entity.*;
import com.moviedateserver.service.PersonService;
import com.moviedateserver.service.PostService;
import com.moviedateserver.service.UserService;
import com.moviedateserver.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/personController")
public class PersonController {

    @Autowired
    private PersonService personService;
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

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
        String spostId= request.getParameter("postId");
        int postId =Integer.parseInt(spostId);
        System.out.println("postId="+postId);
        String sstartPersonId =request.getParameter("startPersonId");
        int startPersonId =Integer.parseInt(sstartPersonId);
        System.out.println("postId="+spostId+" startPersonId="+sstartPersonId);
        String sbyPersonId= request.getParameter("byPersonId");
        int byPersonId =Integer.parseInt(sbyPersonId);
        System.out.println("postId="+spostId+" startPersonId="+sstartPersonId+"byPersonId="+sbyPersonId+ "addTime=");
        //如果需要的时间是当前系统的时间，时间不用从APP那边获取，都是后台这边统一处理，
        // 这样时间才不会因为网络延迟或者每个客户端的系统时间不同而出现时间计算标准不一样
        String personTime = TimeUtil.dateToString(new Date());



        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Person> personList=personService.findPersonByPostId(postId);
        if (personList == null || personList.size() == 0) {

            int addFlag = personService.addPersonByPostId(postId, startPersonId, byPersonId,personTime);
            if (addFlag == 1) {
                System.out.println("添加成功");
                out.print("add_success");
            }
        } else if (personList.size()>=1){
            Post post=postService.findPostByid(postId);
            if (post.getMovieType()==1){
                List<Person> personList1=personService.findPersobByPostpersonId(postId,byPersonId);
                if (personList1==null){
                    int addFlag = personService.addPersonByPostId(postId, startPersonId, byPersonId,personTime);
                    if (addFlag == 1) {
                        System.out.println("添加成功");
                        out.print("add_success");
                    }
                }else {
                    out.print("hasjoined");
                }

            }else {
                 out.print("ending");
            }
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

        List<PersonList> personListList=new ArrayList<PersonList>();
        String spostId= request.getParameter("postId");
        int postId =Integer.parseInt(spostId);

        JSONArray jsonArray = new JSONArray();

        List<Person> personList=personService.findPersonByPostId(postId);
        out =response.getWriter();
        if (personList != null && personList.size() > 0) {

            List<PersonList> personListS=new ArrayList<PersonList>();
            for (Person person:personList){
                User user=userService.findUserById(person.getByPersonId());
                if (user!=null){
                    PersonList personList1=new PersonList();
                    personList1.setByPersonId(person.getByPersonId());
                    personList1.setName(user.getName());
                    personListS.add(personList1);
                }
            }
            if (personListS!=null&&personListS.size()>0){
                for (int i=personListS.size()-1;i>=0;i--){
                    personListList.add(personListS.get(i));
                    System.out.println("friendListList====" + personListList);
                    JSONObject jsonObject=(JSONObject) JSON.toJSON(personListS.get(i));
                    jsonArray.add(jsonObject);
                }

            }

            //获取到的数据传过去APP端
            out.print(jsonArray.toString());
        } else {
            //获取到数据为空时，向APP传输没有找到数据的信号
            out.print("nodata");
        }

        out.flush();
        out.close();
        return null;

    }

    /*
    * 验证是否已经报名
    * */
    @RequestMapping(value = "/findPersobByPostpersonId")
    public String findPersobByPostpersonId(HttpServletRequest request,HttpServletResponse response)throws IOException{
        PrintWriter out=null;
        out = response.getWriter();
        String spostId= request.getParameter("postId");
        int postId =Integer.parseInt(spostId);

        String sbyPersonId= request.getParameter("byPersonId");
        int byPersonId =Integer.parseInt(sbyPersonId);

        System.out.println("postId="+postId+"byPersonId=="+byPersonId);
        List<Person> personList1=personService.findPersobByPostpersonId(postId,byPersonId);
        if (personList1.size()!=0){
            out.print("hasbyin");
        }else {
            out.print("nobyin");
        }
        out.flush();
        out.close();
        return null;
    }

        /**
         * 查找已添加约影人人数
         *
         */
    @RequestMapping(value = "/findPersonCountByPostId")
    public String findPersonCountByPostId(HttpServletRequest request,HttpServletResponse response)throws IOException{

        PrintWriter out=null;
        out = response.getWriter();

        List<PersonList> personListList=new ArrayList<PersonList>();
        String spostId= request.getParameter("postId");
        int postId =Integer.parseInt(spostId);

        JSONArray jsonArray = new JSONArray();

        List<Person> personList=personService.findPersonByPostId(postId);
        out =response.getWriter();
        if (personList != null && personList.size() > 0) {
            out.print(personList.size());
        } else {
            //获取到数据为空时，向APP传输没有找到数据的信号
            out.print(personList.size());
        }

        out.flush();
        out.close();
        return null;

    }

    /*
    * 取消约影
    *
    * */
    @RequestMapping(value = "/deletePersonByPostpersonId")
    public String deletePersonByPostpersonId(HttpServletRequest request,HttpServletResponse response)throws IOException{
        PrintWriter out=null;
        out = response.getWriter();

        String spostId =request.getParameter("postId");
        int postId =Integer.parseInt(spostId);
        String sbyPersonId =request.getParameter("byPersonId");
        int byPersonId =Integer.parseInt(sbyPersonId);

        List<Person> personList1=personService.findPersobByPostpersonId(postId,byPersonId);
        if (personList1 != null || personList1.size() > 0) {
            int deleteFlag = personService.deletePersonByPostpersonId(postId, byPersonId);
            if (deleteFlag == 1) {
                //获取到的数据传过去APP端
                out.print("delete_success");
            } else {
                //获取到数据为空时，向APP传输没有找到数据的信号
                out.print("nodata");
            }
        }else {
            out.print("error");
        }


        out.flush();
        out.close();
        return null;
    }

}
