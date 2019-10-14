package ru.job4j.cars.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import ru.job4j.cars.dao.CarsDbStore;
import ru.job4j.cars.models.*;
import ru.job4j.cars.dao.DbStore;

@Configuration
@ComponentScan("ru.job4j.cars")
public class AppJavaConfig {

    @Bean(name = "userdb")
    public DbStore<UsersEntity> getUserDb() {
        return new DbStore<>(UsersEntity.class);
    }

    @Bean(name = "mdb")
    public  DbStore<MarkEntity> getMarkDb() {
        return new DbStore<>(MarkEntity.class);
    }

    @Bean(name = "mddb")
    public  DbStore<ModelEntity> getModelDb() {
        return new DbStore<>(ModelEntity.class);
    }

    @Bean(name = "cdb")
    public CarsDbStore getAdvanceCarDb() {
        return new CarsDbStore();
    }

    @Bean(name = "citydb")
    public DbStore<CityEntity> getCityDb() {
        return new DbStore<>(CityEntity.class);
    }

    @Bean(name = "trdb")
    public DbStore<TransmissionEntity> getTransDb() {
        return new DbStore<>(TransmissionEntity.class);
    }

    @Bean(name = "bddb")
    public DbStore<BodytypeEntity> getBodyDb() {
        return new DbStore<>(BodytypeEntity.class);
    }

    @Bean(name = "engdb")
    public DbStore<EnginestypeEntity> getEngDb() {
        return new DbStore<>(EnginestypeEntity.class);
    }

    @Bean(name = "drdb")
    public DbStore<DriveunitEntity> getDrvDb() {
        return new DbStore<>(DriveunitEntity.class);
    }

    @Bean(name = "wdb")
    public DbStore<WheelEntity> getWheelDb() {
        return new DbStore<>(WheelEntity.class);
    }

    @Bean(name = "fdb")
    public DbStore<FotoEntity> getFotoDb() {
        return new DbStore<>(FotoEntity.class);
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(10000000);
        return multipartResolver;
    }
}
