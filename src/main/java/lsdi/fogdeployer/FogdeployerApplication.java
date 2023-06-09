package lsdi.fogdeployer;

import lsdi.fogdeployer.DataTransferObjects.IoTGatewayRequest;
import lsdi.fogdeployer.Services.IoTCataloguerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class FogdeployerApplication {
	IoTCataloguerService ioTCataloguerService = IoTCataloguerService.getInstance();
	private final String deployerUuid = System.getenv("DEPLOYER_UUID");
	private final String deployerName = System.getenv("DEPLOYER_NAME");
	private final String deployerUrl = System.getenv("DEPLOYER_URL");

	public static void main(String[] args) {
		SpringApplication.run(FogdeployerApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void init() {
		selfRegister();
	}

	private void selfRegister() {
		IoTGatewayRequest request = new IoTGatewayRequest();
		request.setUuid(deployerUuid);
		request.setDistinguishedName(deployerName);
		request.setUrl(deployerUrl);
		request.setLatitude(1.0);
		request.setLongitude(1.0);

		ioTCataloguerService.registerGateway(request);
	}
}
