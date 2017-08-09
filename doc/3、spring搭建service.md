##Service层开发

开始Service层的编码之前，我们首先需要进行Dao层编码之后的思考:
在Dao层我们只完成了针对表的相关操作包括写了接口方法和映射文件中的sql语句，
并没有编写逻辑的代码，例如对多个Dao层方法的拼接，一些复杂的逻辑我们都需要在Service层完成。
这也是一些初学者容易出现的错误，他们喜欢在Dao层进行逻辑的编写，
其实Dao就是数据访问的缩写，它只进行数据的访问操作，接下来我们便进行Service层代码的编写。

(由于这个例子系统比较简单，service中就只做一些简单的dao层调用)

1）秒杀Service接口设计

在包下创建一个service包用于存放我们的Service接口和其实现类，
（可以根据需要创建一个exception包用于存放service层出现的异常，即自定义异常，也可以不创建，跳过）
一个dto包作为传输层,dto和entity的区别在于:entity用于业务数据的封装，而dto用于完成数据传递，
例如需要获取User表和另一个表中的部分信息，可以根据需要获取的信息另外创建实体类来存取数据进行数据传递

首先创建我们Service接口，里面的方法应该是按”使用者”(程序员)的角度去设计，在service包下创建UserService.java，代码如下:
/**
 * Created by wunaifu on 2017/8/6.
 * 业务接口:站在使用者(程序员)的角度设计接口
 * 三个方面:1.方法定义粒度，方法定义的要非常清楚2.参数，要越简练越好
 * 3.返回类型(return 类型一定要友好/或者return异常，我们允许的异常)
 */
public interface UserService {//创建test快捷方式，选中类名，右键go to->test

    /**
     *  phone和password 来添加 User
     * @param phone
     * @param password
     * @return 插入的行数
     */
    int addUserByPam( String phone, String password);

    /**
     * 通过phone删除User
     * @param phone
     * @return 删除成功返回1,删除失败或者重复删除返回0
     */
    int deleteUserByPhone(String phone);

    /**
     * 通过phone更新User密码 并插入一条数据
     * 这个操作目前没有意义，只是测试一下同时进行多种数据库更新操作，运用事务机制的好处
     * 比如能够成功通过phone更新User密码，但插入一条数据失败，则事务会回滚，两种数据库操作都会失败，
     * 相当于这个方法没有执行，两种数据库操作都没有执行
     * @param phoneU
     * @param passwordU
     * @param phoneI
     * @param passwordI
     * @return
     */
    int updateAndAddUser(String phoneU,String passwordU,String phoneI,String passwordI);

    /**
     * 通过phone查询User
     * @param phone
     * @return
     */
    User findUserByPhone(String phone);

    /**
     * 查找所有User，并按降序排序好
     * @return
     */
    List<User> findAllUser();

}

到此，接口的工作便完成，接下来进行接口实现类的编码工作。

2）秒杀Service接口的实现

在service包下创建impl包存放它的实现类，UserServiceImpl.java，
在将Service交给Spring的容器托管后然后采用注解的方式将Service的实现类加入到Spring IOC容器中:
        //@Component @Service @Dao @Controller
        @Service
        public class UserServiceImpl implements UserService{}
然后//注入Service依赖
      @Autowired //@Resource
      private UserDao userDao;

UserServiceImpl.java代码如下:
@Service
public class UserServiceImpl implements UserService{

    //注入Service依赖
    @Autowired //@Resource
    private UserDao userDao;

    /**
     *  phone和password 来添加 User
     * @param phone
     * @param password
     * @return 插入的行数
     */
    public int addUserByPam(String phone, String password) {
        return userDao.addUserByPam(phone,password);//成功添加返回1，失败返回0
    }

    /**
     * 通过phone删除User
     * @param phone
     * @return 删除成功返回1,删除失败或者重复删除返回0
     */
    public int deleteUserByPhone(String phone) {
        return userDao.deleteUserByPhone(phone);//成功删除返回1，删除失败或者重复删除返回0
    }


    /**
     * 使用注解控制事务方法的优点:
     * 1.开发团队达成一致约定，明确标注事务方法的编程风格
     * 2.保证事务方法的执行时间尽可能短，不要穿插其他网络操作RPC/HTTP请求或者剥离到事务方法外部
     * 3.不是所有的方法都需要事务，如只有一条修改操作、只读操作不要事务控制
     *
     * 通过phone更新User密码 并插入一条数据
     * 这个操作目前没有意义，只是测试一下同时进行多种数据库更新操作，运用事务机制的好处
     * 就是如果有异常则事务会回滚，两种数据库操作都会回到未操作前，
     * 防止需要同时进行多种操作时有部分操作未完成或完成情况不符合我们需求，
     * 有时根据需要手动抛出错误，则事务回滚
     * @param phoneU
     * @param passwordU
     * @param phoneI
     * @param passwordI
     * @return
     */
    @Transactional//这个事务有问题，待测试
    public int updateAndAddUser(String phoneU, String passwordU, String phoneI, String passwordI)
            throws MyTestException,Exception{

        int flag = 0;

        try {
            int flag1 = userDao.updateUserByPhone(phoneU, passwordU);
            if (flag1 == 0) {
                flag = 0;
                throw new MyTestException("update_failure");
            } else {
                int flag2 = userDao.addUserByPam(phoneI, passwordI);
                if (flag2 == 0) {
                    flag = 0;
                    throw new MyTestException("insert_failure");
                }else{
                    flag = 1;
                }
            }
        }catch (Exception e) {
            throw new Exception("update_error:"+e.getMessage());
        }
        return flag;
    }

    /**
     * 通过phone查询User
     * @param phone
     * @return
     */
    public User findUserByPhone(String phone) {
        return userDao.findUserByPhone(phone);
    }
    /**
     * 查找所有User，并按降序排序好
     * @return
     */
    public List<User> findAllUser() {
        return userDao.findAllUser();
    }

}
目前为止我们Service的实现全部完成，接下来要将Service交给Spring的容器托管，进行一些配置。

3）使用Spring托管Service依赖配置

在spring包下创建一个spring-service.xml文件，
然后采用注解的方式将Service的实现类加入到Spring IOC容器中(之前已经注解过了)
@Service
        public class UserServiceImpl implements UserService{}


4）使用Spring声明式事务配置

声明式事务的使用方式:
1.早期使用的方式:ProxyFactoryBean+XMl.
2.tx:advice+aop命名空间，这种配置的好处就是一次配置永久生效。
3.注解@Transactional的方式。在实际开发中，建议使用第三种对我们的事务进行控制，优点见下面代码中的注释。
下面让我们来配置声明式事务，在spring-service.xml中添加对事务的配置:

spring-service.xml文件内容如下:
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context" xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd">

    <!--扫描service包下所有使用注解的类型，注意包的路径是否正确-->
    <context:component-scan base-package="com.fu.blogsystemmaven.service"/>

    <!--配置事务管理器-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <!--注入数据库连接池 ref中的dataSource对应spring-dao.xml中数据库连接池的dataSource，
                                    刚添加时报错是正常，等等运行起来，各种配置就位就可以了-->
        <property name="dataSource" ref="dataSource"></property>

    </bean>

    <!--配置基于注解的声明式事务
    默认使用注解来管理事务行为-->
    <tx:annotation-driven transaction-manager="transactionManager"></tx:annotation-driven>

</beans>

然后在Service实现类的方法中，在需要进行事务声明的方法上加上事务的注解:
@Transactional
    /**
     * 使用注解控制事务方法的优点:
     * 1.开发团队达成一致约定，明确标注事务方法的编程风格
     * 2.保证事务方法的执行时间尽可能短，不要穿插其他网络操作RPC/HTTP请求或者剥离到事务方法外部
     * 3.不是所有的方法都需要事务，如只有一条修改操作、只读操作不要事务控制
     */
    public int updateAndAddUser(String phoneU, String passwordU, String phoneI, String passwordI) {}

5.使用集成测试Service逻辑

进入UserService.java中，选中接口名UserService，右键go to->test-创建->选择Junit4,选择添加的测试方法，finish
然后IDEA会自动在test包的java包下为我们生成对UserService.java中所有方法的测试类UserServiceTest.java

UserServiceTest.java测试如下:
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring的配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void addUserByPam() throws Exception {
        int flag = userService.addUserByPam("18219111604", "123456");
        System.out.println("添加返回=="+flag);
    }

    @Test
    public void deleteUserByPhone() throws Exception {
        int flag = userService.deleteUserByPhone("18219111604");
        System.out.println("删除返回==" + flag);
    }

    @Test
    public void updateAndAddUser() throws Exception {
        int flag=0;
        try {
            flag = userService.updateAndAddUser(
                    "18219111605", "update1234", "18219111606", "123456add");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("更新返回==" + flag);
    }

    @Test
    public void findUserByPhone() throws Exception {
        User user = userService.findUserByPhone("18219111624");
        System.out.println("查找User==="+user);
    }

    @Test
    public void findAllUser() throws Exception {
        List<User> userList=userService.findAllUser();
        for (User user : userList) {
            System.out.println("User===="+user);
        }
    }

}

目前为止，Dao层和Service层的集成测试我们都已经完成，
接下来进行controller的开发编码工作，请查看我的下篇文章4、springMVC搭建controller