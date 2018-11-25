
package org.rogatio.circlead.control.synchronizer.atlassian.attachment;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "webui",
    "self",
    "download"
})
public class Links {

    @JsonProperty("webui")
    private String webui;
    @JsonProperty("self")
    private String self;
    @JsonProperty("download")
    private String download;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("webui")
    public String getWebui() {
        return webui;
    }

    @JsonProperty("webui")
    public void setWebui(String webui) {
        this.webui = webui;
    }

    @JsonProperty("self")
    public String getSelf() {
        return self;
    }

    @JsonProperty("self")
    public void setSelf(String self) {
        this.self = self;
    }

    @JsonProperty("download")
    public String getDownload() {
        return download;
    }

    @JsonProperty("download")
    public void setDownload(String download) {
        this.download = download;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
