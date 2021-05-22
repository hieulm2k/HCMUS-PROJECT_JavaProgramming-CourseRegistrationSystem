package pojo;

import java.sql.Timestamp;
import java.util.Objects;

public class Registers {
    private int id;
    private int studentId;
    private int courseId;
    private Timestamp time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Registers registers = (Registers) o;
        return id == registers.id && studentId == registers.studentId && courseId == registers.courseId && Objects.equals(time, registers.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, studentId, courseId, time);
    }
}
