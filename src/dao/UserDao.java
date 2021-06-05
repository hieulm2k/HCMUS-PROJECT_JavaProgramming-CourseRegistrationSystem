package dao;

import org.hibernate.Transaction;
import pojo.Users;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;

public class UserDao {
    public static List<Users> getAll(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Users> users = null;

        try{
            final String hql = "select u from Users u";
            Query query = session.createQuery(hql);

            users = query.list();
        } catch (HibernateException e) {
            System.out.println(e);
        } finally {
            session.close();
        }
        return users;
    }

    public static Users getById(int Id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Users users = null;

        try{
            final String hql = "select u from Users u where u.id =:id";

            Query query = session.createQuery(hql);
            query.setParameter("id", Id);

            users = (Users) query.list().get(0);
        } catch (HibernateException e) {
            System.out.println(e);
        } finally {
            session.close();
        }
        return users;
    }

    public static Users getByUsername(String username) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Users users = null;

        try{
            final String hql = "select u from Users u where u.username=:username";

            Query query = session.createQuery(hql);
            query.setParameter("username",username);
            if(query.list().stream().count()>0){
                users = (Users) query.list().get(0);
            }
            else{
                users = null;
            }
        } catch (HibernateException e) {
            System.out.println(e);
        } finally {
            session.close();
        }
        return users;
    }

    public static void delete(Users users) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try{
            final String hql = "delete from Users u where u.id =:id";
            Query query = session.createQuery(hql);

            query.setParameter("id", users.getId());
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

    public static void save(Users users) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try{
            session.save(users);
            System.out.println("Command successfully executed....");

        } catch (HibernateException e) {
            System.out.println(e);
        } finally {
            transaction.commit();
            session.clear();
            session.close();
        }
    }

    public static void update(Users users) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try{
            final String hql = "update Users set permission=:permission, username=:username, password=:password," +
                    "dob=:dob, gender=:gender, classId=:classId, stdCode=:stdCode, name=:name where id=:id";
            Query query = session.createQuery(hql);

            query.setParameter("id", users.getId());
            query.setParameter("permission", users.getPermission());
            query.setParameter("username", users.getUsername());
            query.setParameter("password", users.getPassword());
            query.setParameter("name", users.getName());
            query.setParameter("dob", users.getDob());
            query.setParameter("gender", users.getGender());
            query.setParameter("classId", users.getClassId());
            query.setParameter("stdCode", users.getStdCode());

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
