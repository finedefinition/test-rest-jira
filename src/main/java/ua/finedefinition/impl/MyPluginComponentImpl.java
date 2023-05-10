package ua.finedefinition.impl;

import ua.finedefinition.api.MyPluginComponent;
import ua.finedefinition.rest.RestResourceModel;

import com.atlassian.jira.jql.parser.JqlParseException;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.plugin.spring.scanner.annotation.export.ExportAsService;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.ApplicationProperties;
import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.jql.parser.JqlQueryParser;
import com.atlassian.jira.web.bean.PagerFilter;
import com.atlassian.jira.issue.Issue;
import ua.finedefinition.rest.SearchResults;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.UriInfo;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ExportAsService({MyPluginComponent.class})
@Named("myPluginComponent")
public class MyPluginComponentImpl implements MyPluginComponent {
    @ComponentImport
    private final ApplicationProperties applicationProperties;
    @ComponentImport
    private final JqlQueryParser jqlQueryParser;
    @ComponentImport
    private final SearchService searchService;
    @ComponentImport
    private final JiraAuthenticationContext jiraAuthenticationContext;

    @Inject
    public MyPluginComponentImpl(final ApplicationProperties applicationProperties,
                                 final JqlQueryParser jqlQueryParser,
                                 final SearchService searchService,
                                 final JiraAuthenticationContext jiraAuthenticationContext) {
        this.applicationProperties = applicationProperties;
        this.jqlQueryParser = jqlQueryParser;
        this.searchService = searchService;
        this.jiraAuthenticationContext = jiraAuthenticationContext;
    }

    public String getName() {
        if (null != applicationProperties) {
            return "myComponent:" + applicationProperties.getDisplayName();
        }
        return "myComponent";
    }

    /**
     * This method will perform a JQL search and return JSON with the selected fields
     *
     * @param jql        - JQL string
     * @param fields     - fields to return
     * @param uriInfo    - information about the request URI
     * @param maxResults - maximum number of results to return
     * @param startAt    - index of the first result to return
     * @return JSON response
     */
    @Override
    public SearchResults getSearchResults(String jql, String fields, UriInfo uriInfo, int maxResults, int startAt)  {
        try {
            List<String> fieldList = Arrays.asList(fields.split(","));
            com.atlassian.query.Query query = jqlQueryParser.parseQuery(jql);

            PagerFilter pagerFilter = PagerFilter.newPageAlignedFilter(startAt, maxResults);

            com.atlassian.jira.issue.search.SearchResults searchResults = searchService.search(jiraAuthenticationContext.getLoggedInUser(),
                    query, pagerFilter);
            int total = searchResults.getTotal();

            List<Issue> issues = searchResults.getIssues();

            String jiraBaseUrl = applicationProperties.getBaseUrl();

            List<RestResourceModel> issueDataList = issues.stream()
                    .map(issue -> {
                        RestResourceModel issueData = new RestResourceModel();
                        issueData.setKey(issue.getKey());
                        issueData.setDescription(issue.getDescription());
                        return issueData;
                    })
                    .collect(Collectors.toList());

            return new SearchResults(issueDataList, total);

        } catch (JqlParseException e) {
            throw new RuntimeException("Error parsing JQL query: " + e.getMessage(), e);
        } catch (com.atlassian.jira.issue.search.SearchException e) {
            throw new RuntimeException("Error performing JQL search: " + e.getMessage(), e);
        }
    }
}