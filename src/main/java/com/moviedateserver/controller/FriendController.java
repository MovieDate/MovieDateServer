package com.moviedateserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.moviedateserver.entity.Friend;
import com.moviedateserver.service.FriendService;
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
@RequestMapping("/friendtController")
public class FriendController {
    @Autowired
    private FriendService friendService;

    /**
     * 添加好友（点击添加按钮)
     * 是否添加？
     * 1、friendList==null,未添加，添加好友
     * 2、friendList！=null,已添加，删除好友
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/addDFriendByMyId")
    public String addFriendByMyId(HttpServletRequest request, HttpServletResponse response)
        throws IOException{
        PrintWriter out =null;
        out = response.getWriter();

        String smyId= request.getParameter("myId");
        int myId =Integer.parseInt(smyId);
        String sfriendId =request.getParameter("friendId");
        int friendId =Integer.parseInt(sfriendId);;
        //如果需要的时间是当前系统的时间，时间不用从APP那边获取，都是后台这边统一处理，
        // 这样时间才不会因为网络延迟或者每个客户端的系统时间不同而出现时间计算标准不一样
        String addTime = TimeUtil.dateToString(new Date());

        System.out.println("myId="+smyId+" friendId="+sfriendId+" addTime="+addTime);

        List<Friend> friendList=friendService.findFriendByMyId(myId);

        //是否收藏？ collectList==null,未收藏，添加收藏记录  collectList！=null,已收藏，取消收藏（删除收藏记录）

        if (friendList == null || friendList.size() == 0) {

            int addFlag = friendService.addFriendByMyId(myId, friendId, addTime);
            if (addFlag == 1) {
                System.out.println("添加成功");

                out.print("add_success");
            }
        } else {
             //获取到数据为空时，向APP传输没有找到数据的信号
            out.print("nodata");

        }

        out.flush();
        out.close();

        return  null;
    }

    /*
    * 删除已添加好友
    * 1.friendList！=null,已添加，可点击删除按钮
    * 2.friendList==null,未添加
    * @param request
    * @param response
    * @return
    * @throws IOException
    * */
    @RequestMapping(value = "/delFriendByFriendId")
    public  String delFriendByFriendId(HttpServletRequest request,HttpServletResponse response)throws IOException{

        PrintWriter out =null;
        out = response.getWriter();

        String smyId= request.getParameter("myId");
        int myId =Integer.parseInt(smyId);
        String sfriendId =request.getParameter("friendId");
        int friendId =Integer.parseInt(sfriendId);;

        System.out.println("myId="+smyId+" friendId="+sfriendId+" addTime=");

        List<Friend> friendList=friendService.findFriendByMyId(myId);

        //是否收藏？ collectList==null,未收藏，添加收藏记录  collectList！=null,已收藏，取消收藏（删除收藏记录）

        if (friendList != null && friendList.size() > 0) {

            int delFlag = friendService.delFriendByFriendId(friendId);
            if (delFlag == 1) {
                System.out.println("刪除成功");

                out.print("del_success");
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
     * 查找已添加好友
     * 1、friendList==null,未添加，查找失败
     * 2、friendList！=null,已添加，展示查找内容
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/findFriendByMyId")
    public String findFriendByMyId(HttpServletRequest request,HttpServletResponse response)throws IOException{

        PrintWriter out=null;
        out = response.getWriter();

        String smyId=request.getParameter("myId");
        int myId =Integer.parseInt(smyId);


        List<Friend> friendList=friendService.findFriendByMyId(myId);
        out =response.getWriter();
        if (friendList != null && friendList.size() > 0) {
            JSONObject jsonObject = new JSONObject();
            String userJson = jsonObject.toJSONString(friendList);

            System.out.println("friend====" + friendList);
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
