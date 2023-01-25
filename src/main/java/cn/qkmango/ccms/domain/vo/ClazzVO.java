package cn.qkmango.ccms.domain.vo;

import cn.qkmango.ccms.domain.entity.Clazz;
import cn.qkmango.ccms.domain.entity.Faculty;
import cn.qkmango.ccms.domain.entity.Specialty;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * 班级VO
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-12 13:58
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClazzVO implements Serializable {
    private Clazz clazz;
    private Faculty faculty;
    private Specialty specialty;

    public Clazz getClazz() {
        return clazz;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    @Override
    public String toString() {
        return "ClazzVO{" +
                "clazz=" + clazz +
                ", faculty=" + faculty +
                ", specialty=" + specialty +
                '}';
    }
}
