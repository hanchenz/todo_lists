package TodoApplication;

import java.util.List;
import java.util.Objects;

/**
 * An AndFilter checks if a todo object can meet multiple filter requirements at the same time.
 */
class AndFilter implements Filter {
    private final List<Filter> andFilters;

    /**
     * Constructor, create an AndFilter.
     * @param andFilters multiple filter requirements, encoded as a list of filters
     */
    AndFilter(List<Filter> andFilters) {
        this.andFilters = andFilters;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean filter(Todo todo) {
        for (Filter andFilter : andFilters) {
            if (!andFilter.filter(todo)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AndFilter andFilter = (AndFilter) o;
        return Objects.equals(andFilters, andFilter.andFilters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(andFilters);
    }

    @Override
    public String toString() {
        return "AndFilter{" +
                "andFilters=" + andFilters +
                '}';
    }
}
