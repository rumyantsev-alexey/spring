package ru.job4j.cars.dao;

import java.util.List;

/**
 * Интерфейс определяющий основные методы для хранилища
 * @param <K> класс объекта хранения
 */
public interface Store<K> {

    int add(K model);

    boolean update(K model);

    boolean delete(int id);

    List<K> findAll();

    K findById(int id);

    int findIdByModel(K model);
}
