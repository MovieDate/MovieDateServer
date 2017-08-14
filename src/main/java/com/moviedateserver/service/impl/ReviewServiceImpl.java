package com.moviedateserver.service.impl;

import com.moviedateserver.dao.ReviewDao;
import com.moviedateserver.entity.Review;
import com.moviedateserver.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewDao reviewDao;

    public int addUserReviewByPostId(int postId, int postPersonId, String reviewDetails, String reviewTime) {
        return reviewDao.addUserReviewByPostId(postId,postPersonId,reviewDetails,reviewTime);
    }

    public int updateReviewByPostId(int postId, int postPersonId, String reviewDetails) {
        return reviewDao.updateReviewByPostId(postId,postPersonId,reviewDetails);
    }

    public int deleteReviewByReviewDetails(String reviewDetails) {
        return reviewDao.deleteReviewByReviewDetails(reviewDetails);
    }

    public List<Review> findReviewByReviewDetails(String reviewDetails) {
        return reviewDao.findReviewByReviewDetails(reviewDetails);
    }
}
