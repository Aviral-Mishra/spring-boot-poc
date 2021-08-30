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
		basePackages = "com.brillio..mainservice.repository.student",
		entityManagerFactoryRef = "studentEntityManager",
		transactionManagerRef = "studentTransactionManager"
)
public class PersistenceStudentAutoConfiguration {

	@Autowired
	private Environment env;

	@Bean
	public LocalContainerEntityManagerFactoryBean studentEntityManager() {
		LocalContainerEntityManagerFactoryBean em
				= new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(studentDataSource());
		em.setPackagesToScan(
				new String[] { "com.brillio..mainservice.entity.student" });

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

	@Bean(name="spring.second-datasource")
	@ConfigurationProperties(prefix="spring.second-datasource")
	public DataSource studentDataSource(){
		return DataSourceBuilder.create().build();
	}

	@Bean
	public PlatformTransactionManager studentTransactionManager() {

		JpaTransactionManager transactionManager
				= new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(
				studentEntityManager().getObject());
		return transactionManager;
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.second-datasource.liquibase")
	public LiquibaseProperties studentLiquibaseProperties() {
		return new LiquibaseProperties();
	}

	@Bean
	public SpringLiquibase secondaryLiquibase() {
		return springLiquibase(studentDataSource(), studentLiquibaseProperties());
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
