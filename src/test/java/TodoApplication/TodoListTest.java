package TodoApplication;

import static org.junit.jupiter.api.Assertions.*;

import TodoApplication.Todo.Builder;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TodoListTest {

  // A modified todos.csv, sametodos.csv and difftodos.csv are used for this test

  String csvPath;
  FileReader testReader;
  TodoList testList;

  @BeforeEach
  void setUp() throws IOException {
    csvPath = "todos.csv";
    testReader = new FileReader(csvPath);
    testList = testReader.generateTodoList();
  }

  @Test
  void addTodo() {
    Builder newBuilder = new Builder("Go to sleep");
    Todo newTodo = newBuilder.build();
    testList.addTodo(newTodo);
    assertEquals(testList.getTodoList().get(testList.getIDCounter() - 1), newTodo);
  }

  @Test
  void completeTodos() {
    Todo changedTodo = testList.getTodoList().get(0);
    changedTodo.setCompleted();
    List<Integer> complete = Arrays.asList(1, 3, 5);
    testList.completeTodos(complete);
    assertEquals(testList.getTodoList().get(0), changedTodo);
  }

  @Test
  void testEquals() throws IOException {
    assertTrue(testList.equals(testList));
    assertFalse(testList.equals(null));
    assertFalse(testList.equals(testReader));
    FileReader sameReader = new FileReader("sametodos.csv");
    TodoList sameList = sameReader.generateTodoList();
    assertTrue(testList.equals(sameList));
    assertEquals(testList.hashCode(), sameList.hashCode());
    FileReader diffReade = new FileReader("difftodos.csv");
    TodoList diffList = diffReade.generateTodoList();
    assertFalse(testList.equals(diffList));

  }

  @Test
  void testToString() {
    String expectedString = "TodoList{" +
        "contents=" + testList.getContents().toString() +
        ", todoList=" + testList.getTodoList().toString() +
        ", header=" + testList.getHeader().toString() +
        ", IDCounter=" + testList.getIDCounter() +
        '}';
    assertEquals(expectedString, testList.toString());
  }
}