package TodoApplication;

import java.util.HashMap;
import java.util.Map;

/**
 * Some constant commands.
 */
public class Commands {
    public static final String PREFIX = "--";
    public static final String CSV_FILE = "--csv-file";
    public static final String ADD_TODO = "--add-todo";
    public static final String TODO_TEXT = "--todo-text";
    public static final String COMPLETED = "--completed";
    public static final String DUE = "--due";
    public static final String PRIORITY = "--priority";
    public static final String CATEGORY = "--category";
    public static final String COMPLETE_TODO = "--complete-todo";
    public static final String DISPLAY = "--display";
    public static final String SHOW_INCOMPLETE = "--show-incomplete";
    public static final String SHOW_CATEGORY = "--show-category";
    public static final String SORT_BY_DATE = "--sort-by-date";
    public static final String SORT_BY_PRIORITY = "--sort-by-priority";
    public static final int HIGHEST_PRIORITY = 1;
    public static final int LOWEST_PRIORITY = 3;

    /**
     * A Map of valid commands(without --completed-todo). The key is the command and value is
     * whether the command should have argument.
     */
    public static Map<String, Boolean> VALID_COMMANDS = new HashMap<String, Boolean>() {{
        put(CSV_FILE, true);
        put(ADD_TODO, false);
        put(TODO_TEXT, true);
        put(COMPLETED, false);
        put(DUE, true);
        put(PRIORITY, true);
        put(CATEGORY, true);
        put(DISPLAY, false);
        put(SHOW_INCOMPLETE, false);
        put(SHOW_CATEGORY, true);
        put(SORT_BY_DATE, false);
        put(SORT_BY_PRIORITY, false);
    }};

}
