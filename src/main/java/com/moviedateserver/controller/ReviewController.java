package com.moviedateserver.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moviedateserver.entity.Review;
import com.moviedateserver.entity.ReviewList;
import com.moviedateserver.entity.User;
import com.moviedateserver.service.ReviewService;
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
@RequestMapping("/reviewController")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private UserService userService;


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

        /*List<Review> reviewList=reviewService.findReviewByReviewDetails(reviewDetails);

        if (reviewList == null || reviewList.size() == 0) {*/

            int addFlag = reviewService.addUserReviewByPostId(postId,postPersonId,reviewDetails,reviewTime);
            if (addFlag == 1) {
                System.out.println("添加成功");

                out.print("add_success");
            }
        /*}*/ else {
            //更新
            /*updateReviewByPostId(request,response);*/
            out.print("add_failed");

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

   /* @RequestMapping(value = "/updateReviewByPostId")
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
    }*/


    /** 删除評論
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

        String spostId= request.getParameter("postId");
        int postId =Integer.parseInt(spostId);
        String spostPersonId =request.getParameter("postPersonId");
        int postPersonId =Integer.parseInt(spostPersonId);
        String reviewDetails=request.getParameter("reviewDetails");
        String reviewTime = request.getParameter("reviewTime");

        System.out.println("postId"+postId+"postPersonId="+spostPersonId+" reviewDetails="+reviewDetails+"reviewTime="+reviewTime);

      /*  Review review=reviewService.findReviewidByReviewall(postId,postPersonId,reviewDetails,reviewTime);

        out.print("review="+review);*/
        /*if (review != null) {*/

            int delFlag = reviewService.deleteReviewByReviewDetails(postId,postPersonId,reviewDetails,reviewTime);
            if (delFlag == 1) {
                System.out.println("刪除成功");
                System.out.println("刪除成功");
                out.print("del_success");
            }
        /*} */else {
            //获取到数据为空时，向APP传输没有找到数据的信号
            out.print("nodata"+delFlag);

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
    @RequestMapping(value = "/findReviewidByReviewall")
    public String findReviewidByReviewall(HttpServletRequest request,HttpServletResponse response)throws IOException{

        PrintWriter out=null;
        out = response.getWriter();

        String spostId= request.getParameter("postId");
        int postId =Integer.parseInt(spostId);
        String spostPersonId =request.getParameter("postPersonId");
        int postPersonId =Integer.parseInt(spostPersonId);
        String reviewDetails=request.getParameter("reviewDetails");
        String reviewTime = request.getParameter("reviewTime");

        Review review=reviewService.findReviewidByReviewall(postId,postPersonId,reviewDetails,reviewTime);
        if (review != null) {
            JSONObject jsonObject = new JSONObject();
            String reviewJson = jsonObject.toJSONString(review);
            System.out.println("reviewJson====" + reviewJson);

            //获取到的数据传过去APP端
            out.print(reviewJson);
        } else {
            //获取到数据为空时，向APP传输没有找到数据的信号
            out.print("nodata");
        }
        out.flush();
        out.close();
        return null;
    }

    @RequestMapping(value = "/findReviewByPostId")
    public String findReviewByPostId(HttpServletResponse response,HttpServletRequest request)throws IOException{
        PrintWriter out=null;
        out = response.getWriter();

        List<ReviewList> reviewListList=new ArrayList<ReviewList>();
        String spostId= request.getParameter("postId");
        int postId =Integer.parseInt(spostId);

        JSONArray jsonArray = new JSONArray();
        List<Review> reviewList=reviewService.findReviewByPostId(postId);
        if(reviewList!=null&&reviewList.size()>0){
            List<ReviewList> reviewListS=new ArrayList<ReviewList>();
            for (Review review:reviewList){
                User user=userService.findUserById(review.getPostPersonId());
                if (user!=null){
                    ReviewList reviewList1=new ReviewList();
                    reviewList1.setName(user.getName());
                    reviewList1.setPostPersonId(review.getPostPersonId());
                    reviewList1.setReviewDetails(review.getReviewDetails());
                    reviewList1.setReviewTime(review.getReviewTime());

                    reviewListS.add(reviewList1);
                }
            }
            if (reviewListS!=null&&reviewListS.size()>0){
                for (int i=reviewListS.size()-1;i>=0;i--){
                    reviewListList.add(reviewListS.get(i));
                    System.out.println("reviewListList====" + reviewListList);
                    JSONObject jsonObject=(JSONObject) JSON.toJSON(reviewListS.get(i));
                    jsonArray.add(jsonObject);
                }
            }
            //获取到的数据传过去APP端
            out.print(jsonArray.toString());
            //out.print(reviewList.size());
        } else {
            //获取到数据为空时，向APP传输没有找到数据的信号
            out.print("nodata");
        }
        out.flush();
        out.close();
        return null;
    }

    @RequestMapping(value = "/findReviewCountByPostId")
    public String findReviewCountByPostId(HttpServletResponse response,HttpServletRequest request)throws IOException{
        PrintWriter out=null;
        out = response.getWriter();

        List<ReviewList> reviewListList=new ArrayList<ReviewList>();
        String spostId= request.getParameter("postId");
        int postId =Integer.parseInt(spostId);

        JSONArray jsonArray = new JSONArray();
        List<Review> reviewList=reviewService.findReviewByPostId(postId);

        out.print(reviewList.size());

        out.flush();
        out.close();
        return null;

    }



}
