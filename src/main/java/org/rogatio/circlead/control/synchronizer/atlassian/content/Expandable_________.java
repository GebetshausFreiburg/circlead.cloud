
package org.rogatio.circlead.control.synchronizer.atlassian.content;

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
    "editor",
    "atlas_doc_format",
    "view",
    "export_view",
    "styled_view",
    "editor2",
    "anonymous_export_view"
})
public class Expandable_________ {

    @JsonProperty("editor")
    private String editor;
    @JsonProperty("atlas_doc_format")
    private String atlasDocFormat;
    @JsonProperty("view")
    private String view;
    @JsonProperty("export_view")
    private String exportView;
    @JsonProperty("styled_view")
    private String styledView;
    @JsonProperty("editor2")
    private String editor2;
    @JsonProperty("anonymous_export_view")
    private String anonymousExportView;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("editor")
    public String getEditor() {
        return editor;
    }

    @JsonProperty("editor")
    public void setEditor(String editor) {
        this.editor = editor;
    }

    @JsonProperty("atlas_doc_format")
    public String getAtlasDocFormat() {
        return atlasDocFormat;
    }

    @JsonProperty("atlas_doc_format")
    public void setAtlasDocFormat(String atlasDocFormat) {
        this.atlasDocFormat = atlasDocFormat;
    }

    @JsonProperty("view")
    public String getView() {
        return view;
    }

    @JsonProperty("view")
    public void setView(String view) {
        this.view = view;
    }

    @JsonProperty("export_view")
    public String getExportView() {
        return exportView;
    }

    @JsonProperty("export_view")
    public void setExportView(String exportView) {
        this.exportView = exportView;
    }

    @JsonProperty("styled_view")
    public String getStyledView() {
        return styledView;
    }

    @JsonProperty("styled_view")
    public void setStyledView(String styledView) {
        this.styledView = styledView;
    }

    @JsonProperty("editor2")
    public String getEditor2() {
        return editor2;
    }

    @JsonProperty("editor2")
    public void setEditor2(String editor2) {
        this.editor2 = editor2;
    }

    @JsonProperty("anonymous_export_view")
    public String getAnonymousExportView() {
        return anonymousExportView;
    }

    @JsonProperty("anonymous_export_view")
    public void setAnonymousExportView(String anonymousExportView) {
        this.anonymousExportView = anonymousExportView;
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
