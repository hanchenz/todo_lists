package TodoApplication;

import TodoApplication.DisplayOption.DisplayBuilder;
import TodoApplication.Todo.Builder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProcessorTest {

  // this test requires todosForProcessor.csv

  CommandInfo testCommandInfo;
  CommandInfo testCommandInfo2;
  CommandInfo testCommandInfo3;
  CommandInfo testCommandInfo4;
  CommandInfo testCommandInfo5;
  CommandInfo testCommandInfo6;
  CommandInfo testCommandInfo7;
  Todo testTodo;
  List<Integer> testID = new ArrayList<>();
  DisplayOption testDisplayOption;
  DisplayOption testDisplayOption2;
  DisplayOption testDisplayOption3;
  DisplayOption testDisplayOption4;
  DisplayOption testDisplayOption5;
  DisplayOption testDisplayOption6;


  @BeforeEach
  void setUp() {
    Todo.Builder builder = new Builder("test-todo");
    testTodo = builder.build();
    testID.add(1);
    testID.add(3);
    DisplayOption.DisplayBuilder displayBuilder = new DisplayBuilder();
    displayBuilder.sortByDate();
    displayBuilder.showIncomplete();
    testDisplayOption = displayBuilder.buildDisplayOption();
    testCommandInfo = new CommandInfo("todosForProcessor.csv", testTodo, testID, testDisplayOption);

    List<Integer> emptyID= new ArrayList<>();
    DisplayOption.DisplayBuilder displayBuilder2 = new DisplayBuilder();
    displayBuilder2.showCategory("home");
    displayBuilder2.sortByPriority();
    testDisplayOption2 = displayBuilder2.buildDisplayOption();
    testCommandInfo2 = new CommandInfo("todosForProcessor.csv", null, emptyID, testDisplayOption2);

    DisplayOption.DisplayBuilder displayBuilder3 = new DisplayBuilder();
    displayBuilder3.showCategory("school");
    displayBuilder3.showIncomplete();
    testDisplayOption3 = displayBuilder3.buildDisplayOption();
    testCommandInfo3 = new CommandInfo("todosForProcessor.csv", null, emptyID, testDisplayOption3);

    DisplayOption.DisplayBuilder displayBuilder4 = new DisplayBuilder();
    displayBuilder4.showCategory("school");
    testDisplayOption4 = displayBuilder4.buildDisplayOption();
    testCommandInfo4 = new CommandInfo("todosForProcessor.csv", null, emptyID, testDisplayOption4);

    testCommandInfo5 = new CommandInfo("todosForProcessor.csv", null, emptyID, null);

    testDisplayOption5 = new DisplayBuilder().buildDisplayOption();
    testCommandInfo6 = new CommandInfo("todosForProcessor.csv", null, emptyID, testDisplayOption5);

    testDisplayOption6 = new DisplayBuilder().sortByDate().buildDisplayOption();
    testCommandInfo7 = new CommandInfo("todosForProcessor.csv", null, emptyID, testDisplayOption6);
  }

  @Test
  void testProcessor() throws IOException {
    TodoList todolist = Processor.readWrite(testCommandInfo);
    TodoList todolist2 = Processor.readWrite(testCommandInfo7);

    Processor.display(testCommandInfo, todolist);
    Processor.display(testCommandInfo2, todolist);
    Processor.display(testCommandInfo3, todolist);
    Processor.display(testCommandInfo4, todolist);
    Processor.display(testCommandInfo5, todolist);
    Processor.display(testCommandInfo6, todolist);

  }
}