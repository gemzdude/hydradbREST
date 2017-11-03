package io.pivotal.gemfire.toolsmiths.hdbr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"io.pivotal.gemfire.toolsmiths.hdbr"})

public class Main {

  public static void main(String[] args) {
    SpringApplication.run(io.pivotal.gemfire.toolsmiths.hdbr.Main.class, args);
    System.out.println("Made it past spring init");
  }

}
