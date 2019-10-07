package ru.job4j.cars.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.job4j.cars.CarEntity;
import ru.job4j.cars.DbStore;
import ru.job4j.cars.MarkEntity;
import ru.job4j.cars.UsersEntity;

@Configuration
@ComponentScan("ru.job4j.cars")
public class AppJavaConfig {

    @Bean(name = "userdb")
    public DbStore<UsersEntity> getUserDb() {
        return new DbStore<>(UsersEntity.class);
    }

    @Bean(name = "cdb")
    public DbStore<CarEntity> getCarDb() {
        return new DbStore<>(CarEntity.class);
    }

    @Bean(name = "mdb")
    public  DbStore<MarkEntity> getMarkDb() {
        return new DbStore<>(MarkEntity.class);
    }
}
