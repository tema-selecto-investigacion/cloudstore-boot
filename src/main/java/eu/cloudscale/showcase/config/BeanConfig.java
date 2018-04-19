package eu.cloudscale.showcase.config;

import eu.cloudscale.showcase.db.generate.GenerateHibernate;
import net.sf.ehcache.hibernate.SingletonEhCacheRegionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManager;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.jms.Connection;
import javax.jms.Queue;
import javax.jms.Session;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class BeanConfig {

    @Autowired
    Environment environment;

    @Bean
    public SessionFactory sessionFactory(HibernateEntityManagerFactory hemf){
        return hemf.getSessionFactory();
    }

    @Bean
    public Connection activeMQConnection() throws Exception{
        String brokerUrl = this.environment.getRequiredProperty("cloudstore.jms.broker-url");
        return new ActiveMQConnectionFactory(brokerUrl).createConnection();
    }

//    @Autowired
//    private EntityManagerFactory entityManagerFactory;
//
//    @Bean
//    public SessionFactory sessionFactory(){
//        if(this.entityManagerFactory.unwrap(SessionFactory.class) == null){
//            throw new NullPointerException("Factory is not a Hibernate factory");
//        }
//        return this.entityManagerFactory.unwrap(SessionFactory.class);
//    }

//    @Profile("local")
//    @Bean
//    public DataSource localDataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("org.h2.Driver");
//        dataSource.setUrl("jdbc:h2:~/tpcw;MODE=MySQL;DB_CLOSE_ON_EXIT=FALSE");
//        dataSource.setUsername("sa");
//        dataSource.setPassword("sa");
//        return dataSource;
//    }
//
//    @Profile("!local")
//    @Bean
//    public DataSource awsDataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//        dataSource.setUrl("jdbc:mysql://aac2gtq8vzj55d.c7vtta3fbhu3.us-west-2.rds.amazonaws.com:3306/tpcw");
//        dataSource.setUsername("root");
//        dataSource.setPassword("rootroot");
//        return dataSource;
//    }

//    @Bean
//    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
//        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
//        sessionFactoryBean.setDataSource(dataSource);
//        sessionFactoryBean.setPackagesToScan("eu.cloudscale.showcase.db");
//        Properties hibernateProperties = new Properties();
//        hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
//        hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
////        hibernateProperties.put("hibernate.show_sql", "true");
//        hibernateProperties.put("hibernate.hbm2ddl.auto", "create-drop");
//        hibernateProperties.put("hibernate.generate_statistics", true);
//        hibernateProperties.put("hibernate.cache.use_second_level_cache", true);
//        hibernateProperties.put("hibernate.cache.use_query_cache", true);
//        hibernateProperties.put("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory");
//        hibernateProperties.put("hibernate.cache.provider_configuration_file_resource_path", "classpath:ehcache.xml");
//
//        sessionFactoryBean.setHibernateProperties(hibernateProperties);
//
//        return sessionFactoryBean;
//    }

//    @Bean(name = "transactionManager")
//    public HibernateTransactionManager getTransactionManager() {
//        HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory());
//        return transactionManager;
//    }

}
