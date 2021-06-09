package dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import pojo.Registers;
import util.HibernateUtil;

import java.util.List;

public class RegisterDao {
    public static List<Registers> getAll(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Registers> registers = null;

        try{
            final String hql = "select reg from Registers reg";
            Query query = session.createQuery(hql);

            registers = query.list();
        } catch (HibernateException e) {
            System.out.println(e);
        } finally {
            session.close();
        }
        return registers;
    }

    public static List<Registers> getByUserId(int id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Registers> registers = null;

        try{
            final String hql = "select reg from Registers reg where reg.users.id=:id";
            Query query = session.createQuery(hql);
            query.setParameter("id",id);

            registers = query.list();
        } catch (HibernateException e) {
            System.out.println(e);
            registers = null;
        } finally {
            session.close();
        }
        return registers;
    }

    public static List<Registers> getByUserNCourse(int userId, int courseId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Registers> registers = null;

        try{
            final String hql = "select reg from Registers reg where reg.users.id=:userId and reg.courses.id=:courseId";
            Query query = session.createQuery(hql);
            query.setParameter("userId",userId);
            query.setParameter("courseId",courseId);

            registers = query.list();
        } catch (HibernateException e) {
            System.out.println(e);
            registers = null;
        } finally {
            session.close();
        }
        return registers;
    }

    public static List<Registers> getBySubjectId(int id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Registers> registers = null;

        try{
            final String hql = "select reg from Registers reg where reg.courses.subjects.id=:id";
            Query query = session.createQuery(hql);
            query.setParameter("id",id);

            registers = query.list();
        } catch (HibernateException e) {
            System.out.println(e);
            registers = null;
        } finally {
            session.close();
        }
        return registers;
    }

    public static List<Registers> getByCourseId(int id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Registers> registers = null;

        try{
            final String hql = "select reg from Registers reg where reg.courses.id=:id";
            Query query = session.createQuery(hql);
            query.setParameter("id",id);

            registers = query.list();
        } catch (HibernateException e) {
            System.out.println(e);
            registers = null;
        } finally {
            session.close();
        }
        return registers;
    }

    public static Registers getById(int Id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Registers registers = null;

        try{
            final String hql = "select reg from Registers reg where reg.id =:id";

            Query query = session.createQuery(hql);
            query.setParameter("id", Id);

            registers = (Registers) query.list().get(0);
        } catch (HibernateException e) {
            System.out.println(e);
        } finally {
            session.close();
        }
        return registers;
    }

    public static void delete(Registers registers) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try{
            final String hql = "delete from Registers reg where reg.id =:id";
            Query query = session.createQuery(hql);

            query.setParameter("id", registers.getId());
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

    public static void save(Registers registers) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try{
            session.save(registers);
            System.out.println("Command successfully executed....");

        } catch (HibernateException e) {
            System.out.println(e);
        } finally {
            transaction.commit();
            session.clear();
            session.close();
        }
    }

    public static void update(Registers registers) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try{
            final String hql = "update Registers set users=:users, courses=:courses, time=:time  where id=:id";
            Query query = session.createQuery(hql);

            query.setParameter("id", registers.getId());
            query.setParameter("users", registers.getUsers());
            query.setParameter("courses", registers.getCourses());
            query.setParameter("time", registers.getTime());

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
