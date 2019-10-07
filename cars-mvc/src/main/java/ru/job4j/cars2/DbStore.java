package ru.job4j.cars2;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Класс реализует интерфейс Store для hibernate-реализации
 * @param <K> дженерик класс сущностей используемых в этом проекте
 */
public class DbStore<K extends AbsProjectEntity> implements Store<K> {

    private final EntityManagerFactory factory = EntityManagerFactorySigl.getEntityManagerFactory();
    private final String entityname;
    private final Class<K> fullclass;

    /**
     * Конструктор с определением сокращенного имени класса
     * @param genclass
     */
    public DbStore(Class<K> genclass) {
        fullclass = genclass;
        String[] names =  genclass.getName().split("\\.");
        entityname = names[names.length - 1];
    }

    protected String getEntityname() {
        return entityname;
    }

    @Override
    public int add(K model) {
        K mod = model;
        return this.tx(
                session -> {
                    session.persist(mod);
                    return mod.getId();
                }
        );
    }

    @Override
    public boolean update(K model) {
        return this.tx(
                session -> {
                    boolean result = true;
                    try {
                        session.merge(model);
                    } catch (IllegalArgumentException e) {
                        result = false;
                    }
                    return result;
                }
        );
    }

    @Override
    public boolean delete(int id) {
        return this.tx(
                session -> {
                    boolean result = true;
                    K mod = session.find(fullclass, id);
                    if (mod != null) {
                        session.remove(mod);
                    } else {
                        result = false;
                    }
                    return result;
                }
        );
    }

    @Override
    public List<K> findAll() {
        return this.tx(
                session -> {
                    String qr = "FROM " + entityname + " ORDER BY id";
                    Query query = session.createQuery(qr);
                    return (List<K>) query.getResultList();
                }
        );
    }

    @Override
    public K findById(int id) {
        return this.tx(
                session -> session.find(fullclass, id)
        );
    }

    @Override
    public int findIdByModel(K model) {
        int result = 0;
        List<K> temp = this.findAll();
        for (K one: temp) {
            if (one.equals(model)) {
                result = one.getId();
            }
        }
        return result;
    }

    protected <T> T tx(final Function<EntityManager, T> command) {
        T result = null;
        EntityManager session = factory.createEntityManager();
        EntityTransaction transaction = session.getTransaction();
        try {
            transaction.begin();
            result = command.apply(session);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }

}
