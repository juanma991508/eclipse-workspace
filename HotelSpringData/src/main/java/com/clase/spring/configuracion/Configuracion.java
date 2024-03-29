package com.clase.spring.configuracion;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.google.common.base.Preconditions;

@Configuration
@PropertySource({"classpath:persistence-mysql.properties"})
@ComponentScan(basePackages= {"com.clase.spring"})
@EnableTransactionManagement
public class Configuracion {

	@Autowired
	private Environment env;
	
	 @Bean
	 public PlatformTransactionManager transactionManager() {
	    
	     return new JpaTransactionManager(entityManagerFactory().getObject());
	 }

	
	 @Bean
	    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		 LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
	      em.setDataSource(dataSource());
	      em.setPackagesToScan(new String[] { "com.clase.spring.dominio" });

	      HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	      em.setJpaVendorAdapter(vendorAdapter);
	      em.setJpaProperties(additionalProperties());

	      return em;
	    }
	 
	 @Bean
	 public DataSource dataSource(){
	     DriverManagerDataSource dataSource = new DriverManagerDataSource();
	     dataSource.setDriverClassName(Preconditions.checkNotNull(env.getProperty("jdbc.driverClassName")));
	     dataSource.setUrl(Preconditions.checkNotNull(env.getProperty("jdbc.url")));
	     dataSource.setUsername( Preconditions.checkNotNull(env.getProperty("jdbc.username")) );
	     dataSource.setPassword(Preconditions.checkNotNull(env.getProperty("jdbc.password")) );
	     return dataSource;
	 }
	
	
	 @Bean
	 public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
	     return new PersistenceExceptionTranslationPostProcessor();
	 }
	
 Properties additionalProperties() {
	  Properties properties = new Properties();
	  properties.setProperty("hibernate.dialect",Preconditions.checkNotNull(env.getProperty("hibernate.dialect")));   
	  properties.setProperty("hibernate.show_sql",Preconditions.checkNotNull(env.getProperty("hibernate.show_sql")));
	  properties.setProperty("hibernate.hbm2ddl.auto",Preconditions.checkNotNull(env.getProperty("hibernate.hbm2ddl.auto")));

	  return properties;
    }
}
