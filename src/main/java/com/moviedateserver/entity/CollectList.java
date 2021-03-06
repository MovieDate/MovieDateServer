package com.moviedateserver.entity;

public class CollectList {
    private int postId;
    private int postPersonId;
    /*private String site;
    private String movieName;
    private String details;
    private String name;
    private String nickname;*/
    private String collectTime;
    private String postTime;       //发帖时间
    private String site;        //地点
    private String movieName;     //约影的影片名字
    private String movieTime;     //约影时间
    private int sex;         //约影对象性别（0表示男，1表示女，2表示不限制）
    private String endTime;      //完成约影时间（不显示，在约影完成按键直接获取存储）
    private int movieType;     //约影类型（0为2人单独约影。1为多人或团体约影，PP端获取约影类型时，获取约影人表，设置好报名人数，如果超过报名人数，约影人可挑选）
    private String details;       //发帖内容
    private String name;
    private String nickname;
    private int gender;

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getMovieTime() {
        return movieTime;
    }

    public void setMovieTime(String movieTime) {
        this.movieTime = movieTime;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getMovieType() {
        return movieType;
    }

    public void setMovieType(int movieType) {
        this.movieType = movieType;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }



    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getPostPersonId() {
        return postPersonId;
    }

    public void setPostPersonId(int postPersonId) {
        this.postPersonId = postPersonId;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(String collectTime) {
        this.collectTime = collectTime;
    }

    @Override
    public String toString() {
        return "CollectList{" +
                "postId=" + postId +
                ", postPersonId=" + postPersonId +
                ", collectTime='" + collectTime + '\'' +
                ", postTime='" + postTime + '\'' +
                ", site='" + site + '\'' +
                ", movieName='" + movieName + '\'' +
                ", movieTime='" + movieTime + '\'' +
                ", sex=" + sex +
                ", endTime='" + endTime + '\'' +
                ", movieType=" + movieType +
                ", details='" + details + '\'' +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", gender=" + gender +
                '}';
    }
}
