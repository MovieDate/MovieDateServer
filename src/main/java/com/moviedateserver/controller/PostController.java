package com.moviedateserver.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moviedateserver.entity.Person;
import com.moviedateserver.entity.Post;
import com.moviedateserver.entity.PostList;
import com.moviedateserver.entity.User;
import com.moviedateserver.service.PersonService;
import com.moviedateserver.service.PostService;
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
@RequestMapping("/postController")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private PersonService personService;

    /**
     * 添加帖子/更新
     * 是否添加？
     * 1、post==null,发帖
     * 2.post!=null,更新帖子
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/addPostByPostPersonId")
    public String addPostByPostPersonId(HttpServletRequest request, HttpServletResponse response)throws IOException{

        PrintWriter out =null;
        out = response.getWriter();

        /*String sid=request.getParameter("id");
        int id=Integer.parseInt(sid);*/
        String spostPersonId= request.getParameter("postPersonId");
        int postPersonId =Integer.parseInt(spostPersonId);
        //發帖需要的时间是当前系统的时间，时间不用从APP那边获取，都是后台这边统一处理，
        // 这样时间才不会因为网络延迟或者每个客户端的系统时间不同而出现时间计算标准不一样
        String postTime = TimeUtil.dateToString(new Date());
        String site =request.getParameter("site");
        String movieName=request.getParameter("movieName");
        String movieTime=request.getParameter("movieTime");
        String ssex=request.getParameter("sex");
        int sex =Integer.parseInt(ssex);
        String smovieType=request.getParameter("movieType");
        int movieType =Integer.parseInt(smovieType);
        String details=request.getParameter("details");

        System.out.println(" postPersonId="+spostPersonId+"postTime"+postTime+
                " site="+site+" movieName="+movieName+" movieTime="+movieTime+" sex="+sex+" movieType="+movieType+" details="+details);

        /*Post post=postService.findPostByid(id);
        if (post==null){*/
            int addFlag = postService.addPostByPostPersonId(postPersonId,postTime,site,movieName,movieTime,sex,movieType,details);
            if (addFlag == 1) {
                System.out.println("添加成功");

                out.print("add_success");
            }
       /* }else {
            int upaFlag=postService.updatePostById(id,postPersonId,postTime,site,movieName,movieTime,sex,endTime,movieType,details);
            if(upaFlag==1){
                System.out.println("更新成功");
                out.print("update_success");
            }
        }*/

        out.flush();
        out.close();

        return null;
    }

    /**
     * 更新帖子
     * 是否添加？
     * 1、post==null
     * 2.post!=null,更新帖子
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/updatePostById")
    public String updatePostById(HttpServletRequest request, HttpServletResponse response)throws IOException{

        PrintWriter out =null;
        out = response.getWriter();

        String sid=request.getParameter("id");
        int id=Integer.parseInt(sid);
        String spostPersonId= request.getParameter("postPersonId");
        int postPersonId =Integer.parseInt(spostPersonId);
        //發帖需要的时间是当前系统的时间，时间不用从APP那边获取，都是后台这边统一处理，
        // 这样时间才不会因为网络延迟或者每个客户端的系统时间不同而出现时间计算标准不一样
        String postTime = TimeUtil.dateToString(new Date());
        String site =request.getParameter("site");
        String movieName=request.getParameter("movieName");
        String movieTime=request.getParameter("movieTime");
        String endTime=request.getParameter("endTime");
        String ssex=request.getParameter("sex");
        int sex =Integer.parseInt(ssex);
        String smovieType=request.getParameter("movieType");
        int movieType =Integer.parseInt(smovieType);
        String details=request.getParameter("details");

        System.out.println(" postPersonId="+spostPersonId+"postTime"+postTime+
                " site="+site+" movieName="+movieName+" movieTime="+movieTime+" endTime="
                +endTime+" sex="+sex+" movieType="+movieType+" details="+details);

        Post post=postService.findPostByid(id);
        if (post!=null){
            int upaFlag=postService.updatePostById(id,postPersonId,postTime,site,movieName,movieTime,sex,endTime,movieType,details);
            if(upaFlag==1){
                System.out.println("更新成功");
                out.print("update_success");
        }else {
                out.print("不存在该贴，无法更新");

            }
        }

        out.flush();
        out.close();

        return null;
    }


    /*
    * 删除帖子
    * 因为一个postpersonid可以有多个帖子，但是一次只要求删一个
    * 根據帖子id刪除
    * @param request
    * @param response
    * @return
    * @throws IOException
    * */
    @RequestMapping(value = "/deletePostByPostPersonId")
    public  String deletePostByPostPersonId(HttpServletRequest request,HttpServletResponse response)throws IOException{

        PrintWriter out =null;
        out = response.getWriter();

        String sid= request.getParameter("id");
        int id =Integer.parseInt(sid);

        System.out.println("id="+sid);

        int delFlag = postService.deletePostByPostPersonId(id);
            if (delFlag == 1) {
                System.out.println("刪除成功");
                out.print("del_success");
            }
         else {
            //获取到数据为空时，向APP传输没有找到数据的信号
            out.print("nodata");

        }
        out.flush();
        out.close();
        return null;
    }

    /**
     * 查找帖子
     * 电影名字，地点，内容
     * 1、postList==null,查找失败
     * 2、postList！=null，展示查找内容
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/findPostByid")
    public String findPostByid(HttpServletRequest request, HttpServletResponse response)
            throws IOException{


        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String sid = request.getParameter("id");
        int id=Integer.parseInt(sid);

        Post post = new Post();
        post = postService.findPostByid(id);
        if (post != null) {
            JSONObject jsonObject = new JSONObject();
            String postJson = jsonObject.toJSONString(post);

            System.out.println("post====" + post);
            System.out.println("postJson====" + postJson);

            //获取到的数据传过去APP端
            out.print(postJson);
        } else {
            //获取到数据为空时，向APP传输没有找到数据的信号
            out.print("nodata");
        }

        out.flush();
        out.close();
        return null;
    }



    @RequestMapping(value = "/findPostBymovieName")
    public String findPostBymovieName(HttpServletRequest request,HttpServletResponse response)throws IOException{

        PrintWriter out=null;
        out = response.getWriter();

        String movieName=request.getParameter("movieName");

        List<Post> postList=postService.findPostBymovieName(movieName);
        out =response.getWriter();
        if (postList != null && postList.size() > 0) {
            JSONObject jsonObject = new JSONObject();
            String userJson = jsonObject.toJSONString(postList);

            System.out.println("review====" + postList);
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

    @RequestMapping(value = "/findPostBysite")
    public String findPostBysite(HttpServletRequest request,HttpServletResponse response)throws IOException{

        PrintWriter out=null;
        out = response.getWriter();

        String site=request.getParameter("site");

        List<Post> postList=postService.findPostBysite(site);
        out =response.getWriter();
        if (postList != null && postList.size() > 0) {
            JSONObject jsonObject = new JSONObject();
            String userJson = jsonObject.toJSONString(postList);

            System.out.println("review====" + postList);
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

    /*
    * 通过发帖人id查找帖子（查找我发起的约影功能使用）
    * 且约影未完成的（即帖子endtime==null）
    * */
    @RequestMapping(value = "/findPostBypostPersonId")
    public String findPostBypostPersonId(HttpServletRequest request, HttpServletResponse response)throws IOException {
        PrintWriter out =null;
        out = response.getWriter();
        List<PostList> postListList=new ArrayList<PostList>();

        String spostPersonId=request.getParameter("postPersonId");
        int postPersonId=Integer.parseInt(spostPersonId);

        List<Post> postList = postService.findPostBypostPersonId(postPersonId);
        JSONArray jsonArray = new JSONArray();
        if (postList != null && postList.size() > 0) {
            List<PostList> postListS=new ArrayList<PostList>();
            for (Post post : postList) {
                User user=userService.findUserById(post.getPostPersonId());
                if(user!=null){
                    PostList postlist=new PostList();
                    postlist.setId(post.getId());
                    postlist.setDetails(post.getDetails());
                    postlist.setEndTime(post.getEndTime());
                    postlist.setMovieName(post.getMovieName());
                    postlist.setMovieTime(post.getMovieTime());
                    postlist.setMovieType(post.getMovieType());
                    postlist.setSite(post.getSite());
                    postlist.setSex(post.getSex());
                    postlist.setPostTime(post.getPostTime());
                    postlist.setPostPersonId(post.getPostPersonId());
                    postlist.setName(user.getName());
                    postlist.setNickname(user.getNickname());
                    postlist.setGender(user.getGender());
                    if (postlist.getEndTime()==null) {
                        postListS.add(postlist);
                    }
                }
            }
            if(postListS!=null&&postListS.size()>0){
                for (int i= postListS.size()-1;i>=0;i--){
                    postListList.add(postListS.get(i));
                    System.out.println("collectlistList====" + postListList);
                    //json数组转换方法
                    JSONObject jsonObj = (JSONObject) JSON.toJSON(postListS.get(i));
                    jsonArray.add(jsonObj);
                }
                out.print(jsonArray.toString());

            }else {
                out.print("null");
            }


        }else {
            //获取到数据为空时，向APP传输没有找到数据的信号
            out.print("nodata");
        }

        out.flush();
        out.close();
        return null;//这里返回空就行
    }


    /*
    * 通过发帖人id查找帖子（查找我参与的约影功能使用）
    * 且约影未完成的（即帖子endtime==null）
    * */
    @RequestMapping(value = "/findPostByjoin")
    public String findPostByjoin(HttpServletRequest request, HttpServletResponse response)throws IOException {
        PrintWriter out =null;
        out = response.getWriter();
        List<PostList> postListList=new ArrayList<PostList>();

        String sbyPersonId=request.getParameter("byPersonId");
        int byPersonId=Integer.parseInt(sbyPersonId);

        List<Person> personList = personService.findPersonBybyPersonId(byPersonId);
        JSONArray jsonArray = new JSONArray();
        if (personList != null && personList.size() > 0) {
            List<PostList> postListS=new ArrayList<PostList>();
            for (Person person : personList) {
                Post post=postService.findPostByid(person.getPostId());
                if(post!=null){
                    User user=userService.findUserById(post.getPostPersonId());
                    if (user!=null)
                    {
                        PostList postlist=new PostList();
                        postlist.setId(post.getId());
                        postlist.setDetails(post.getDetails());
                        postlist.setEndTime(post.getEndTime());
                        postlist.setMovieName(post.getMovieName());
                        postlist.setMovieTime(post.getMovieTime());
                        postlist.setMovieType(post.getMovieType());
                        postlist.setSite(post.getSite());
                        postlist.setSex(post.getSex());
                        postlist.setPostTime(post.getPostTime());
                        postlist.setPostPersonId(post.getPostPersonId());
                        postlist.setName(user.getName());
                        postlist.setNickname(user.getNickname());
                        postlist.setGender(user.getGender());
                        if (postlist.getEndTime()==null) {
                            postListS.add(postlist);
                        }
                    }
                    else {
                        out.print("usernull");
                    }

                }
                else {
                    out.print("postnull");
                }
            }
            if(postListS!=null&&postListS.size()>0){
                for (int i= postListS.size()-1;i>=0;i--){
                    postListList.add(postListS.get(i));
                    System.out.println("collectlistList====" + postListList);
                    //json数组转换方法
                    JSONObject jsonObj = (JSONObject) JSON.toJSON(postListS.get(i));
                    jsonArray.add(jsonObj);
                }
                out.print(jsonArray.toString());

            }else {
                out.print("postListS.size="+postListS.size());
                out.print("null");
            }
        }else {
            //获取到数据为空时，向APP传输没有找到数据的信号
            out.print("nodata");
        }

        out.flush();
        out.close();
        return null;//这里返回空就行
    }

     /*
    * 通过发帖人id查找帖子（查找历史约影功能）
    * 且约影未完成的（即帖子endtime==null）
    * */
     @RequestMapping(value = "/findPostByhistory")
     public String findPostByhistory(HttpServletRequest request, HttpServletResponse response)throws IOException {

         List<PostList> postListList=new ArrayList<PostList>();
         PrintWriter out =null;
         out = response.getWriter();

         String sbyPersonId=request.getParameter("byPersonId");
         int byPersonId=Integer.parseInt(sbyPersonId);

         List<PostList> postListS = new ArrayList<PostList>();
         //查找自己发起的
         List<Post> postList = postService.findPostBypostPersonId(byPersonId);
         JSONArray jsonArray = new JSONArray();
         if (postList != null && postList.size() > 0) {

             for (Post post : postList) {
                 User user = userService.findUserById(post.getPostPersonId());
                 if (user != null) {
                     PostList postlist = new PostList();
                     postlist.setId(post.getId());
                     postlist.setDetails(post.getDetails());
                     postlist.setEndTime(post.getEndTime());
                     postlist.setMovieName(post.getMovieName());
                     postlist.setMovieTime(post.getMovieTime());
                     postlist.setMovieType(post.getMovieType());
                     postlist.setSite(post.getSite());
                     postlist.setSex(post.getSex());
                     postlist.setPostTime(post.getPostTime());
                     postlist.setPostPersonId(post.getPostPersonId());
                     postlist.setName(user.getName());
                     postlist.setNickname(user.getNickname());
                     postlist.setGender(user.getGender());
                     if (postlist.getEndTime() == null) {
                         postListS.add(postlist);
                     }
                 }
             }
         }

         //查找参与他人的
         List<Person> personList = personService.findPersonBybyPersonId(byPersonId);
         if (personList != null && personList.size() > 0) {
             for (Person person : personList) {
                 Post post=postService.findPostByid(person.getPostId());
                 if(post!=null){
                     User user=userService.findUserById(post.getPostPersonId());
                     if (user!=null)
                     {
                         PostList postlist=new PostList();
                         postlist.setId(post.getId());
                         postlist.setDetails(post.getDetails());
                         postlist.setEndTime(post.getEndTime());
                         postlist.setMovieName(post.getMovieName());
                         postlist.setMovieTime(post.getMovieTime());
                         postlist.setMovieType(post.getMovieType());
                         postlist.setSite(post.getSite());
                         postlist.setSex(post.getSex());
                         postlist.setPostTime(post.getPostTime());
                         postlist.setPostPersonId(post.getPostPersonId());
                         postlist.setName(user.getName());
                         postlist.setNickname(user.getNickname());
                         postlist.setGender(user.getGender());
                         if (postlist.getEndTime()==null) {
                             postListS.add(postlist);
                         }
                     }
                     else {
                         out.print("usernull");
                     }

                 }
                 else {
                     out.print("postnull");
                 }
             }
             if(postListS!=null&&postListS.size()>0){
                 for (int i= postListS.size()-1;i>=0;i--){
                     postListList.add(postListS.get(i));
                     System.out.println("collectlistList====" + postListList);
                     //json数组转换方法
                     JSONObject jsonObj = (JSONObject) JSON.toJSON(postListS.get(i));
                     jsonArray.add(jsonObj);
                 }
                 out.print(jsonArray.toString());

             }else {
                 out.print("postListS.size="+postListS.size());
                 out.print("null");
             }
         }else {
             //获取到数据为空时，向APP传输没有找到数据的信号
             out.print("nodata");
         }

         out.flush();
         out.close();
         return null;//这里返回空就行
     }

    /*
    * 查找所有的post
    * */
    @RequestMapping(value = "/findAllPost")
    public String findAllPost(HttpServletRequest request, HttpServletResponse response)throws IOException {
        PrintWriter out =null;
        out = response.getWriter();
        List<PostList> postListList=new ArrayList<PostList>();

        List<Post> postList = postService.findAllPost();
        JSONArray jsonArray = new JSONArray();
        if (postList != null && postList.size() > 0) {
            List<PostList> postListS=new ArrayList<PostList>();
            for (Post post : postList) {
                User user=userService.findUserById(post.getPostPersonId());
                if(user!=null){
                    PostList postlist=new PostList();
                    postlist.setId(post.getId());
                    postlist.setDetails(post.getDetails());
                    postlist.setEndTime(post.getEndTime());
                    postlist.setMovieName(post.getMovieName());
                    postlist.setMovieTime(post.getMovieTime());
                    postlist.setMovieType(post.getMovieType());
                    postlist.setSite(post.getSite());
                    postlist.setSex(post.getSex());
                    postlist.setPostTime(post.getPostTime());
                    postlist.setPostPersonId(post.getPostPersonId());
                    postlist.setName(user.getName());
                    postlist.setNickname(user.getNickname());
                    postlist.setGender(user.getGender());
                    postListS.add(postlist);

                }
            }
            if(postListS!=null&&postListS.size()>0){
                for (int i= postListS.size()-1;i>=0;i--){
                    postListList.add(postListS.get(i));
                    System.out.println("collectlistList====" + postListList);
                    //json数组转换方法
                    JSONObject jsonObj = (JSONObject) JSON.toJSON(postListS.get(i));
                    jsonArray.add(jsonObj);
                }
                out.print(jsonArray.toString());

            }else {
                out.print("null");
            }


        }else {
            //获取到数据为空时，向APP传输没有找到数据的信号
            out.print("nodata");
        }

        out.flush();
        out.close();
        return null;//这里返回空就行
    }

    /*模糊搜索*/
    @RequestMapping(value = "/findposttab")
    public String findposttab(HttpServletRequest request,HttpServletResponse response)throws IOException {

        PrintWriter out = null;
        out = response.getWriter();
        List<PostList> postListList = new ArrayList<PostList>();

        String details = request.getParameter("details");

        List<Post> postList = postService.findposttab(details);
        JSONArray jsonArray = new JSONArray();
        if (postList != null && postList.size() > 0) {
            List<PostList> postListS = new ArrayList<PostList>();
            for (Post post : postList) {
                User user = userService.findUserById(post.getPostPersonId());
                if (user != null) {
                    PostList postlist = new PostList();
                    postlist.setId(post.getId());
                    postlist.setDetails(post.getDetails());
                    postlist.setEndTime(post.getEndTime());
                    postlist.setMovieName(post.getMovieName());
                    postlist.setMovieTime(post.getMovieTime());
                    postlist.setMovieType(post.getMovieType());
                    postlist.setSite(post.getSite());
                    postlist.setSex(post.getSex());
                    postlist.setPostTime(post.getPostTime());
                    postlist.setPostPersonId(post.getPostPersonId());
                    postlist.setName(user.getName());
                    postlist.setNickname(user.getNickname());
                    postlist.setGender(user.getGender());
                    postListS.add(postlist);
                }
            }
            if (postListS != null && postListS.size() > 0) {
                for (int i = postListS.size() - 1; i >= 0; i--) {
                    postListList.add(postListS.get(i));
                    System.out.println("collectlistList====" + postListList);
                    //json数组转换方法
                    JSONObject jsonObj = (JSONObject) JSON.toJSON(postListS.get(i));
                    jsonArray.add(jsonObj);
                }
                out.print(jsonArray.toString());
            } else {
                out.print("null");
            }

        } else {
            //获取到数据为空时，向APP传输没有找到数据的信号
            out.print("nodata");
        }

        out.flush();
        out.close();
        return null;//这里返回空就行
    }

}
