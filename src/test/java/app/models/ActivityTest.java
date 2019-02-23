package app.models;


import org.junit.Assert;
import org.junit.Test;

public class ActivityTest {


    @Test
    public void getConstructor() {
        Activity a = new Activity("audi", "34.5");

        Assert.assertEquals("audi", a.getName());
        Assert.assertEquals("34.5", a.getCo2());
    }

    @Test
    public void getSetters() {
        Activity a = new Activity("audi", "34.5");

        a.setName("ford");
        a.setCo2("12.5");
        Assert.assertEquals("ford", a.getName());
        Assert.assertEquals("12.5", a.getCo2());
    }

}