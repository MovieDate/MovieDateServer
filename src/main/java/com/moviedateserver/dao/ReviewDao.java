package com.moviedateserver.dao;

import com.moviedateserver.entity.Review;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReviewDao {
    /*通过 postId 来添加*/
    int addUserReviewByPostId(@Param("postId")int postId, @Param("postPersonId")int postPersonId,
                              @Param("reviewDetails")String reviewDetails, @Param("reviewTime")String reviewTime);

   /* 通过 postId ,postPersonId 来添加评论 */
   int updateReviewByPostId(@Param("postId")int postId, @Param("postPersonId")int postPersonId,
                            @Param("reviewDetails")String reviewDetails);

   /*通过 reviewDetails删除评论*/
   int deleteReviewByReviewDetails(String reviewDetails);

   /*通过reviewDetails 查询信息 */
   List<Review> findReviewByReviewDetails(String reviewDetails);

}
