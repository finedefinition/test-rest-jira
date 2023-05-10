package ua.finedefinition.rest;

import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import ua.finedefinition.api.MyPluginComponent;

import com.atlassian.plugins.rest.common.security.AnonymousAllowed;
import com.atlassian.sal.api.user.UserManager;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/search")
@Named
public class RestResource {

    @ComponentImport
    private final UserManager userManager;
    @Inject
    private final MyPluginComponent myPluginComponent;

    @Inject
    public RestResource(UserManager userManager, MyPluginComponent myPluginComponent) {
        this.userManager = userManager;
        this.myPluginComponent = myPluginComponent;
    }

    @POST
    @AnonymousAllowed
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSearchResults(
            RestResourceRequest restResourceRequest,
            @Context UriInfo uriInfo
    ) {
        if (restResourceRequest.isFetchAll()) {
            List<Map<String, Object>> allIssues = getAllIssues(restResourceRequest, uriInfo);
            return Response.ok(allIssues).build();
        } else {
            RestResourceResponse response = searchAndReturnResponse(restResourceRequest, uriInfo);
            return Response.ok(response).build();
        }
    }

    private RestResourceResponse searchAndReturnResponse(RestResourceRequest restResourceRequest, UriInfo uriInfo) {
        String jql = createJqlQuery(restResourceRequest);
        String fields = String.join(",", restResourceRequest.getFields());
        int maxResults = restResourceRequest.getMaxResults();
        int startAt = restResourceRequest.getStartAt();

        SearchResults searchResults = myPluginComponent.getSearchResults(jql, fields, uriInfo, maxResults, startAt);
        List<RestResourceModel> restResourceModels = searchResults.getResults();
        List<Map<String, Object>> issues = searchResults.getResults().stream()
                .map(this::convertToMap)
                .collect(Collectors.toList());


        List<RestResourceModel> issueDataList = issues.stream()
                .map(issue -> {
                    RestResourceModel issueData = new RestResourceModel();
                    issueData.setKey((String) issue.get("key"));
                    issueData.setDescription((String) issue.get("description"));
                    return issueData;
                })
                .collect(Collectors.toList());

        RestResourceResponse response = new RestResourceResponse();
        response.setMaxResults(maxResults);
        response.setStartAt(startAt);
        response.setTotal(searchResults.getTotal());
        response.setExpand("schema,names");
        response.setIssues(issueDataList);

        return response;
    }


//    private RestResourceModel convertToRestResourceModel(Map<String, Object> map) {
//        RestResourceModel restResourceModel = new RestResourceModel();
//        restResourceModel.setKey((String) map.get("key"));
//        restResourceModel.setDescription((String) map.get("description"));
//        return restResourceModel;
//    }

    public List<Map<String, Object>> getAllIssues(RestResourceRequest request, UriInfo uriInfo) {
        List<Map<String, Object>> allIssues = new ArrayList<>();
        int startAt = request.getStartAt();
        int maxResults = request.getMaxResults();
        boolean fetchAll = request.isFetchAll();
        boolean hasMoreResults = true;

        while (hasMoreResults && fetchAll) {
            String modifiedJql = request.getJql();
            request.setJql(modifiedJql);
            request.setStartAt(startAt);

            RestResourceResponse response = searchAndReturnResponse(request, uriInfo);

            List<RestResourceModel> restResourceModels = response.getIssues();
            List<Map<String, Object>> issues = restResourceModels.stream()
                    .map(this::convertToMap)
                    .collect(Collectors.toList());

            if (!issues.isEmpty()) {
                allIssues.addAll(issues);
                startAt += maxResults;
            } else {
                hasMoreResults = false;
            }
        }

        return allIssues;
    }

    private Map<String, Object> convertToMap(RestResourceModel restResourceModel) {
        Map<String, Object> map = new HashMap<>();
        map.put("key", restResourceModel.getKey());
        map.put("description", restResourceModel.getDescription());
        // add other fields as needed
        return map;
    }


    private String createJqlQuery(RestResourceRequest restResourceRequest) {
        String jql = "project=\"" + restResourceRequest.getProjectKey() + "\"";

        if (restResourceRequest.getJql() != null && !restResourceRequest.getJql().trim().isEmpty()) {
            jql = restResourceRequest.getJql();
        }

        return jql;
    }
}