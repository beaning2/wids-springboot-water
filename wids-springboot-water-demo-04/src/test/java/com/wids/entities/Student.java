package com.wids.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 学生实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Comparable<Student> {

    private int id;

    private String stuName;

    private int stuAge;
    /**
     * 学生爱好
     */
    private String stuHobby;
    /**
     * 学生的升高
     */
    private int stuHeight;

    /**
     * 内部比较器，可以比较学生的ID，然后根据id进行升序排序
     **/

    @Override
    public int compareTo(Student o) {
        if (this.getId() > o.getId()) {
            return 1;
        } else if (this.getId() < o.getId()) {
            return -1;
        } else {
            return 0;
        }
    }
}
