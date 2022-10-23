package TodoApplication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TodoIteratorTest {

    List<Todo> testTodoList;
    Todo testTodo1;
    Todo testTodo2;
    Todo testTodo3;
    Todo testTodo4;

    @BeforeEach
    void setUp() {
        testTodoList = new ArrayList<>();

        Todo.Builder testBuilder1 = new Todo.Builder("test-todo1");
        testBuilder1.completeTodo().addDue(LocalDate.of(2022, 4, 25)).
                changePriority(2).addCategory("5004"); // completed
        testTodo1 = testBuilder1.build();

        Todo.Builder testBuilder2 = new Todo.Builder("test-todo2");
        testBuilder2.addDue(LocalDate.of(2021, 10, 2)).
                changePriority(1).addCategory("5800"); // not completed
        testTodo2 = testBuilder2.build();

        Todo.Builder testBuilder3 = new Todo.Builder("test-todo3");
        // not completed // category is default null // priority is default value 3
        testBuilder3.addDue(LocalDate.of(2022, 6, 15));
        testTodo3 = testBuilder3.build();

        Todo.Builder testBuilder4 = new Todo.Builder("test-todo4");
        testBuilder4.completeTodo().addDue(LocalDate.of(2022, 4, 30)).changePriority(1).
                addCategory(new String("5004")); // completed // another way to add category string
        testTodo4 = testBuilder4.build();

        testTodoList.add(testTodo1);
        testTodoList.add(testTodo2);
        testTodoList.add(testTodo3);
        testTodoList.add(testTodo4);
    }

    @Test
    void hasNext() {
        Iterator testTodoIterator1 = new TodoIterator(testTodoList, new IncompleteFilter());
        assertTrue(testTodoIterator1.hasNext());
        testTodoIterator1.next(); // testTodo2
        testTodoIterator1.next(); // testTodo3
        assertFalse(testTodoIterator1.hasNext());

        Iterator testTodoIterator2 = new TodoIterator(testTodoList, new CategoryFilter("5004"));
        assertTrue(testTodoIterator2.hasNext());
        testTodoIterator2.next(); // testTodo1
        testTodoIterator2.next(); // testTodo4
        assertFalse(testTodoIterator2.hasNext());

        Iterator testTodoIterator3 = new TodoIterator(testTodoList,
                new AndFilter(Arrays.asList(new IncompleteFilter(), new CategoryFilter("?"))));
        assertTrue(testTodoIterator3.hasNext());
        testTodoIterator3.next(); // testTodo3
        assertFalse(testTodoIterator3.hasNext());

    }

    @Test
    void next() {
        Iterator testTodoIterator1 = new TodoIterator(testTodoList, new IncompleteFilter());
        assertEquals(testTodo2, testTodoIterator1.next());
        assertEquals(testTodo3, testTodoIterator1.next());

        Iterator testTodoIterator2 = new TodoIterator(testTodoList, new CategoryFilter("5004"));
        assertEquals(testTodo1, testTodoIterator2.next());
        assertEquals(testTodo4, testTodoIterator2.next());

    }

    @Test
    void remove() {
        Iterator testTodoIterator1 = new TodoIterator(testTodoList, new IncompleteFilter());
        assertThrows(UnsupportedOperationException.class, () -> {testTodoIterator1.remove();});

    }

    @Test
    void testEquals_SameMemoryLocation() {
        Iterator testTodoIterator1 = new TodoIterator(testTodoList, new IncompleteFilter());
        assertTrue(testTodoIterator1.equals(testTodoIterator1));
    }

    @Test
    void testEquals_NullObject() {
        Iterator testTodoIterator1 = new TodoIterator(testTodoList, new IncompleteFilter());
        Iterator nullIterator;
        assertFalse(testTodoIterator1.equals(null));
    }

    @Test
    void testEquals_DifferentDataTypes() {
        Iterator testTodoIterator1 = new TodoIterator(testTodoList, new IncompleteFilter());
        String testString = "test";
        assertFalse(testTodoIterator1.equals(testString));
    }

    @Test
    void testEquals_SameFieldsOverall() {
        Iterator testTodoIterator1 = new TodoIterator(testTodoList, new IncompleteFilter());
        Iterator testTodoIterator2 = new TodoIterator(testTodoList, new IncompleteFilter());

        assertTrue(testTodoIterator1.equals(testTodoIterator2));

    }

    @Test
    void testEquals_differentIndex() {
        Iterator testTodoIterator1 = new TodoIterator(testTodoList, new IncompleteFilter());
        Iterator testTodoIterator2 = new TodoIterator(testTodoList, new IncompleteFilter());
        testTodoIterator1.next();
        assertFalse(testTodoIterator1.equals(testTodoIterator2));
    }

    @Test
    void testEquals_differentFilteredTodos() {
        Iterator testTodoIterator1 = new TodoIterator(testTodoList, new IncompleteFilter());
        Iterator testTodoIterator2 = new TodoIterator(testTodoList, new CategoryFilter("5004"));
        assertFalse(testTodoIterator1.equals(testTodoIterator2));
    }

    @Test
    void testHashCode() {
        Iterator testTodoIterator1 = new TodoIterator(testTodoList, new IncompleteFilter());

        List<Todo> newTestTodoList = new ArrayList<>();
        newTestTodoList.add(testTodo2);
        newTestTodoList.add(testTodo3);
        int expectedHashCode = Objects.hash(0, newTestTodoList);
        assertEquals(expectedHashCode, testTodoIterator1.hashCode());
    }

    @Test
    void testToString() {
        Iterator testTodoIterator1 = new TodoIterator(testTodoList, new IncompleteFilter());

        List<Todo> newTestTodoList = new ArrayList<>();
        newTestTodoList.add(testTodo2);
        newTestTodoList.add(testTodo3);

        String expectedString = "TodoIterator{" +
                "index=" + 0 +
                ", filteredTodos=" + newTestTodoList +
                '}';
        assertEquals(expectedString, testTodoIterator1.toString());
    }
}