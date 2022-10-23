package TodoApplication;

import static org.junit.jupiter.api.Assertions.*;

import TodoApplication.Todo.Builder;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterTest {

  // newtodo.csv and difftodos.csv are used for this test

  String csvPath;
  FileWriter testWriter;

  @BeforeEach
  void setUp() {
    csvPath = "newtodo.csv";
    testWriter = new FileWriter(csvPath);

  }

  @Test
  void getCsvPath() {
    String expectedPath = "newtodo.csv";
    assertEquals(expectedPath, testWriter.getCsvPath());
  }

  @Test
  void writeFile() throws IOException {
    FileReader reader = new FileReader("todos.csv");
    TodoList aList = reader.generateTodoList();
    List<Integer> complete = Arrays.asList(1, 3, 5);
    aList.completeTodos(complete);
    Builder newBuilder = new Builder("Go to sleep");
    Todo newTodo = newBuilder.build();
    aList.addTodo(newTodo);
    testWriter.writeFile(aList.getContents());
  }

  @Test
  void testEquals() {
    assertTrue(testWriter.equals(testWriter));
    assertFalse(testWriter.equals(null));
    assertFalse(testWriter.equals(csvPath));
    FileWriter sameWriter = new FileWriter(csvPath);
    assertTrue(testWriter.equals(sameWriter));
    assertEquals(testWriter.hashCode(), sameWriter.hashCode());
    FileWriter diffWriter = new FileWriter("difftodos.csv");
    assertFalse(testWriter.equals(diffWriter));
  }

  @Test
  void testToString() {
    String expectedString = "FileWriter{" +
        "csvPath='" + csvPath + '\'' +
        '}';
    assertEquals(expectedString, testWriter.toString());
  }
}