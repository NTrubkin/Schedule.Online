package com.company.dao.api;

import com.company.model.Account;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;

public interface IDAO<T> {
    public void setSessionFactory(SessionFactory sessionFactory);

    /**
     * Создает объект в базе данных, используя Hibernate метод session.save(t);
     *
     * @param t transient или detached сущность
     */
    public void create(T t);

    /**
     * Читает из базы данных объект с id
     *
     * @param id id сущности в базе данных
     * @return persistent сущность, если успех
     */
    public T read(Serializable id);

    /**
     * Обновляет объект в базе данных, используя Hibernate метод session.update(t);
     *
     * @param t detached сущность
     */
    public void update(T t);

    /**
     * Удаляет объект из базы данных, используя Hibernate метод session.delete(t);
     *
     * @param t сущность
     */
    public void delete(T t);

    /**
     * Удаляет из базы данных объект с id
     *
     * @param id id объекта в базе данных
     */
    void delete(Serializable id);

    /**
     * Читает из базы данных все объекты установленного типа сущности
     *
     * @return List<T>, если успех
     */
    public List<T> readAll();

}
