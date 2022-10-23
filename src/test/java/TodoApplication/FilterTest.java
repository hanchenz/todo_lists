package TodoApplication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class FilterTest {

    Filter testCategoryFilter;
    Filter testAndFilter;
    Filter testIncompleteFilter;
    List<Filter> testListOfFilters;

    @BeforeEach
    void setUp() {
        testCategoryFilter = new CategoryFilter("category");
        testIncompleteFilter = new IncompleteFilter();
        testListOfFilters = new ArrayList<>();
        testListOfFilters.add(testCategoryFilter);
        testListOfFilters.add(testIncompleteFilter);
        testAndFilter = new AndFilter(testListOfFilters);
    }

    @Test
    void testEquals_SameMemoryLocation() {
        assertTrue(testCategoryFilter.equals(testCategoryFilter));
        assertTrue(testIncompleteFilter.equals(testIncompleteFilter));
        assertTrue(testAndFilter.equals(testAndFilter));

    }

    @Test
    void testEquals_NullObject() {
        Filter nullFilter;
        assertFalse(testCategoryFilter.equals(null));
        assertFalse(testIncompleteFilter.equals(null));
        assertFalse(testAndFilter.equals(null));
    }

    @Test
    void testEquals_DifferentDataTypes() {
        String testString = "test";
        assertFalse(testCategoryFilter.equals(testString));
        assertFalse(testIncompleteFilter.equals(testString));
        assertFalse(testAndFilter.equals(testString));
    }

    @Test
    void testEquals_SameFieldsOverall() {
        Filter newTestCategoryFilter = new CategoryFilter("category");
        Filter newTestIncompleteFilter = new IncompleteFilter();
        List<Filter> testListOfFilters = new ArrayList<>();
        testListOfFilters.add(newTestCategoryFilter);
        testListOfFilters.add(newTestIncompleteFilter);
        Filter newTestAndFilter = new AndFilter(testListOfFilters);

        assertTrue(testCategoryFilter.equals(newTestCategoryFilter));
        assertTrue(testIncompleteFilter.equals(newTestIncompleteFilter));
        assertTrue(testAndFilter.equals(newTestAndFilter));

    }

    @Test
    void testEquals_differentCategory() {
        Filter newTestCategoryFilter = new CategoryFilter("new category");
        assertFalse(testCategoryFilter.equals(newTestCategoryFilter));
    }

    @Test
    void testEquals_differentFilterList() {
        Filter newTestCategoryFilter = new CategoryFilter("category");
        List<Filter> testListOfFilters = new ArrayList<>();
        testListOfFilters.add(newTestCategoryFilter);
        Filter newTestAndFilter = new AndFilter(testListOfFilters);
        assertFalse(testAndFilter.equals(newTestAndFilter));
    }

    @Test
    void testHashCode() {
        int expectedTestCategoryFilterHashCode = Objects.hash("category");
        assertEquals(expectedTestCategoryFilterHashCode, testCategoryFilter.hashCode());
        int expectedTestIncompleteFilterHashCode = 456;
        assertEquals(expectedTestIncompleteFilterHashCode, testIncompleteFilter.hashCode());
        int expectedTestAndFilterHashCode = Objects.hash(testListOfFilters);
        assertEquals(expectedTestAndFilterHashCode, testAndFilter.hashCode());
    }

    @Test
    void testToString() {
        String expectedTestCategoryFilterString = "CategoryFilter{" +
                "category='" + "category" + '\'' +
                '}';
        assertEquals(expectedTestCategoryFilterString, testCategoryFilter.toString());

        String expectedTestIncompleteFilterString = "IncompleteFilter{}";
        assertEquals(expectedTestIncompleteFilterString, testIncompleteFilter.toString());

        String expectedTestAndFilterString = "AndFilter{" +
                "andFilters=" + testListOfFilters +
                '}';
        assertEquals(expectedTestAndFilterString, testAndFilter.toString());
    }
}