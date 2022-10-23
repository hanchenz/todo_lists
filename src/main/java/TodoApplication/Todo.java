package TodoApplication;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Todo class keeps track of todo item. This class contains default/optional parameters. Use the
 * builder pattern to handle construction.
 */
public class Todo {

    private int ID;
    private final String text;
    private boolean completed;  // default false
    private final LocalDate due; // optional
    private final Integer priority;   // 1,2,3, default 3
    private final String category;  // optional

    /**
     * Constructor, create a new Todo object using Builder.
     * @param builder builder to help construct Todo, encoded as Builder
     */
    private Todo(Builder builder) {
        this.text = builder.text;
        this.completed = builder.completed;
        this.due = builder.due;
        this.priority = builder.priority;
        this.category = builder.category;
    }

    /**
     * Get ID
     * @return ID
     */
    public int getID() {
        return ID;
    }

    /**
     * Get text
     * @return text
     */
    public String getText() {
        return text;
    }

    /**
     * Check if the todo is completed
     * @return true if completed
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Get due date
     * @return due date
     */
    public LocalDate getDue() {
        return due;
    }

    /**
     * Get priority
     * @return priority
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * Get category
     * @return category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Set ID
     * @param ID
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * Set completed to true
     */
    public void setCompleted() {
        this.completed = true;
    }

    /**
     * Check if o is equal to this
     * @param o - object to be compared
     * @return true if two objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Todo todo = (Todo) o;
        return ID == todo.ID && completed == todo.completed && priority == todo.priority
            && Objects.equals(text, todo.text) && Objects.equals(due, todo.due)
            && Objects.equals(category, todo.category);
    }

    /**
     * Get the hash code
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(ID, text, completed, due, priority, category);
    }

    /**
     * to string
     * @return the string
     */
    @Override
    public String toString() {
        return "Todo{" +
            "ID=" + ID +
            ", text='" + text + '\'' +
            ", completed=" + completed +
            ", due=" + due +
            ", priority=" + priority +
            ", category='" + category + '\'' +
            '}';
    }

    /**
     * Builder is a class help to construct Todo
     */
    public static class Builder {

        private String text;
        private boolean completed;
        private LocalDate due;
        private int priority;
        private String category;

        /**
         * Constructor, create a new Builder object with given text. Completed is default false and
         * priority is default 3.
         * @param text - todo text, encoded as String
         */
        public Builder(String text) {
            this.text = text;
            this.completed = false;
            this.priority = 3;
        }

        /**
         * Set completed to true
         * @return this
         */
        public Builder completeTodo() {
            this.completed = true;
            return this;
        }

        /**
         * Set due to be given due
         * @param due - due date, encoded as LocalDate
         * @return this
         */
        public Builder addDue(LocalDate due) {
            this.due = due;
            return this;
        }

        /**
         * Change priority to given priority
         * @param priority - priority, encoded as int
         * @return this
         */
        public Builder changePriority(int priority) {
            this.priority = priority;
            return this;
        }

        /**
         * Set the category to given category
         * @param category - category, encoded as String
         * @return this
         */
        public Builder addCategory(String category) {
            this.category = category;
            return this;
        }

        /**
         * Help to construct Todo
         * @return a new Todo object
         */
        public Todo build() {
            return new Todo(this);
        }

    }

}
