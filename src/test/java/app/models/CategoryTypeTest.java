package app.models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryTypeTest {

    CategoryType categoryType;

    @Before
    public void setUp() throws Exception {
        categoryType = new CategoryType();
        categoryType.setId(1);
        categoryType.setCategory_name("categoryName");
    }

    @After
    public void tearDown() throws Exception {
        categoryType = null;
    }

    @Test
    public void getId() {
        assertEquals(1, categoryType.getId());
    }

    @Test
    public void setId() {
        categoryType.setId(2);
        assertEquals(2, categoryType.getId());
    }

    @Test
    public void getCategory_name() {
        assertEquals("categoryName", categoryType.getCategory_name());
    }

    @Test
    public void setCategory_name() {
        categoryType.setCategory_name("otherCategoryName");
        assertEquals("otherCategoryName", categoryType.getCategory_name());
    }
}