package com.moviedateserver.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moviedateserver.entity.Collect;
import com.moviedateserver.entity.CollectList;
import com.moviedateserver.entity.Post;
import com.moviedateserver.entity.User;
import com.moviedateserver.service.CollectService;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/collectController")
public class CollectController {
    @Autowired
    private CollectService collectService;
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    /**
     * 收藏帖子（点击收藏按钮，添加收藏和取消收藏就这一个接口就行）
     * 是否收藏？
     * 1、collectList==null,未收藏，添加收藏记录
     * 2、collectList！=null,已收藏，取消收藏（删除收藏记录）
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/addCollectByCollecterId")
    public String addCollectByCollecterId(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        PrintWriter out=null;
        out = response.getWriter();

        String spostId= request.getParameter("postId");
        int postId =Integer.parseInt(spostId);
        String scollecterId =request.getParameter("collecterId");
        int collecterId =Integer.parseInt(scollecterId);
//        String collectTime =request.getParameter("collectTime");
        //如果需要的时间是当前系统的时间（例如收藏时的时间），时间不用从APP那边获取，都是后台这边统一处理，
        // 这样时间才不会因为网络延迟或者每个客户端的系统时间不同而出现时间计算标准不一样
        String collectTime = TimeUtil.dateToString(new Date());

        System.out.println("postId="+spostId+" collecterId="+scollecterId+" collectTime="+collectTime);

        Collect collect=collectService.findCollectByCollectPostId(postId,collecterId);

        //是否收藏？ collect==null,未收藏，添加收藏记录

        if (collect == null)
        {
            int addFlag = collectService.addCollectByCollecterId(postId, collecterId, collectTime);
            if (addFlag == 1) {
                System.out.println("添加成功");

                out.print("add_success");
            }
        } else {

            out.print("hasadd");
        }

        out.flush();
        out.close();
        return null;

    }

    /**
     * 查找我的收藏帖子
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/findCollectByCollecterId")
    public String findCollectByCollecterId(HttpServletRequest request,HttpServletResponse response)throws IOException{

        PrintWriter out=null;
        out = response.getWriter();

        String scollecterId =request.getParameter("collecterId");
        int collecterId =Integer.parseInt(scollecterId);


        List<Collect> collectList=collectService.findCollectByCollecterId(collecterId);
        out =response.getWriter();
        if (collectList != null && collectList.size() > 0) {
            JSONObject jsonObject = new JSONObject();
            String userJson = jsonObject.toJSONString(collectList);

            System.out.println("collect====" + collectList);
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

    /**
     * 查找我的收藏帖子
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/findCollectByCollecterId2")
    public String findCollectByCollecterId2(HttpServletRequest request,HttpServletResponse response)throws IOException{
        PrintWriter out=null;
        out = response.getWriter();

        //收藏列表页面要显示的数据
        List<CollectList> collectlistList = new ArrayList<CollectList>();

        //前端传来的数据
        String scollecterId =request.getParameter("collecterId");
        int collecterId =Integer.parseInt(scollecterId);


        List<Collect> collectList=collectService.findCollectByCollecterId(collecterId);
        JSONArray jsonArray = new JSONArray();
        if (collectList != null && collectList.size() > 0) {

            //收藏列表页面要显示的数据
            List<CollectList> collectListS = new ArrayList<CollectList>();
            //依次查询帖子
            for (Collect collect:collectList){
                Post post= postService.findPostByid(collect.getPostId());
                if (post!=null){
                    CollectList collectlist = new CollectList();
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MM-dd HH:mm");
                    String date=simpleDateFormat.format(post.getPostTime());
                    String collectDate=simpleDateFormat.format(collect.getCollectTime());
                    //给collectlist类赋值
                    collectlist.setPostId(post.getId());
                    collectlist.setPostPersonId(post.getPostPersonId());
                    collectlist.setCollectTime(collectDate);
                    collectlist.setPostTime(date);
                    collectlist.setSite(post.getSite());
                    collectlist.setMovieName(post.getMovieName());
                    collectlist.setMovieTime(post.getMovieTime());
                    collectlist.setSex(post.getSex());
                    collectlist.setEndTime(post.getEndTime());
                    collectlist.setMovieType(post.getMovieType());
                    collectlist.setDetails(post.getDetails());


                    User user=userService.findUserById(post.getPostPersonId());
                    if (user!=null){
                        collectlist.setName(user.getName());
                        collectlist.setNickname(user.getNickname());
                    }
                    //赋值给collectlist类暂存
                    collectListS.add(collectlist);
                }
            }
            if (collectListS!=null&&collectListS.size()>0){
                for (int i=collectListS.size()-1;i>=0;i--){
                    collectlistList.add(collectListS.get(i));
                    System.out.println("collectlistList====" + collectlistList);
                    //json数组转换方法
                    JSONObject jsonObj = (JSONObject) JSON.toJSON(collectListS.get(i));
                    jsonArray.add(jsonObj);
                }
                //获取到的数据传过去APP端
                out.print(jsonArray.toString());
            }else {
                out.print("null");
            }



        } else {
            //获取到数据为空时，向APP传输没有找到数据的信号
            out.print("nodata");
        }

        out.flush();
        out.close();
        return null;

    }

    /**
     * 验证是否已经收藏帖子
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/findCollectByCollectPostId")
    public String findCollectByCollectPostId(HttpServletRequest request,HttpServletResponse response)throws IOException{
        PrintWriter out=null;
        out = response.getWriter();

        String spostId =request.getParameter("postId");
        int postId =Integer.parseInt(spostId);
        String scollecterId =request.getParameter("collecterId");
        int collecterId =Integer.parseInt(scollecterId);

        Collect collect = new Collect();
        collect = collectService.findCollectByCollectPostId(postId,collecterId);

        if (collect != null) {
            //将User转换成json数据
            JSONObject jsonObject = new JSONObject();
            String collectJson = jsonObject.toJSONString(collect);

            System.out.println("collect====" + collect);
            System.out.println("collectJson====" + collectJson);

            //获取到的数据传过去APP端
            out.print("collected");
        } else {
            //获取到数据为空时，向APP传输没有找到数据的信号
            out.print("nodata");
        }


        out.flush();
        out.close();
        return null;
    }

    /**
     * 取消收藏帖子
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/deleteCollect")
    public String deleteCollect(HttpServletRequest request,HttpServletResponse response)throws IOException{
        PrintWriter out=null;
        out = response.getWriter();

        String spostId =request.getParameter("postId");
        int postId =Integer.parseInt(spostId);
        String scollecterId =request.getParameter("collecterId");
        int collecterId =Integer.parseInt(scollecterId);

        Collect collect = new Collect();
        collect = collectService.findCollectByCollectPostId(postId,collecterId);
        if (collect !=null) {
            int collectFlag = collectService.deleteCollect(postId, collecterId);
            if (collectFlag == 1) {
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
