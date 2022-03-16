package ch.cs.eb.ipa.model;

/**
 * author: Elijon Berisha
 * date: 14.03.2022
 * class: SearchCriteriaContainer.java
 */

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
