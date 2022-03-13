package com.wids.test;

import com.wids.entities.Student;

import java.util.*;

/**
 场景：有一个集合存储了若干个Student(学号、姓名、年龄、爱好、身高)元素，从集合中取出最高的三个学生身高(189,188,187)
 要求：年龄必须大于18、年龄不能重复、降序、限制3个
 步骤：
 ​      源头：List<Student>
 ​     中间件：
 ​          第一个流水线过滤掉小于18的学生
      ​	    第二个流水线提取学生(Stduent)的身高(int)
 ​          第三个流水线去掉重复的身高(如图：有三个绿色的菱形，需要去掉重复的只保留一个)
 ​          第四个流水线对升高进行降序排序(例如：189,188,187,186)
 ​          第五个流水线返回限制的个数，只保留3个(如图：从4个菱形中暴露三个)
 ​      终端：将流水线对象Stream转换为新的集合List<Integer>
  注意：首先使用JDK7之前的做法完成
  小结：以上是JDK之前的做法完成了功能，但是很复杂，代码写的越多BUG越多。可以使用JDK8提供的Lambda表达式和Stream流来简化操作
 */
public class TestStudent {
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
        // 第一个流水线过滤掉年龄小于18的学生
        List<Student> studentList = new ArrayList<>();
        for (Student student : list) {
            if (student.getStuAge() >= 18) {
                studentList.add(student);
            }
        }
        //  第二个流水线提取学生(Stduent)的身高(int)
        // ageList身高存储学生的升高
        List<Integer> ageList = new ArrayList<>();
        for (Student student : studentList) {
            ageList.add(student.getStuHeight());
        }
        System.out.println(ageList);
        // 第三个流水线去掉重复的身高。[181, 188, 189, 176, 189, 176]此时有两个189，我们需要去掉重复的数据
        Set<Integer> ageSet = new HashSet<>(ageList);
        ageList.clear();
        ageList.addAll(ageSet);
        System.out.println(ageList);
        //  第四个流水线对升高进行降序排序
        ageList.sort(Collections.reverseOrder());
        System.out.println(ageList);
        // 第五个流水线返回限制的个数，只保留3个(如图：从4个菱形中暴露三个)
        List<Integer> finalAgeList = new ArrayList<>();
        for (int i = 0; i < ageList.size(); i++) {
            if (i == 3) {
                break;
            }
            finalAgeList.add(ageList.get(i));
        }
        System.out.println("最终的学生升高："+finalAgeList);
    }

}
