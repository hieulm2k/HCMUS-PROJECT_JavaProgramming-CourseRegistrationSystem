package pojo;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

public class Sessions {
    private int id;
    private Date start;
    private Date end;
    private Semesters semesters;

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

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sessions sessions = (Sessions) o;
        return id == sessions.id && Objects.equals(start, sessions.start) && Objects.equals(end, sessions.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, start, end);
    }
}
