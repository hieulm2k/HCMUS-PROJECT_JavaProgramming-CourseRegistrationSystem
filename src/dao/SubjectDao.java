package dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import pojo.Subjects;
import util.HibernateUtil;

import java.util.List;

public class SubjectDao {
    public static List<Subjects> getAll(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Subjects> subjects = null;

        try{
            final String hql = "select sb from Subjects sb";
            Query query = session.createQuery(hql);

            subjects = query.list();
        } catch (HibernateException e) {
            System.out.println(e);
        } finally {
            session.close();
        }
        return subjects;
    }

    public static Subjects getById(int Id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Subjects subjects = null;

        try{
            final String hql = "select sb from Subjects sb where sb.id =:id";

            Query query = session.createQuery(hql);
            query.setParameter("id", Id);

            subjects = (Subjects) query.list().get(0);
        } catch (HibernateException e) {
            System.out.println(e);
        } finally {
            session.close();
        }
        return subjects;
    }

    public static void delete(Subjects subjects) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try{
            final String hql = "delete from Subjects sb where sb.id =:id";
            Query query = session.createQuery(hql);

            query.setParameter("id", subjects.getId());
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

    public static void save(Subjects subjects) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try{
            session.save(subjects);
            System.out.println("Command successfully executed....");

        } catch (HibernateException e) {
            System.out.println(e);
        } finally {
            transaction.commit();
            session.clear();
            session.close();
        }
    }

    public static void update(Subjects subjects) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try{
            final String hql = "update Subjects set code=:code, name=:name, credit=:credit where id=:id";
            Query query = session.createQuery(hql);

            query.setParameter("id", subjects.getId());
            query.setParameter("code", subjects.getCode());
            query.setParameter("name", subjects.getName());
            query.setParameter("credit", subjects.getCredit());

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
