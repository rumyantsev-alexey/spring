package ru.job4j.cars.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("ru.job4j.cars.repositories")
@EnableTransactionManagement
public class SpringDataConfig {
        @Bean
        public LocalEntityManagerFactoryBean entityManagerFactory() {
            LocalEntityManagerFactoryBean result =
                    new LocalEntityManagerFactoryBean();
            result.setPersistenceUnitName("cars-mvc");
            return result;
        }

        @Bean
        public PlatformTransactionManager transactionManager() {
            JpaTransactionManager result = new JpaTransactionManager();
            result.setEntityManagerFactory(entityManagerFactory().getObject());
            return result;
        }
}
