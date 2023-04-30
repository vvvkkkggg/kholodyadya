package org.vgk.kholodyadya.repository;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.vgk.kholodyadya.database.SingletonSessionFactory;
import org.vgk.kholodyadya.entity.Products;
import org.vgk.kholodyadya.entity.Users;

import java.util.List;


public class ProductRepository {
    public static boolean checkIfProductExists(String productName) throws HibernateException {
        SessionFactory sessionFactory = SingletonSessionFactory.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {

            Query<Products> query = session.createNamedQuery("DeptEmployee_FindAllByDepartment", Products.class);
            query.setParameter("product_name", productName);
            List<Products> result = query.getResultList();
            return result.isEmpty();

        } catch (Throwable e) {
            // TODO
        }
        return true;
    }
}