package io.pivotal.gemfire.toolsmiths.hydradb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

//@Configuration
//@PropertySource(value = { "classpath:config/application.properties" })
//@EnableWebMvc
//@EnableSpringDataWebSupport
//@EnableTransactionManagement
//@EnableJpaRepositories(
//    entityManagerFactoryRef = "tracEntityManagerFactory",
//    transactionManagerRef = "tracTransactionManager",
//    basePackages = { "io.pivotal.gemfire.toolsmiths.hydradb.data.trac" }
//)
public class TracConfig {

  @Autowired
  private Environment env;

  @Bean(name = "tracDataSource", destroyMethod="close")
  @ConfigurationProperties(prefix = "spring.tracDataSource")
  public DataSource dataSource()
  {
    return DataSourceBuilder.create().build();
  }

  @Bean(name = "tracEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean
  tracEntityManagerFactory(
      EntityManagerFactoryBuilder builder,
      @Qualifier("tracDataSource") DataSource dataSource)
  {
    return
        builder
            .dataSource(dataSource)
            .packages("io.pivotal.gemfire.toolsmiths.hydradb.data.trac")
            .persistenceUnit("trac")
            .build();
  }

  @Bean(name = "tracTransactionManager")
  public PlatformTransactionManager tracTransactionManager(
      @Qualifier("tracEntityManagerFactory") EntityManagerFactory
          tracEntityManagerFactory)
  {
    return new JpaTransactionManager(tracEntityManagerFactory);
  }

  @Bean(name = "tracJdbcTemplate")
  public JdbcTemplate tracJdbcTemplate(DataSource tracDataSource) {
    JdbcTemplate tracJdbcTemplate = new JdbcTemplate(tracDataSource);
    tracJdbcTemplate.setResultsMapCaseInsensitive(true);
    return tracJdbcTemplate;
  }
}