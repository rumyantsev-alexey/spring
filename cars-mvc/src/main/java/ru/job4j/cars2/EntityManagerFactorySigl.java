package ru.job4j.cars2;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactorySigl {

    private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("cars-jpa2");

    public static EntityManagerFactory getEntityManagerFactory() {
        return EMF;
    }
}
