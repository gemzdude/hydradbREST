package io.pivotal.gemfire.toolsmiths.hdbr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;


// extends JdbcDaoSupport
public class HydraDao {

  @Autowired
  JdbcTemplate jdbcTemplate;

  @Autowired
  DataSource datasource;
//  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
//    this.jdbcTemplate = jdbcTemplate;
//  }

  @Autowired
  public void setDataSource(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  public JdbcTemplate getTemplate() { return jdbcTemplate; }
  public String getURL() { return "http://localhost:8080/hdb"; }

  public String sayHello() { return "dao hello"; }

//  @Autowired
//  public HydraDao(JdbcTemplate jdbcTemplate) {
//    this.jdbcTemplate = jdbcTemplate;
// }
}
