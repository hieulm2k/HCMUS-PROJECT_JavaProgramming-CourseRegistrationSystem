package dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import pojo.Courses;
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
            final String hql = "update Courses set semesterId=:semesterId, subjectId=:subjectId," +
                    "tutorName=:tutorName, room=:room, weekDay=:weekDay, timeCase=:timeCase, maxSlot=:maxSlot  where id=:id";
            Query query = session.createQuery(hql);

            query.setParameter("id", courses.getId());
            query.setParameter("semesterId", courses.getSemesterId());
            query.setParameter("subjectId", courses.getSubjectId());
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
