package io.pivotal.gemfire.toolsmiths.hdbr;

import io.pivotal.gemfire.toolsmiths.hdbr.data.gen.Host;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/host")
public class HostEndpoint {

  @Autowired
  HostRepo dao;

  @GetMapping(value="", params="name")
  private Host getHost(@RequestParam("name") String name) {

    Host theHost = dao.getHostByName(name);

    return theHost;
  }

  @GetMapping(value="", params="id")
  private Host getHost(@RequestParam("id") Integer id) {

    Host theHost = dao.getHostById(id);

    return theHost;
  }

  @GetMapping(value="/max")
  private Integer maxHost() {
    return dao.maxHost();
  }

}
