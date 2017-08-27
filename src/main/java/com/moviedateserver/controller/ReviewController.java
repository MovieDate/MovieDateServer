package com.moviedateserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.moviedateserver.entity.Review;
import com.moviedateserver.service.ReviewService;
import com.moviedateserver.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.View;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/reviewController")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    /**
     * 添加評論（点击添加按钮)
     * 是否添加？
     * 1、reviewList==null,沒評論
     * 2、reviewList！=null,已經評論，調用update
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/addUserReviewByPostId")
    public String addUserReviewByPostId(HttpServletRequest request, HttpServletResponse response)
            throws IOException{
        PrintWriter out =null;
        out = response.getWriter();

        String spostId= request.getParameter("postId");
        int postId =Integer.parseInt(spostId);
        String spostPersonId =request.getParameter("postPersonId");
        int postPersonId =Integer.parseInt(spostPersonId);
        String reviewDetails=request.getParameter("reviewDetails");
        //如果需要的时间是当前系统的时间，时间不用从APP那边获取，都是后台这边统一处理，
        // 这样时间才不会因为网络延迟或者每个客户端的系统时间不同而出现时间计算标准不一样
        String reviewTime = TimeUtil.dateToString(new Date());

        System.out.println("postId="+spostId+" postPersonId="+spostPersonId+"reviewDetails"+reviewDetails+" reviewTime="+reviewTime);

        List<Review> reviewList=reviewService.findReviewByReviewDetails(reviewDetails);

        if (reviewList == null || reviewList.size() == 0) {

            int addFlag = reviewService.addUserReviewByPostId(postId,postPersonId,reviewDetails,reviewTime);
            if (addFlag == 1) {
                System.out.println("添加成功");

                out.print("add_success");
            }
        } else {
            //更新
            updateReviewByPostId(request,response);

        }

        out.flush();
        out.close();

        return  null;
    }

    /*
    * 添加新的評論
    * 通過驗證postId和postPersonId來添加評論
    * 1、reviewList==null,沒評論
    * 2、reviewList！=null,已經評論，調用update
    * @param request
    * @param response
    * @return
    * @throws IOException
    * */

    @RequestMapping(value = "/updateReviewByPostId")
    public String updateReviewByPostId(HttpServletRequest request, HttpServletResponse response)
            throws IOException{

        PrintWriter out =null;
        out = response.getWriter();

        String spostId= request.getParameter("postId");
        int postId =Integer.parseInt(spostId);
        String spostPersonId =request.getParameter("postPersonId");
        int postPersonId =Integer.parseInt(spostPersonId);
        String reviewDetails=request.getParameter("reviewDetails");

        System.out.println("postId="+spostId+" postPersonId="+spostPersonId+"reviewDetails"+reviewDetails);
        List<Review> reviewList=reviewService.findReviewByReviewDetails(reviewDetails);

        if (reviewList != null && reviewList.size() > 0) {

            int upaFlag = reviewService.updateReviewByPostId(postId,postPersonId,reviewDetails);
            if (upaFlag == 1) {
                System.out.println("添加成功");

                out.print("add_success");
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
    * 删除評論
    * 1.reviewList！=null,已添加，可点击删除按钮
    * 2.reviewList==null,未添加
    * @param request
    * @param response
    * @return
    * @throws IOException
    * */
    @RequestMapping(value = "/deleteReviewByReviewDetails")
    public  String deleteReviewByReviewDetails(HttpServletRequest request,HttpServletResponse response)throws IOException{

        PrintWriter out =null;
        out = response.getWriter();

        String spostPersonId= request.getParameter("postPersonId");
        int postPersonId =Integer.parseInt(spostPersonId);
        String reviewDetails=request.getParameter("reviewDetails");

        System.out.println("postPersonId="+spostPersonId+" reviewDetails="+reviewDetails);

        List<Review> reviewList=reviewService.findReviewByReviewDetails(reviewDetails);


        if (reviewList != null && reviewList.size() > 0) {

            int delFlag = reviewService.deleteReviewByReviewDetails(reviewDetails,postPersonId);
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
     * 查找評論
     * 1、reviewList==null,查找失败
     * 2、reviewList！=null，展示查找内容
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/findFriendByMyId")
    public String findFriendByMyId(HttpServletRequest request,HttpServletResponse response)throws IOException{

        PrintWriter out=null;
        out = response.getWriter();

        String reviewDetails=request.getParameter("reviewDetails");

        List<Review> reviewList=reviewService.findReviewByReviewDetails(reviewDetails);
        out =response.getWriter();
        if (reviewList != null && reviewList.size() > 0) {
            JSONObject jsonObject = new JSONObject();
            String userJson = jsonObject.toJSONString(reviewList);

            System.out.println("review====" + reviewList);
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