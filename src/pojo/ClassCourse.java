package pojo;

import java.util.Objects;

public class ClassCourse {
    private int id;
    private int classId;
    private int courseId;
    private Classes classes;
    private Courses courses;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }

    public Courses getCourses() {
        return courses;
    }

    public void setCourses(Courses courses) {
        this.courses = courses;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassCourse that = (ClassCourse) o;
        return id == that.id && classId == that.classId && courseId == that.courseId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, classId, courseId);
    }
}
