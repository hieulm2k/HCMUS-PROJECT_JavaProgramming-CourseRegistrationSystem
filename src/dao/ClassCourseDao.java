package dao;

import org.hibernate.Transaction;
import pojo.ClassCourse;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;
import java.util.List;

public class ClassCourseDao {
    public static List<ClassCourse> getAll(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<ClassCourse> classCourse = null;

        try{
            final String hql = "select cl from ClassCourse cl";
            Query query = session.createQuery(hql);

            classCourse = query.list();
        } catch (HibernateException e) {
            System.out.println(e);
        } finally {
            session.close();
        }
        return classCourse;
    }

    public static ClassCourse getById(int Id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        ClassCourse classCourse  = null;

        try{
            final String hql = "select cl from ClassCourse cl where cl.id =:id";

            Query query = session.createQuery(hql);
            query.setParameter("id", Id);

            classCourse = (ClassCourse) query.list().get(0);
        } catch (HibernateException e) {
            System.out.println(e);
        } finally {
            session.close();
        }
        return classCourse;
    }

    public static void delete(ClassCourse classCourse) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try{
            final String hql = "delete from ClassCourse cl where cl.id =:id";
            Query query = session.createQuery(hql);

            query.setParameter("id", classCourse.getId());
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

    public static void save(ClassCourse classCourse) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try{
            session.save(classCourse);
            System.out.println("Command successfully executed....");

        } catch (HibernateException e) {
            System.out.println(e);
        } finally {
            transaction.commit();
            session.clear();
            session.close();
        }
    }

    public static void update(ClassCourse classCourse) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try{
            final String hql = "update ClassCourse set classId=:classid, courseId=:courseid where id=:id";
            Query query = session.createQuery(hql);
            query.setParameter("classid", classCourse.getClassId());
            query.setParameter("courseid", classCourse.getCourseId());
            query.setParameter("id", classCourse.getId());

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
