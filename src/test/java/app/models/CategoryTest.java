package app.models;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.Assert;
import org.junit.Test;

public class CategoryTest {

    @Test
    public void setConstructor() {
        Category c = new Category();

        c.setName("eating");
        c.setId(0);
        c.setAmount_saved(200);
        c.setXp_points(50);
        c.setCategory_type_id(5);

        Assert.assertEquals("eating", c.getName());
        Assert.assertEquals(0, c.getId());
        Assert.assertEquals(200, c.getAmount_saved());
        Assert.assertEquals(50, c.getXp_points(), 0.001);
        Assert.assertEquals(5, c.getCategory_type_id());

    }
}
