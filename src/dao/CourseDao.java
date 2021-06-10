package dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import pojo.Courses;
import pojo.Semesters;
import util.HibernateUtil;

import java.util.List;

public class CourseDao {
    public static List<Courses> getAll(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Courses> cours = null;

        try{
            final String hql = "select cl from Courses cl";
            Query query = session.createQuery(hql);

            cours = query.list();
        } catch (HibernateException e) {
            System.out.println(e);
        } finally {
            session.close();
        }
        return cours;
    }

    public static List<Courses> getAllOfSemester(int semesId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Courses> course = null;

        try{
            final String hql = "select cl from Courses cl where cl.semesters.id=:id";
            Query query = session.createQuery(hql);
            query.setParameter("id", semesId);
            course = query.list();
        } catch (HibernateException e) {
            System.out.println(e);
            course = null;
        } finally {
            session.close();
        }
        return course;
    }

    public static Courses getById(int Id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Courses courses = null;

        try{
            final String hql = "select c from Courses c where c.id =:id";

            Query query = session.createQuery(hql);
            query.setParameter("id", Id);

            courses = (Courses) query.list().get(0);
        } catch (HibernateException e) {
            System.out.println(e);
        } finally {
            session.close();
        }
        return courses;
    }

    public static Courses getBySubjectNClass(String subjectName, String classname) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Courses courses = null;

        try{
            final String hql = "select c from Courses c where c.subjects.name=:subject and c.classes.name=:class";

            Query query = session.createQuery(hql);
            query.setParameter("subject", subjectName);
            query.setParameter("class", classname);
            if(query.list().stream().count()>0){
                courses = (Courses) query.list().get(0);
            }
        } catch (HibernateException e) {
            System.out.println(e);
        } finally {
            session.close();
        }
        return courses;
    }

    public static Courses getByRoomDayTime(String room, int day, int time) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Courses courses = null;

        try{
            final String hql = "select c from Courses c where c.room=:room and c.weekDay=:day and c.timeCase=:time";

            Query query = session.createQuery(hql);
            query.setParameter("room", room);
            query.setParameter("day", day);
            query.setParameter("time", time);

            if(query.list().stream().count()>0){
                courses = (Courses) query.list().get(0);
            }
        } catch (HibernateException e) {
            System.out.println(e);
        } finally {
            session.close();
        }
        return courses;
    }

    public static void delete(Courses courses) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try{
            final String hql = "delete from Courses c where c.id =:id";
            Query query = session.createQuery(hql);

            query.setParameter("id", courses.getId());
            int res = query.executeUpdate();

            System.out.println("Command successfully executed....");
            System.out.println("Numer of records effected...,"+res);

        } catch (HibernateException e) {
            System.out.println(e);
        } finally {
            transaction.commit();
            session.clear();
            session.close();
        }
    }

    public static void save(Courses courses) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try{
            session.save(courses);
            System.out.println("Command successfully executed....");

        } catch (HibernateException e) {
            System.out.println(e);
        } finally {
            transaction.commit();
            session.clear();
            session.close();
        }
    }

    public static void update(Courses courses) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try{
            final String hql = "update Courses set semesters=:semesters, subjects=:subjects," +
                    "tutorName=:tutorName, room=:room, weekDay=:weekDay, timeCase=:timeCase, maxSlot=:maxSlot  where id=:id";
            Query query = session.createQuery(hql);

            query.setParameter("id", courses.getId());
            query.setParameter("semesters", courses.getSemesters());
            query.setParameter("subjects", courses.getSubjects());
            query.setParameter("tutorName", courses.getTutorName());
            query.setParameter("room", courses.getRoom());
            query.setParameter("weekDay", courses.getWeekDay());
            query.setParameter("timeCase", courses.getTimeCase());
            query.setParameter("maxSlot", courses.getMaxSlot());

            int res = query.executeUpdate();

            System.out.println("Command successfully executed....");
            System.out.println("Numer of records effected...,"+res);

        } catch (HibernateException e) {
            System.out.println(e);
        } finally {
            transaction.commit();
            session.clear();
            session.close();
        }
    }
}
