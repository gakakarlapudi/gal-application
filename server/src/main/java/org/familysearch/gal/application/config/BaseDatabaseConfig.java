package org.familysearch.gal.application.config;

import com.googlecode.flyway.core.Flyway;
import org.familysearch.gal.application.dal.api.base.ValidatingHibernateJPADialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@EnableJpaRepositories(basePackages = "org.familysearch.gal.application.dal.api.**",
                entityManagerFactoryRef = "entityManagerFactory",
                transactionManagerRef = "transactionManager")
@EnableTransactionManagement
public abstract class BaseDatabaseConfig {
    /**
     * Translates Hibernate exceptions to Spring exceptions for @Repository (DAO) classes
     */
    @Autowired
    Environment env;

    @Bean
    PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
        return new org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor();
    }

    /** Flyway Configuration */
    public abstract Flyway flyway();

    public abstract DataSource dataSource();

    /** JPA configuration */
    public abstract HibernateJpaVendorAdapter jpaVendorAdapter();

    /** EntityManager factory */
    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSource());
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        localContainerEntityManagerFactoryBean.setJpaDialect(jpaDialect());
        localContainerEntityManagerFactoryBean.setPersistenceUnitName("appGalleryPU");
        return localContainerEntityManagerFactoryBean;
    }

    /** JPA Dialect */
    @Bean
    ValidatingHibernateJPADialect jpaDialect() {
        ValidatingHibernateJPADialect validatingHibernateJPADialect = new ValidatingHibernateJPADialect();
        return validatingHibernateJPADialect;
    }

    /** TransactionManager */
    @Bean
    PlatformTransactionManager transactionManager() {
        final JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        jpaTransactionManager.setJpaDialect(jpaDialect());
        return jpaTransactionManager;
    }
}
