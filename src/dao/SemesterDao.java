package dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import pojo.Semesters;
import util.HibernateUtil;

import java.util.List;

public class SemesterDao {
    public static List<Semesters> getAll(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Semesters> semesters = null;

        try{
            final String hql = "select se from Semesters se";
            Query query = session.createQuery(hql);

            semesters = query.list();
        } catch (HibernateException e) {
            System.out.println(e);
        } finally {
            session.close();
        }
        return semesters;
    }

    public static Semesters getById(int Id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Semesters semesters = null;

        try{
            final String hql = "select se from Semesters se where se.id =:id";

            Query query = session.createQuery(hql);
            query.setParameter("id", Id);

            semesters = (Semesters) query.list().get(0);
        } catch (HibernateException e) {
            System.out.println(e);
        } finally {
            session.close();
        }
        return semesters;
    }

    public static void delete(Semesters semesters) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try{
            final String hql = "delete from Semesters se where se.id =:id";
            Query query = session.createQuery(hql);

            query.setParameter("id", semesters.getId());
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

    public static void save(Semesters semesters) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try{
            session.save(semesters);
            System.out.println("Command successfully executed....");

        } catch (HibernateException e) {
            System.out.println(e);
        } finally {
            transaction.commit();
            session.clear();
            session.close();
        }
    }

    public static void update(Semesters semesters) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try{
            final String hql = "update Semesters set type=:type, schoolYear=:schoolYear," +
                    "startDate=:startDate, endDate=:endDate,isCurrent=:isCurrent where id=:id";
            Query query = session.createQuery(hql);

            query.setParameter("id", semesters.getId());
            query.setParameter("type", semesters.getType());
            query.setParameter("schoolYear", semesters.getSchoolYear());
            query.setParameter("startDate", semesters.getStartDate());
            query.setParameter("endDate", semesters.getEndDate());
            query.setParameter("isCurrent", semesters.getIsCurrent());

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
