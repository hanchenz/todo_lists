package TodoApplication;

import static org.junit.jupiter.api.Assertions.*;

import TodoApplication.DisplayOption.DisplayBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DisplayOptionTest {
    DisplayOption testDisplayOption;
    DisplayBuilder testDisplayBuilder;

    @BeforeEach
    void setUp() {
        testDisplayBuilder = new DisplayBuilder().showIncomplete().showCategory("5004");
        testDisplayOption = testDisplayBuilder.buildDisplayOption();
    }

    @Test
    void BuilderSortByDate() {
        assertThrows(IllegalArgumentException.class, ()-> {
            testDisplayBuilder.sortByDate().sortByPriority();
        });
    }

    @Test
    void BuilderSortByPriority() {
        assertThrows(IllegalArgumentException.class, ()-> {
            testDisplayBuilder.sortByPriority().sortByDate();
        });
    }

    @Test
    void isShowIncomplete() {
        assertTrue(testDisplayOption.isShowIncomplete());
    }

    @Test
    void isShowCategory() {
        assertTrue(testDisplayOption.isShowCategory());
    }

    @Test
    void getCategory() {
        assertTrue(testDisplayOption.getCategory().equals("5004"));
    }

    @Test
    void isSortByDate() {
        assertFalse(testDisplayOption.isSortByDate());
    }

    @Test
    void isSortByPriority() {
        assertFalse(testDisplayOption.isSortByPriority());
    }

    @Test
    void testEquals_SameMemoryLocation() {
        assertTrue(testDisplayOption.equals(testDisplayOption));
    }

    @Test
    void testEquals_NullObject() {
        assertFalse(testDisplayOption.equals(null));
    }

    @Test
    void testEquals_DifferentDataType() {
        assertFalse(testDisplayOption.equals(testDisplayBuilder));
    }

    @Test
    void testEquals_SameFields() {
        DisplayBuilder newTestDisplayBuilder = new DisplayBuilder().showIncomplete().showCategory("5004");
        DisplayOption newDisplayOption = newTestDisplayBuilder.buildDisplayOption();
        assertTrue(testDisplayOption.equals(newDisplayOption));
    }

    @Test
    void testEquals_DifferentShowIncompete() {
        DisplayBuilder newTestDisplayBuilder = new DisplayBuilder().showCategory("5004");
        DisplayOption newDisplayOption = newTestDisplayBuilder.buildDisplayOption();
        assertFalse(testDisplayOption.equals(newDisplayOption));
    }

    @Test
    void testEquals_DifferentShowCategory() {
        DisplayBuilder newTestDisplayBuilder = new DisplayBuilder().showIncomplete();
        DisplayOption newDisplayOption = newTestDisplayBuilder.buildDisplayOption();
        assertFalse(testDisplayOption.equals(newDisplayOption));
    }

    @Test
    void testEquals_DifferentCategory() {
        DisplayBuilder newTestDisplayBuilder = new DisplayBuilder().showIncomplete().showCategory("5008");
        DisplayOption newDisplayOption = newTestDisplayBuilder.buildDisplayOption();
        assertFalse(testDisplayOption.equals(newDisplayOption));
    }

    @Test
    void testEquals_DifferentSortByDate() {
        DisplayBuilder newTestDisplayBuilder = new DisplayBuilder().showIncomplete().showCategory("5004").
            sortByDate();
        DisplayOption newDisplayOption = newTestDisplayBuilder.buildDisplayOption();
        assertFalse(testDisplayOption.equals(newDisplayOption));
    }

    @Test
    void testEquals_DifferentSortByPriority() {
        DisplayBuilder newTestDisplayBuilder = new DisplayBuilder().showIncomplete().showCategory("5004").
            sortByPriority();
        DisplayOption newDisplayOption = newTestDisplayBuilder.buildDisplayOption();
        assertFalse(testDisplayOption.equals(newDisplayOption));
    }

    @Test
    void testHashCode() {
        DisplayBuilder newTestDisplayBuilder = new DisplayBuilder().showIncomplete().showCategory("5004");
        DisplayOption newDisplayOption = newTestDisplayBuilder.buildDisplayOption();
        assertEquals(newDisplayOption.hashCode(), testDisplayOption.hashCode());
    }

    @Test
    void testToString() {
        String expectedString = "DisplayOption{" +
            "showIncomplete=" + true +
            ", showCategory=" + true +
            ", category='" + "5004" + '\'' +
            ", sortByDate=" + false +
            ", sortByPriority=" + false +
            '}';
        assertEquals(expectedString, testDisplayOption.toString());
    }
}