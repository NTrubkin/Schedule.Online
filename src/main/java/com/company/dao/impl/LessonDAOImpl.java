package com.company.dao.impl;

import com.company.dao.api.LessonDAO;
import com.company.model.Lesson;
import org.hibernate.SessionFactory;

public class LessonDAOImpl extends DAO<Lesson> implements LessonDAO {
    public LessonDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
