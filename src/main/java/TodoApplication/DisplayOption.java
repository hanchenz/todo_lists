package TodoApplication;

import java.util.Objects;

/**
 * DisplayOption contains the information of display with optional parameters.
 * Use the builder pattern to handle constructor.
 */
public class DisplayOption {

    private final boolean showIncomplete;
    private final boolean showCategory;
    private final String category;
    private final boolean sortByDate;
    private final boolean sortByPriority;

    /**
     * Constructor, create a new DisplayOption object
     * @param displayBuilder - a display builder, encoded as DisplayBuilder
     */
    private DisplayOption(DisplayBuilder displayBuilder) {
        this.showIncomplete = displayBuilder.showIncomplete;
        this.showCategory = displayBuilder.showCategory;
        this.category = displayBuilder.category;
        this.sortByDate = displayBuilder.sortByDate;
        this.sortByPriority = displayBuilder.sortByPriority;
    }

    /**
     * Get whether to show incomplete items
     * @return true if show incomplete items
     */
    public boolean isShowIncomplete() {
        return showIncomplete;
    }

    /**
     * Get whether to show by category
     * @return true if show by category
     */
    public boolean isShowCategory() {
        return showCategory;
    }

    /**
     * Get the category to be displayed
     * @return the category to be displayed
     */
    public String getCategory() {
        return category;
    }

    /**
     * Get whether to sort by date
     * @return true if sort by date
     */
    public boolean isSortByDate() {
        return sortByDate;
    }

    /**
     * Get whether to sort by priority
     * @return true if sort by priority
     */
    public boolean isSortByPriority() {
        return sortByPriority;
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
        DisplayOption that = (DisplayOption) o;
        return showIncomplete == that.showIncomplete && showCategory == that.showCategory
            && sortByDate == that.sortByDate && sortByPriority == that.sortByPriority
            && Objects.equals(category, that.category);
    }

    /**
     * Get the hash code
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(showIncomplete, showCategory, category, sortByDate, sortByPriority);
    }

    /**
     * to String
     * @return the string
     */
    @Override
    public String toString() {
        return "DisplayOption{" +
            "showIncomplete=" + showIncomplete +
            ", showCategory=" + showCategory +
            ", category='" + category + '\'' +
            ", sortByDate=" + sortByDate +
            ", sortByPriority=" + sortByPriority +
            '}';
    }

    /**
     * DisplayBuilder is a class helps to construct the DisplayOption.
     */
    public static class DisplayBuilder {
        private boolean showIncomplete;
        private boolean showCategory;
        private String category;
        private boolean sortByDate;
        private boolean sortByPriority;

        /**
         * Constructor, create a new DisplayBuilder object, all field is default false and category
         * is default null.
         */
        public DisplayBuilder() {
            this.showIncomplete = false;
            this.showCategory = false;
            this.category = null;
            this.sortByDate = false;
            this.sortByPriority = false;
        }

        /**
         * Set showIncomplete to true
         * @return this
         */
        public DisplayBuilder showIncomplete() {
            this.showIncomplete = true;
            return this;
        }

        /**
         * Set showCategory to true and set category equal to given category
         * @param category - given category, encoded as String
         * @return this
         */
        public DisplayBuilder showCategory(String category) {
            this.showCategory = true;
            this.category = category;
            return this;
        }

        /**
         * Set sortByDate to true
         * @throws IllegalArgumentException if this.sortByPriority is true
         * @return this
         */
        public DisplayBuilder sortByDate() {
            if (this.sortByPriority) {
                throw new IllegalArgumentException("Cannot sort by date and by priority in the same time.");
            }
            this.sortByDate = true;
            return this;
        }

        /**
         * Set sortByPriority to true
         * @throws IllegalArgumentException if this.sortByDate is true
         * @return this
         */
        public DisplayBuilder sortByPriority() {
            if (this.sortByDate) {
                throw new IllegalArgumentException("Cannot sort by date and by priority in the same time.");
            }
            this.sortByPriority = true;
            return this;
        }

        /**
         * Help to build the DisplayOption
         * @return a new DisplayOption
         */
        public DisplayOption buildDisplayOption() {
            return new DisplayOption(this);
        }
    }
}
