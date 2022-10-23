package TodoApplication;

/**
 * An IncompleteFilter checks if a todo object is completed or not.
 */
class IncompleteFilter implements Filter {
    private static final int HASH_CODE = 456;
    /**
     * Constructor, create a IncompleteFilter.
     */
    IncompleteFilter() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean filter(Todo todo) {
        return !todo.isCompleted();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return HASH_CODE;
    }

    @Override
    public String toString() {
        return "IncompleteFilter{}";
    }
}
