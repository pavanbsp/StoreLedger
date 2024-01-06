package com.jar.storeLedger.entity.repository;


import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public abstract class AbstractRepository<T> {

    @PersistenceContext
    private EntityManager em;
    private Class<T> clazz;

    public AbstractRepository(Class clazz) {
        this.clazz = clazz;
    }

    @Transactional(readOnly = true)
    public void save(T t) {
        em.persist(t);
    }

    @Transactional
    public T select(Object id) {
        return em.find(clazz, id);
    }

    @Transactional
    public <V> T select(String fieldName, V fieldValue) {
        TypedQuery<T> query = em.createQuery("SELECT e FROM " + clazz.getSimpleName() + " e WHERE " + fieldName + " = :fieldValue", clazz);
        query.setParameter("fieldValue", fieldValue);
        return this.selectSingleOrNone(query);
    }

    @Transactional
    public void delete(T t) {
        em.remove(t);
    }

    protected T selectSingleOrNone(TypedQuery<T> query) {
        query.setMaxResults(1);
        List<T> list = query.getResultList();
        if (list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

}
