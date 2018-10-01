package com.example.multidb.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@PropertySources({@PropertySource("classpath:datasource-cfg.properties")})
public class DataSource2Config {
	
	private static final String DATA_SOURCE_DRIVER_CLASS_NAME_2 = "spring.datasource.driver-class-name.2";
	private static final String DATA_SOURCE_URL_2 = "spring.datasource.url.2";
	private static final String DATA_SOURCE_USER_NAME_2 = "spring.datasource.username.2";
	private static final String DATA_SOURCE_PASSWORD_2 = "spring.datasource.password.2";
	
	private static final String HIBERNATE_DIRECT = "hibernate.dialect";
	private static final String PROPERTIES_HIBERNATE_DIRECT = "spring.jpa.properties.hibernate.dialect.2";
	private static final String HIBERNATE_SHOW_SQL = "hibernate.show-sql";
	private static final String PROPERTIES_HIBERNATE_SHOW_SQL = "spring.jpa.show-sql.2";
	
	@Autowired
	private Environment env;
	
	@Bean
	public DataSource ds2Datasource() {
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName(env.getProperty(DATA_SOURCE_DRIVER_CLASS_NAME_2));
		dataSource.setUrl(env.getProperty(DATA_SOURCE_URL_2));
		dataSource.setUsername(env.getProperty(DATA_SOURCE_USER_NAME_2));
		dataSource.setPassword(env.getProperty(DATA_SOURCE_PASSWORD_2));
		
		return dataSource;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean ds2EntityManager() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(ds2Datasource());
		
		// Scan Entities in Package:
		em.setPackagesToScan(new String[] { Constants.PACKAGE_ENTITLES_2});
		em.setPersistenceUnitName(Constants.JPA_UNIT_NAME_2);
		
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		
		HashMap<String, Object> properties = new HashMap<>();
		properties.put(HIBERNATE_DIRECT, env.getProperty(PROPERTIES_HIBERNATE_DIRECT));
		properties.put(HIBERNATE_SHOW_SQL, env.getProperty(PROPERTIES_HIBERNATE_SHOW_SQL));
		
		em.setJpaPropertyMap(properties);
		em.afterPropertiesSet();

		return em;
	}
	
	@Bean
	public PlatformTransactionManager ds1TransactionManger() {
		
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(ds2EntityManager().getObject());
		return transactionManager;
	}

}
