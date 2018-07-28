
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

    @JsonProperty("content")
    private Content content;
    @JsonProperty("title")
    private String title;
    @JsonProperty("excerpt")
    private String excerpt;
    @JsonProperty("url")
    private String url;
    @JsonProperty("resultGlobalContainer")
    private ResultGlobalContainer resultGlobalContainer;
    @JsonProperty("breadcrumbs")
    private List<Object> breadcrumbs = null;
    @JsonProperty("entityType")
    private String entityType;
    @JsonProperty("iconCssClass")
    private String iconCssClass;
    @JsonProperty("lastModified")
    private String lastModified;
    @JsonProperty("friendlyLastModified")
    private String friendlyLastModified;
    @JsonProperty("score")
    private Double score;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("content")
    public Content getContent() {
        return content;
    }

    @JsonProperty("content")
    public void setContent(Content content) {
        this.content = content;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("excerpt")
    public String getExcerpt() {
        return excerpt;
    }

    @JsonProperty("excerpt")
    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty("resultGlobalContainer")
    public ResultGlobalContainer getResultGlobalContainer() {
        return resultGlobalContainer;
    }

    @JsonProperty("resultGlobalContainer")
    public void setResultGlobalContainer(ResultGlobalContainer resultGlobalContainer) {
        this.resultGlobalContainer = resultGlobalContainer;
    }

    @JsonProperty("breadcrumbs")
    public List<Object> getBreadcrumbs() {
        return breadcrumbs;
    }

    @JsonProperty("breadcrumbs")
    public void setBreadcrumbs(List<Object> breadcrumbs) {
        this.breadcrumbs = breadcrumbs;
    }

    @JsonProperty("entityType")
    public String getEntityType() {
        return entityType;
    }

    @JsonProperty("entityType")
    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    @JsonProperty("iconCssClass")
    public String getIconCssClass() {
        return iconCssClass;
    }

    @JsonProperty("iconCssClass")
    public void setIconCssClass(String iconCssClass) {
        this.iconCssClass = iconCssClass;
    }

    @JsonProperty("lastModified")
    public String getLastModified() {
        return lastModified;
    }

    @JsonProperty("lastModified")
    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    @JsonProperty("friendlyLastModified")
    public String getFriendlyLastModified() {
        return friendlyLastModified;
    }

    @JsonProperty("friendlyLastModified")
    public void setFriendlyLastModified(String friendlyLastModified) {
        this.friendlyLastModified = friendlyLastModified;
    }

    @JsonProperty("score")
    public Double getScore() {
        return score;
    }

    @JsonProperty("score")
    public void setScore(Double score) {
        this.score = score;
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
