package pojo;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Subjects {
    private int id;
    private String code;
    private String name;
    private Integer credit;
    Set<Courses> coursesSet = new HashSet<Courses>(0);

    public Set<Courses> getCoursesSet() {
        return coursesSet;
    }

    public void setCoursesSet(Set<Courses> coursesSet) {
        this.coursesSet = coursesSet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subjects subjects = (Subjects) o;
        return id == subjects.id && Objects.equals(code, subjects.code) && Objects.equals(name, subjects.name) && Objects.equals(credit, subjects.credit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name, credit);
    }
}
