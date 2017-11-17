package io.pivotal.gemfire.toolsmiths.hydradb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

@Configuration
@PropertySource(value = { "classpath:config/application.properties" })
@EnableWebMvc
@EnableSpringDataWebSupport
@EnableTransactionManagement
//@EnableJpaRepositories(
//    entityManagerFactoryRef = "hydraEntityManagerFactory",
//    transactionManagerRef = "hydraTransactionManager",
//    basePackages = { "io.pivotal.gemfire.toolsmiths.hydradb.data.hydra" }
//)
public class HydraConfig {

  @Autowired
  private Environment env;

//  @Primary
//  @Bean(name="hydraDataSource", destroyMethod="close")
//  @ConfigurationProperties(prefix = "spring.hydraDataSource")
//  public DataSource dataSource()
//  {
//    return DataSourceBuilder.create().build();
//  }
//
//  @Primary
//  @Bean(name = "hydraEntityManagerFactory")
//  public LocalContainerEntityManagerFactoryBean
//  hydraEntityManagerFactory(
//      EntityManagerFactoryBuilder builder,
//      @Qualifier("hydraDataSource") DataSource dataSource)
//  {
//    return
//        builder
//            .dataSource(dataSource)
//            .packages("io.pivotal.gemfire.toolsmiths.hydradb.data.hydra")
//            .persistenceUnit("hydra")
//            .build();
//  }
//
//  @Primary
//  @Bean(name = "hydraTransactionManager")
//  public PlatformTransactionManager hydraTransactionManager(
//      @Qualifier("hydraEntityManagerFactory") EntityManagerFactory
//          hydraEntityManagerFactory)
//  {
//    return new JpaTransactionManager(hydraEntityManagerFactory);
//  }
//
//  @Bean(name = "hydraJdbcTemplate")
//  public JdbcTemplate hydraJdbcTemplate(DataSource hydraDataSource) {
//    JdbcTemplate hydraJdbcTemplate = new JdbcTemplate(hydraDataSource);
//    hydraJdbcTemplate.setResultsMapCaseInsensitive(true);
//    return hydraJdbcTemplate;
//  }
/* ***************************************** */
  @Bean
  @Primary
  @ConfigurationProperties("spring.hydraDataSource")
  public DataSourceProperties hydraDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean
  @Primary
  @ConfigurationProperties("spring.hydraDataSource")
  public DataSource hydraDataSource() {
    return hydraDataSourceProperties().initializeDataSourceBuilder().build();
//    DriverManagerDataSource hydraDataSource = new DriverManagerDataSource();
//    hydraDataSource.setDriverClassName(env.getRequiredProperty("spring.hydraDataSource.driver-class-name"));
//    hydraDataSource.setUrl(env.getRequiredProperty("spring.hydraDataSource.url"));
//    hydraDataSource.setUsername(env.getRequiredProperty("spring.hydraDataSource.username"));
//    hydraDataSource.setPassword(env.getRequiredProperty("spring.hydraDataSource.password"));
//    return hydraDataSource;
  }

  @Bean
  public JdbcTemplate hydraJdbcTemplate(DataSource hydraDataSource) {
    JdbcTemplate hydraJdbcTemplate = new JdbcTemplate(hydraDataSource);
    hydraJdbcTemplate.setResultsMapCaseInsensitive(true);
    return hydraJdbcTemplate;
  }

}
