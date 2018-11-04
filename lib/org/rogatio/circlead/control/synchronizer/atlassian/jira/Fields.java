
package org.rogatio.circlead.control.synchronizer.atlassian.jira;

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
 * The Class Fields.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "issuetype",
    "timespent",
    "project",
    "fixVersions",
    "aggregatetimespent",
    "resolution",
    "resolutiondate",
    "workratio",
    "watches",
    "lastViewed",
    "customfield_10060",
    "customfield_10061",
    "created",
    "customfield_10062",
    "customfield_10063",
    "customfield_10064",
    "customfield_10065",
    "customfield_10066",
    "priority",
    "labels",
    "aggregatetimeoriginalestimate",
    "timeestimate",
    "versions",
    "issuelinks",
    "assignee",
    "updated",
    "status",
    "components",
    "timeoriginalestimate",
    "description",
    "customfield_10010",
    "customfield_10011",
    "customfield_10012",
    "customfield_10013",
    "customfield_10058",
    "timetracking",
    "customfield_10059",
    "security",
    "customfield_10008",
    "aggregatetimeestimate",
    "attachment",
    "customfield_10009",
    "summary",
    "creator",
    "subtasks",
    "reporter",
    "customfield_10000",
    "aggregateprogress",
    "customfield_10001",
    "customfield_10004",
    "environment",
    "duedate",
    "progress",
    "comment",
    "votes",
    "worklog"
})
public class Fields {

    /** The issuetype. */
    @JsonProperty("issuetype")
    private Issuetype issuetype;
    
    /** The timespent. */
    @JsonProperty("timespent")
    private Object timespent;
    
    /** The project. */
    @JsonProperty("project")
    private Project project;
    
    /** The fix versions. */
    @JsonProperty("fixVersions")
    private List<Object> fixVersions = null;
    
    /** The aggregatetimespent. */
    @JsonProperty("aggregatetimespent")
    private Object aggregatetimespent;
    
    /** The resolution. */
    @JsonProperty("resolution")
    private Resolution resolution;
    
    /** The resolutiondate. */
    @JsonProperty("resolutiondate")
    private String resolutiondate;
    
    /** The workratio. */
    @JsonProperty("workratio")
    private Integer workratio;
    
    /** The watches. */
    @JsonProperty("watches")
    private Watches watches;
    
    /** The last viewed. */
    @JsonProperty("lastViewed")
    private Object lastViewed;
    
    /** The customfield 10060. */
    @JsonProperty("customfield_10060")
    private Object customfield10060;
    
    /** The customfield 10061. */
    @JsonProperty("customfield_10061")
    private Object customfield10061;
    
    /** The created. */
    @JsonProperty("created")
    private String created;
    
    /** The customfield 10062. */
    @JsonProperty("customfield_10062")
    private Object customfield10062;
    
    /** The customfield 10063. */
    @JsonProperty("customfield_10063")
    private Object customfield10063;
    
    /** The customfield 10064. */
    @JsonProperty("customfield_10064")
    private Object customfield10064;
    
    /** The customfield 10065. */
    @JsonProperty("customfield_10065")
    private Object customfield10065;
    
    /** The customfield 10066. */
    @JsonProperty("customfield_10066")
    private List<Object> customfield10066 = null;
    
    /** The priority. */
    @JsonProperty("priority")
    private Priority priority;
    
    /** The labels. */
    @JsonProperty("labels")
    private List<String> labels = null;
    
    /** The aggregatetimeoriginalestimate. */
    @JsonProperty("aggregatetimeoriginalestimate")
    private Object aggregatetimeoriginalestimate;
    
    /** The timeestimate. */
    @JsonProperty("timeestimate")
    private Object timeestimate;
    
    /** The versions. */
    @JsonProperty("versions")
    private List<Object> versions = null;
    
    /** The issuelinks. */
    @JsonProperty("issuelinks")
    private List<Issuelink> issuelinks = null;
    
    /** The assignee. */
    @JsonProperty("assignee")
    private Assignee assignee;
    
    /** The updated. */
    @JsonProperty("updated")
    private String updated;
    
    /** The status. */
    @JsonProperty("status")
    private Status_ status;
    
    /** The components. */
    @JsonProperty("components")
    private List<Object> components = null;
    
    /** The timeoriginalestimate. */
    @JsonProperty("timeoriginalestimate")
    private Object timeoriginalestimate;
    
    /** The description. */
    @JsonProperty("description")
    private Description description;
    
    /** The customfield 10010. */
    @JsonProperty("customfield_10010")
    private Object customfield10010;
    
    /** The customfield 10011. */
    @JsonProperty("customfield_10011")
    private String customfield10011;
    
    /** The customfield 10012. */
    @JsonProperty("customfield_10012")
    private Object customfield10012;
    
    /** The customfield 10013. */
    @JsonProperty("customfield_10013")
    private String customfield10013;
    
    /** The customfield 10058. */
    @JsonProperty("customfield_10058")
    private Object customfield10058;
    
    /** The timetracking. */
    @JsonProperty("timetracking")
    private Timetracking timetracking;
    
    /** The customfield 10059. */
    @JsonProperty("customfield_10059")
    private Object customfield10059;
    
    /** The security. */
    @JsonProperty("security")
    private Object security;
    
    /** The customfield 10008. */
    @JsonProperty("customfield_10008")
    private Object customfield10008;
    
    /** The aggregatetimeestimate. */
    @JsonProperty("aggregatetimeestimate")
    private Object aggregatetimeestimate;
    
    /** The attachment. */
    @JsonProperty("attachment")
    private List<Object> attachment = null;
    
    /** The customfield 10009. */
    @JsonProperty("customfield_10009")
    private Customfield10009 customfield10009;
    
    /** The summary. */
    @JsonProperty("summary")
    private String summary;
    
    /** The creator. */
    @JsonProperty("creator")
    private Creator creator;
    
    /** The subtasks. */
    @JsonProperty("subtasks")
    private List<Object> subtasks = null;
    
    /** The reporter. */
    @JsonProperty("reporter")
    private Reporter reporter;
    
    /** The customfield 10000. */
    @JsonProperty("customfield_10000")
    private String customfield10000;
    
    /** The aggregateprogress. */
    @JsonProperty("aggregateprogress")
    private Aggregateprogress aggregateprogress;
    
    /** The customfield 10001. */
    @JsonProperty("customfield_10001")
    private Customfield10001 customfield10001;
    
    /** The customfield 10004. */
    @JsonProperty("customfield_10004")
    private Object customfield10004;
    
    /** The environment. */
    @JsonProperty("environment")
    private Object environment;
    
    /** The duedate. */
    @JsonProperty("duedate")
    private Object duedate;
    
    /** The progress. */
    @JsonProperty("progress")
    private Progress progress;
    
    /** The comment. */
    @JsonProperty("comment")
    private Comment comment;
    
    /** The votes. */
    @JsonProperty("votes")
    private Votes votes;
    
    /** The worklog. */
    @JsonProperty("worklog")
    private Worklog worklog;
    
    /** The additional properties. */
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * Gets the issuetype.
     *
     * @return the issuetype
     */
    @JsonProperty("issuetype")
    public Issuetype getIssuetype() {
        return issuetype;
    }

    /**
     * Sets the issuetype.
     *
     * @param issuetype the new issuetype
     */
    @JsonProperty("issuetype")
    public void setIssuetype(Issuetype issuetype) {
        this.issuetype = issuetype;
    }

    /**
     * Gets the timespent.
     *
     * @return the timespent
     */
    @JsonProperty("timespent")
    public Object getTimespent() {
        return timespent;
    }

    /**
     * Sets the timespent.
     *
     * @param timespent the new timespent
     */
    @JsonProperty("timespent")
    public void setTimespent(Object timespent) {
        this.timespent = timespent;
    }

    /**
     * Gets the project.
     *
     * @return the project
     */
    @JsonProperty("project")
    public Project getProject() {
        return project;
    }

    /**
     * Sets the project.
     *
     * @param project the new project
     */
    @JsonProperty("project")
    public void setProject(Project project) {
        this.project = project;
    }

    /**
     * Gets the fix versions.
     *
     * @return the fix versions
     */
    @JsonProperty("fixVersions")
    public List<Object> getFixVersions() {
        return fixVersions;
    }

    /**
     * Sets the fix versions.
     *
     * @param fixVersions the new fix versions
     */
    @JsonProperty("fixVersions")
    public void setFixVersions(List<Object> fixVersions) {
        this.fixVersions = fixVersions;
    }

    /**
     * Gets the aggregatetimespent.
     *
     * @return the aggregatetimespent
     */
    @JsonProperty("aggregatetimespent")
    public Object getAggregatetimespent() {
        return aggregatetimespent;
    }

    /**
     * Sets the aggregatetimespent.
     *
     * @param aggregatetimespent the new aggregatetimespent
     */
    @JsonProperty("aggregatetimespent")
    public void setAggregatetimespent(Object aggregatetimespent) {
        this.aggregatetimespent = aggregatetimespent;
    }

    /**
     * Gets the resolution.
     *
     * @return the resolution
     */
    @JsonProperty("resolution")
    public Resolution getResolution() {
        return resolution;
    }

    /**
     * Sets the resolution.
     *
     * @param resolution the new resolution
     */
    @JsonProperty("resolution")
    public void setResolution(Resolution resolution) {
        this.resolution = resolution;
    }

    /**
     * Gets the resolutiondate.
     *
     * @return the resolutiondate
     */
    @JsonProperty("resolutiondate")
    public String getResolutiondate() {
        return resolutiondate;
    }

    /**
     * Sets the resolutiondate.
     *
     * @param resolutiondate the new resolutiondate
     */
    @JsonProperty("resolutiondate")
    public void setResolutiondate(String resolutiondate) {
        this.resolutiondate = resolutiondate;
    }

    /**
     * Gets the workratio.
     *
     * @return the workratio
     */
    @JsonProperty("workratio")
    public Integer getWorkratio() {
        return workratio;
    }

    /**
     * Sets the workratio.
     *
     * @param workratio the new workratio
     */
    @JsonProperty("workratio")
    public void setWorkratio(Integer workratio) {
        this.workratio = workratio;
    }

    /**
     * Gets the watches.
     *
     * @return the watches
     */
    @JsonProperty("watches")
    public Watches getWatches() {
        return watches;
    }

    /**
     * Sets the watches.
     *
     * @param watches the new watches
     */
    @JsonProperty("watches")
    public void setWatches(Watches watches) {
        this.watches = watches;
    }

    /**
     * Gets the last viewed.
     *
     * @return the last viewed
     */
    @JsonProperty("lastViewed")
    public Object getLastViewed() {
        return lastViewed;
    }

    /**
     * Sets the last viewed.
     *
     * @param lastViewed the new last viewed
     */
    @JsonProperty("lastViewed")
    public void setLastViewed(Object lastViewed) {
        this.lastViewed = lastViewed;
    }

    /**
     * Gets the customfield 10060.
     *
     * @return the customfield 10060
     */
    @JsonProperty("customfield_10060")
    public Object getCustomfield10060() {
        return customfield10060;
    }

    /**
     * Sets the customfield 10060.
     *
     * @param customfield10060 the new customfield 10060
     */
    @JsonProperty("customfield_10060")
    public void setCustomfield10060(Object customfield10060) {
        this.customfield10060 = customfield10060;
    }

    /**
     * Gets the customfield 10061.
     *
     * @return the customfield 10061
     */
    @JsonProperty("customfield_10061")
    public Object getCustomfield10061() {
        return customfield10061;
    }

    /**
     * Sets the customfield 10061.
     *
     * @param customfield10061 the new customfield 10061
     */
    @JsonProperty("customfield_10061")
    public void setCustomfield10061(Object customfield10061) {
        this.customfield10061 = customfield10061;
    }

    /**
     * Gets the created.
     *
     * @return the created
     */
    @JsonProperty("created")
    public String getCreated() {
        return created;
    }

    /**
     * Sets the created.
     *
     * @param created the new created
     */
    @JsonProperty("created")
    public void setCreated(String created) {
        this.created = created;
    }

    /**
     * Gets the customfield 10062.
     *
     * @return the customfield 10062
     */
    @JsonProperty("customfield_10062")
    public Object getCustomfield10062() {
        return customfield10062;
    }

    /**
     * Sets the customfield 10062.
     *
     * @param customfield10062 the new customfield 10062
     */
    @JsonProperty("customfield_10062")
    public void setCustomfield10062(Object customfield10062) {
        this.customfield10062 = customfield10062;
    }

    /**
     * Gets the customfield 10063.
     *
     * @return the customfield 10063
     */
    @JsonProperty("customfield_10063")
    public Object getCustomfield10063() {
        return customfield10063;
    }

    /**
     * Sets the customfield 10063.
     *
     * @param customfield10063 the new customfield 10063
     */
    @JsonProperty("customfield_10063")
    public void setCustomfield10063(Object customfield10063) {
        this.customfield10063 = customfield10063;
    }

    /**
     * Gets the customfield 10064.
     *
     * @return the customfield 10064
     */
    @JsonProperty("customfield_10064")
    public Object getCustomfield10064() {
        return customfield10064;
    }

    /**
     * Sets the customfield 10064.
     *
     * @param customfield10064 the new customfield 10064
     */
    @JsonProperty("customfield_10064")
    public void setCustomfield10064(Object customfield10064) {
        this.customfield10064 = customfield10064;
    }

    /**
     * Gets the customfield 10065.
     *
     * @return the customfield 10065
     */
    @JsonProperty("customfield_10065")
    public Object getCustomfield10065() {
        return customfield10065;
    }

    /**
     * Sets the customfield 10065.
     *
     * @param customfield10065 the new customfield 10065
     */
    @JsonProperty("customfield_10065")
    public void setCustomfield10065(Object customfield10065) {
        this.customfield10065 = customfield10065;
    }

    /**
     * Gets the customfield 10066.
     *
     * @return the customfield 10066
     */
    @JsonProperty("customfield_10066")
    public List<Object> getCustomfield10066() {
        return customfield10066;
    }

    /**
     * Sets the customfield 10066.
     *
     * @param customfield10066 the new customfield 10066
     */
    @JsonProperty("customfield_10066")
    public void setCustomfield10066(List<Object> customfield10066) {
        this.customfield10066 = customfield10066;
    }

    /**
     * Gets the priority.
     *
     * @return the priority
     */
    @JsonProperty("priority")
    public Priority getPriority() {
        return priority;
    }

    /**
     * Sets the priority.
     *
     * @param priority the new priority
     */
    @JsonProperty("priority")
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    /**
     * Gets the labels.
     *
     * @return the labels
     */
    @JsonProperty("labels")
    public List<String> getLabels() {
        return labels;
    }

    /**
     * Sets the labels.
     *
     * @param labels the new labels
     */
    @JsonProperty("labels")
    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    /**
     * Gets the aggregatetimeoriginalestimate.
     *
     * @return the aggregatetimeoriginalestimate
     */
    @JsonProperty("aggregatetimeoriginalestimate")
    public Object getAggregatetimeoriginalestimate() {
        return aggregatetimeoriginalestimate;
    }

    /**
     * Sets the aggregatetimeoriginalestimate.
     *
     * @param aggregatetimeoriginalestimate the new aggregatetimeoriginalestimate
     */
    @JsonProperty("aggregatetimeoriginalestimate")
    public void setAggregatetimeoriginalestimate(Object aggregatetimeoriginalestimate) {
        this.aggregatetimeoriginalestimate = aggregatetimeoriginalestimate;
    }

    /**
     * Gets the timeestimate.
     *
     * @return the timeestimate
     */
    @JsonProperty("timeestimate")
    public Object getTimeestimate() {
        return timeestimate;
    }

    /**
     * Sets the timeestimate.
     *
     * @param timeestimate the new timeestimate
     */
    @JsonProperty("timeestimate")
    public void setTimeestimate(Object timeestimate) {
        this.timeestimate = timeestimate;
    }

    /**
     * Gets the versions.
     *
     * @return the versions
     */
    @JsonProperty("versions")
    public List<Object> getVersions() {
        return versions;
    }

    /**
     * Sets the versions.
     *
     * @param versions the new versions
     */
    @JsonProperty("versions")
    public void setVersions(List<Object> versions) {
        this.versions = versions;
    }

    /**
     * Gets the issuelinks.
     *
     * @return the issuelinks
     */
    @JsonProperty("issuelinks")
    public List<Issuelink> getIssuelinks() {
        return issuelinks;
    }

    /**
     * Sets the issuelinks.
     *
     * @param issuelinks the new issuelinks
     */
    @JsonProperty("issuelinks")
    public void setIssuelinks(List<Issuelink> issuelinks) {
        this.issuelinks = issuelinks;
    }

    /**
     * Gets the assignee.
     *
     * @return the assignee
     */
    @JsonProperty("assignee")
    public Assignee getAssignee() {
        return assignee;
    }

    /**
     * Sets the assignee.
     *
     * @param assignee the new assignee
     */
    @JsonProperty("assignee")
    public void setAssignee(Assignee assignee) {
        this.assignee = assignee;
    }

    /**
     * Gets the updated.
     *
     * @return the updated
     */
    @JsonProperty("updated")
    public String getUpdated() {
        return updated;
    }

    /**
     * Sets the updated.
     *
     * @param updated the new updated
     */
    @JsonProperty("updated")
    public void setUpdated(String updated) {
        this.updated = updated;
    }

    /**
     * Gets the status.
     *
     * @return the status
     */
    @JsonProperty("status")
    public Status_ getStatus() {
        return status;
    }

    /**
     * Sets the status.
     *
     * @param status the new status
     */
    @JsonProperty("status")
    public void setStatus(Status_ status) {
        this.status = status;
    }

    /**
     * Gets the components.
     *
     * @return the components
     */
    @JsonProperty("components")
    public List<Object> getComponents() {
        return components;
    }

    /**
     * Sets the components.
     *
     * @param components the new components
     */
    @JsonProperty("components")
    public void setComponents(List<Object> components) {
        this.components = components;
    }

    /**
     * Gets the timeoriginalestimate.
     *
     * @return the timeoriginalestimate
     */
    @JsonProperty("timeoriginalestimate")
    public Object getTimeoriginalestimate() {
        return timeoriginalestimate;
    }

    /**
     * Sets the timeoriginalestimate.
     *
     * @param timeoriginalestimate the new timeoriginalestimate
     */
    @JsonProperty("timeoriginalestimate")
    public void setTimeoriginalestimate(Object timeoriginalestimate) {
        this.timeoriginalestimate = timeoriginalestimate;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    @JsonProperty("description")
    public Description getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description the new description
     */
    @JsonProperty("description")
    public void setDescription(Description description) {
        this.description = description;
    }

    /**
     * Gets the customfield 10010.
     *
     * @return the customfield 10010
     */
    @JsonProperty("customfield_10010")
    public Object getCustomfield10010() {
        return customfield10010;
    }

    /**
     * Sets the customfield 10010.
     *
     * @param customfield10010 the new customfield 10010
     */
    @JsonProperty("customfield_10010")
    public void setCustomfield10010(Object customfield10010) {
        this.customfield10010 = customfield10010;
    }

    /**
     * Gets the customfield 10011.
     *
     * @return the customfield 10011
     */
    @JsonProperty("customfield_10011")
    public String getCustomfield10011() {
        return customfield10011;
    }

    /**
     * Sets the customfield 10011.
     *
     * @param customfield10011 the new customfield 10011
     */
    @JsonProperty("customfield_10011")
    public void setCustomfield10011(String customfield10011) {
        this.customfield10011 = customfield10011;
    }

    /**
     * Gets the customfield 10012.
     *
     * @return the customfield 10012
     */
    @JsonProperty("customfield_10012")
    public Object getCustomfield10012() {
        return customfield10012;
    }

    /**
     * Sets the customfield 10012.
     *
     * @param customfield10012 the new customfield 10012
     */
    @JsonProperty("customfield_10012")
    public void setCustomfield10012(Object customfield10012) {
        this.customfield10012 = customfield10012;
    }

    /**
     * Gets the customfield 10013.
     *
     * @return the customfield 10013
     */
    @JsonProperty("customfield_10013")
    public String getCustomfield10013() {
        return customfield10013;
    }

    /**
     * Sets the customfield 10013.
     *
     * @param customfield10013 the new customfield 10013
     */
    @JsonProperty("customfield_10013")
    public void setCustomfield10013(String customfield10013) {
        this.customfield10013 = customfield10013;
    }

    /**
     * Gets the customfield 10058.
     *
     * @return the customfield 10058
     */
    @JsonProperty("customfield_10058")
    public Object getCustomfield10058() {
        return customfield10058;
    }

    /**
     * Sets the customfield 10058.
     *
     * @param customfield10058 the new customfield 10058
     */
    @JsonProperty("customfield_10058")
    public void setCustomfield10058(Object customfield10058) {
        this.customfield10058 = customfield10058;
    }

    /**
     * Gets the timetracking.
     *
     * @return the timetracking
     */
    @JsonProperty("timetracking")
    public Timetracking getTimetracking() {
        return timetracking;
    }

    /**
     * Sets the timetracking.
     *
     * @param timetracking the new timetracking
     */
    @JsonProperty("timetracking")
    public void setTimetracking(Timetracking timetracking) {
        this.timetracking = timetracking;
    }

    /**
     * Gets the customfield 10059.
     *
     * @return the customfield 10059
     */
    @JsonProperty("customfield_10059")
    public Object getCustomfield10059() {
        return customfield10059;
    }

    /**
     * Sets the customfield 10059.
     *
     * @param customfield10059 the new customfield 10059
     */
    @JsonProperty("customfield_10059")
    public void setCustomfield10059(Object customfield10059) {
        this.customfield10059 = customfield10059;
    }

    /**
     * Gets the security.
     *
     * @return the security
     */
    @JsonProperty("security")
    public Object getSecurity() {
        return security;
    }

    /**
     * Sets the security.
     *
     * @param security the new security
     */
    @JsonProperty("security")
    public void setSecurity(Object security) {
        this.security = security;
    }

    /**
     * Gets the customfield 10008.
     *
     * @return the customfield 10008
     */
    @JsonProperty("customfield_10008")
    public Object getCustomfield10008() {
        return customfield10008;
    }

    /**
     * Sets the customfield 10008.
     *
     * @param customfield10008 the new customfield 10008
     */
    @JsonProperty("customfield_10008")
    public void setCustomfield10008(Object customfield10008) {
        this.customfield10008 = customfield10008;
    }

    /**
     * Gets the aggregatetimeestimate.
     *
     * @return the aggregatetimeestimate
     */
    @JsonProperty("aggregatetimeestimate")
    public Object getAggregatetimeestimate() {
        return aggregatetimeestimate;
    }

    /**
     * Sets the aggregatetimeestimate.
     *
     * @param aggregatetimeestimate the new aggregatetimeestimate
     */
    @JsonProperty("aggregatetimeestimate")
    public void setAggregatetimeestimate(Object aggregatetimeestimate) {
        this.aggregatetimeestimate = aggregatetimeestimate;
    }

    /**
     * Gets the attachment.
     *
     * @return the attachment
     */
    @JsonProperty("attachment")
    public List<Object> getAttachment() {
        return attachment;
    }

    /**
     * Sets the attachment.
     *
     * @param attachment the new attachment
     */
    @JsonProperty("attachment")
    public void setAttachment(List<Object> attachment) {
        this.attachment = attachment;
    }

    /**
     * Gets the customfield 10009.
     *
     * @return the customfield 10009
     */
    @JsonProperty("customfield_10009")
    public Customfield10009 getCustomfield10009() {
        return customfield10009;
    }

    /**
     * Sets the customfield 10009.
     *
     * @param customfield10009 the new customfield 10009
     */
    @JsonProperty("customfield_10009")
    public void setCustomfield10009(Customfield10009 customfield10009) {
        this.customfield10009 = customfield10009;
    }

    /**
     * Gets the summary.
     *
     * @return the summary
     */
    @JsonProperty("summary")
    public String getSummary() {
        return summary;
    }

    /**
     * Sets the summary.
     *
     * @param summary the new summary
     */
    @JsonProperty("summary")
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * Gets the creator.
     *
     * @return the creator
     */
    @JsonProperty("creator")
    public Creator getCreator() {
        return creator;
    }

    /**
     * Sets the creator.
     *
     * @param creator the new creator
     */
    @JsonProperty("creator")
    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    /**
     * Gets the subtasks.
     *
     * @return the subtasks
     */
    @JsonProperty("subtasks")
    public List<Object> getSubtasks() {
        return subtasks;
    }

    /**
     * Sets the subtasks.
     *
     * @param subtasks the new subtasks
     */
    @JsonProperty("subtasks")
    public void setSubtasks(List<Object> subtasks) {
        this.subtasks = subtasks;
    }

    /**
     * Gets the reporter.
     *
     * @return the reporter
     */
    @JsonProperty("reporter")
    public Reporter getReporter() {
        return reporter;
    }

    /**
     * Sets the reporter.
     *
     * @param reporter the new reporter
     */
    @JsonProperty("reporter")
    public void setReporter(Reporter reporter) {
        this.reporter = reporter;
    }

    /**
     * Gets the customfield 10000.
     *
     * @return the customfield 10000
     */
    @JsonProperty("customfield_10000")
    public String getCustomfield10000() {
        return customfield10000;
    }

    /**
     * Sets the customfield 10000.
     *
     * @param customfield10000 the new customfield 10000
     */
    @JsonProperty("customfield_10000")
    public void setCustomfield10000(String customfield10000) {
        this.customfield10000 = customfield10000;
    }

    /**
     * Gets the aggregateprogress.
     *
     * @return the aggregateprogress
     */
    @JsonProperty("aggregateprogress")
    public Aggregateprogress getAggregateprogress() {
        return aggregateprogress;
    }

    /**
     * Sets the aggregateprogress.
     *
     * @param aggregateprogress the new aggregateprogress
     */
    @JsonProperty("aggregateprogress")
    public void setAggregateprogress(Aggregateprogress aggregateprogress) {
        this.aggregateprogress = aggregateprogress;
    }

    /**
     * Gets the customfield 10001.
     *
     * @return the customfield 10001
     */
    @JsonProperty("customfield_10001")
    public Customfield10001 getCustomfield10001() {
        return customfield10001;
    }

    /**
     * Sets the customfield 10001.
     *
     * @param customfield10001 the new customfield 10001
     */
    @JsonProperty("customfield_10001")
    public void setCustomfield10001(Customfield10001 customfield10001) {
        this.customfield10001 = customfield10001;
    }

    /**
     * Gets the customfield 10004.
     *
     * @return the customfield 10004
     */
    @JsonProperty("customfield_10004")
    public Object getCustomfield10004() {
        return customfield10004;
    }

    /**
     * Sets the customfield 10004.
     *
     * @param customfield10004 the new customfield 10004
     */
    @JsonProperty("customfield_10004")
    public void setCustomfield10004(Object customfield10004) {
        this.customfield10004 = customfield10004;
    }

    /**
     * Gets the environment.
     *
     * @return the environment
     */
    @JsonProperty("environment")
    public Object getEnvironment() {
        return environment;
    }

    /**
     * Sets the environment.
     *
     * @param environment the new environment
     */
    @JsonProperty("environment")
    public void setEnvironment(Object environment) {
        this.environment = environment;
    }

    /**
     * Gets the duedate.
     *
     * @return the duedate
     */
    @JsonProperty("duedate")
    public Object getDuedate() {
        return duedate;
    }

    /**
     * Sets the duedate.
     *
     * @param duedate the new duedate
     */
    @JsonProperty("duedate")
    public void setDuedate(Object duedate) {
        this.duedate = duedate;
    }

    /**
     * Gets the progress.
     *
     * @return the progress
     */
    @JsonProperty("progress")
    public Progress getProgress() {
        return progress;
    }

    /**
     * Sets the progress.
     *
     * @param progress the new progress
     */
    @JsonProperty("progress")
    public void setProgress(Progress progress) {
        this.progress = progress;
    }

    /**
     * Gets the comment.
     *
     * @return the comment
     */
    @JsonProperty("comment")
    public Comment getComment() {
        return comment;
    }

    /**
     * Sets the comment.
     *
     * @param comment the new comment
     */
    @JsonProperty("comment")
    public void setComment(Comment comment) {
        this.comment = comment;
    }

    /**
     * Gets the votes.
     *
     * @return the votes
     */
    @JsonProperty("votes")
    public Votes getVotes() {
        return votes;
    }

    /**
     * Sets the votes.
     *
     * @param votes the new votes
     */
    @JsonProperty("votes")
    public void setVotes(Votes votes) {
        this.votes = votes;
    }

    /**
     * Gets the worklog.
     *
     * @return the worklog
     */
    @JsonProperty("worklog")
    public Worklog getWorklog() {
        return worklog;
    }

    /**
     * Sets the worklog.
     *
     * @param worklog the new worklog
     */
    @JsonProperty("worklog")
    public void setWorklog(Worklog worklog) {
        this.worklog = worklog;
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
