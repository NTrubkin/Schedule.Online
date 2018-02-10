package com.company.dao.impl;

import com.company.dao.api.IDAO;
import com.company.util.GenericReflector;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.lang.InstantiationException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Обобщенный Hibernate DAO.
 * Содержит CRUD методы и сеттер фабрики Hibernate сессий
 *
 * @param <T> Hibernate сущность, для которой разработан DAO
 */
public abstract class DAO<T> implements IDAO<T> {
    protected static final String HIBERNATE_EXC_MSG = "Hibernate exception occurred: ";
    private static final Logger LOGGER = Logger.getLogger(DAO.class);
    private final SessionFactory sessionFactory;

    public DAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Создает объект в базе данных, используя Hibernate метод session.save(t);
     *
     * @param t transient или detached сущность
     */
    public void create(T t) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(t);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            LOGGER.error(HIBERNATE_EXC_MSG, e);
            throw e;
        } finally {
            session.close();
        }
    }


    @Override
    public T read(Serializable id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            T result = (T) session.get(GenericReflector.getClassParameterType(this.getClass()), id);
            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            LOGGER.error(HIBERNATE_EXC_MSG, e);
            throw e;
        } finally {
            session.close();
        }
    }

    /**
     * Обновляет объект в базе данных, используя Hibernate метод session.update(t);
     *
     * @param t detached сущность
     */
    public void update(T t) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(t);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            LOGGER.error(HIBERNATE_EXC_MSG, e);
            throw e;
        } finally {
            session.close();
        }
    }

    /**
     * Удаляет объект из базы данных, используя Hibernate метод session.delete(t);
     *
     * @param t сущность
     */
    public void delete(T t) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(t);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            LOGGER.error(HIBERNATE_EXC_MSG, e);
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void delete(Serializable id) {

        T obj = null;
        try {
            Class c = GenericReflector.getClassParameterType(this.getClass());
            obj = (T) c.newInstance();
            c.getMethod("setId", id.getClass()).invoke(obj, id);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            // что-то не так с рефлексией
            LOGGER.error(HIBERNATE_EXC_MSG, e);
            throw new HibernateException(e);
        }
        delete(obj);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> readAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            List<T> projects = session.createCriteria(GenericReflector.getClassParameterType(this.getClass())).list();
            transaction.commit();
            return projects;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            LOGGER.error(HIBERNATE_EXC_MSG, e);
            throw e;
        } finally {
            session.close();
        }
    }

    /**
     * Важно, чтобы поле было уникальным
     * @param field
     * @param value
     * @return
     */
    @SuppressWarnings("unchecked")
    protected T readByField(String field, Object value) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(GenericReflector.getClassParameterType(this.getClass()));
            criteria.add(Restrictions.eq(field, value));
            T object = (T) criteria.uniqueResult();
            transaction.commit();
            return object;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            LOGGER.error(HIBERNATE_EXC_MSG);
            throw e;
        } finally {
            session.close();
        }
    }
}
