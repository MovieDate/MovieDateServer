package com.moviedateserver.controller;

import com.moviedateserver.entity.Collect;
import com.moviedateserver.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
@RequestMapping("/collectController")
public class CollectController {
    @Autowired
    private CollectService collectService;


    @RequestMapping(value = "/addCollectByCollecterId")
    public String addCollectByCollecterId(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter out=null;
        out = response.getWriter();

        String spostId= request.getParameter("postId");
        int postId =Integer.getInteger(spostId);
        String scollecterId =request.getParameter("collecterId");
        int collecterId =Integer.getInteger(scollecterId);
        String collectTime =request.getParameter("collectTime");

        List<Collect> collectList=collectService.findCollectByCollecterId(postId);
        if (collectList != null && collectList.size() > 0) {

            int addFlag=collectService.addCollectByCollecterId(postId,collecterId,collectTime);
            if (addFlag==1)
            {
                System.out.println("添加成功");

                out.print("add_success");
            }
        }

        out.flush();
        out.close();
        return null;

    }



}
