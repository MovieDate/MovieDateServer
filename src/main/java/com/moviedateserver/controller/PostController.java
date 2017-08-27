package com.moviedateserver.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moviedateserver.entity.Post;
import com.moviedateserver.service.PostService;
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
@RequestMapping("/postController")
public class PostController {
    @Autowired
    private PostService postService;

    /*
    * 查找所有的post
    * */
    @RequestMapping(value = "/findAllPost")
    public String findAllPost(HttpServletRequest request, HttpServletResponse response)throws IOException {
        PrintWriter out =null;
        out = response.getWriter();
        List<Post> postList = postService.findAllPost();
        if (postList != null && postList.size() > 0) {
            //将List转换成json数据
            JSONArray jsonArray = new JSONArray();
            for (Post post : postList) {
                JSONObject jsonObj = (JSONObject) JSON.toJSON(post);
                jsonArray.add(jsonObj);
            }

            System.out.println("userList===="+postList);
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
     * 添加帖子/更新
     * 是否添加？
     * 1、post==null,发帖
     * 2.post!=null,更新帖子
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/addPostByPostPersonId")
    public String addPostByPostPersonId(HttpServletRequest request, HttpServletResponse response)throws IOException{

        PrintWriter out =null;
        out = response.getWriter();

        /*String sid=request.getParameter("id");
        int id=Integer.parseInt(sid);*/
        String spostPersonId= request.getParameter("postPersonId");
        int postPersonId =Integer.parseInt(spostPersonId);
        //發帖需要的时间是当前系统的时间，时间不用从APP那边获取，都是后台这边统一处理，
        // 这样时间才不会因为网络延迟或者每个客户端的系统时间不同而出现时间计算标准不一样
        String postTime = TimeUtil.dateToString(new Date());
        String site =request.getParameter("site");
        String movieName=request.getParameter("movieName");
        String movieTime=request.getParameter("movieTime");
        String ssex=request.getParameter("sex");
        int sex =Integer.parseInt(ssex);
        String smovieType=request.getParameter("movieType");
        int movieType =Integer.parseInt(smovieType);
        String details=request.getParameter("details");

        System.out.println(" postPersonId="+spostPersonId+"postTime"+postTime+
                " site="+site+" movieName="+movieName+" movieTime="+movieTime+" sex="+sex+" movieType="+movieType+" details="+details);

        /*Post post=postService.findPostByid(id);
        if (post==null){*/
            int addFlag = postService.addPostByPostPersonId(postPersonId,postTime,site,movieName,movieTime,sex,movieType,details);
            if (addFlag == 1) {
                System.out.println("添加成功");

                out.print("add_success");
            }
       /* }else {
            int upaFlag=postService.updatePostById(id,postPersonId,postTime,site,movieName,movieTime,sex,endTime,movieType,details);
            if(upaFlag==1){
                System.out.println("更新成功");
                out.print("update_success");
            }
        }*/

        out.flush();
        out.close();

        return null;
    }

    /**
     * 更新帖子
     * 是否添加？
     * 1、post==null
     * 2.post!=null,更新帖子
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/updatePostById")
    public String updatePostById(HttpServletRequest request, HttpServletResponse response)throws IOException{

        PrintWriter out =null;
        out = response.getWriter();

        String sid=request.getParameter("id");
        int id=Integer.parseInt(sid);
        String spostPersonId= request.getParameter("postPersonId");
        int postPersonId =Integer.parseInt(spostPersonId);
        //發帖需要的时间是当前系统的时间，时间不用从APP那边获取，都是后台这边统一处理，
        // 这样时间才不会因为网络延迟或者每个客户端的系统时间不同而出现时间计算标准不一样
        String postTime = TimeUtil.dateToString(new Date());
        String site =request.getParameter("site");
        String movieName=request.getParameter("movieName");
        String movieTime=request.getParameter("movieTime");
        String endTime=request.getParameter("endTime");
        String ssex=request.getParameter("sex");
        int sex =Integer.parseInt(ssex);
        String smovieType=request.getParameter("movieType");
        int movieType =Integer.parseInt(smovieType);
        String details=request.getParameter("details");

        System.out.println(" postPersonId="+spostPersonId+"postTime"+postTime+
                " site="+site+" movieName="+movieName+" movieTime="+movieTime+" endTime="
                +endTime+" sex="+sex+" movieType="+movieType+" details="+details);

        Post post=postService.findPostByid(id);
        if (post!=null){
            int upaFlag=postService.updatePostById(id,postPersonId,postTime,site,movieName,movieTime,sex,endTime,movieType,details);
            if(upaFlag==1){
                System.out.println("更新成功");
                out.print("update_success");
        }else {
                out.print("不存在该贴，无法更新");

            }
        }

        out.flush();
        out.close();

        return null;
    }


    /*
    * 删除帖子
    * 因为一个postpersonid可以有多个帖子，但是一次只要求删一个
    * 根據帖子id刪除
    * @param request
    * @param response
    * @return
    * @throws IOException
    * */
    @RequestMapping(value = "/deletePostByPostPersonId")
    public  String deletePostByPostPersonId(HttpServletRequest request,HttpServletResponse response)throws IOException{

        PrintWriter out =null;
        out = response.getWriter();

        String sid= request.getParameter("id");
        int id =Integer.parseInt(sid);

        System.out.println("id="+sid);

        int delFlag = postService.deletePostByPostPersonId(id);
            if (delFlag == 1) {
                System.out.println("刪除成功");
                out.print("del_success");
            }
         else {
            //获取到数据为空时，向APP传输没有找到数据的信号
            out.print("nodata");

        }
        out.flush();
        out.close();
        return null;
    }

    /**
     * 查找帖子
     * 电影名字，地点，内容
     * 1、postList==null,查找失败
     * 2、postList！=null，展示查找内容
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/findPostBymovieName")
    public String findPostBymovieName(HttpServletRequest request,HttpServletResponse response)throws IOException{

        PrintWriter out=null;
        out = response.getWriter();

        String movieName=request.getParameter("movieName");

        List<Post> postList=postService.findPostBymovieName(movieName);
        out =response.getWriter();
        if (postList != null && postList.size() > 0) {
            JSONObject jsonObject = new JSONObject();
            String userJson = jsonObject.toJSONString(postList);

            System.out.println("review====" + postList);
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

    @RequestMapping(value = "/findPostBysite")
    public String findPostBysite(HttpServletRequest request,HttpServletResponse response)throws IOException{

        PrintWriter out=null;
        out = response.getWriter();

        String site=request.getParameter("site");

        List<Post> postList=postService.findPostBymovieName(site);
        out =response.getWriter();
        if (postList != null && postList.size() > 0) {
            JSONObject jsonObject = new JSONObject();
            String userJson = jsonObject.toJSONString(postList);

            System.out.println("review====" + postList);
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

    @RequestMapping(value = "/findPostBydetails")
    public String findPostBydetails(HttpServletRequest request,HttpServletResponse response)throws IOException{

        PrintWriter out=null;
        out = response.getWriter();

        String details=request.getParameter("details");

        List<Post> postList=postService.findPostBymovieName(details);
        out =response.getWriter();
        if (postList != null && postList.size() > 0) {
            JSONObject jsonObject = new JSONObject();
            String userJson = jsonObject.toJSONString(postList);

            System.out.println("review====" + postList);
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
