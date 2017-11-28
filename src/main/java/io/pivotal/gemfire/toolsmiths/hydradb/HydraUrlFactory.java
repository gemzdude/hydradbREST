package io.pivotal.gemfire.toolsmiths.hydradb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class HydraUrlFactory {

  @Autowired
  private Environment env;

  @Value("${spring.hydraEndpoint.host:localhostx}")
  String hydradbHost;

  @Value("${spring.hydraEndpoint.endpoint:/HydraDB}")
  String hydradbEndpoint;
  
  @Value("#{new Integer('${spring.hydraEndpoint.port:8080}')}")
  Integer hydradbPort;

  String getURL(Integer port) {
    UriComponents uriComponents = UriComponentsBuilder.newInstance()
        .scheme("http").host(hydradbHost).port(port).path(hydradbEndpoint).build();
    return uriComponents.toUriString();
  }

  String getURL() {
    return getURL(hydradbPort);
  }

  void setPort(Integer port) {
    this.hydradbPort = port;
  }

  Integer getPort() {
    return hydradbPort;
  }
}
