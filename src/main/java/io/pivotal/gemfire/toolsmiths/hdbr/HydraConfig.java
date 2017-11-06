package io.pivotal.gemfire.toolsmiths.hdbr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

@Configuration
//@ComponentScan(basePackages = "HydraConfig.class")
// @ComponentScan(value = {'your.package.here'}, excludeFilters = @Filter(ConfigurationToIgnore.class))
@PropertySource(value = { "classpath:application.properties" })
@EnableWebMvc
@EnableSpringDataWebSupport
//@EnableJpaRepositories("io.pivotal.gemfire.toolsmiths.hdbr")
public class HydraConfig {

  @Autowired
  private Environment env;

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(env.getRequiredProperty("spring.datasource.driver-class-name"));
    dataSource.setUrl(env.getRequiredProperty("spring.datasource.url"));
    dataSource.setUsername(env.getRequiredProperty("spring.datasource.username"));
    dataSource.setPassword(env.getRequiredProperty("spring.datasource.password"));
    return dataSource;
  }

  @Bean
  public JdbcTemplate jdbcTemplate(DataSource dataSource) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    jdbcTemplate.setResultsMapCaseInsensitive(true);
    return jdbcTemplate;
  }

  @Bean
  public HydraDao dao() { return new HydraDao(); }

}
