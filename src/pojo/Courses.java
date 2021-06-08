package pojo;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Courses {
    private int id;
    private String tutorName;
    private String room;
    private int weekDay;
    private int timeCase;
    private int maxSlot;
    private Semesters semesters;
    private Subjects subjects;
    private Set<ClassCourse> classCourseSet = new HashSet<ClassCourse>(0);
    private Set<Registers> registersSet = new HashSet<Registers>(0);

    public Set<Registers> getRegistersSet() {
        return registersSet;
    }

    public void setRegistersSet(Set<Registers> registersSet) {
        this.registersSet = registersSet;
    }

    public Set<ClassCourse> getClassCourseSet() {
        return classCourseSet;
    }

    public void setClassCourseSet(Set<ClassCourse> classCourseSet) {
        this.classCourseSet = classCourseSet;
    }

    public Semesters getSemesters() {
        return semesters;
    }

    public void setSemesters(Semesters semesters) {
        this.semesters = semesters;
    }

    public int getId() {
        return id;
    }

    public Subjects getSubjects() {
        return subjects;
    }

    public void setSubjects(Subjects subjects) {
        this.subjects = subjects;
    }

    public void setId(int id) {
        this.id = id;
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
        return id == courses.id && weekDay == courses.weekDay && timeCase == courses.timeCase && maxSlot == courses.maxSlot && Objects.equals(tutorName, courses.tutorName) && Objects.equals(room, courses.room);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tutorName, room, weekDay, timeCase, maxSlot);
    }
}
