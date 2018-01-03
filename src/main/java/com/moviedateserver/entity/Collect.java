package com.moviedateserver.entity;

import java.util.Date;

public class Collect {
    private int id;      //自动编号,自增长
    private int postId;  //帖子id
    private int collecterId;   //收藏人id
    private Date collectTime;//收藏时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getCollecterId() {
        return collecterId;
    }

    public void setCollecterId(int collecterId) {
        this.collecterId = collecterId;
    }

    public Date getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(Date collectTime) {
        this.collectTime = collectTime;
    }

    @Override
    public String toString() {
        return "Collect{" +
                "id=" + id +
                ", postId=" + postId +
                ", collecterId=" + collecterId +
                ", collectTime='" + collectTime + '\'' +
                '}';
    }
}
