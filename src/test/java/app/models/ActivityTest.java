package app.models;


import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Date;

public class ActivityTest {

    @Test
    public void setConstructor() {
        Activity a = new Activity();

        Date now = new Date();
        Timestamp timestamp = new Timestamp(now.getTime());

        a.setId(0);
        a.setUser_id(11);
        a.setCategory_id(22);
        a.setAmount(33.44);
        a.setXp_points(55.66);
        a.setLast_update(timestamp);

        Assert.assertEquals(0, a.getId());
        Assert.assertEquals(11, a.getUser_id());
        Assert.assertEquals(22, a.getCategory_id());
        Assert.assertEquals(33.44, a.getAmount(), 0.001);
        Assert.assertEquals(55.66, a.getXp_points(), 0.001);
        Assert.assertEquals(timestamp, a.getLast_update());
    }

}