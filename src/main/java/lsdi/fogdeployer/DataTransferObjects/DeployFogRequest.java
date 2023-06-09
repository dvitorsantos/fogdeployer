package lsdi.fogdeployer.DataTransferObjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.List;

@Data
public class DeployFogRequest extends DeployRequest {
    @Nullable
    @JsonProperty("edge_deploy_requests")
    List<DeployRequest> edgeDeployRequests;
}
