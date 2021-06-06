package dao;

import org.hibernate.Transaction;
import pojo.Classes;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;
import java.util.List;

public class ClassDao {
    public static List<Classes> getAll(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Classes> Classes = null;

        try{
            final String hql = "select cl from Classes cl";
            Query query = session.createQuery(hql);

            Classes = query.list();
        } catch (HibernateException e) {
            System.out.println(e);
        } finally {
            session.close();
        }
        return Classes;
    }

    public static Classes getById(int Id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Classes classes = null;

        try{
            final String hql = "select cl from Classes cl where cl.id =:id";

            Query query = session.createQuery(hql);
            query.setParameter("id", Id);

            classes = (Classes) query.list().get(0);
        } catch (HibernateException e) {
            System.out.println(e);
        } finally {
            session.close();
        }
        return classes;
    }

    public static Classes getByName(String name) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Classes classes = null;

        try{
            final String hql = "select cl from Classes cl where cl.name =:name";

            Query query = session.createQuery(hql);
            query.setParameter("name", name);
            if(query.stream().count() > 0){
                classes = (Classes) query.list().get(0);
            }
        } catch (HibernateException e) {
            System.out.println(e);
        } finally {
            session.close();
        }
        return classes;
    }

    public static void delete(Classes classes) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try{
            final String hql = "delete from Classes cl where cl.id =:id";
            Query query = session.createQuery(hql);

            query.setParameter("id", classes.getId());
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

    public static void save(Classes classes) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try{
            session.save(classes);
            System.out.println("Command successfully executed....");

        } catch (HibernateException e) {
            System.out.println(e);
        } finally {
            transaction.commit();
            session.clear();
            session.close();
        }
    }

    public static void update(Classes classes) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try{
            final String hql = "update Classes set name=:name where id=:id";
            Query query = session.createQuery(hql);
            query.setParameter("name", classes.getName());
            query.setParameter("id", classes.getId());

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
