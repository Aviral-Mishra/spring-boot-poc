package com.brillio.mainservice.config;


import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@PropertySource({ "classpath:application.properties" })
@EnableJpaRepositories(
		basePackages = "com.brillio..mainservice.repository.employee",
		entityManagerFactoryRef = "employeeEntityManager",
		transactionManagerRef = "employeeTransactionManager"
)
public class PersistenceEmployeeAutoConfiguration {

	@Autowired
	private Environment env;

	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean employeeEntityManager() {
		LocalContainerEntityManagerFactoryBean em
				= new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(employeeDataSource());
		em.setPackagesToScan(
				new String[] { "com.brillio..mainservice.entity.employee" });

		HibernateJpaVendorAdapter vendorAdapter
				= new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto",
				env.getProperty("hibernate.hbm2ddl.auto"));
		properties.put("hibernate.dialect",
				env.getProperty("hibernate.dialect"));
		em.setJpaPropertyMap(properties);

		return em;
	}

	@Primary
	@Bean(name = "spring.datasource")
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource employeeDataSource(){
		return DataSourceBuilder.create().build();
	}

	@Primary
	@Bean
	public PlatformTransactionManager employeeTransactionManager() {

		JpaTransactionManager transactionManager
				= new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(
				employeeEntityManager().getObject());
		return transactionManager;
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.liquibase")
	public LiquibaseProperties employeeLiquibaseProperties() {
		return new LiquibaseProperties();
	}

	@Bean("liquibase")
	public SpringLiquibase employeeLiquibase() {
		return springLiquibase(employeeDataSource(), employeeLiquibaseProperties());
	}

	private static SpringLiquibase springLiquibase(DataSource dataSource, LiquibaseProperties properties) {
		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setDataSource(dataSource);
		liquibase.setChangeLog(properties.getChangeLog());
		liquibase.setContexts(properties.getContexts());
		liquibase.setDefaultSchema(properties.getDefaultSchema());
		liquibase.setDropFirst(properties.isDropFirst());
		liquibase.setShouldRun(properties.isEnabled());
		liquibase.setLabels(properties.getLabels());
		liquibase.setChangeLogParameters(properties.getParameters());
		liquibase.setRollbackFile(properties.getRollbackFile());
		return liquibase;
	}

}
