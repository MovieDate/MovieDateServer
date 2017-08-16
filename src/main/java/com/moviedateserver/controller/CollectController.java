package com.moviedateserver.controller;

import com.moviedateserver.entity.Collect;
import com.moviedateserver.service.CollectService;
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
@RequestMapping("/collectController")
public class CollectController {
    @Autowired
    private CollectService collectService;

    /**
     * 收藏帖子（点击收藏按钮，添加收藏和取消收藏就这一个接口就行）
     * 是否收藏？
     * 1、collectList==null,未收藏，添加收藏记录
     * 2、collectList！=null,已收藏，取消收藏（删除收藏记录）
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/addCollectByCollecterId")
    public String addCollectByCollecterId(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        PrintWriter out=null;
        out = response.getWriter();

        String spostId= request.getParameter("postId");
        int postId =Integer.parseInt(spostId);
        String scollecterId =request.getParameter("collecterId");
        int collecterId =Integer.parseInt(scollecterId);
//        String collectTime =request.getParameter("collectTime");
        //如果需要的时间是当前系统的时间（例如收藏时的时间），时间不用从APP那边获取，都是后台这边统一处理，
        // 这样时间才不会因为网络延迟或者每个客户端的系统时间不同而出现时间计算标准不一样
        String collectTime = TimeUtil.dateToString(new Date());

        System.out.println("postId="+spostId+" collecterId="+scollecterId+" collectTime="+collectTime);

        List<Collect> collectList=collectService.findCollectByCollecterId(postId);

        //是否收藏？ collectList==null,未收藏，添加收藏记录  collectList！=null,已收藏，取消收藏（删除收藏记录）

        if (collectList != null && collectList.size() > 0) {

            int addFlag = collectService.addCollectByCollecterId(postId, collecterId, collectTime);
            if (addFlag == 1) {
                System.out.println("添加成功");

                out.print("add_success");
            }
        } else {

        }

        out.flush();
        out.close();
        return null;

    }



}
