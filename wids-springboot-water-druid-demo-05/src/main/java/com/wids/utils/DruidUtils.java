package com.wids.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * 数据库连接池工具类，用来创建数据库连接池的
 * 所有厂商的数据库连接池必须遵循一个标准(javax.sql.DataSource)
 * 所有数据库连接池厂商必须实现javax.sql.DataSource标准
 * 创建数据库连接池对象步骤：
 * 1 定义DataSource
 * 2 定义static block，创建数据库连接池(使用DruidDataSourceFactory工厂创建)
 * 3 定义静态方法将Druid连接池对象(DataSource)提供给外界使用
 * 4 定义静态方法将连接对象(Connection)提供给外界使用
 */
public class DruidUtils {

    /**
     * 一个项目只有一个连接池对象，所以使用static修饰
     * dataSource编译期是一个接口，运行期是一个Druid连接池对象(指向DruidDataSource)
     */
    private static DataSource dataSource ;
    // 在静态块中创建数据库连接池

    /**
     * 步骤：
     * 1 将磁盘配置文件(druid.properties)加载到IO管道
     * 2 将IO管道加载到Properties缓存
     * 注意：使用当前线程的类加载器去加载
     * 3 创建Druid连接池对象(使用DruidDataSourceFactory工厂创建),并将创建的对象返回给DataSource对象
     * 小结：
     * 1 磁盘配置文件使用当前线程的类加载器加载到IO(管道)中
     * 2 将IO管道中的数据加载到缓存(Properties)
     * 3 使用Druid工厂去创建Druid连接池，并且将Properties缓存里面的连接池配置信息告诉Druid
     * 4 DataSource getDataSource()是一个工具方法，目的是提供给外界使用我创建的Druid连接池
     * 5 Connection getConnection()是一个工具方法，目的将Druid连接池里面的Connection对象给拱给外界去处理请求
     */
    static {
        try (
                // Thread.currentThread().getContextClassLoader() 表示当前线程的类加载器
                // getResourceAsStream获取当前线程类加载器的流资源，将磁盘配置文件druid.properties加载到IO流(InputStream)
                InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("druid.properties");
                ){
            // 将io流对象(里面存储了数据库连接池的配置信息)加载到缓存
            Properties props = new Properties();
            props.load(in);
            // 创建数据库连接池对象
            dataSource = DruidDataSourceFactory.createDataSource(props);
        } catch (Exception e) {
            System.err.println("数据库连接池创建失败");
            throw new RuntimeException("数据库连接池创建失败");
        }
    }

    /**
     * 提供静态方法，返回静态块创建的Druid连接池对象给外界使用
     * @return Druid连接池对象
     */
    public static  DataSource getDataSource(){
        return dataSource;
    }

    /**
     * 获取Druid连接池里面的Connection对象，返回给外界(提供给外界使用)
     * 什么是外界？除了当前类DruidUtils以外的地方都是外界
     * @return Connection对象
     * @throws Exception
     */
    public static Connection getConnection() throws Exception {
        return dataSource.getConnection();
    }
}
