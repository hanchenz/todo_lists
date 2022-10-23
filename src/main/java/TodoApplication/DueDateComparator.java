package TodoApplication;

import java.util.Comparator;
/**
 * DueDateComparator helps to compare two todo objects depending on their due dates.
 *
 */
public class DueDateComparator implements Comparator<Todo> {

    private static final int HASH_CODE = 678;
    /**
     * Compare two todo objects
     * @param todo1 - object to be compared
     * @param todo2 - object to be compared
     * @return an indicator that indicates which object has a latter due date, encoded as an integer
     * 0 means equal, 1 means todo1 is the latter one / null, -1 means todo2 is the latter one / null
     */
    @Override
    public int compare(Todo todo1, Todo todo2) {
        if (todo1.getDue() != null && todo2.getDue() != null) {
            return todo1.getDue().compareTo(todo2.getDue());
        } else if(todo1.getDue() == null) {
            return 1; // if todo1 is null, we should regard todo1 as the latter one
        } else {
            return -1; // if todo2 is null, we should regard todo2 as the latter one

        }
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
        return "DueDateComparator{}";
    }
}
