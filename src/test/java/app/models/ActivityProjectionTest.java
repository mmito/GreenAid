package app.models;

import app.models.ActivityProjection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ActivityProjectionTest {

    private final static double DELTA = 0;
    private ActivityProjection activityProjection;

    @Before
    public void setUp() {
        activityProjection = new ActivityProjection("username-test", "category-test", 1.0, 1.0);
    }

    @After
    public void tearDown() {
        activityProjection = null;
    }

    @Test
    public void constructorDefault() {
        activityProjection = new ActivityProjection();
        assertNotNull(activityProjection);
    }

    @Test
    public void constructorParameters() {
        activityProjection = new ActivityProjection("username-test", "category-test", 1.0, 1.0);
        assertNotNull(activityProjection);
    }

    @Test
    public void getUsername() {
        assertEquals("username-test", activityProjection.getUsername());
    }

    @Test
    public void setUsername() {
        activityProjection.setUsername("username-test-2");
        assertEquals("username-test-2", activityProjection.getUsername());
    }

    @Test
    public void getCategory() {
        assertEquals("category-test", activityProjection.getCategory());
    }

    @Test
    public void setCategory() {
        activityProjection.setCategory("category-test-2");
        assertEquals("category-test-2", activityProjection.getCategory());
    }

    @Test
    public void getAmount() {
        assertEquals(1.0, activityProjection.getAmount(), DELTA);
    }

    @Test
    public void setAmount() {
        activityProjection.setAmount(2.0);
        assertEquals(2.0, activityProjection.getAmount(), DELTA);
    }

    @Test
    public void getXp_points() {
        assertEquals(1.0, activityProjection.getXp_points(), DELTA);
    }

    @Test
    public void setXp_points() {
        activityProjection.setXp_points(2.0);
        assertEquals(2.0, activityProjection.getXp_points(), DELTA);
    }

    @Test
    public void equalsSameObject() {
        assertEquals(activityProjection, activityProjection);
    }

    @Test
    public void equalsNullObject() {
        assertNotEquals(activityProjection, null);
    }

    @Test
    public void equalsDifferentClass() {
        assertNotEquals(activityProjection, new String());
    }

    @Test
    public void equalsDifferentAmount() {
        ActivityProjection activityProjection2 = new ActivityProjection("username-test", "category-test", 2.0, 1.0);
        assertNotEquals(activityProjection, activityProjection2);
    }

    @Test
    public void equalsDifferentXP() {
        ActivityProjection activityProjection2 = new ActivityProjection("username-test", "category-test", 1.0, 2.0);
        assertNotEquals(activityProjection, activityProjection2);
    }

    @Test
    public void equalsDifferentUsername() {
        ActivityProjection activityProjection2 = new ActivityProjection("username-test-2", "category-test", 1.0, 1.0);
        assertNotEquals(activityProjection, activityProjection2);
    }

    @Test
    public void equalsDifferentCategory() {
        ActivityProjection activityProjection2 = new ActivityProjection("username-test", "category-test-2", 1.0, 1.0);
        assertNotEquals(activityProjection, activityProjection2);
    }

    @Test
    public void equalsTrue() {
        ActivityProjection activityProjection2 = new ActivityProjection("username-test", "category-test", 1.0, 1.0);
        assertEquals(activityProjection, activityProjection2);
    }


}