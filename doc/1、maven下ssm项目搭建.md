## 系统API
## How to play

## Develop environment
IDEA+Maven+SSM框架。 ssm即spring、springMVC、mybatis框架

## Written in front of words
可以参考以下内容，里面有大神详细的项目搭建过程
- 1.[Java高并发秒杀APi之业务分析与DAO层代码编写](http://codingxiaxw.cn/2016/11/27/53-maven-ssm-seckill-dao/)。
- 2.[Java高并发秒杀APi之Service层代码编写](http://codingxiaxw.cn/2016/11/28/54-seckill-service/)。
- 3.[Java高并发秒杀APi之Web层代码编写](http://codingxiaxw.cn/2016/11/28/55-seckill-web/)。


项目搭建目录：
0、[Maven安装配置及创建你的第一个Maven项目](http://codingxiaxw.cn/2016/11/24/51-first-maven-project/)
    创建web项目，配置Tomcat，能运行起来出现helloworld页面则web项目创建成功
    配置pom.xml，在里面添加jar包依赖，相当于引入jar包，
    在这里配置我们需要的第三方jar包的坐标配置信息，如SSM框架、数据库、日志

1、运用mybatis搭建dao
    1）首先创建数据库，创建对应表的实体类
    2）针对实体创建出对应dao层的接口，只需定义好接口，不用实现具体方法
    3）配置我们的MyBatis，在main的resources包下创建MyBatis全局配置文件mybatis-config.xml文件并配置好
    4）mybatis为我们提供了mapper动态代理开发的方式为我们自动实现Dao的接口，
        即在mapper包下创建对应Dao接口的xml映射文件，里面用于编写我们操作数据库的sql语句
    5）MyBatis和Spring的整合，在resources包下创建一个spring包，里面放置spring对Dao、Service、transaction的配置文件，
        在resources包下创建jdbc.properties用于配置数据库的连接信息
    6）利用junit进行我们Dao层编码的测试，测试通过则mybatis对数据库的操作成功

2、运用spring搭建service
    1）秒杀Service接口设计，在包下创建一个service包用于存放我们的Service接口和其实现类
    2）秒杀Service接口的实现，在service包下创建impl包存放它的实现类UserServiceImpl.java
    3）使用Spring托管Service依赖配置，在spring包下创建一个spring-service.xml文件，然后采用注解的方式将Service的实现类加入到Spring IOC容器中
    4）使用Spring声明式事务配置，在spring-service.xml中添加对事务的配置，然后在Service实现类的方法中，在需要进行事务声明的方法上加上事务的注解:
    5）使用集成测试Service逻辑

3、运用springMVC搭建controller


具体操作及步骤请看下面内容
项目开始------------------------------》》》
用maven对项目进行管理的知识很简单，关于创建maven项目的知识大家看我的这篇文章便可以在几分钟内掌握:
[Maven安装配置及创建你的第一个Maven项目](http://codingxiaxw.cn/2016/11/24/51-first-maven-project/)

## 1.相关技术介绍
**MySQL:**1.这里我们采用手写代码创建相关表，掌握这种能力对我们以后的项目二次上线会有很大的帮助；2.SQL技巧；3.事务和行级锁的理解和一些应用。

**MyBatis:**1.DAO层的设计与开发。2.MyBatis的合理使用，使用Mapper动态代理的方式进行数据库的访问。3.MyBatis和Spring框架的整合:如何高效的去整合MyBatis和Spring框架。  

**Spring:**1.Spring IOC帮我们整合Service以及Service所有的依赖。2.声明式事务。对Spring声明式事务做一些分析以及它的行为分析。  

**Spring MVC:**1.Restful接口设计和使用。Restful现在更多的被应用在一些互联网公司Web层接口的应用上。2.框架运作流程。3.Spring Controller的使用技巧。

下面开始我们的项目的开发。  

## 2.系统DAO层代码编写

### 2.1用Maven创建我们的项目seckill
在命令行中输入如下命令:  

```
mvn archetype:generate -DgroupId=com.fu.blogsystemmaven -DartifactId=blogsystemmaven -Dpackage=com.fu.blogsystemmaven -Dversion=1.0-SNAPSHOT -DarchetypeArtifactId=maven-archetype-webapp

```
然后使用IDEA打开该项目，在IDEA中对项目按照Maven项目的标准骨架补全我们项目的相应文件包，最后的工程结构如下:  

<img src="http://od2xrf8gr.bkt.clouddn.com/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202016-11-27%20%E4%B8%8B%E5%8D%883.53.43.png" width="50%" />   

或者参考[Maven安装配置及创建你的第一个Maven项目](http://codingxiaxw.cn/2016/11/24/51-first-maven-project/)
创建web项目，配置Tomcat，能运行起来出现helloworld页面则web项目创建成功
    配置pom.xml，在里面添加jar包依赖，相当于引入jar包，
    在这里配置我们需要的第三方jar包的坐标配置信息，如SSM框架、数据库、日志

main包下进行我们项目的代码编写及相关配置文件，test包下进行我们项目的测试。  

打开WEB-INF下的web.xml，它默认为我们创建servlet版本为2.3，需要修改它的根标签为:
```xml
<web-app xmlns="http://java.sun.com/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://java.sun.com/xml/ns/javaee
                      http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
         version="3.0"
         metadata-complete="true">
<!--用maven创建的web-app需要修改servlet的版本为3.0-->
</web-app>
```

然后打开pom.xml，在里面添加我们需要的第三方jar包的坐标配置信息，如SSM框架、数据库、日志，如下:
```
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">

  <modelVersion>4.0.0</modelVersion>
  <groupId>com.fu.blogsystemmaven</groupId>
  <artifactId>blogsystemmaven</artifactId>
  <packaging>war</packaging>
  <version>1.0-SNAPSHOT</version>
  <name>blogsystemmaven Maven Webapp</name>
  <url>http://maven.apache.org</url>
  <dependencies>
    <!--3.0的junit是使用编程的方式来进行测试，而junit4是使用注解的方式来运行junit
    将版本改为4.10-->
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.10</version>
      <scope>test</scope>
    </dependency>

    <!--补全项目依赖-->
    <!--1.日志 java日志有:slf4j,log4j,logback,common-logging
        slf4j:是规范/接口
        日志实现:log4j,logback,common-logging
        使用:slf4j+logback
    -->
    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-api</artifactId>
      <version>1.7.12</version>
    </dependency>
    <dependency>
      <groupId>ch.qos.logback</groupId>
      <artifactId>logback-core</artifactId>
      <version>1.1.1</version>
    </dependency>
    <!--实现slf4j接口并整合-->
    <dependency>
      <groupId>ch.qos.logback</groupId>
      <artifactId>logback-classic</artifactId>
      <version>1.1.1</version>
    </dependency>

    <!--1.数据库相关依赖-->
    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>5.1.35</version>
      <scope>runtime</scope>
    </dependency>
    <dependency>
      <groupId>c3p0</groupId>
      <artifactId>c3p0</artifactId>
      <version>0.9.1.1</version>
    </dependency>

    <!--2.dao框架:MyBatis依赖-->
    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis</artifactId>
      <version>3.3.0</version>
    </dependency>
    <!--mybatis自身实现的spring整合依赖-->
    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis-spring</artifactId>
      <version>1.2.3</version>
    </dependency>

    <!--3.Servlet web相关依赖-->
    <dependency>
      <groupId>taglibs</groupId>
      <artifactId>standard</artifactId>
      <version>1.1.2</version>
    </dependency>
    <dependency>
      <groupId>jstl</groupId>
      <artifactId>jstl</artifactId>
      <version>1.2</version>
    </dependency>
    <dependency>
      <groupId>com.fasterxml.jackson.core</groupId>
      <artifactId>jackson-databind</artifactId>
      <version>2.5.4</version>
    </dependency>
    <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>javax.servlet-api</artifactId>
      <version>3.1.0</version>
    </dependency>

    <!--4:spring依赖-->
    <!--1)spring核心依赖-->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-core</artifactId>
      <version>4.1.7.RELEASE</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-beans</artifactId>
      <version>4.1.7.RELEASE</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-context</artifactId>
      <version>4.1.7.RELEASE</version>
    </dependency>

    <!--2)spring dao层依赖-->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-jdbc</artifactId>
      <version>4.1.7.RELEASE</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-tx</artifactId>
      <version>4.1.7.RELEASE</version>
    </dependency>

    <!--3)springweb相关依赖-->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-web</artifactId>
      <version>4.1.7.RELEASE</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-webmvc</artifactId>
      <version>4.1.7.RELEASE</version>
    </dependency>

    <!--4)spring test相关依赖-->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-test</artifactId>
      <version>4.1.7.RELEASE</version>
    </dependency>
    <dependency>
      <groupId>commons-cli</groupId>
      <artifactId>commons-cli</artifactId>
      <version>1.0</version>
      <scope>test</scope>
    </dependency>
  </dependencies>

  <build>
    <finalName>blogsystemmaven</finalName>
  </build>
</project>```

到此，我们项目的初始化工作完成。  

### 2.2系统业务分析
系统业务流程如下:  跳过
