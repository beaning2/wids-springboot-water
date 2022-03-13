package com.wids.test;


import com.wids.entities.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  场景：有一个集合存储了若干个Student(学号、姓名、年龄、爱好、身高)元素，从集合中取出最高的三个学生身高(189,188,187)
 *  要求：年龄必须大于18、年龄不能重复、降序、限制3个
 *  步骤：
 *  ​      源头：List<Student>
 *  ​     中间件：
 *  ​          第一个流水线过滤掉小于18的学生
 *       ​	    第二个流水线提取学生(Stduent)的身高(int)
 *  ​          第三个流水线去掉重复的身高(如图：有三个绿色的菱形，需要去掉重复的只保留一个)
 *  ​          第四个流水线对升高进行降序排序(例如：189,188,187,186)
 *  ​          第五个流水线返回限制的个数，只保留3个(如图：从4个菱形中暴露三个)
 *  ​      终端：将流水线对象Stream转换为新的集合List<Integer>
 */
public class LambdaTwoStudentTest {
    /**
     * 源头：原始的Student集合
     */
    private static List<Student> list = new ArrayList<>();

    static{
        list.add(new Student(101, "Jason", 18, "Football",181));
        list.add(new Student(109, "Robert", 16, "Football",171));
        list.add(new Student(103, "Jerry", 15, "Football",178));
        list.add(new Student(105, "Jackson", 17, "Football",187));
        list.add(new Student(102, "Martin", 19, "Football",188));
        list.add(new Student(106, "Stone", 23, "Football",189));
        list.add(new Student(108, "Lucy", 21, "Football",176));
        list.add(new Student(107, "Hanmeimei", 10, "Football",187));
        list.add(new Student(116, "Terry", 23, "Football",189));
        list.add(new Student(128, "Marry", 21, "Football",176));
        list.add(new Student(137, "Herry", 10, "Football",187));
    }

    public static void main(String[] args) {

        List<Integer> finalHeightList = list.stream()
                // 中间件
                .filter(stu -> stu.getStuAge() >= 18)
                .map(stu -> stu.getStuHeight())
                .distinct()
                .sorted(Collections.reverseOrder())
                .limit(3)
                // 将中间件的Stream流对象转换为集合
                .collect(Collectors.toList());
        // 最终的学生升高：：：：：[189, 188, 181]
        System.out.println("最终的学生升高：：：：："+finalHeightList);
        // 小结1：源头list.stream()，作用将集合转换为Stream流对象
        // 小结2： 此时中间件分为5步：过滤(filter)  提取(map)  去重(distinct)  排序(sorted)  限制(limit)
        //        中间件我们采用的是连式编程(击鼓传花)  类似StringBuffer的append
        // 小结3： 中间件转换为集合.collect(Collectors.toList());
        // 小结4： 工作中经常使用Lambda，很重要。练习练习再练习  。 选择性的掌握
    }
}
