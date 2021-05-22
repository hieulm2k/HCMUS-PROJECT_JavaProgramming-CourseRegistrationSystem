package pojo;

import java.util.Objects;

public class Courses {
    private int id;
    private int semesterId;
    private int subjectId;
    private String tutorName;
    private String room;
    private int weekDay;
    private int timeCase;
    private int maxSlot;

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

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getTutorName() {
        return tutorName;
    }

    public void setTutorName(String tutorName) {
        this.tutorName = tutorName;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(int weekDay) {
        this.weekDay = weekDay;
    }

    public int getTimeCase() {
        return timeCase;
    }

    public void setTimeCase(int timeCase) {
        this.timeCase = timeCase;
    }

    public int getMaxSlot() {
        return maxSlot;
    }

    public void setMaxSlot(int maxSlot) {
        this.maxSlot = maxSlot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Courses courses = (Courses) o;
        return id == courses.id && semesterId == courses.semesterId && subjectId == courses.subjectId && weekDay == courses.weekDay && timeCase == courses.timeCase && maxSlot == courses.maxSlot && Objects.equals(tutorName, courses.tutorName) && Objects.equals(room, courses.room);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, semesterId, subjectId, tutorName, room, weekDay, timeCase, maxSlot);
    }
}
