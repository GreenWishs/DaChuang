package com.jlu.mzx.tiaoji.tools;

/**
 * Created by gyl on 2016/8/18.
 */
public class Volunteer {
    private String teacher;
    private String school;

    public Volunteer(String teacher, String school) {
        this.teacher = teacher;
        this.school = school;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
