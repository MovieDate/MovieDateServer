##4、springMVC搭建controller

整合配置Spring MVC框架

1）首先在WEB-INF的web.xml中进行我们前端控制器DispatcherServlet的配置

<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns="http://java.sun.com/xml/ns/javaee"
         xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
         version="3.0"
         metadata-complete="true">
  <!-- 用maven创建的web项目 要修改servlet版本为3.0 -->
  <!--防止乱码配置-->
  <filter>
    <filter-name>encodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
      <param-name>encoding</param-name>
      <param-value>UTF-8</param-value>
    </init-param>
    <init-param>
      <param-name>forceEncoding</param-name>
      <param-value>true</param-value>
    </init-param>
  </filter>
  <filter-mapping>
    <filter-name>encodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>

  <!--配置DispatcherServlet-->
  <servlet>
    <servlet-name>seckill-dispatcher</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <!--
        配置SpringMVC 需要加载的配置文件
        spring-dao.xml，spring-service.xml,spring-web.xml
        Mybites -> spring -> springMvc
    -->
    <init-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>classpath:spring/spring-*.xml</param-value>
    </init-param>
  </servlet>
  <servlet-mapping>
    <servlet-name>seckill-dispatcher</servlet-name>
    <!--默认匹配所有请求-->
    <url-pattern>/</url-pattern>
  </servlet-mapping>
</web-app>

2）然后在spring容器中进行web层相关bean(即Controller)的配置，在spring包下创建一个spring-web.xml

<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/mvc
        http://www.springframework.org/schema/mvc/spring-mvc.xsd">
    <!--配置spring mvc-->
    <!--1,开启springmvc注解模式-->
    <!--简化配置：
    a.自动注册DefaultAnnotationHandlerMapping,AnnotationMethodHandlerAdapter
    b.默认提供一系列的功能:数据绑定，数字和日期的format@NumberFormat,@DateTimeFormat
    c:xml,json的默认读写支持-->
    <mvc:annotation-driven/>
    <!--2.静态资源默认servlet配置-->
    <!--
        1).加入对静态资源处理：js,gif,png
        2).允许使用 "/" 做整体映射
    -->
    <mvc:default-servlet-handler/>
    <!--3：配置JSP 显示ViewResolver-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="viewClass" value="org.springframework.web.servlet.view.JstlView"/>
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <property name="suffix" value=".jsp"/>
    </bean>
    <!--4:扫描web相关的controller-->
    <context:component-scan base-package="com.fu.blogsystemmaven.controller"/>
</beans>

这样我们便完成了Spring MVC的相关配置(即将Spring MVC框架整合到了我们的项目中)，
接下来就要基于Restful接口进行我们项目的Controller开发工作了。

3)Controller开发

Controller中的每一个方法都对应我们系统中的一个资源URL，其设计应该遵循Restful接口的设计风格。
在com.fu.blogsystemmaven包下创建一个controller包用于放web层Controller开发的代码，
在该包下创建一个UserController.java，内容如下:
@Component
@RequestMapping("/user")//url:模块/资源/{}/细分
public class UserController {

    @Autowired
    private UserService userService;

//    method = RequestMethod.GET  get方式的请求，不写method则表示get和post方法请求都可以
    @RequestMapping(value = "/allUser",method = RequestMethod.POST)
    public String findAllUser(HttpServletResponse response) throws IOException{
        response.setContentType("text/html;charset=utf-8");

        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取列表页
        List<User> userList = userService.findAllUser();

        //将List转换成json数据
        JSONArray jsonArray = new JSONArray();
        for (User user : userList) {
            JSONObject jsonObj = (JSONObject) JSON.toJSON(user);
            jsonArray.add(jsonObj);
        }

        System.out.println("userList===="+userList);
        System.out.println("jsonArry===="+jsonArray);

        out.print(jsonArray.toString());;//向APP传输json数据

        out.flush();
        out.close();
        return null;//这里返回空就行
    }

    //1.这个是Restful接口设计方法
    @RequestMapping(value = "/{phone}/user", method = RequestMethod.POST)
    //@ResponseBody//这些参数就是APP那边请求的参数  HttpServletResponse是用来返回数据的，不是APP那边请求的参数
    public String findUserByPhone0(@PathVariable("phone") String phone,HttpServletResponse response)
            throws IOException{

//        response.setContentType("text/html;charset=utf-8");

        User user = new User();
            user = userService.findUserByPhone(phone);
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //将User转换成json数据
        JSONObject jsonObject=new JSONObject();
        String userJson=jsonObject.toJSONString(user);

        System.out.println("user===="+user);
        System.out.println("userJson===="+userJson);

        //获取到的数据传过去APP端
        out.print(userJson);

        out.flush();
        out.close();
        return null;
    }

    //2.这个是简单的接口设计方法
    @RequestMapping(value = "/findUserByPhone1", method = RequestMethod.POST)
    //@ResponseBody//这些参数就是APP那边请求的参数  HttpServletResponse是用来返回数据的，不是APP那边请求的参数
    public String findUserByPhone1(@Param("phone") String phone, HttpServletResponse response)
            throws IOException{

//        response.setContentType("text/html;charset=utf-8");

        User user = new User();
        user = userService.findUserByPhone(phone);
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //将User转换成json数据
        JSONObject jsonObject=new JSONObject();
        String userJson=jsonObject.toJSONString(user);

        System.out.println("user===="+user);
        System.out.println("userJson===="+userJson);

        //获取到的数据传过去APP端
        out.print(userJson);

        out.flush();
        out.close();
        return null;
    }

    //2.这个是简单的接口设计方法
    @RequestMapping(value = "/findUserByPhone2", method = RequestMethod.POST)
    //@ResponseBody//这些参数就是APP那边请求的参数  HttpServletResponse是用来返回数据的，不是APP那边请求的参数
    public String findUserByPhone2(HttpServletRequest request, HttpServletResponse response)
            throws IOException{

//        response.setContentType("text/html;charset=utf-8");
//        request.setCharacterEncoding("UTF-8");

        String phone = request.getParameter("phone");

        User user = new User();
        user = userService.findUserByPhone(phone);
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //将User转换成json数据
        JSONObject jsonObject=new JSONObject();
        String userJson=jsonObject.toJSONString(user);

        System.out.println("user===="+user);
        System.out.println("userJson===="+userJson);

        //获取到的数据传过去APP端
        out.print(userJson);

        out.flush();
        out.close();
        return null;
    }

}


Controller开发中的方法完全是对照Service接口方法进行开发的，
