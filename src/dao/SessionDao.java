package dao;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import pojo.Sessions;
import util.HibernateUtil;

import java.util.List;

public class SessionDao {
    public static List<Sessions> getAll(){
        org.hibernate.Session session = HibernateUtil.getSessionFactory().openSession();
        List<Sessions> sessions = null;

        try{
            final String hql = "select ses from Sessions ses";
            Query query = session.createQuery(hql);

            sessions = query.list();
        } catch (HibernateException e) {
            System.out.println(e);
        } finally {
            session.close();
        }
        return sessions;
    }

    public static Sessions getById(int Id) {
        org.hibernate.Session session = HibernateUtil.getSessionFactory().openSession();
        Sessions sessions  = null;

        try{
            final String hql = "select ses from Sessions ses where ses.id =:id";

            Query query = session.createQuery(hql);
            query.setParameter("id", Id);

            sessions = (Sessions) query.list().get(0);
        } catch (HibernateException e) {
            System.out.println(e);
        } finally {
            session.close();
        }
        return sessions;
    }

    public static void delete(Sessions sessions) {
        org.hibernate.Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try{
            final String hql = "delete from Sessions ses where ses.id =:id";
            Query query = session.createQuery(hql);

            query.setParameter("id", sessions.getId());
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

    public static void save(Sessions sessions) {
        org.hibernate.Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try{
            session.save(sessions);
            System.out.println("Command successfully executed....");

        } catch (HibernateException e) {
            System.out.println(e);
        } finally {
            transaction.commit();
            session.clear();
            session.close();
        }
    }

    public static void update(Sessions sessions) {
        org.hibernate.Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try{
            final String hql = "update Sessions set semesterId=:semesterId, start=:start, end=:end where id=:id";
            Query query = session.createQuery(hql);

            query.setParameter("id", sessions.getId());
            query.setParameter("semesterId", sessions.getSemesterId());
            query.setParameter("start", sessions.getStart());
            query.setParameter("end", sessions.getEnd());

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
