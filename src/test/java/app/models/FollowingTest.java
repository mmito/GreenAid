package app.models;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Date;

public class FollowingTest {

    @Test
    public void setConstructor() {
        Following f = new Following();

        Date now = new Date();
        Timestamp timestamp = new Timestamp(now.getTime());

        f.setId(0L);
        f.setUser_id_1(1L);
        f.setUser_id_2(2L);
        f.setLast_update(timestamp);

        Assert.assertEquals(new Long(0), f.getId());
        Assert.assertEquals(new Long(1), f.getUser_id_1());
        Assert.assertEquals(new Long(2), f.getUser_id_2());
        Assert.assertEquals(timestamp, f.getLast_update());

    }
}
