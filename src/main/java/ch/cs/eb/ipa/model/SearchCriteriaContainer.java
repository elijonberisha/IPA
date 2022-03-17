package ch.cs.eb.ipa.model;

/**
 * author: Elijon Berisha
 * date: 14.03.2022
 * class: SearchCriteriaContainer.java
 */

// SERVES AS A DATA CONTAINER FOR THE SEARCH FUNCTIONS
public class SearchCriteriaContainer {
    // STORES SEARCH TERM
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
