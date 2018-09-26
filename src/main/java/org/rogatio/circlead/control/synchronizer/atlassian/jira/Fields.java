
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

    @JsonProperty("issuetype")
    private Issuetype issuetype;
    @JsonProperty("timespent")
    private Object timespent;
    @JsonProperty("project")
    private Project project;
    @JsonProperty("fixVersions")
    private List<Object> fixVersions = null;
    @JsonProperty("aggregatetimespent")
    private Object aggregatetimespent;
    @JsonProperty("resolution")
    private Resolution resolution;
    @JsonProperty("resolutiondate")
    private String resolutiondate;
    @JsonProperty("workratio")
    private Integer workratio;
    @JsonProperty("watches")
    private Watches watches;
    @JsonProperty("lastViewed")
    private Object lastViewed;
    @JsonProperty("customfield_10060")
    private Object customfield10060;
    @JsonProperty("customfield_10061")
    private Object customfield10061;
    @JsonProperty("created")
    private String created;
    @JsonProperty("customfield_10062")
    private Object customfield10062;
    @JsonProperty("customfield_10063")
    private Object customfield10063;
    @JsonProperty("customfield_10064")
    private Object customfield10064;
    @JsonProperty("customfield_10065")
    private Object customfield10065;
    @JsonProperty("customfield_10066")
    private List<Object> customfield10066 = null;
    @JsonProperty("priority")
    private Priority priority;
    @JsonProperty("labels")
    private List<String> labels = null;
    @JsonProperty("aggregatetimeoriginalestimate")
    private Object aggregatetimeoriginalestimate;
    @JsonProperty("timeestimate")
    private Object timeestimate;
    @JsonProperty("versions")
    private List<Object> versions = null;
    @JsonProperty("issuelinks")
    private List<Issuelink> issuelinks = null;
    @JsonProperty("assignee")
    private Assignee assignee;
    @JsonProperty("updated")
    private String updated;
    @JsonProperty("status")
    private Status_ status;
    @JsonProperty("components")
    private List<Object> components = null;
    @JsonProperty("timeoriginalestimate")
    private Object timeoriginalestimate;
    @JsonProperty("description")
    private Description description;
    @JsonProperty("customfield_10010")
    private Object customfield10010;
    @JsonProperty("customfield_10011")
    private String customfield10011;
    @JsonProperty("customfield_10012")
    private Object customfield10012;
    @JsonProperty("customfield_10013")
    private String customfield10013;
    @JsonProperty("customfield_10058")
    private Object customfield10058;
    @JsonProperty("timetracking")
    private Timetracking timetracking;
    @JsonProperty("customfield_10059")
    private Object customfield10059;
    @JsonProperty("security")
    private Object security;
    @JsonProperty("customfield_10008")
    private Object customfield10008;
    @JsonProperty("aggregatetimeestimate")
    private Object aggregatetimeestimate;
    @JsonProperty("attachment")
    private List<Object> attachment = null;
    @JsonProperty("customfield_10009")
    private Customfield10009 customfield10009;
    @JsonProperty("summary")
    private String summary;
    @JsonProperty("creator")
    private Creator creator;
    @JsonProperty("subtasks")
    private List<Object> subtasks = null;
    @JsonProperty("reporter")
    private Reporter reporter;
    @JsonProperty("customfield_10000")
    private String customfield10000;
    @JsonProperty("aggregateprogress")
    private Aggregateprogress aggregateprogress;
    @JsonProperty("customfield_10001")
    private Customfield10001 customfield10001;
    @JsonProperty("customfield_10004")
    private Object customfield10004;
    @JsonProperty("environment")
    private Object environment;
    @JsonProperty("duedate")
    private Object duedate;
    @JsonProperty("progress")
    private Progress progress;
    @JsonProperty("comment")
    private Comment comment;
    @JsonProperty("votes")
    private Votes votes;
    @JsonProperty("worklog")
    private Worklog worklog;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("issuetype")
    public Issuetype getIssuetype() {
        return issuetype;
    }

    @JsonProperty("issuetype")
    public void setIssuetype(Issuetype issuetype) {
        this.issuetype = issuetype;
    }

    @JsonProperty("timespent")
    public Object getTimespent() {
        return timespent;
    }

    @JsonProperty("timespent")
    public void setTimespent(Object timespent) {
        this.timespent = timespent;
    }

    @JsonProperty("project")
    public Project getProject() {
        return project;
    }

    @JsonProperty("project")
    public void setProject(Project project) {
        this.project = project;
    }

    @JsonProperty("fixVersions")
    public List<Object> getFixVersions() {
        return fixVersions;
    }

    @JsonProperty("fixVersions")
    public void setFixVersions(List<Object> fixVersions) {
        this.fixVersions = fixVersions;
    }

    @JsonProperty("aggregatetimespent")
    public Object getAggregatetimespent() {
        return aggregatetimespent;
    }

    @JsonProperty("aggregatetimespent")
    public void setAggregatetimespent(Object aggregatetimespent) {
        this.aggregatetimespent = aggregatetimespent;
    }

    @JsonProperty("resolution")
    public Resolution getResolution() {
        return resolution;
    }

    @JsonProperty("resolution")
    public void setResolution(Resolution resolution) {
        this.resolution = resolution;
    }

    @JsonProperty("resolutiondate")
    public String getResolutiondate() {
        return resolutiondate;
    }

    @JsonProperty("resolutiondate")
    public void setResolutiondate(String resolutiondate) {
        this.resolutiondate = resolutiondate;
    }

    @JsonProperty("workratio")
    public Integer getWorkratio() {
        return workratio;
    }

    @JsonProperty("workratio")
    public void setWorkratio(Integer workratio) {
        this.workratio = workratio;
    }

    @JsonProperty("watches")
    public Watches getWatches() {
        return watches;
    }

    @JsonProperty("watches")
    public void setWatches(Watches watches) {
        this.watches = watches;
    }

    @JsonProperty("lastViewed")
    public Object getLastViewed() {
        return lastViewed;
    }

    @JsonProperty("lastViewed")
    public void setLastViewed(Object lastViewed) {
        this.lastViewed = lastViewed;
    }

    @JsonProperty("customfield_10060")
    public Object getCustomfield10060() {
        return customfield10060;
    }

    @JsonProperty("customfield_10060")
    public void setCustomfield10060(Object customfield10060) {
        this.customfield10060 = customfield10060;
    }

    @JsonProperty("customfield_10061")
    public Object getCustomfield10061() {
        return customfield10061;
    }

    @JsonProperty("customfield_10061")
    public void setCustomfield10061(Object customfield10061) {
        this.customfield10061 = customfield10061;
    }

    @JsonProperty("created")
    public String getCreated() {
        return created;
    }

    @JsonProperty("created")
    public void setCreated(String created) {
        this.created = created;
    }

    @JsonProperty("customfield_10062")
    public Object getCustomfield10062() {
        return customfield10062;
    }

    @JsonProperty("customfield_10062")
    public void setCustomfield10062(Object customfield10062) {
        this.customfield10062 = customfield10062;
    }

    @JsonProperty("customfield_10063")
    public Object getCustomfield10063() {
        return customfield10063;
    }

    @JsonProperty("customfield_10063")
    public void setCustomfield10063(Object customfield10063) {
        this.customfield10063 = customfield10063;
    }

    @JsonProperty("customfield_10064")
    public Object getCustomfield10064() {
        return customfield10064;
    }

    @JsonProperty("customfield_10064")
    public void setCustomfield10064(Object customfield10064) {
        this.customfield10064 = customfield10064;
    }

    @JsonProperty("customfield_10065")
    public Object getCustomfield10065() {
        return customfield10065;
    }

    @JsonProperty("customfield_10065")
    public void setCustomfield10065(Object customfield10065) {
        this.customfield10065 = customfield10065;
    }

    @JsonProperty("customfield_10066")
    public List<Object> getCustomfield10066() {
        return customfield10066;
    }

    @JsonProperty("customfield_10066")
    public void setCustomfield10066(List<Object> customfield10066) {
        this.customfield10066 = customfield10066;
    }

    @JsonProperty("priority")
    public Priority getPriority() {
        return priority;
    }

    @JsonProperty("priority")
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @JsonProperty("labels")
    public List<String> getLabels() {
        return labels;
    }

    @JsonProperty("labels")
    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    @JsonProperty("aggregatetimeoriginalestimate")
    public Object getAggregatetimeoriginalestimate() {
        return aggregatetimeoriginalestimate;
    }

    @JsonProperty("aggregatetimeoriginalestimate")
    public void setAggregatetimeoriginalestimate(Object aggregatetimeoriginalestimate) {
        this.aggregatetimeoriginalestimate = aggregatetimeoriginalestimate;
    }

    @JsonProperty("timeestimate")
    public Object getTimeestimate() {
        return timeestimate;
    }

    @JsonProperty("timeestimate")
    public void setTimeestimate(Object timeestimate) {
        this.timeestimate = timeestimate;
    }

    @JsonProperty("versions")
    public List<Object> getVersions() {
        return versions;
    }

    @JsonProperty("versions")
    public void setVersions(List<Object> versions) {
        this.versions = versions;
    }

    @JsonProperty("issuelinks")
    public List<Issuelink> getIssuelinks() {
        return issuelinks;
    }

    @JsonProperty("issuelinks")
    public void setIssuelinks(List<Issuelink> issuelinks) {
        this.issuelinks = issuelinks;
    }

    @JsonProperty("assignee")
    public Assignee getAssignee() {
        return assignee;
    }

    @JsonProperty("assignee")
    public void setAssignee(Assignee assignee) {
        this.assignee = assignee;
    }

    @JsonProperty("updated")
    public String getUpdated() {
        return updated;
    }

    @JsonProperty("updated")
    public void setUpdated(String updated) {
        this.updated = updated;
    }

    @JsonProperty("status")
    public Status_ getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(Status_ status) {
        this.status = status;
    }

    @JsonProperty("components")
    public List<Object> getComponents() {
        return components;
    }

    @JsonProperty("components")
    public void setComponents(List<Object> components) {
        this.components = components;
    }

    @JsonProperty("timeoriginalestimate")
    public Object getTimeoriginalestimate() {
        return timeoriginalestimate;
    }

    @JsonProperty("timeoriginalestimate")
    public void setTimeoriginalestimate(Object timeoriginalestimate) {
        this.timeoriginalestimate = timeoriginalestimate;
    }

    @JsonProperty("description")
    public Description getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(Description description) {
        this.description = description;
    }

    @JsonProperty("customfield_10010")
    public Object getCustomfield10010() {
        return customfield10010;
    }

    @JsonProperty("customfield_10010")
    public void setCustomfield10010(Object customfield10010) {
        this.customfield10010 = customfield10010;
    }

    @JsonProperty("customfield_10011")
    public String getCustomfield10011() {
        return customfield10011;
    }

    @JsonProperty("customfield_10011")
    public void setCustomfield10011(String customfield10011) {
        this.customfield10011 = customfield10011;
    }

    @JsonProperty("customfield_10012")
    public Object getCustomfield10012() {
        return customfield10012;
    }

    @JsonProperty("customfield_10012")
    public void setCustomfield10012(Object customfield10012) {
        this.customfield10012 = customfield10012;
    }

    @JsonProperty("customfield_10013")
    public String getCustomfield10013() {
        return customfield10013;
    }

    @JsonProperty("customfield_10013")
    public void setCustomfield10013(String customfield10013) {
        this.customfield10013 = customfield10013;
    }

    @JsonProperty("customfield_10058")
    public Object getCustomfield10058() {
        return customfield10058;
    }

    @JsonProperty("customfield_10058")
    public void setCustomfield10058(Object customfield10058) {
        this.customfield10058 = customfield10058;
    }

    @JsonProperty("timetracking")
    public Timetracking getTimetracking() {
        return timetracking;
    }

    @JsonProperty("timetracking")
    public void setTimetracking(Timetracking timetracking) {
        this.timetracking = timetracking;
    }

    @JsonProperty("customfield_10059")
    public Object getCustomfield10059() {
        return customfield10059;
    }

    @JsonProperty("customfield_10059")
    public void setCustomfield10059(Object customfield10059) {
        this.customfield10059 = customfield10059;
    }

    @JsonProperty("security")
    public Object getSecurity() {
        return security;
    }

    @JsonProperty("security")
    public void setSecurity(Object security) {
        this.security = security;
    }

    @JsonProperty("customfield_10008")
    public Object getCustomfield10008() {
        return customfield10008;
    }

    @JsonProperty("customfield_10008")
    public void setCustomfield10008(Object customfield10008) {
        this.customfield10008 = customfield10008;
    }

    @JsonProperty("aggregatetimeestimate")
    public Object getAggregatetimeestimate() {
        return aggregatetimeestimate;
    }

    @JsonProperty("aggregatetimeestimate")
    public void setAggregatetimeestimate(Object aggregatetimeestimate) {
        this.aggregatetimeestimate = aggregatetimeestimate;
    }

    @JsonProperty("attachment")
    public List<Object> getAttachment() {
        return attachment;
    }

    @JsonProperty("attachment")
    public void setAttachment(List<Object> attachment) {
        this.attachment = attachment;
    }

    @JsonProperty("customfield_10009")
    public Customfield10009 getCustomfield10009() {
        return customfield10009;
    }

    @JsonProperty("customfield_10009")
    public void setCustomfield10009(Customfield10009 customfield10009) {
        this.customfield10009 = customfield10009;
    }

    @JsonProperty("summary")
    public String getSummary() {
        return summary;
    }

    @JsonProperty("summary")
    public void setSummary(String summary) {
        this.summary = summary;
    }

    @JsonProperty("creator")
    public Creator getCreator() {
        return creator;
    }

    @JsonProperty("creator")
    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    @JsonProperty("subtasks")
    public List<Object> getSubtasks() {
        return subtasks;
    }

    @JsonProperty("subtasks")
    public void setSubtasks(List<Object> subtasks) {
        this.subtasks = subtasks;
    }

    @JsonProperty("reporter")
    public Reporter getReporter() {
        return reporter;
    }

    @JsonProperty("reporter")
    public void setReporter(Reporter reporter) {
        this.reporter = reporter;
    }

    @JsonProperty("customfield_10000")
    public String getCustomfield10000() {
        return customfield10000;
    }

    @JsonProperty("customfield_10000")
    public void setCustomfield10000(String customfield10000) {
        this.customfield10000 = customfield10000;
    }

    @JsonProperty("aggregateprogress")
    public Aggregateprogress getAggregateprogress() {
        return aggregateprogress;
    }

    @JsonProperty("aggregateprogress")
    public void setAggregateprogress(Aggregateprogress aggregateprogress) {
        this.aggregateprogress = aggregateprogress;
    }

    @JsonProperty("customfield_10001")
    public Customfield10001 getCustomfield10001() {
        return customfield10001;
    }

    @JsonProperty("customfield_10001")
    public void setCustomfield10001(Customfield10001 customfield10001) {
        this.customfield10001 = customfield10001;
    }

    @JsonProperty("customfield_10004")
    public Object getCustomfield10004() {
        return customfield10004;
    }

    @JsonProperty("customfield_10004")
    public void setCustomfield10004(Object customfield10004) {
        this.customfield10004 = customfield10004;
    }

    @JsonProperty("environment")
    public Object getEnvironment() {
        return environment;
    }

    @JsonProperty("environment")
    public void setEnvironment(Object environment) {
        this.environment = environment;
    }

    @JsonProperty("duedate")
    public Object getDuedate() {
        return duedate;
    }

    @JsonProperty("duedate")
    public void setDuedate(Object duedate) {
        this.duedate = duedate;
    }

    @JsonProperty("progress")
    public Progress getProgress() {
        return progress;
    }

    @JsonProperty("progress")
    public void setProgress(Progress progress) {
        this.progress = progress;
    }

    @JsonProperty("comment")
    public Comment getComment() {
        return comment;
    }

    @JsonProperty("comment")
    public void setComment(Comment comment) {
        this.comment = comment;
    }

    @JsonProperty("votes")
    public Votes getVotes() {
        return votes;
    }

    @JsonProperty("votes")
    public void setVotes(Votes votes) {
        this.votes = votes;
    }

    @JsonProperty("worklog")
    public Worklog getWorklog() {
        return worklog;
    }

    @JsonProperty("worklog")
    public void setWorklog(Worklog worklog) {
        this.worklog = worklog;
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
