package TodoApplication;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * FileWriter write the content into a file pointed by the csv path
 */
public class FileWriter {

  private final String csvPath;
  private final static String EMPTY_LINE = "\n";

  /**
   * Constructs a FileWriter
   * @param csvPath - the csv path, as a string
   */
  public FileWriter(String csvPath) {
    this.csvPath = csvPath;
  }

  /**
   * getter of csv path
   * @return - the csv path as a string
   */
  public String getCsvPath() {
    return csvPath;
  }

  /**
   * write the contents into the csv file
   * @param contents - the contents of the TodoList, as a string list
   * @throws IOException - thrown when error happen during file writing
   */
  public void writeFile(List<String> contents) throws IOException {
    try {
      java.io.FileWriter csvWriter = new java.io.FileWriter(this.getCsvPath());
      for (int i = 0; i < contents.size(); i++) {
        csvWriter.write(contents.get(i));
        csvWriter.write(EMPTY_LINE);
      }
      csvWriter.close();
    } catch (IOException e){
      throw e;
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
    FileWriter that = (FileWriter) o;
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
    return "FileWriter{" +
        "csvPath='" + csvPath + '\'' +
        '}';
  }
}