package app.models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.*;

public class FollowingTest {

    private Following following;

    @Before
    public void setUp() {
        following = new Following(1L, 2L, new Timestamp(System.currentTimeMillis()));
    }

    @After
    public void tearDown() {
        following = null;
    }

    @Test
    public void testConstructor() {
        following = new Following();
        assertNotNull(following);
    }

    @Test
    public void testConstructorParameters() {
        following = new Following(1L, 2L, new Timestamp(System.currentTimeMillis()));
        assertNotNull(following);
    }

    @Test
    public void getUser_id_1() {
        assertEquals((Long)1L, following.getUser_id_1());
    }

    @Test
    public void setUser_id_1() {
        following.setUser_id_1(2L);
        assertEquals((Long)2L, following.getUser_id_1());
    }

    @Test
    public void getUser_id_2() {
        assertEquals((Long)2L, following.getUser_id_2());
    }

    @Test
    public void setUser_id_2() {
        following.setUser_id_2(3L);
        assertEquals((Long)3L, following.getUser_id_2());
    }

    @Test
    public void getLast_update() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        following = new Following(1L, 2L, timestamp);
        assertEquals(timestamp, following.getLast_update());
    }

    @Test
    public void setLast_update() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        following.setLast_update(timestamp);
        assertEquals(timestamp, following.getLast_update());
    }

    @Test
    public void getId() {
        assertNull(following.getId());
    }

    @Test
    public void setId() {
        following.setId(2L);
        assertEquals((Long)2L, following.getId());
    }

    @Test
    public void equalsSameObject() {
        assertEquals(following, following);
    }

    @Test
    public void equalsNullObject() {
        assertNotEquals(following, null);
    }

    @Test
    public void equalsDifferentClass() {
        assertNotEquals(following, new String());
    }

    @Test
    public void equalsDifferentId() {
        Following following2 = new Following(1L, 2L, new Timestamp(System.currentTimeMillis()));
        following2.setId(2L);
        assertNotEquals(following, following2);
    }

    @Test
    public void equalsTrue() {
        Following following2 = new Following(1L, 2L, new Timestamp(System.currentTimeMillis()));
        assertEquals(following, following2);
    }
}
