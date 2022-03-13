package com.wids.mapper;

import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wids.entities.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * SpringBoot的测试类，用来测试MyBatis-Plus常用的方法
 * 被@SpringBootTest修饰的类是SpringBoot的测试类
 *
 * MyBatis-Plus小结：
 * 1 它提供了现成的CRUD方法供我们调用，提高开发效率。
 * 2 我们只需要将表名称和列名称放到实体类中
 * 3 常用的方法：
 * selectList()查询多条数据
 * selectOne()根据条件查询一行数据
 * insert()插入一行数据
 * delete()删除一行数据
 * update()修改(更新数据)数据
 * 明天：
 * 1 剩下的MyBatis-Plus讲完
 * 2 启动项目(送水工后台管理系统)，完成登录功能、客户列表功能
 * 作业1：练习反射（LinkedList，HashMap）,练习反射创建对象、调用方法、暴力破解
 * 作业2： 练习MyBatis-Plus常用的方法
 */
@SpringBootTest
public class CustomerMapperTests {

    @Autowired
    private CustomerMapper customerMapper;

    /**
     * 客户地址等于武汉并且水票数量大于20
     * 步骤：
     * 1 创建QueryWrapper对象
     * 2 封装查询条件(多条件)
     * 3 调用selectList方法执行查询并且注入查询条件
     * 4 打印结果
     */
    @Test
    public void andTest() {
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        // 链式编程封装查询条件
        // 下面的eq和gt会在程序执行期MyBatis-Plus框架帮我们自动转换为WHERE (cust_address = ? AND cust_ticket > ?)
        // gt表示大于
        queryWrapper.eq("cust_address","武汉").gt("cust_ticket",20);
        List<Customer> list = customerMapper.selectList(queryWrapper);
        // 获取customerMapper在运行期的Class对象
        Class<? extends CustomerMapper> custMapperClazz = customerMapper.getClass();
        System.out.println(custMapperClazz);
        // 获取clazz的父类型
        System.out.println("custMapperClazz父类型：");
        Class<?> superclass = custMapperClazz.getSuperclass();
        System.out.println(superclass);
        // 获取clazz实现的所有接口
        System.out.println("custMapperClazz实现的接口：");
        Class<?>[] interfaces = custMapperClazz.getInterfaces();
        for (Class<?> anInterface : interfaces) {
            System.out.println(anInterface);
        }
        for (Customer customer : list) {
            System.out.println(customer);
        }
    }

    /**
     * 对tb_customer表的id列进行降序排序
     * 小结：
     *  queryWrapper.orderByDesc("id");方法执行期MyBatis-Plus框架会转换为对应的SQL语句 ORDER BY id DESC
     */
    @Test
    public void orderByDescTest() {
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        // 对id列进行降序排序 ORDER BY id DESC
        queryWrapper.orderByDesc("id");
        List<Customer> list = customerMapper.selectList(queryWrapper);
        for (Customer customer : list) {
            System.out.println(customer);
        }
    }

    /**
     * 区间查询客户ID在1到3之间的所有客户信息
     *
     * 小结：
     *         queryWrapper.between("id",1,3); 区间查询程序执行期将会转换为  WHERE (id BETWEEN ? AND ?)
     *         该方法支持3个参数 参数1：表示列名称 参数2：表示区间查询的小值，参数3：表示取件查询的大值
     */
    @Test
    public void betweenAndTest() {
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("id",1,3);

        List<Customer> list = customerMapper.selectList(queryWrapper);
        for (Customer customer : list) {
            System.out.println(customer);
        }
    }

    /**
     * 模糊查询：查询所有包含“老”的所有客户信息
     * 步骤：
     * 1 创建QueryWrapper对象
     * 2 设置查询条件  qw.like("cust_name","老"); ---->  where cust_name '%老%'
     * 3 调用CustomerMapper对象的selectList方法，并注入QueryWrapper对象
     * 4 打印查询结果
     * SELECT id,cust_name,cust_address,cust_mobile,cust_ticket
     * FROM tb_customer
     * WHERE (cust_name LIKE ?)
     * 还SQL语句一共涉及到三个关键字： select from where
     * 问题：MySQL在执行SQL语句的时候先执行那个关键字，或者说这些关键字执行的顺序是啥？
     * 执行顺序  from--->where--->select
     */
    @Test
    public void likeTest() {
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("cust_name","老");
        List<Customer> customerList = customerMapper.selectList(queryWrapper);
        for (Customer customer : customerList) {
            System.out.println(customer);
        }
    }


    /**
     * 查询客户名称不等于老王的客户信息
     * 步骤：
     * 1 创建QueryWraper对象
     * 2 封装查询条件
     * 3 调用customerMapper对象的selectList方法查询数据，并注入QueryWrapper
     * 4 打印查询结果
     * 小结：
     * ne全程not equals 表示不等于，该方法接受两个参数：参数1:表示列名称，参数2：表示对应的值
     *
     */
    @Test
    public void selectNotTest(){
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("cust_name","老王");
        List<Customer> list = customerMapper.selectList(queryWrapper);
        for (Customer customer : list) {
            System.out.println(customer);
        }
    }

    /**
     * 根据ID删除对应的客户信息
     * 步骤：
     * 1 定义QueryWrapper，封装要删除的条件
     * 2 设置删除条件
     * 3 调用MyBatis-Plus内置的delete方法，并将QueryWrapper注入到该方法中完成删除，返回受影响行数
     * 4 打印删除受影响行数
     */
    @Test
    public void deleteCustomerByIdTest() {
        QueryWrapper<Customer> qw = new QueryWrapper<>();
        qw.eq("id",7);
        int rows = customerMapper.delete(qw);
        System.out.println("delete customer rows = "+rows);

    }

    /**
     * 修改tb_customer表里面的数据
     * 步骤：
     * 1 创建Customer对象,封装了要修改的数据
     * 2 设置要修改的数据
     * 3 创建QueryWrapper对象
     * 4 设置要修改的主键(cid)值
     * 5 调用CustomerMapper对象的update方法修改数据，并返回结果
     * 6 打印受影响的行数
     */
    @Test
    public void updateCustomerTest() {
        Customer cust = new Customer();
        cust.setCid(3);
        cust.setCustName("老方");
        cust.setCustAddress("厦门");
        cust.setCustMobile("13956786543");
        cust.setCustTicket(800);

        // 封装where条件
        QueryWrapper<Customer> qw = new QueryWrapper<>();
        qw.eq("cid",cust.getCid());
        // update方法是MyBatis-Plus提供的内置方法用来做修改
        // 该方法支持两个参数，参数1：采集要修改的数据，参数2：封装where后面的查询条件
        int rows = customerMapper.update(cust, qw);
        System.out.println("update rows = "+rows);
    }

    /**
     * 根据ID查询对应的客户信息(1条）
     * 步骤：
     * 1 创建QueryWrapper对象封装SQL语句where查询条件
     * 2 设置where查询条件
     * 3 调用MyBatis-Plus内置的方法selectOne，并且将查询条件注入到该方法中
     * 4 打印查询结果
     * 小结：selectOne方法是BaseMapper提供的方法用来查询某一行数据，该方法通常要注入查询条件
     */
    @Test
    public void getCustomerByIdTest(){
        QueryWrapper<Customer> qw = new QueryWrapper<>();
        // eq全称是equals，该方法有两个参数：参数1:定义查询的列名称，参数2:定义查询的列值
        // 程序执行的时候根据方法的参数会自动生成where id = 3
        //  WHERE (id = ?)
        qw.eq("id",3);
        Customer customer = customerMapper.selectOne(qw);
        System.out.println("result = "+customer);
    }

    /**
     * 向tb_customer表插入一行数据
     * 步骤：
     * 1 采集要插入的数据
     * 2 调用insert方法保存插入的数据
     * 3 打印受影响行数
     */
    @Test
    public void saveCustomerTest(){
        // 采集的数据
        Customer cust = new Customer();
        cust.setCustName("老曹");
        cust.setCustMobile("18571563786");
        cust.setCustAddress("大连");
        cust.setCustTicket(500);
        // 调用MyBatis-Plus内置的方法插入数据，并返回受影响行数
        // INSERT INTO tb_customer ( cust_name, cust_address, cust_mobile, cust_ticket ) VALUES ( ?, ?, ?, ? )
        // MyBatis-Plus能够帮助我们自动生成SQL语句
        // rows表示执行DML返回的受影响行数，大于0插入成功，否则插入失败
        int rows = customerMapper.insert(cust);
        System.out.println(rows);
    }

    /**
     * 查询tb_customer表所有的数据
     */
    @Test
    public void listCustomerTest() {

        /**
         * SELECT id,cust_name,cust_address,cust_mobile,cust_ticket FROM tb_customer
         * 上面的SQL语句我没有定义，MyBatis-Plus自动帮我们生成的，因为它提供了内置的CRUD操作
         * 我只需要将查询的表名称通过实体类的@TableName("tb_customer)注解告诉MyBatis-Plus
         * 然后MyBatis-Plus在运行期使用反射获取表名称，然后获取属性名称，将驼峰属性名自动转换为下划线_
         *
         * 此时我们查询tb_customer表中的所有数据不需要where查询条件，所以selectList方法传入null即可
         *
         * MyBatis-Plus小结
         * 步骤：
         * 1 定义实体类，通过@TableName注解指定实体类对应的表名称，程序运行期完成映射
         * 2 定义Mapper接口，继承父接口BaseMapper并且指定反省
         * 3 定义SpringBoot测试类来测试MyBatis-Plus
         * 注意：
         * selectList方法是CustomerMapper父接口BaseMapper的方法，用来查询一个结果集，返回对应的列表
         *
         */
        List<Customer> list = customerMapper.selectList(null);
        for (Customer customer : list) {
            System.out.println(customer);
        }
    }
}
