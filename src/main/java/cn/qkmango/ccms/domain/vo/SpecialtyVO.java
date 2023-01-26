package cn.qkmango.ccms.domain.vo;

import cn.qkmango.ccms.domain.entity.Specialty;

import java.io.Serializable;

/**
 * 专业 VO
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-11 13:32
 */
public class SpecialtyVO extends Specialty implements Serializable {

    private String facultyName;

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    @Override
    public String toString() {
        return "SpecialtyVO{" +
                "facultyName='" + facultyName + '\'' +
                ", id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                '}';
    }
}
