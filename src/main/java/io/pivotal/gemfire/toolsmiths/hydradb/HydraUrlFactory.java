package io.pivotal.gemfire.toolsmiths.hydradb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class HydraUrlFactory {

  @Autowired
  private Environment env;

  String getURL(Integer port) {
    String host = env.getRequiredProperty("spring.HydraEndpoint.host");
    String endpoint = env.getRequiredProperty("spring.HydraEndpoint.endpoint");
    UriComponents uriComponents = UriComponentsBuilder.newInstance()
        .scheme("http").host(host).port(port).path(endpoint).build();
    return uriComponents.toUriString();
  }

  String getURL() {
    String host = env.getRequiredProperty("spring.HydraEndpoint.host");
    String endpoint = env.getRequiredProperty("spring.HydraEndpoint.endpoint");
    UriComponents uriComponents = UriComponentsBuilder.newInstance()
        .scheme("http").host(host).path(endpoint).build();
    return uriComponents.toUriString();
  }
}
