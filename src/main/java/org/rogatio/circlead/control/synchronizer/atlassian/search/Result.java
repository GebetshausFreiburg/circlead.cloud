
package org.rogatio.circlead.control.synchronizer.atlassian.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

// TODO: Auto-generated Javadoc
/**
 * The Class Result.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "content",
    "title",
    "excerpt",
    "url",
    "resultGlobalContainer",
    "breadcrumbs",
    "entityType",
    "iconCssClass",
    "lastModified",
    "friendlyLastModified",
    "score"
})
public class Result {

    /** The content. */
    @JsonProperty("content")
    private Content content;
    
    /** The title. */
    @JsonProperty("title")
    private String title;
    
    /** The excerpt. */
    @JsonProperty("excerpt")
    private String excerpt;
    
    /** The url. */
    @JsonProperty("url")
    private String url;
    
    /** The result global container. */
    @JsonProperty("resultGlobalContainer")
    private ResultGlobalContainer resultGlobalContainer;
    
    /** The breadcrumbs. */
    @JsonProperty("breadcrumbs")
    private List<Object> breadcrumbs = null;
    
    /** The entity type. */
    @JsonProperty("entityType")
    private String entityType;
    
    /** The icon css class. */
    @JsonProperty("iconCssClass")
    private String iconCssClass;
    
    /** The last modified. */
    @JsonProperty("lastModified")
    private String lastModified;
    
    /** The friendly last modified. */
    @JsonProperty("friendlyLastModified")
    private String friendlyLastModified;
    
    /** The score. */
    @JsonProperty("score")
    private Double score;
    
    /** The additional properties. */
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * Gets the content.
     *
     * @return the content
     */
    @JsonProperty("content")
    public Content getContent() {
        return content;
    }

    /**
     * Sets the content.
     *
     * @param content the new content
     */
    @JsonProperty("content")
    public void setContent(Content content) {
        this.content = content;
    }

    /**
     * Gets the title.
     *
     * @return the title
     */
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     *
     * @param title the new title
     */
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the excerpt.
     *
     * @return the excerpt
     */
    @JsonProperty("excerpt")
    public String getExcerpt() {
        return excerpt;
    }

    /**
     * Sets the excerpt.
     *
     * @param excerpt the new excerpt
     */
    @JsonProperty("excerpt")
    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    /**
     * Gets the url.
     *
     * @return the url
     */
    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    /**
     * Sets the url.
     *
     * @param url the new url
     */
    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets the result global container.
     *
     * @return the result global container
     */
    @JsonProperty("resultGlobalContainer")
    public ResultGlobalContainer getResultGlobalContainer() {
        return resultGlobalContainer;
    }

    /**
     * Sets the result global container.
     *
     * @param resultGlobalContainer the new result global container
     */
    @JsonProperty("resultGlobalContainer")
    public void setResultGlobalContainer(ResultGlobalContainer resultGlobalContainer) {
        this.resultGlobalContainer = resultGlobalContainer;
    }

    /**
     * Gets the breadcrumbs.
     *
     * @return the breadcrumbs
     */
    @JsonProperty("breadcrumbs")
    public List<Object> getBreadcrumbs() {
        return breadcrumbs;
    }

    /**
     * Sets the breadcrumbs.
     *
     * @param breadcrumbs the new breadcrumbs
     */
    @JsonProperty("breadcrumbs")
    public void setBreadcrumbs(List<Object> breadcrumbs) {
        this.breadcrumbs = breadcrumbs;
    }

    /**
     * Gets the entity type.
     *
     * @return the entity type
     */
    @JsonProperty("entityType")
    public String getEntityType() {
        return entityType;
    }

    /**
     * Sets the entity type.
     *
     * @param entityType the new entity type
     */
    @JsonProperty("entityType")
    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    /**
     * Gets the icon css class.
     *
     * @return the icon css class
     */
    @JsonProperty("iconCssClass")
    public String getIconCssClass() {
        return iconCssClass;
    }

    /**
     * Sets the icon css class.
     *
     * @param iconCssClass the new icon css class
     */
    @JsonProperty("iconCssClass")
    public void setIconCssClass(String iconCssClass) {
        this.iconCssClass = iconCssClass;
    }

    /**
     * Gets the last modified.
     *
     * @return the last modified
     */
    @JsonProperty("lastModified")
    public String getLastModified() {
        return lastModified;
    }

    /**
     * Sets the last modified.
     *
     * @param lastModified the new last modified
     */
    @JsonProperty("lastModified")
    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    /**
     * Gets the friendly last modified.
     *
     * @return the friendly last modified
     */
    @JsonProperty("friendlyLastModified")
    public String getFriendlyLastModified() {
        return friendlyLastModified;
    }

    /**
     * Sets the friendly last modified.
     *
     * @param friendlyLastModified the new friendly last modified
     */
    @JsonProperty("friendlyLastModified")
    public void setFriendlyLastModified(String friendlyLastModified) {
        this.friendlyLastModified = friendlyLastModified;
    }

    /**
     * Gets the score.
     *
     * @return the score
     */
    @JsonProperty("score")
    public Double getScore() {
        return score;
    }

    /**
     * Sets the score.
     *
     * @param score the new score
     */
    @JsonProperty("score")
    public void setScore(Double score) {
        this.score = score;
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
