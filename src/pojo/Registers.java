package pojo;

import java.sql.Timestamp;
import java.util.Objects;

public class Registers {
    private int id;
    private Timestamp time;
    private Users users;
    private Courses courses;

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Courses getCourses() {
        return courses;
    }

    public void setCourses(Courses courses) {
        this.courses = courses;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return id == registers.id && Objects.equals(time, registers.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, time);
    }
}
