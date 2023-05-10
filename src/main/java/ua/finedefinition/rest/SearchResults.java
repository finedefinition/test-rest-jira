package ua.finedefinition.rest;

import java.util.List;

public class SearchResults {
    private List<RestResourceModel> results;
    private int total;

    public SearchResults(List<RestResourceModel> results, int total) {
        this.results = results;
        this.total = total;
    }

    public List<RestResourceModel> getResults() {
        return results;
    }

    public int getTotal() {
        return total;
    }
}
