package ua.finedefinition.rest;

import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.fields.Field;
import com.atlassian.jira.component.ComponentAccessor;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "issue")
@XmlAccessorType(XmlAccessType.FIELD)
public class RestResourceModel {

    @XmlElement(name = "expand")
    private String expand;

    @XmlElement(name = "id")
    private String id;

    @XmlElement(name = "key")
    private String key;

    @XmlElement(name = "self")
    private String self;

    @XmlElement(name = "transitions")
    private String transitions;

    @XmlElement(name = "description")
    private String description;

    @XmlElement(name = "value")
    private String message;

    public RestResourceModel() {
    }

    public String getExpand() {
        return expand;
    }

    public void setExpand(String expand) {
        this.expand = expand;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getTransitions() {
        return transitions;
    }

    public void setTransitions(String transitions) {
        this.transitions = transitions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setField(String field, Issue issue) {
        Field jiraField = ComponentAccessor.getFieldManager().getField(field);
        if (jiraField != null) {
            Object fieldValue;
            if (jiraField instanceof CustomField) {
                fieldValue = issue.getCustomFieldValue((CustomField) jiraField);
            } else {
                fieldValue = getStandardFieldValue(field, issue);
            }
            setMessage(fieldValue != null ? fieldValue.toString() : null);
        } else {
            setMessage(null);
        }
    }

    private Object getStandardFieldValue(String field, Issue issue) {
        switch (field) {
            case "summary":
                return issue.getSummary();
            case "description":
                return issue.getDescription();
            case "assignee":
                return issue.getAssignee();
            case "reporter":
                return issue.getReporter();

            default:
                return null;
        }
    }
}