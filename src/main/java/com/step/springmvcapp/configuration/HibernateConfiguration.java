package com.step.springmvcapp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;
import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@EnableJpaRepositories(basePackages = {"com.itstep.myfirstspringapp.repository"},
        repositoryImplementationPostfix = "Impl",
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager")
@EnableTransactionManagement
public class HibernateConfiguration {   

    @Bean
    public DataSource dataSource() {  // создание соединения с БД
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver"); //com.mysql.jdbc.Driver
        dataSource.setUrl("jdbc:mysql://localhost:3306/myDB?createDatabaseIfNotExist=true"); //?createDatabaseIfNotExist=true
        dataSource.setUsername("root");
        dataSource.setPassword("Kaiko1994Ulia");

        return dataSource;
    }

    private final Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(
          "hibernate.hbm2ddl.auto", "update"); //spring.jpa.hibernate.ddl create update
        hibernateProperties.setProperty(
          "hibernate.dialect ", "org.hibernate.dialect.MySQL5InnoDBDialect"); //!!! org.hibernate.dialect.H2Dialect 

        return hibernateProperties;
    }
    
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(true);
        adapter.setGenerateDdl(true); // creating/updating tables 
        adapter.setDatabase(Database.MYSQL); // Тип базы данных конфигурации
        LocalContainerEntityManagerFactoryBean emfb = 
            new LocalContainerEntityManagerFactoryBean();
        emfb.setDataSource(dataSource());
        emfb.setPackagesToScan("com.step.springmvcapp");
        emfb.setJpaProperties(hibernateProperties());
        emfb.setJpaVendorAdapter(adapter);
        
        return emfb;
    }
    
    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory){
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setDataSource(dataSource());
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        return jpaTransactionManager;
    }
 
    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
