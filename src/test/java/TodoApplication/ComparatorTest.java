package TodoApplication;

import TodoApplication.Todo.Builder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComparatorTest {
    List<Todo> testTodoList;
    Todo testTodo1;
    Todo testTodo2;
    Todo testTodo3;

    @BeforeEach
    void setUp() {
        testTodoList = new ArrayList<>();
        Builder testBuilder1 = new Builder("test-todo1");
        testBuilder1.completeTodo().addDue(LocalDate.of(2022, 4, 25)).
                changePriority(2).addCategory("5004");
        testTodo1 = testBuilder1.build();
        Builder testBuilder2 = new Builder("test-todo2");
        testBuilder2.completeTodo().addDue(LocalDate.of(2021, 10, 2)).
                changePriority(1).addCategory("5800");
        testTodo2 = testBuilder2.build();
        Builder testBuilder3 = new Builder("test-todo3");
        testBuilder3.completeTodo().addCategory("6650"); // due date is null // priority is default value 3
        testTodo3 = testBuilder3.build();
        testTodoList.add(testTodo1);
        testTodoList.add(testTodo2);
        testTodoList.add(testTodo3);
    }

    @Test
    void compareDueDate() {
        assertEquals(1, new DueDateComparator().compare(testTodo1, testTodo2));
        assertEquals(-1, new DueDateComparator().compare(testTodo1, testTodo3));
        assertEquals(1, new DueDateComparator().compare(testTodo3, testTodo2));
        assertEquals(0, new DueDateComparator().compare(testTodo1, testTodo1));
    }

    @Test
    void sortByDueDate() {
        List<Todo> expectedTodoList = new ArrayList<>();
        expectedTodoList.add(testTodo2);
        expectedTodoList.add(testTodo1);
        expectedTodoList.add(testTodo3);

        Collections.sort(testTodoList, new DueDateComparator());
        assertEquals(expectedTodoList, testTodoList);
    }

    @Test
    void comparePriority() {
        assertEquals(1, new PriorityComparator().compare(testTodo1, testTodo2));
    }

    @Test
    void sortByPriority() {
        List<Todo> expectedTodoList = new ArrayList<>();
        expectedTodoList.add(testTodo2);
        expectedTodoList.add(testTodo1);
        expectedTodoList.add(testTodo3);

        Collections.sort(testTodoList, new PriorityComparator());
        assertEquals(expectedTodoList, testTodoList);
    }

    @Test
    void testEqualsAndTostring() {
        DueDateComparator dueDateComparator = new DueDateComparator();
        PriorityComparator priorityComparator = new PriorityComparator();
        assertTrue(dueDateComparator.equals(dueDateComparator));
        assertFalse(dueDateComparator.equals(null));
        assertFalse(dueDateComparator.equals(priorityComparator));
        DueDateComparator dueDateComparator2 = new DueDateComparator();
        assertTrue(dueDateComparator.equals(dueDateComparator2));
        assertEquals(dueDateComparator.hashCode(), dueDateComparator2.hashCode());

        assertTrue(priorityComparator.equals(priorityComparator));
        assertFalse(priorityComparator.equals(null));
        assertFalse(priorityComparator.equals(dueDateComparator));
        PriorityComparator priorityComparator2 = new PriorityComparator();
        assertTrue(priorityComparator.equals(priorityComparator2));
        assertEquals(priorityComparator.hashCode(), priorityComparator2.hashCode());

        String expectedString1 = "DueDateComparator{}";
        assertEquals(expectedString1, dueDateComparator.toString());
        String expectedString2 = "PriorityComparator{}";
        assertEquals(expectedString2, priorityComparator.toString());
    }
}