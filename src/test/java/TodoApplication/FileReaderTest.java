package TodoApplication;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderTest {

  // A modified todos.csv, falsedate.csv, falsepriority.csv and difftodos.csv
  // are used to run this test.

  String csvPath;
  FileReader testReader;

  @BeforeEach
  void setUp() {
    csvPath = "todos.csv";
    testReader = new FileReader(csvPath);
  }

  @Test
  void getCsvPath() {
    String expectedCsvPath = "todos.csv";
    assertEquals(expectedCsvPath, testReader.getCsvPath());
  }

  @Test
  void generateTodoList() throws IOException {
    try {
      TodoList testTodoList = testReader.generateTodoList();
    } catch (IOException e){
      fail("Should not catch exception");
    }

    try{
      FileReader falseReader = new FileReader("nonexistence.csv");
      TodoList todoList = falseReader.generateTodoList();
      fail("Should catch exception");
    } catch (IOException e){

    }
    try{
      FileReader falseDateReader = new FileReader("falsedate.csv");
      TodoList todoList = falseDateReader.generateTodoList();
      fail("Should catch exception");
    } catch (IllegalArgumentException e){

    }

    try{
      FileReader falsePriorityReader = new FileReader("falsepriority.csv");
      TodoList todoList = falsePriorityReader.generateTodoList();
      fail("Should catch exception");
    } catch (IllegalArgumentException e){

    }
  }

  @Test
  void testEquals() {
    assertTrue(testReader.equals(testReader));
    assertFalse(testReader.equals(null));
    assertFalse(testReader.equals(1));
    FileReader sameReade = new FileReader(csvPath);
    assertTrue(testReader.equals(sameReade));
    assertEquals(testReader.hashCode(), sameReade.hashCode());
    FileReader diffReader = new FileReader("difftodos.csv");
    assertFalse(testReader.equals(diffReader));
  }

  @Test
  void testToString(){
    String expectedString = "FileReader{" +
        "csvPath='" + csvPath + '\'' +
        '}';
    assertEquals(expectedString, testReader.toString());
  }
}