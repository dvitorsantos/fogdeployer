package lsdi.fogdeployer.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lsdi.fogdeployer.DataTransferObjects.DeployFogRequest;
import lsdi.fogdeployer.DataTransferObjects.DeployRequest;
import lsdi.fogdeployer.DataTransferObjects.IoTGatewayRequest;
import lsdi.fogdeployer.DataTransferObjects.RuleRequestResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class DeployService {
    MqttService mqttService = MqttService.getInstance();
    RestTemplate restTemplate = new RestTemplate();
    private final String COLECTOR_URL = System.getenv("COLLECTOR_URL");
    private final String WORKER_URL = System.getenv("WORKER_URL");

    public void deploy(DeployFogRequest deployRequest) {
        deployFog(deployRequest);
        if (deployRequest.getEdgeDeployRequests() != null) {
            deployRequest.getEdgeDeployRequests().forEach(this::deployEdge);
        }
    }

    public void deployFog(DeployRequest deployRequest) {
        deployRequest.getRules().forEach(rule -> restTemplate.postForObject(COLECTOR_URL + "/subscribe/" + rule.getEventType(), null, DeployRequest.class));
        restTemplate.postForObject(WORKER_URL + "/deploy", deployRequest, DeployRequest.class);
    }

    public void deployEdge(DeployRequest deployRequest) {
        ObjectMapper mapper = new ObjectMapper();
        new Thread(() -> {
            try {
            
                mqttService.subscribe("/deploy-status/" + deployRequest.getHostUuid(), (topic, message) -> {
                    System.out.println("Deploy status received: " + message.getPayload());
                });

                System.out.println("INFO: Deploy request published to " + "/deploy/" + deployRequest.getHostUuid());
                mqttService.publish("/deploy/" + deployRequest.getHostUuid(), mapper.writeValueAsBytes(deployRequest));
        
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
