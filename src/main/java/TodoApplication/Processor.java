package TodoApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Class Processor handles all the following process:
 * read and write csv file,
 * and display todolist.
 */
public class Processor {

    /**
     * Read and write csv file
     * @param commandInfo - commandInfo, encoded as CommandInfo
     * @return a TodoList
     * @throws IOException whenever an input or output operation is failed
     */
    protected static TodoList readWrite(CommandInfo commandInfo) throws IOException {
        String csvPath = commandInfo.getCsvPath();
        FileReader reader = new FileReader(csvPath);

        TodoList todoList = reader.generateTodoList();
        if (commandInfo.getCompletedTodoID().size() > 0) {
            todoList.completeTodos(commandInfo.getCompletedTodoID());
        }
        if (commandInfo.getNewTodo() != null) {
            todoList.addTodo(commandInfo.getNewTodo());
        }
        FileWriter writer = new FileWriter(csvPath);
        writer.writeFile(todoList.getContents());
        return todoList;
    }

    /**
     * Filter todo with given information
     * @param commandInfo - commandInfo, encoded as CommandInfo
     * @param todoList - a list of todo, encoded as TodoList
     * @return filtered list of todo
     */
    protected static List<Todo> filterTodo(CommandInfo commandInfo, TodoList todoList) {
        // filter the todo list
        TodoIterator todoIterator;
        List<Todo> filteredTodoList = new ArrayList<>();
        DisplayOption displayOption = commandInfo.getDisplayOption();
        List<Todo> originalTodoList = todoList.getTodoList();

        if (displayOption.isShowIncomplete() && displayOption.isShowCategory()) {
            todoIterator = new TodoIterator(originalTodoList,
                new AndFilter(Arrays.asList(new IncompleteFilter(),
                    new CategoryFilter(displayOption.getCategory()))));
        } else if (displayOption.isShowIncomplete()) {
            todoIterator = new TodoIterator(originalTodoList, new IncompleteFilter());
        } else if (displayOption.isShowCategory()) {
            todoIterator = new TodoIterator(originalTodoList,
                new CategoryFilter(displayOption.getCategory()));
        } else {
            // if we do not need to filter the list, copy the original list
            todoIterator = null;
            filteredTodoList.addAll(originalTodoList);
        }
        if (todoIterator != null) {
            while (todoIterator.hasNext()) {
                filteredTodoList.add(todoIterator.next());
            }
        }
        return filteredTodoList;
    }

    /**
     * Display todolist with given information
     * @param commandInfo - commandInfo, encoded as CommandInfo
     * @param todoList - a list of todo, encoded as TodoList
     */
    protected static void display(CommandInfo commandInfo, TodoList todoList) {
        DisplayOption displayOption = commandInfo.getDisplayOption();
        List<Todo> originalTodoList = todoList.getTodoList();
        if (displayOption == null) {
            return;
        }
        // if there is no option provided, display the whole todo list
        if (!(displayOption.isSortByPriority() || displayOption.isSortByDate() ||
            displayOption.isShowCategory() || displayOption.isShowIncomplete())) {
            System.out.println("The entire Todo list is:");
            for (Todo todo : originalTodoList) {
                System.out.println(todo);
            }
            return;
        }
        List<Todo> filteredTodoList = filterTodo(commandInfo, todoList);
        // sort the list if needed
        if (displayOption.isSortByDate()) {
            Collections.sort(filteredTodoList, new DueDateComparator());
        }
        if (displayOption.isSortByPriority()) {
            Collections.sort(filteredTodoList, new PriorityComparator());
        }
        System.out.println("The customized Todo list is:");
        for (Todo todo : filteredTodoList) {
            System.out.println(todo);
        }
    }
}
