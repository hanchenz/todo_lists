package TodoApplication;

import java.util.Objects;

/**
 * A CategoryFilter checks if the category of a todo object can meet the filter requirement.
 */
class CategoryFilter implements Filter {
    private static final String EMPTY_CATEGORY = "?";
    private final String category;

    /**
     * Constructor, create a CategoryFilter.
     * @param category the category filter requirement, encoded as a String
     */
    CategoryFilter(String category) {
        this.category = category;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean filter(Todo todo) {
        String todoCategory = todo.getCategory();
        if (todoCategory == null && this.category.equals(EMPTY_CATEGORY)) {
            return true;
        }

        if (todoCategory == null) {
            return false;
        }

        return todoCategory.equals(category);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryFilter that = (CategoryFilter) o;
        return Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category);
    }

    @Override
    public String toString() {
        return "CategoryFilter{" +
                "category='" + category + '\'' +
                '}';
    }
}
