package com.wids.test;

import com.wids.lambda.Calc;
import com.wids.lambda.impl.CalcImpl;

/**
 * 在控制台打印99乘法表
 */
public class ChengFaBiaoTest {

    /**
     * 向控制台打印99乘法表
     * @param calc 99乘法表的计算接口，封装了乘法表的计算功能
     */
    public static void printChengFaBiao(Calc calc) {
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= i; j++) {
                // 调用计算接口，获取列行的乘积
                int result = calc.doCalc(j, i);
                System.out.print(result+" ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        // 调用printChengFaBiao方法完成99乘法表的打印
        // 第一种方式：接口显示的创建实现类，将其作为实参传入到printChengFaBiao方法中
        // 缺点：JDK1.0(1995 16MB)的时候这种写法对内存消耗相当大
        // printChengFaBiao(new CalcImpl());
        // 为了解决上面的缺点，匿名内部类横空出世。匿名内部类存在的目的代替显示创建的接口实现类
        // 匿名内部类的doCalc方法什么时候会调用
        // 匿名内部类有点：使用方便不需要显示的定义接口实现类，内存和磁盘消耗相对较少(相对JDK1.0)
        // 匿名内部类缺点：代码不易阅读，难点是不知道何时调用匿名内部类的方法。通常都是有外部类去调用(接口回调)
//        printChengFaBiao(new Calc() {
//            @Override
//            public int doCalc(int first, int second) {
//                return first * second;
//            }
//        });
        // Lambda的存在就是为了解决匿名内部类的缺点，它能够简化匿名内部类的开发
        //  printChengFaBiao((int first ,int second) -> {return  first * second;});
        // 还可以进一步的做简化
        printChengFaBiao((first , second) -> first * second);
        // 小结1：Lambda表达式简化了开发，省略了new接口，省略了方法名称
        // 小结2: Lambda表达式，忽略参数类型，如果函数体只有一行return可以省略、大括号可以省略、分号也可以省略；
        // 小结3： ->是Lambda表达式的核心必须有
        // 小结4：Lambda表达式依赖的接口只能有一个抽象方法，否则Lambda表达式就会报错
        // 小结5：Lambda表达式完成的99乘法表计算的行为我们定义在printChengFaBiao的参数里面了，所以(first , second) -> first * second又叫做行为参数化
        // 小结6：Lambda表达式依赖的接口有一个专门的注解@FunctionalInterface,它表示函数式接口。一旦某个接口定义了该注解它就能够支持Lambda
        // 例如：Runnable接口定义了@FunctionalInterface注解，就表示Runnable接口可以使用Lambda表达式(函数式编程)
    }
}
