package app.client;

import app.models.ActivityProjection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ActivityProjectionTest {

    private ActivityProjection activityProjection;
    private final double DELTA = 0.0;

    @Before
    public void setUp() {
        activityProjection = new ActivityProjection("username", "category", 1, 100);
    }

    @After
    public void tearDown() {
        activityProjection = null;
    }

    @Test
    public void constructorDefault() {
        ActivityProjection activityProjection = new ActivityProjection();
        assertEquals(new ActivityProjection(), activityProjection);
    }

    @Test
    public void constructorWithParameters() {
        ActivityProjection expected = new ActivityProjection("username", "category", 1, 100);
        assertEquals(expected, activityProjection);
    }

    @Test
    public void getUsername() {
        assertEquals("username", activityProjection.getUsername());
    }

    @Test
    public void setUsername() {
        activityProjection.setUsername("changedUsername");
        assertEquals("changedUsername", activityProjection.getUsername());
    }

    @Test
    public void getCategory() {
        assertEquals("category", activityProjection.getCategory());
    }

    @Test
    public void setCategory() {
        activityProjection.setCategory("changedCategory");
        assertEquals("changedCategory", activityProjection.getCategory());
    }

    @Test
    public void getAmount() {
        assertEquals(1, activityProjection.getAmount(), DELTA);
    }

    @Test
    public void setAmount() {
        activityProjection.setAmount(2);
        assertEquals(2, activityProjection.getAmount(), DELTA);
    }

    @Test
    public void getXp_points() {
        assertEquals(100, activityProjection.getXp_points(), DELTA);
    }

    @Test
    public void setXp_points() {
        activityProjection.setXp_points(200);
        assertEquals(200, activityProjection.getXp_points(), DELTA);
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
    public void equalsDifferentClassObject() {
        Object falseActivityProjection = "not an ActivityProjection class";
        assertNotEquals(activityProjection, falseActivityProjection);
    }

    @Test
    public void equalsTrue() {
        ActivityProjection other = new ActivityProjection("username", "category", 1, 100);
        assertEquals(activityProjection, other);
    }

    @Test
    public void equalsDifferentAmount() {
        ActivityProjection other = new ActivityProjection("username", "category", 2, 100);
        assertNotEquals(activityProjection, other);
    }

    @Test
    public void equalsDifferentXP() {
        ActivityProjection other = new ActivityProjection("username", "category", 1, 200);
        assertNotEquals(activityProjection, other);
    }

    @Test
    public void equalsDifferentUsername() {
        ActivityProjection other = new ActivityProjection("changedUsername", "category", 1, 100);
        assertNotEquals(activityProjection, other);
    }

    @Test
    public void equalsDifferentCategory() {
        ActivityProjection other = new ActivityProjection("username", "changedCategory", 1, 100);
        assertNotEquals(activityProjection, other);
    }
}