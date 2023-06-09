package lsdi.fogdeployer.DataTransferObjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DeployRequest {
    @JsonProperty("host_uuid")
    private String hostUuid;
    @JsonProperty("webhook_url")
    private String webhookUrl;
    private List<RuleRequestResponse> rules;
}