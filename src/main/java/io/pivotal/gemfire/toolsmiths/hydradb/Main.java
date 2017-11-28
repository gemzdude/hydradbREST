package io.pivotal.gemfire.toolsmiths.hydradb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"io.pivotal.gemfire.toolsmiths.hydradb"})

public class Main {

  public static void main(String[] args) {
    SpringApplication.run(io.pivotal.gemfire.toolsmiths.hydradb.Main.class, args);
  }

}
