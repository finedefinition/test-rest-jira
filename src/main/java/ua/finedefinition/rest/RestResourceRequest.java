package ua.finedefinition.rest;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class RestResourceRequest {
    private String projectId;
    private String projectName;
    private String projectKey;
    private String username;
    private List<String> expand;
    private List<String> fields;
    private List<String> properties;
    private boolean fieldsByKeys;
    private String jql;
    private int maxResults;
    private int startAt;
    private String validateQuery;

    private boolean fetchAll;

    public boolean isFetchAll() {
        return fetchAll;
    }

    public void setFetchAll(boolean fetchAll) {
        this.fetchAll = fetchAll;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectKey() {
        return projectKey;
    }

    public void setProjectKey(String projectKey) {
        this.projectKey = projectKey;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isFieldsByKeys() {
        return fieldsByKeys;
    }

    public void setFieldsByKeys(boolean fieldsByKeys) {
        this.fieldsByKeys = fieldsByKeys;
    }

    public String getJql() {
        return jql;
    }

    public void setJql(String jql) {
        this.jql = jql;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public int getStartAt() {
        return startAt;
    }

    public void setStartAt(int startAt) {
        this.startAt = startAt;
    }

    public String getValidateQuery() {
        return validateQuery;
    }

    public void setValidateQuery(String validateQuery) {
        this.validateQuery = validateQuery;
    }

    public List<String> getExpand() {
        return expand;
    }

    public void setExpand(List<String> expand) {
        this.expand = expand;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public List<String> getProperties() {
        return properties;
    }

    public void setProperties(List<String> properties) {
        this.properties = properties;
    }
}
