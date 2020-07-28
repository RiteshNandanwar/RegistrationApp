package com.ritesh.springsecurity.demo.config;

import java.beans.PropertyVetoException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = "com.ritesh.springsecurity.demo")
@PropertySource("classpath:persistence-mysql.properties")   //read property files
public class DemoAppConfig 
{
	//set up variable to hold the properties
	
	@Autowired
	private Environment environment;
	
	//set logger for Diagnostic
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	//Defile a bean for view resolver
	@Bean
	public ViewResolver viewResolver()
	{
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		
		viewResolver.setPrefix("/WEB-INF/view/");
		viewResolver.setSuffix(".jsp");
		
		return viewResolver;
	}
	
	//Define a bean for our security datasource
	
	@Bean
	public DataSource securityDataSource() 
	{
		//create connection pool
		ComboPooledDataSource securityDataSource = new ComboPooledDataSource();
				
		//set the JDBC deriver class
				
		try {
			securityDataSource.setDriverClass(environment.getProperty("jdbc.driver"));
		} catch (PropertyVetoException e) {
			throw new RuntimeException(e); 
		}
		
		//log the connection props
		
		logger.info(">>>>JDBC.url = "+environment.getProperty("jdbc.url"));
		logger.info(">>>>JDBC.user = "+environment.getProperty("jdbc.user"));
		
		//set DB connection properties
		
		securityDataSource.setJdbcUrl(environment.getProperty("jdbc.url"));
		securityDataSource.setUser(environment.getProperty("jdbc.user"));
		securityDataSource.setPassword(environment.getProperty("jdbc.password"));
		
		//set connection pool props
		securityDataSource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
		securityDataSource.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));
		securityDataSource.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));
		securityDataSource.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));
		
		return securityDataSource;
	}
	private int getIntProperty(String propName)
	{
		String propVal = environment.getProperty(propName);
		
		//string to int
		int intPropValue = Integer.parseInt(propVal);
		
		return intPropValue;
	}
	
	//
	@Bean
	public LocalSessionFactoryBean sessionFactory() 
	{
		//create session Factory
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		
		//set a properties
		sessionFactory.setDataSource(securityDataSource());
		sessionFactory.setPackagesToScan(environment.getProperty("hiberante.packagesToScan"));
		sessionFactory.setHibernateProperties(getHibernateProperties());
		
		return sessionFactory;
	}

	private Properties getHibernateProperties() 
	{
		// set hibernate properties
		Properties prop = new Properties();
		prop.setProperty("hibernate.dialect", environment.getProperty("hibernate.dialect"));
		prop.setProperty("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
		
		return prop;
	}
	
	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory)
	{
		// setup transaction manager based on session factory
		
		HibernateTransactionManager txmanager = new HibernateTransactionManager();
		txmanager.setSessionFactory(sessionFactory);
		
		return txmanager;
	}
	
}
