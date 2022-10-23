package TodoApplication;

import static org.junit.jupiter.api.Assertions.*;

import TodoApplication.Todo.Builder;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TodoTest {
    Todo testTodo;
    Builder testBuilder = new Builder("test-todo");

    @BeforeEach
    void setUp() {
        testBuilder.completeTodo().addDue(LocalDate.of(2022, 4, 25)).
            changePriority(2).addCategory("5004");
        testTodo = testBuilder.build();
    }

    @Test
    void getID() {
        testTodo.setID(1);
        assertTrue(testTodo.getID() == 1);
    }

    @Test
    void getText() {
        assertTrue(testTodo.getText().equals("test-todo"));
    }

    @Test
    void isCompleted() {
        assertTrue(testTodo.isCompleted());
    }

    @Test
    void getDue() {
        assertTrue(testTodo.getDue().equals(LocalDate.of(2022, 4, 25)));
    }

    @Test
    void getPriority() {
        assertTrue(testTodo.getPriority() == 2);
    }

    @Test
    void getCategory() {
        assertTrue(testTodo.getCategory().equals("5004"));
    }

    @Test
    void testEquals_SameMemoryLocation() {
        assertTrue(testTodo.equals(testTodo));
    }

    @Test
    void testEquals_NullObject() {
        assertFalse(testTodo.equals(null));
    }

    @Test
    void testEquals_DifferentDataType() {
        assertFalse(testTodo.equals(testBuilder));
    }

    @Test
    void testEquals_SameFields() {
        Builder newTestBuilder = new Builder("test-todo").completeTodo().
            addDue(LocalDate.of(2022, 4, 25)).changePriority(2).addCategory("5004");
        Todo newTestTodo = newTestBuilder.build();
        assertTrue(testTodo.equals(newTestTodo));
    }

    @Test
    void testEquals_DifferentID() {
        Builder newTestBuilder = new Builder("test-todo").completeTodo()
            .addDue(LocalDate.of(2022, 4, 25)).changePriority(2).addCategory("5004");
        Todo newTestTodo = newTestBuilder.build();
        newTestTodo.setID(1);
        testTodo.setID(2);
        assertFalse(testTodo.equals(newTestTodo));
    }

    @Test
    void testEquals_DifferentText() {
        Builder newTestBuilder = new Builder("new-test-todo").completeTodo().
            addDue(LocalDate.of(2022, 4, 25)).changePriority(2).addCategory("5004");
        Todo newTestTodo = newTestBuilder.build();
        assertFalse(testTodo.equals(newTestTodo));
    }

    @Test
    void testEquals_DifferentCompleted() {
        Builder newTestBuilder = new Builder("test-todo").
            addDue(LocalDate.of(2022, 4, 25)).changePriority(2).addCategory("5004");
        Todo newTestTodo = newTestBuilder.build();
        assertFalse(testTodo.equals(newTestTodo));
    }

    @Test
    void testEquals_DifferentDue() {
        Builder newTestBuilder = new Builder("test-todo").completeTodo().
            addDue(LocalDate.of(2022, 4, 20)).changePriority(2).addCategory("5004");
        Todo newTestTodo = newTestBuilder.build();
        assertFalse(testTodo.equals(newTestTodo));
    }

    @Test
    void testEquals_DifferentPriority() {
        Builder newTestBuilder = new Builder("test-todo").completeTodo().
            addDue(LocalDate.of(2022, 4, 25)).changePriority(1).addCategory("5004");
        Todo newTestTodo = newTestBuilder.build();
        assertFalse(testTodo.equals(newTestTodo));
    }

    @Test
    void testEquals_DifferentCategory() {
        Builder newTestBuilder = new Builder("test-todo").completeTodo().
            addDue(LocalDate.of(2022, 4, 25)).changePriority(2).addCategory("5008");
        Todo newTestTodo = newTestBuilder.build();
        assertFalse(testTodo.equals(newTestTodo));
    }

    @Test
    void testHashCode() {
        Builder newTestBuilder = new Builder("test-todo").completeTodo().
            addDue(LocalDate.of(2022, 4, 25)).changePriority(2).addCategory("5004");
        Todo newTestTodo = newTestBuilder.build();
        assertEquals(newTestTodo.hashCode(), testTodo.hashCode());
    }

    @Test
    void testToString() {
        String expectedString = "Todo{" +
            "ID=" + 0 +
            ", text='" + "test-todo" + '\'' +
            ", completed=" + true +
            ", due=" + LocalDate.of(2022, 4, 25) +
            ", priority=" + 2 +
            ", category='" + "5004" + '\'' +
            '}';
        assertEquals(expectedString, testTodo.toString());
    }
}