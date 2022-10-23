package TodoApplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * TodoList stores information of a todolist, includes its contents, the list of Todo objects,
 * the header and an ID counter that keep tracks of the ID so new ID can be assigned when new
 * todo is added
 */
public class TodoList {

  private List<String> contents;
  private List<Todo> todoList;
  private final List<String> header;
  private int IDCounter;

  /**
   * construct a TodoList object
   * @param contents - the contents of the list, each item represents a todo, the form is same
   *                 as when they are written in the csv file. Encoded as a string list
   * @param todoList - the list of Todo objects, as a Todo list.
   * @param header - the header of the TodoList, as a string list
   */
  public TodoList(List<String> contents, List<Todo> todoList,
      List<String> header) {
    this.contents = contents;
    this.todoList = todoList;
    this.header = header;
    this.IDCounter = contents.size() - 1;
  }

  /**
   * getter of contents
   * @return - the contents, as a string list
   */
  public List<String> getContents() {
    return contents;
  }

  /**
   * setter of contents
   * @param contents - the contents, as a string list
   */
  public void setContents(List<String> contents) {
    this.contents = contents;
  }

  /**
   * getter of todo list
   * @return - the todo list, as a list of Todo
   */
  public List<Todo> getTodoList() {
    return todoList;
  }

  /**
   * Setter of todo list
   * @param todoList - the todo list, as a list of todo
   */
  public void setTodoList(List<Todo> todoList) {
    this.todoList = todoList;
  }

  /**
   * getter of header
   * @return - the header, as a string list
   */
  public List<String> getHeader() {
    return header;
  }

  /**
   * getter of ID counter
   * @return - the ID counter, as an integer
   */
  public int getIDCounter() {
    return IDCounter;
  }

  /**
   * setter of ID counter
   * @param IDCounter - the ID counter, as an integer
   */
  public void setIDCounter(int IDCounter) {
    this.IDCounter = IDCounter;
  }

  /**
   * Helper function. Converts a Todo object into a map. This step is necessary since the header
   * can be in different order for each csv file.
   * @param todo - a Todo object
   * @return - the map representation of the Todo. The keys are the header and the values are the
   * todo's data. Value that is null will be saved as question mark, just as how it will be written
   * in the csv file.
   */
  private Map<String, String> todoMap(Todo todo){
    Map<String, String> todoTXTMap = new HashMap<>();
    todoTXTMap.put(Constant.ID, String.valueOf(todo.getID()));
    todoTXTMap.put(Constant.TEXT, todo.getText());
    todoTXTMap.put(Constant.COMPLETED, String.valueOf(todo.isCompleted()));
    String dueDate;
    if (todo.getDue() == null){
      dueDate = Constant.NO_INPUT;
    } else {
      dueDate = todo.getDue().toString();
    }
    todoTXTMap.put(Constant.DUE, dueDate);
    todoTXTMap.put(Constant.PRIORITY, String.valueOf(todo.getPriority()));
    String categoryString;
    if (todo.getCategory() == null){
      categoryString = Constant.NO_INPUT;
    } else {
      categoryString = todo.getCategory();
    }
    todoTXTMap.put(Constant.CATEGORY, categoryString);
    return todoTXTMap;
  }

  /**
   * Converts the Todo object into a String, which has the same form when it is written in the CSV
   * file.
   * @param todo - an Todo object
   * @return - the string in csv file form
   */
  private String todoTXT(Todo todo){
    Map<String, String> map = todoMap(todo);
    String todoString = "";
    for (int i=0; i<this.getHeader().size(); i++){
      String temp;
      if (i == 0){
        temp = Constant.PARENTHESES + map.get(this.getHeader().get(i)) + Constant.PARENTHESES;
      } else {
        temp = Constant.COMA + Constant.PARENTHESES + map.get(this.getHeader().get(i))
            + Constant.PARENTHESES;
      }
      todoString += temp;
    }
    return todoString;
  }

  /**
   * Add a new Todo to the TodoList. contents and todoList field will be updated
   * @param newTodo - the new todo, as a Todo object
   */
  public void addTodo(Todo newTodo){
    this.setIDCounter(this.getIDCounter() + 1);
    newTodo.setID(this.getIDCounter());
    List<Todo> newTodoList = this.getTodoList();
    newTodoList.add(newTodo);
    this.setTodoList(newTodoList);
    List<String> newContents = this.getContents();
    newContents.add(this.todoTXT(newTodo));
    this.setContents(newContents);
  }

  /**
   * Complete a list of Todo using their IDs. contents and todoList field will be updated
   * @param completedTodoID - a list of IDs that are completed, as a integer list
   */
  public void completeTodos(List<Integer> completedTodoID){
    for (int i=0; i<completedTodoID.size(); i++){
      if (completedTodoID.get(i) > this.getIDCounter()){
        throw new IllegalArgumentException("This todo does not exist.");
      }
      int completeIndex = completedTodoID.get(i) - 1;

      this.getTodoList().get(completeIndex).setCompleted();
      String newTodoContent = this.getContents().get(completedTodoID.get(i)).
          replaceAll(Constant.FALSE, Constant.TRUE);
      List<String> newContents = this.getContents();
      newContents.set(completedTodoID.get(i), newTodoContent);
      this.setContents(newContents);
    }
  }

  /**
   * Compare two objects
   * @param o - another object
   * @return - true if equal and false if otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TodoList todoList1 = (TodoList) o;
    return IDCounter == todoList1.IDCounter && Objects.equals(contents, todoList1.contents)
        && Objects.equals(todoList, todoList1.todoList) && Objects.equals(header,
        todoList1.header);
  }

  /**
   * hashcode of an object
   * @return - the hashcode of the object
   */
  @Override
  public int hashCode() {
    return Objects.hash(contents, todoList, header, IDCounter);
  }

  /**
   * Convert the object into a string
   * @return - the string representation of the object
   */
  @Override
  public String toString() {
    return "TodoList{" +
        "contents=" + contents +
        ", todoList=" + todoList +
        ", header=" + header +
        ", IDCounter=" + IDCounter +
        '}';
  }
}