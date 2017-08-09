
### 2.3Dao层设计开发
1）-----------------------------------------
首先创建数据库，相关表的sql语句我在main包下的sql包中已经给出。当然这个文件在项目中没什么用，只是用来记录SQL语句

然后创建对应表的实体类，在java包下创建com.fu.blogsystemmaven.entity包，创建一个User.java实体类，代码如下:
```java
public class User {

    private int id;//自增
    private String phone;//联系方式（登陆账号）
    private String nickname;//昵称
    private String password;
    private String name;
    private String age;
    private String gender;//性别:男（1）女（0）
    private String address;
    private String profession;//职业
    private String introduction;//个人简介
    private String type="0";//用户类型:普通用户（0）管理员（1）

    //构造方法也可以根据需要来创建，方便初始化
    //创建get、set和tostring方法
}
```
2）--------------------------------------------------------
然后针对实体创建出对应dao层的接口，在com.fu.blogsystemmaven.dao包下创建UserDao.java:
```java:创建的是接口类
public interface UserDao {//创建test快捷方式，选中类名，右键go to->test

    /**
     * 通过 User 来添加 User
     * @param user
     * @return  插入的行数
     */
    int addUserByUser(User user);

    /**
     * 通过 phone和password 来添加 User
     * @param phone
     * @param password
     * @return 插入的行数
     */
    int addUserByPam(String phone,String password);
    //int addUserByPam(@Param("phone") String phone,@Param("password") String password);

    /**
     * 通过phone删除User
     * @param phone
     * @return
     */
    int deleteUserByPhone(String phone);

    /**
     * 通过phone更新User
     * @param phone
     * @return
     */
    int updateUserByPhone(String phone);

    /**
     * 通过phone查询User
     * @param phone
     * @return
     */
    int findUserByPhone(String phone);

}
```
3）-------------------------------------------------------------------
接下来基于MyBatis来实现我们之前设计的Dao层接口。首先需要配置我们的MyBatis，
在main的resources包下创建MyBatis全局配置文件mybatis-config.xml文件，
在浏览器中输入`http://mybatis.github.io/mybatis-3/zh/index.html`打开MyBatis的官网文档，点击左边的"入门"栏框，
找到mybatis全局配置文件，在这里有xml的一个规范，也就是它的一个xml约束，拷贝:
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
  PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-config.dtd">
```
到我们的项目mybatis全局配置文件中，然后在全局配置文件中加入如下配置信息:
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!--配置全局属性-->
    <settings>
        <!--使用jdbc的getGeneratekeys获取自增主键值-->
        <setting name="useGeneratedKeys" value="true"/>
        <!--使用列别名替换列名　　默认值为true
        select name as title(实体中的属性名是title) form table;
        开启后mybatis会自动帮我们把表中name的值赋到对应实体的title属性中
        -->
        <setting name="useColumnLabel" value="true"/>

        <!--开启驼峰命名转换Table:create_time到 Entity(createTime)-->
        <setting name="mapUnderscoreToCamelCase" value="true"/>
    </settings>

</configuration>
```
4）-----------------------------------------------------------------------
配置文件创建好后我们需要关注的是Dao接口该如何实现，mybatis为我们提供了mapper动态代理开发的方式为我们自动实现Dao的接口。
在mapper包下创建对应Dao接口的xml映射文件，里面用于编写我们操作数据库的sql语句，UserDao.xml。
既然又是一个xml文件，我们肯定需要它的dtd文件，在官方文档中，点击左侧"XML配置"，在它的一些事例中，找到它的xml约束:
```xml
<!DOCTYPE mapper
    PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
```
加入到两个mapper映射xml文件中，然后对照Dao层方法编写我们的映射文件内容如下:
UserDao.xml:
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!-- namespace为对应的dao类路径 -->
<mapper namespace="com.fu.blogsystemmaven.dao.UserDao">
    <!--目的:为dao接口方法提供sql语句配置
    即针对dao接口中的方法编写我们的sql语句-->

    <!--id对应dao的方法名，如果是多参数parameterType不用给，但需要在dao方法的参数前加上@Param("killTime")
            例如：int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);-->
    <!--注意：增删改查对应相应的标签，更新操作对应update标签，插入对应insert，查找对应select，删除对应delete-->
    <update id="updateUserByPhone">
        <!--具体SQL语句-->
        UPDATE user_tab
        SET password = #{password}
        WHERE phone=#{phone};
    </update>

    <select id="findUserByPhone" resultType="User" parameterType="String">
        SELECT *
        FROM user_tab
        WHERE phone=#{phone}
    </select>

    <select id="findAllUser" resultType="User">
        SELECT *
        FROM user_tab
        ORDER BY age DESC
    </select>

    <!--<select id="addUserByUser" resultType="int" parameterType="User">-->
        <!--&lt;!&ndash;当出现主键冲突时(即重复时)，会报错;不想让程序报错，加入ignore&ndash;&gt;-->
        <!--INSERT ignore INTO user_tab(phone,password)-->
        <!--VALUES (#{user1.getPhone},#{user1.getPassword})-->
    <!--</select>-->

    <insert id="addUserByPam">
        <!--当出现主键冲突时(即重复时)，会报错;不想让程序报错，加入ignore-->
        INSERT ignore INTO user_tab(phone,password,name,nickname,age,gender,address,profession,introduction,type)
        VALUES (#{phone},#{password},"0","0",0,"0","0","0","0",0);
    </insert>

    <delete id="deleteUserByPhone" parameterType="String">
        <!--具体SQL语句-->
        DELETE FROM user_tab
        WHERE phone=#{phone};
    </delete>
</mapper>
```
5）-------------------------------------------------------------------
接下来我们开始MyBatis和Spring的整合，整合目标:1.更少的编码:dao只写接口，不写实现类。2.更少的配置:别名、配置扫描映射xml文件、dao实现。
3.足够的灵活性:自由定制SQL语句、自由传结果集自动赋值。

在resources包下创建一个spring包，里面放置spring对Dao、Service、transaction的配置文件。
在浏览器中输入`http://docs.spring.io/spring/docs/`进入到Spring的官网中下载其pdf官方文档，在其官方文档中找到它的xml的定义内容头部:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
```

在spring包下创建一个spring配置dao层对象的配置文件spring-dao.xml，加入上述dtd约束，然后添加二者整合的配置，内容如下:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

    <!--配置整合mybatis过程
    1.配置数据库相关参数properties的属性：${url} 在resource下创建数据库配置文件jdbc.properties-->
    <context:property-placeholder location="classpath:jdbc.properties"/>

    <!--2.数据库连接池-->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <!--配置连接池属性-->
        <property name="driverClass" value="${jdbc.driver}" />

        <!-- 基本属性 url、user、password -->
        <property name="jdbcUrl" value="${jdbc.url}" />
        <property name="user" value="${jdbc.username}" />
        <property name="password" value="${jdbc.password}" />

        <!--c3p0连接池的私有属性-->
        <property name="maxPoolSize" value="30"/>
        <property name="minPoolSize" value="10"/>
        <!--关闭连接后不自动commit-->
        <property name="autoCommitOnClose" value="false"/>
        <!--获取连接超时时间-->
        <property name="checkoutTimeout" value="1000"/>
        <!--当获取连接失败重试次数-->
        <property name="acquireRetryAttempts" value="2"/>
    </bean>

    <!--约定大于配置-->
    <!--３.配置SqlSessionFactory对象-->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <!--往下才是mybatis和spring真正整合的配置-->
        <!--注入数据库连接池-->
        <property name="dataSource" ref="dataSource"/>
        <!--配置mybatis全局配置文件:mybatis-config.xml-->
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
        <!--扫描entity包,使用别名,多个时用;隔开-->
        <property name="typeAliasesPackage" value="com.fu.blogsystemmaven.entity"/>
        <!--扫描sql配置文件:mapper需要的xml文件-->
        <property name="mapperLocations" value="classpath:mapper/*.xml"/>
    </bean>

    <!--４:配置扫描Dao接口包,动态实现DAO接口,注入到spring容器-->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <!--注入SqlSessionFactory-->
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
        <!-- 给出需要扫描的Dao接口-->
        <property name="basePackage" value="com.fu.blogsystemmaven.dao"/>
    </bean>

</beans>
```

需要我们在resources包下创建jdbc.properties用于配置数据库的连接信息，内容如下:
```properties：PS：有些人没有加上jdbc.即driver=com.mysql.jdbc.Driver，可能会连接不上数据库
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/blogsystem?useUnicode=true&characterEncoding=utf-8
jdbc.username=root
jdbc.password=root
```
6）----------------------------------------------------
这样我们便完成了Dao层编码的开发，接下来就可以利用junit进行我们Dao层编码的测试了。
首先测试UserDao.java，进入UserDao.java中，选中类名UserDao，右键go to->test-创建->选择Junit4,选择添加的测试方法，finish
然后IDEA会自动在test包的java包下为我们生成对UserDao.java中所有方法的测试类UserDaoTest.java,
然后便可以在这个测试类中对UserDao接口的所有方法进行测试了,在该方法中添加内容:
```java
//配置spring和junit整合，这样junit在启动时就会加载spring容器
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring的配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class UserDaoTest {

    //注入Dao实现类依赖
//    @Resource
    @Autowired
    private UserDao userDao;

    @Test
    public void addUserByPam() throws Exception {
        int addAmount = userDao.addUserByPam("18219111603","123456");
        System.out.println("更新操作后返回的数目：" + addAmount);
    }
    @Test
    public void deleteUserByPhone() throws Exception {
        int deleteAmount = userDao.deleteUserByPhone("18219111602");
        System.out.println("更新操作后返回的数目：" + deleteAmount);
    }
    @Test
    public void updateUserByPhone() throws Exception {
        int updateAmount = userDao.updateUserByPhone("18219111609","testpassword01");
        System.out.println("更新操作后返回的数目：" + updateAmount);
    }
    @Test
    public void findUserByPhone() throws Exception {
        User user = userDao.findUserByPhone("18219111629");
        System.out.println("user==="+user);
    }
    @Test
    public void findAllUser() throws Exception {
        List<User> userList=userDao.findAllUser();
        for (User user : userList) {
            System.out.println("User===="+user);
        }
    }
}
```
到此，我们成功完成了Dao层开发及测试，接下来我们将进行Service层的开发工作，
请查看3、spring搭建service.md

