package ru.job4j.cars;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactorySigl {

    private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("cars-mvc");

    public static EntityManagerFactory getEntityManagerFactory() {
        return EMF;
    }
}
