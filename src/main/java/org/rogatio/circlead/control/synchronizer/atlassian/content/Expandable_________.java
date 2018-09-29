
package org.rogatio.circlead.control.synchronizer.atlassian.content;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

// TODO: Auto-generated Javadoc
/**
 * The Class Expandable_________.
 */
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

    /** The editor. */
    @JsonProperty("editor")
    private String editor;
    
    /** The atlas doc format. */
    @JsonProperty("atlas_doc_format")
    private String atlasDocFormat;
    
    /** The view. */
    @JsonProperty("view")
    private String view;
    
    /** The export view. */
    @JsonProperty("export_view")
    private String exportView;
    
    /** The styled view. */
    @JsonProperty("styled_view")
    private String styledView;
    
    /** The editor 2. */
    @JsonProperty("editor2")
    private String editor2;
    
    /** The anonymous export view. */
    @JsonProperty("anonymous_export_view")
    private String anonymousExportView;
    
    /** The additional properties. */
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * Gets the editor.
     *
     * @return the editor
     */
    @JsonProperty("editor")
    public String getEditor() {
        return editor;
    }

    /**
     * Sets the editor.
     *
     * @param editor the new editor
     */
    @JsonProperty("editor")
    public void setEditor(String editor) {
        this.editor = editor;
    }

    /**
     * Gets the atlas doc format.
     *
     * @return the atlas doc format
     */
    @JsonProperty("atlas_doc_format")
    public String getAtlasDocFormat() {
        return atlasDocFormat;
    }

    /**
     * Sets the atlas doc format.
     *
     * @param atlasDocFormat the new atlas doc format
     */
    @JsonProperty("atlas_doc_format")
    public void setAtlasDocFormat(String atlasDocFormat) {
        this.atlasDocFormat = atlasDocFormat;
    }

    /**
     * Gets the view.
     *
     * @return the view
     */
    @JsonProperty("view")
    public String getView() {
        return view;
    }

    /**
     * Sets the view.
     *
     * @param view the new view
     */
    @JsonProperty("view")
    public void setView(String view) {
        this.view = view;
    }

    /**
     * Gets the export view.
     *
     * @return the export view
     */
    @JsonProperty("export_view")
    public String getExportView() {
        return exportView;
    }

    /**
     * Sets the export view.
     *
     * @param exportView the new export view
     */
    @JsonProperty("export_view")
    public void setExportView(String exportView) {
        this.exportView = exportView;
    }

    /**
     * Gets the styled view.
     *
     * @return the styled view
     */
    @JsonProperty("styled_view")
    public String getStyledView() {
        return styledView;
    }

    /**
     * Sets the styled view.
     *
     * @param styledView the new styled view
     */
    @JsonProperty("styled_view")
    public void setStyledView(String styledView) {
        this.styledView = styledView;
    }

    /**
     * Gets the editor 2.
     *
     * @return the editor 2
     */
    @JsonProperty("editor2")
    public String getEditor2() {
        return editor2;
    }

    /**
     * Sets the editor 2.
     *
     * @param editor2 the new editor 2
     */
    @JsonProperty("editor2")
    public void setEditor2(String editor2) {
        this.editor2 = editor2;
    }

    /**
     * Gets the anonymous export view.
     *
     * @return the anonymous export view
     */
    @JsonProperty("anonymous_export_view")
    public String getAnonymousExportView() {
        return anonymousExportView;
    }

    /**
     * Sets the anonymous export view.
     *
     * @param anonymousExportView the new anonymous export view
     */
    @JsonProperty("anonymous_export_view")
    public void setAnonymousExportView(String anonymousExportView) {
        this.anonymousExportView = anonymousExportView;
    }

    /**
     * Gets the additional properties.
     *
     * @return the additional properties
     */
    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    /**
     * Sets the additional property.
     *
     * @param name the name
     * @param value the value
     */
    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
