package app.services;


import app.models.Activity;
import app.repository.ActivityRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivityServiceImplTest {

    @Autowired
    private ActivityServiceImpl activityService;

    @MockBean
    private ActivityRepository activityRepositoryMock;

    @Test
    public void saveActivity() {

        Activity activity = new Activity();
        activity.setId(1);
        activity.setUser_id(2);

        Mockito.when(activityRepositoryMock.save(activity)).thenReturn(activity);

        this.activityService.save(activity);
        verify(activityRepositoryMock).save(activity);
    }

    @Test
    public void findActivityByUserID() {

        Activity activity_1 = new Activity();
        activity_1.setId(1);
        activity_1.setUser_id(3);

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

}
