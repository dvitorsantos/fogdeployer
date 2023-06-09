package lsdi.fogdeployer.Controllers;

import lsdi.fogdeployer.DataTransferObjects.DeployFogRequest;
import lsdi.fogdeployer.Services.DeployService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class DeployController {
    RestTemplate restTemplate = new RestTemplate();
    private final String workerUrl = System.getenv("WORKER_URL");
    private final String collectorUrl = System.getenv("COLLECTOR_URL");
    DeployService deployService = new DeployService();

    @PostMapping("/deploy")
    public void deploy(@RequestBody DeployFogRequest deployRequest) {
        deployService.deploy(deployRequest);
    }
}
