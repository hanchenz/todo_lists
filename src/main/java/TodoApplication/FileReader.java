package TodoApplication;

import TodoApplication.Todo.Builder;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * FileReader read a csv file from the provided csv path and generate a TodoList object
 */
public class FileReader {

  private final String csvPath;
  private static final Integer HEADER_INDEX = 0;
  private static final String CSV_DELIMITER = "\"*,*\"";
  private static final Integer INITIAL_DATA_ROW_NUM = 1;

  /**
   * Constructs a FileReader
   * @param csvPath - the csv path, as a string
   */
  public FileReader(String csvPath) {
    this.csvPath = csvPath;
  }

  /**
   * getter of csvPath
   * @return - the csv path, as a string
   */
  public String getCsvPath() {
    return csvPath;
  }

  /**
   * Read the file from the provide path and return a list of string, in which each line is tored
   * as a string item
   * @return - the contents as a string list
   * @throws IOException - thrown when reading process has issues
   */
  private List<String> readFile() throws IOException {
    List<String> contents = new ArrayList<>();
    try (BufferedReader inputFile = new BufferedReader(new java.io.FileReader(this.getCsvPath()))) {
      String line;

      while ((line = inputFile.readLine()) != null) {
        if (line.trim().isEmpty()) {
          continue;
        }
        contents.add(line);
      }
    } catch (IOException e) {
      throw e;
    }

    return contents;
  }

  /**
   * Parse a string with the CSV_DELIMITER
   * @param rawData - a row of a csv file, encoded as a String
   * @return the information of a parsed row in a csv file, encoded as a list of String
   */
  private List<String> parse(String rawData) {
    List<String> parsedData = new ArrayList<>(Arrays.asList(rawData.split(CSV_DELIMITER)));
    // due to the regex pattern, there will be an additional empty data unit at the front after parsing,
    // here we simply remove it
    if (parsedData.get(0).equals(Constant.EMPTY_STRING))
      parsedData.remove(0);
    return parsedData;
  }

  /**
   * Generate the header of the file
   * @param contents - the contents of the file, as a string list
   * @return - the header of the file, as a string list
   */
  private List<String> generateHeader(List<String> contents) {
    String headerString = contents.get(HEADER_INDEX);
    List<String> header = parse(headerString);
    return header;
  }

  /**
   * Generate each row's data as a map. The header's value will be used as key and the row's data
   * will be matched with the header as the value.
   * @param row - the row's content, as a string
   * @param header - the header of the file, as a string list
   * @return - a map that represents the row
   */
  private Map<String, String> generateRow(String row, List<String> header) {
    Map<String, String> todoRow = new HashMap<>();
    List<String> rowData = parse(row);
    for (int i = 0; i < header.size(); i++) {
      if (rowData.get(i).equals(Constant.NO_INPUT)) {
        todoRow.put(header.get(i), null);
      } else {
        todoRow.put(header.get(i), rowData.get(i));
      }
    }
    return todoRow;
  }

  /**
   * The row's map will be used to build Todo objects.
   * IllegalArgument Exception will be thrown if the date cannot be converted into Localdate,
   * if the priority is out of range or is not an integer.
   * @param todoRow - the map representation of the row
   * @return - a Todo object of that row.
   */
  private Todo generateTodo(Map<String, String> todoRow){
    Builder builder = new Builder(todoRow.get(Constant.TEXT));
    if (todoRow.get(Constant.COMPLETED).equals(Constant.TRUE)){
      builder.completeTodo();
    }
    if (todoRow.get(Constant.DUE) != null){
      try {
        builder.addDue(LocalDate.parse(todoRow.get(Constant.DUE)));
      } catch (DateTimeParseException e) {
        throw new IllegalArgumentException("Invalid due time.");
      }
    }
    if (todoRow.get(Constant.PRIORITY) != null){
      try {
        int priority = Integer.parseInt(todoRow.get(Constant.PRIORITY));
        if (priority < Commands.HIGHEST_PRIORITY || priority > Commands.LOWEST_PRIORITY) {
          throw new IllegalArgumentException("Priority should be a number from 1 to 3.");
        }
        builder.changePriority(priority);
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Priority should be a number from 1 to 3.");
      }
    }
    if (todoRow.get(Constant.CATEGORY) != null){
      builder.addCategory(todoRow.get(Constant.CATEGORY));
    }
    return builder.build();
  }

  /**
   * This function will run the whole file reading process. It will read the file, generate the
   * contents and header, and then convert each row into a Todo Object,
   * and finally create a TodoList object that saves all this information.
   * @return - a TodoList object that saves all the information of the file
   * @throws IOException - thrown when error happens during file reading
   */
  public TodoList generateTodoList() throws IOException{
    List<String> contents = readFile();
    List<String> header = generateHeader(contents);
    List<Todo> todoList = new ArrayList<>();
    for (int i = INITIAL_DATA_ROW_NUM; i < contents.size(); i++) {
      Map<String, String> todoRow = generateRow(contents.get(i), header);
      Todo newTodo = generateTodo(todoRow);
      int todoID = Integer.parseInt(todoRow.get(Constant.ID));
      newTodo.setID(todoID);
      todoList.add(newTodo);
    }
    return new TodoList(contents, todoList, header);
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
    FileReader that = (FileReader) o;
    return Objects.equals(csvPath, that.csvPath);
  }

  /**
   * hashcode of an object
   * @return - the hashcode of the object
   */
  @Override
  public int hashCode() {
    return Objects.hash(csvPath);
  }

  /**
   * Convert the object into a string
   * @return - the string representation of the object
   */
  @Override
  public String toString() {
    return "FileReader{" +
        "csvPath='" + csvPath + '\'' +
        '}';
  }
}