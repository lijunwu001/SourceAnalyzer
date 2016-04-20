package sk.wu.lijun.project.bc.persistence;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import sk.wu.lijun.project.bc.tree.oop.treenodes.IPersistenceObject;

/**
 * Created by Lijun on 2016/4/20.
 */
public class HibernateUtils {
    private static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    public static void save(IPersistenceObject object){
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(object);
            transaction.commit();
        }catch (HibernateException e){
            if (transaction != null){
                transaction.rollback();
            }
        }
    }
}
