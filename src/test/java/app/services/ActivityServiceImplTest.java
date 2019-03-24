package app.services;

import app.models.Activity;
import app.repository.ActivityRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivityServiceImplTest {

    @Autowired
    private ActivityServiceImpl activityService;

    @MockBean
    private ActivityRepository activityRepositoryMock;

    Activity activity_1;

    @Before
    public void setUp() {
        activity_1 = new Activity();
        activity_1.setId(1);
        activity_1.setUser_id(3);
    }

    @Test
    public void saveActivity() {
        Mockito.when(activityRepositoryMock.save(activity_1)).thenReturn(activity_1);

        this.activityService.save(activity_1);
        Mockito.verify(activityRepositoryMock).save(activity_1);
    }

    @Test
    public void deleteActivity() {
        Mockito.when(activityService.findById(1)).thenReturn(activity_1);

        Mockito.doAnswer((i) -> {
            Assert.assertEquals(activity_1, i.getArgument(0));
            return null;
        }).when(activityRepositoryMock).delete(activity_1);

        this.activityService.delete(activity_1);
    }

    @Test
    public void findActivityByUserId() {
        Activity activity_2 = new Activity();
        activity_2.setId(2);
        activity_2.setUser_id(3);

        List activityList = new ArrayList();
        activityList.add(activity_1);
        activityList.add(activity_2);

        Mockito.when(activityRepositoryMock.findByUser_id(3)).thenReturn(activityList);

        List result = this.activityService.findByUser_id(3);

        Assert.assertEquals(activityList, result);
    }

    @Test
    public void findActivityById() {
        Mockito.when(activityRepositoryMock.findById(1)).thenReturn(activity_1);

        Assert.assertEquals(activity_1, activityService.findById(1));
    }
}
