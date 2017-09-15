package com.moviedateserver.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moviedateserver.entity.Friend;
import com.moviedateserver.entity.FriendList;
import com.moviedateserver.entity.User;
import com.moviedateserver.service.FriendService;
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
@RequestMapping("/friendtController")
public class FriendController {
    @Autowired
    private FriendService friendService;
    @Autowired
    private UserService userService;

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
    @RequestMapping(value = "/addFriendByMyId")
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

        Friend friend=friendService.findFriendByMyIdFriendId(myId,friendId);

        //是否收藏？ friend==null,未添加 friend！=null,已添加

        if (friend == null ) {

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
        List<FriendList> friendListList =new ArrayList<FriendList>();

        String smyId=request.getParameter("myId");
        int myId =Integer.parseInt(smyId);

        List<Friend> friendList=friendService.findFriendByMyId(myId);
        JSONArray jsonArray = new JSONArray();

        if (friendList != null && friendList.size() > 0) {

            List<FriendList> friendListS=new ArrayList<FriendList>();
            for (Friend friend:friendList){
                User user=userService.findUserById(friend.getFriendId());
                if (user!=null){
                    FriendList friendList1=new FriendList();
                    friendList1.setName(user.getName());
                    friendList1.setFriendId(friend.getFriendId());
                    friendList1.setFriendphone(user.getPhone());

                    friendListS.add(friendList1);
                }
            }
            if (friendListS!=null&&friendListS.size()>0){
                for (int i=friendListS.size()-1;i>=0;i--){
                    friendListList.add(friendListS.get(i));
                    System.out.println("friendListList====" + friendListList);
                    JSONObject jsonObject=(JSONObject) JSON.toJSON(friendListS.get(i));
                    jsonArray.add(jsonObject);
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

    /*
    * 验证好友是否已添加
    1、friendList==null,未添加
     * 2、friendList！=null,已添加
     * @param request
     * @param response
     * @return
     * @throws IOException
    * */
    @RequestMapping(value = "/findFriendByMyIdFriendId")
    public String findFriendByMyIdFriendId(HttpServletRequest request,HttpServletResponse response)throws IOException{
        PrintWriter out=null;
        out = response.getWriter();

        String smyId= request.getParameter("myId");
        int myId =Integer.parseInt(smyId);
        String sfriendId =request.getParameter("friendId");
        int friendId =Integer.parseInt(sfriendId);;

        System.out.println("myId="+smyId+" friendId="+sfriendId+" addTime=");

        Friend friend=new Friend();
        friend=friendService.findFriendByMyIdFriendId(myId,friendId);
        if (friend!=null){
            //将User转换成json数据
            JSONObject jsonObject = new JSONObject();
            String friendJson = jsonObject.toJSONString(friend);

            System.out.println("friend====" + friend);
            System.out.println("friendJson====" + friendJson);

            //获取到的数据传过去APP端
            out.print(friendJson);
        } else {
            //获取到数据为空时，向APP传输没有找到数据的信号
            out.print("nodata");
        }

        out.flush();
        out.close();
        return null;
    }


}
