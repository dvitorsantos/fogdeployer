package lsdi.fogdeployer.Services;

import lsdi.fogdeployer.DataTransferObjects.IoTGatewayRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IoTCataloguerService {
    private static IoTCataloguerService instance;
    private final RestTemplate restTemplate = new RestTemplate();
    private final String url;

    private IoTCataloguerService() {
        url = System.getenv("IOTCATALOGER_URL");
    }

    public static IoTCataloguerService getInstance() {
        if (instance == null) {
            instance = new IoTCataloguerService();
        }
        return instance;
    }

    public void registerGateway(IoTGatewayRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-SSL-Client-DN", request.getDistinguishedName());

        HttpEntity<Object> entity = new HttpEntity<>(request, headers);
        restTemplate.postForObject(url + "/gateway", entity, IoTGatewayRequest.class);
    }

    public IoTGatewayRequest getGateway(String uuid) {
        return restTemplate.getForObject(url + "/gateway/" + uuid, IoTGatewayRequest.class);
    }
}
