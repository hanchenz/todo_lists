package TodoApplication;

import java.util.Comparator;

/**
 * DueDateComparator helps to compare two todo objects depending on their priorities.
 *
 */
public class PriorityComparator implements Comparator<Todo> {

    private static final int HASH_CODE = 456;
    /**
     * Compare two todo objects
     * @param todo1 - object to be compared
     * @param todo2 - object to be compared
     * @return an indicator that indicates which object has a greater priority, encoded as an integer
     * 0 means equal, 1 means todo1 has a greater priority, -1 means todo2 has a greater priority
     */
    @Override
    public int compare(Todo todo1, Todo todo2) {
        return todo1.getPriority().compareTo(todo2.getPriority());
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
        return "PriorityComparator{}";
    }
}
