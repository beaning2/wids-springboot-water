package com.wids.utils;

import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * 测试Druid数据库连接池
 */
public class DruidUtilsTest {

    /**
     * 测试方法，获取创建的数据库连接池
     * 打印结果：
     * {
     * 	CreateTime:"2022-03-02 16:43:05",
     * 	ActiveCount:0,
     * 	PoolingCount:0,
     * 	CreateCount:0,
     * 	DestroyCount:0,
     * 	CloseCount:0,
     * 	ConnectCount:0,
     * 	Connections:[
     * 	]
     * }
     * 思考一个问题：我定义的druid.properties配置文件，声明了初始连接是5，但是	ActiveCount(有效的活动连接数量)是0  为什么？
     */
    @Test
    public void getDataSourceTest(){
        // 获取DruidUtils工具类中创建的连接池对象
        DataSource ds = DruidUtils.getDataSource();
        /**
         * 打印结果：
         * {
         * 	CreateTime:"2022-03-02 16:43:05",  连接池创建的时间
         * 	ActiveCount:0,    连接池里面的活动连接数量
         * 	PoolingCount:0,   连接池里面剩余的连接数量
         * 	CreateCount:0,  连接池里面连接对象的总数
         * 	DestroyCount:0, 已销毁的连接数量
         * 	CloseCount:0, 已归还的连接数量
         * 	ConnectCount:0,
         * 	Connections:[   它是一个集合，里面存储了Connection对象。此时表示连接池中还没有使用的Connection对象
         * 	]
         * }
         * 问题：我在配置文件中定义的连接池初始大小是5，最大活动大小为10。但是现在打印的都是0，这是为啥？
         * Druid连接池有一个延迟加载的机制。不是创建连接池成功就立马分配初始连接大小，而是第一次调用连接池里面的Connection对象时，分配
         * 初始连接大小
         * */
        System.out.println(ds);
    }

    /**
     * 获取Druid连接池里面的Connection对象。
     * 目的：观察获取Connection对象之前Druid连接池的状态和获取Connection对象之后Druid连接池的状态
     */
    @Test
    public void getConnectionTest(){
        DataSource ds = DruidUtils.getDataSource();
        System.out.println("获取Connection连接对象之前："+ds);
        try {
            Connection connection = DruidUtils.getConnection();
            System.out.println(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("获取Connection连接对象之After::::"+ds);
        /**
         * 获取Druid连接池里面Connection对象之后，连接池里面的对象发生了变化
         * 获取Connection连接对象之After::::{
         * 	CreateTime:"2022-03-03 09:26:26",
         * 	ActiveCount:1,   连接池里面的活动连接数量(此时我使用了Druid连接池里面的一个Connection对象，所以活动连接数量就是1)
         * 	PoolingCount:4, 连接池里面剩余的连接数量
         * 	CreateCount:5,  连接池里面连接对象的总数
         * 	DestroyCount:0, 已销毁的连接数量
         * 	CloseCount:0, 已归还的连接数量
         * 	ConnectCount:1,  连接池里面的活动连接数量(此时我使用了Druid连接池里面的一个Connection对象，所以活动连接数量就是1)
         * 	Connections:[    它是一个集合，里面存储了Connection对象。此时表示连接池中还没有使用的Connection对象
         *                {ID:1773283386, ConnectTime:"2022-03-03 09:26:26", UseCount:0, LastActiveTime:"2022-03-03 09:26:26"},
         *        {ID:581318631, ConnectTime:"2022-03-03 09:26:26", UseCount:0, LastActiveTime:"2022-03-03 09:26:26"},
         *        {ID:1877453512, ConnectTime:"2022-03-03 09:26:26", UseCount:0, LastActiveTime:"2022-03-03 09:26:26"},
         *        {ID:487694075, ConnectTime:"2022-03-03 09:26:26", UseCount:0, LastActiveTime:"2022-03-03 09:26:26"}
         * 	]
         * }
         * 小结：Druid连接池使用了延迟加载的方式。不是创建连接池就指定初始连接大小，而是第一次使用连接池里面的Connection时指定初始连接大小
         */
    }

    /**
     * 连接池最大的连接大小为10 maxActive=10，此时连续调用11次 DruidUtils.getConnection();方法会发生什么情况
     * 打印结果：
     * com.mysql.jdbc.JDBC4Connection@7ce3cb8e
     * com.mysql.jdbc.JDBC4Connection@78b66d36
     * com.mysql.jdbc.JDBC4Connection@5223e5ee
     * com.mysql.jdbc.JDBC4Connection@bef2d72
     * com.mysql.jdbc.JDBC4Connection@69b2283a
     * com.mysql.jdbc.JDBC4Connection@22a637e7
     * com.mysql.jdbc.JDBC4Connection@6fe7aac8
     * com.mysql.jdbc.JDBC4Connection@1d119efb
     * com.mysql.jdbc.JDBC4Connection@659a969b
     * com.mysql.jdbc.JDBC4Connection@76908cc0
     * com.alibaba.druid.pool.GetConnectionTimeoutException: wait millis 3002, active 10, maxActive 10, creating 0
     * 小结：最大连接数量为10，但是调用了11次getConnection方法。此时会等待3000毫秒(maxWait=3000),如果3000毫秒之后还没有
     * 可用的Connection对象就会抛出异常(GetConnectionTimeoutException)
     * 工作中如何避免GetConnectionTimeoutException异常？需要在Connection对象使用完毕之后归还连接(调用close方法)]
     * 当我调用了close方法之后打印结果如下：再也不会抛出GetConnectionTimeoutException异常
     * 重点：使用完连接对象一定要归还，MyBatis-Plus框架帮我们自动的归还了连接
     * com.mysql.jdbc.JDBC4Connection@7ce3cb8e
     * com.mysql.jdbc.JDBC4Connection@7ce3cb8e
     * com.mysql.jdbc.JDBC4Connection@7ce3cb8e
     * com.mysql.jdbc.JDBC4Connection@7ce3cb8e
     * com.mysql.jdbc.JDBC4Connection@7ce3cb8e
     * com.mysql.jdbc.JDBC4Connection@7ce3cb8e
     * com.mysql.jdbc.JDBC4Connection@7ce3cb8e
     * com.mysql.jdbc.JDBC4Connection@7ce3cb8e
     * com.mysql.jdbc.JDBC4Connection@7ce3cb8e
     * com.mysql.jdbc.JDBC4Connection@7ce3cb8e
     * com.mysql.jdbc.JDBC4Connection@7ce3cb8e
     */
    @Test
    public void getConnectionTest2() {
        try {
            for (int i = 0; i < 11; i++) {
                Connection connection = DruidUtils.getConnection();
                System.out.println(connection);
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
