package ua.finedefinition.api;

import ua.finedefinition.rest.SearchResults;

import javax.ws.rs.core.UriInfo;


public interface MyPluginComponent
{
    String getName();

    SearchResults getSearchResults(String jql, String fields, UriInfo uriInfo, int maxResults, int startAt);

}
