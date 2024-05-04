package dev.scottdickerson.rbacservice.rbacapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
@Slf4j
public class RbacApiApplication {

  public static void main(String[] args) {

    log.info("Starting RBAC API");
    SpringApplication.run(RbacApiApplication.class, args);
  }
}
