package inter_face;



public interface Searchable {

    boolean matchesSearch(String query);

    String getSearchableInfo();

    default boolean exactMatch(String field, String value) {
        return field != null && field.equalsIgnoreCase(value);
    }

    default boolean partialMatch(String field, String query) {
        return field != null && field.toLowerCase().contains(query.toLowerCase());
    }
}