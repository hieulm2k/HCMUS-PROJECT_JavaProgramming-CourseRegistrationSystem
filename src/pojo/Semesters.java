package pojo;

import java.sql.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Semesters {
    private int id;
    private int type;
    private String schoolYear;
    private Date startDate;
    private Date endDate;
    private byte isCurrent;
    private Set<Sessions> sessionsSet = new HashSet<Sessions>(0);
    private Set<Courses> coursesSet = new HashSet<Courses>(0);

    public Set<Courses> getCoursesSet() {
        return coursesSet;
    }

    public void setCoursesSet(Set<Courses> coursesSet) {
        this.coursesSet = coursesSet;
    }

    public Set<Sessions> getSessionsSet() {
        return sessionsSet;
    }

    public void setSessionsSet(Set<Sessions> sessionsSet) {
        this.sessionsSet = sessionsSet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public byte getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(byte isCurrent) {
        this.isCurrent = isCurrent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Semesters semesters = (Semesters) o;
        return id == semesters.id && type == semesters.type && isCurrent == semesters.isCurrent && Objects.equals(schoolYear, semesters.schoolYear) && Objects.equals(startDate, semesters.startDate) && Objects.equals(endDate, semesters.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, schoolYear, startDate, endDate, isCurrent);
    }
}
