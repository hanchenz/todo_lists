package TodoApplication;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * An TodoIterator helps to filter a list of todo objects under certain conditions.
 *
 */
public class TodoIterator implements Iterator<Todo> {
    private Integer index = 0;
    private List<Todo> filteredTodos;
    /**
     * Constructor, create a TodoIterator.
     * @param todos the list of todo objects, encoded as a List
     * @param filter the filter requirements, encoded as a Filter
     */
    public TodoIterator(List<Todo> todos, Filter filter) {
        this.filteredTodos = new ArrayList<>();
        for (Todo todo: todos) {
            if (filter.filter(todo)) {
                filteredTodos.add(todo);
            }
        }
    }

    /**
     * Checks if there is any remaining todo object
     * @return an indicator that indicates if there is any remaining todo object, encoded as a Boolean
     */
    @Override
    public boolean hasNext() {
        return this.index < this.filteredTodos.size();
    }

    /**
     * Get the next todo object
     * @return the next todo object, encoded as a todo object
     */
    @Override
    public Todo next() {
        Todo helperTodo = this.filteredTodos.get(index);
        this.index = this.index + 1;
        return helperTodo;
    }

    /**
     * remove a todo object
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException("This operation is not supported!");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodoIterator that = (TodoIterator) o;
        return Objects.equals(index, that.index) && Objects.equals(filteredTodos, that.filteredTodos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, filteredTodos);
    }

    @Override
    public String toString() {
        return "TodoIterator{" +
                "index=" + index +
                ", filteredTodos=" + filteredTodos +
                '}';
    }
}
