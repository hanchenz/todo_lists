package TodoApplication;

/**
 * A Filter interface defines the method a filter has to implement.
 */
interface Filter {
    /**
     * Checks if a todo object can be filtered
     * @param todo - object to be checked
     * @return an indicator that indicates if the object can be filtered, encoded as a Boolean
     */
    public Boolean filter(Todo todo);
}
