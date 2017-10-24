package com.moviedateserver.dao;

import com.moviedateserver.entity.Review;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReviewDao {
    /*通过 postId 来添加*/
    int addUserReviewByPostId(@Param("postId")int postId, @Param("postPersonId")int postPersonId,
                              @Param("reviewDetails")String reviewDetails, @Param("reviewTime")String reviewTime);

   /* 通过 postId ,postPersonId 来更新评论 */
   int updateReviewByPostId(@Param("postId")int postId, @Param("postPersonId")int postPersonId,
                            @Param("reviewDetails")String reviewDetails);

   /*通过 reviewDetails删除评论*/
   int deleteReviewByReviewDetails(@Param("postId")int postId,@Param("postPersonId")int postPersonId,
                                   @Param("reviewDetails")String reviewDetails,@Param("reviewTime")String reviewTime);

   /*通过reviewDetails 查询信息 */
   Review findReviewidByReviewall(@Param("postId")int postId, @Param("postPersonId")int postPersonId,
                                        @Param("reviewDetails")String reviewDetails, @Param("reviewTime")String reviewTime);

   /*通过帖子id搜索该帖子的所有评论*/
   List<Review> findReviewByPostId(int postId);

}
