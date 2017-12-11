package io.pivotal.gemfire.toolsmiths.hydradb;

import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.Host;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo.HostRepo;
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
    return dao.getHostByName(name)[0];
  }

  @GetMapping(value="", params="id")
  private Host getHost(@RequestParam("id") Integer id) {
    return dao.getHostById(id);
  }

}
