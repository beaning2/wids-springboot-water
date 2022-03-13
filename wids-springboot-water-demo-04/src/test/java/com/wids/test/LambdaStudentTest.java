package com.wids.test;

import com.wids.entities.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
public class LambdaStudentTest {
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
        /**
         * 步骤：
         * 1 将list集合转换为Stream流
         * 2 对Stream流进行一系列的中间件(工序)操作： 过滤 提取 去重 排序 限制
         * 3 将流水线转换为终端List<Integer>
         * 备注：第二步是重点
         */
        Stream<Student> stuStream = list.stream();
        // 去掉年龄小于18的学生
        Stream<Student> stuHeightStream = stuStream.filter(student -> student.getStuAge() >= 18);
        // 提取学生身高
        Stream<Integer> finalHgtStream = stuHeightStream.map(student -> student.getStuHeight());
        // 去掉重复的升高
        Stream<Integer> distinctStream = finalHgtStream.distinct();
        // 排序
        Stream<Integer> sortedStream = distinctStream.sorted(Collections.reverseOrder());
        // 限制3个
        Stream<Integer> limitStream = sortedStream.limit(3);
        // 将流水线转换为终端List<Integer>
        List<Integer> finalHeightList = limitStream.collect(Collectors.toList());
        System.out.println("最终的结果："+finalHeightList);

    }
}
