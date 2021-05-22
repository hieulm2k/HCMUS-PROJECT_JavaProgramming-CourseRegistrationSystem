package pojo;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Classes {
    private int id;
    private String name;
    private Set<ClassCourse> classCourseSet = new HashSet<ClassCourse>(0);

    public Set<ClassCourse> getClassCourseSet() {
        return classCourseSet;
    }

    public void setClassCourseSet(Set<ClassCourse> classCourseSet) {
        this.classCourseSet = classCourseSet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Classes classes = (Classes) o;
        return id == classes.id && Objects.equals(name, classes.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Classes{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
