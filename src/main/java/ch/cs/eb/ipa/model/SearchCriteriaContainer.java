package ch.cs.eb.ipa.model;

public class SearchCriteriaContainer {
    private String searchTerm;

    public SearchCriteriaContainer() {
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    @Override
    public String toString() {
        return "SearchCriteriaContainer{" +
                "searchTerm='" + searchTerm + '\'' +
                '}';
    }
}
