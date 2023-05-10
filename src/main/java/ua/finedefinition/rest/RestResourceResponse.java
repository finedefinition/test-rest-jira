package ua.finedefinition.rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class RestResourceResponse {
    @XmlElement
    private String baseUri;

    @XmlElement
    private String requestUri;

    @XmlElement
    private int maxResults;

    @XmlElement
    private int startAt;

    @XmlElement
    private int total;

    @XmlElement
    private String expand;

    @XmlElement
    private List<RestResourceModel> issues;

    public RestResourceResponse(String baseUri, String requestUri, List<RestResourceModel> issues) {
        this.baseUri = baseUri;
        this.requestUri = requestUri;
        this.issues = issues;
    }

    public RestResourceResponse() {
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getExpand() {
        return expand;
    }

    public void setExpand(String expand) {
        this.expand = expand;
    }

    public List<RestResourceModel> getIssues() {
        return issues;
    }

    public void setIssues(List<RestResourceModel> issues) {
        this.issues = issues;
    }
}
