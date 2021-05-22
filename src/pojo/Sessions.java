package pojo;

import java.sql.Timestamp;
import java.util.Objects;

public class Sessions {
    private int id;
    private int semesterId;
    private Timestamp start;
    private Timestamp end;
    private  Semesters semesters;

    public Semesters getSemesters() {
        return semesters;
    }

    public void setSemesters(Semesters semesters) {
        this.semesters = semesters;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sessions sessions = (Sessions) o;
        return id == sessions.id && semesterId == sessions.semesterId && Objects.equals(start, sessions.start) && Objects.equals(end, sessions.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, semesterId, start, end);
    }
}
