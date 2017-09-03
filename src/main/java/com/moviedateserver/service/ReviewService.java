package com.moviedateserver.service;

import com.moviedateserver.entity.Review;

import java.util.List;

public interface ReviewService {
    /*通过 postId 来添加*/
    int addUserReviewByPostId(int postId,int postPersonId,String reviewDetails,String reviewTime);

    /* 通过 postId ,postPersonId 来添加评论 */
    int updateReviewByPostId(int postId,int postPersonId,String reviewDetails);

    /*通过 reviewDetails删除评论*/
    int deleteReviewByReviewDetails(String reviewDetails,int postPersonId);

    /*通过reviewDetails 查询信息 */
    List<Review> findReviewByReviewDetails(String reviewDetails);

    /*通过帖子id搜索该帖子的所有评论*/
    List<Review> findReviewByPostId(int postId);
}
