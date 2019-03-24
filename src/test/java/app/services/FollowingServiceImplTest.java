package app.services;

import app.models.Following;
import app.repository.FollowingRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FollowingServiceImplTest {

    @Autowired
    FollowingServiceImpl followingService;

    @MockBean
    FollowingRepository followingRepositoryMock;

    Following following;

    @Before
    public void setUp() {
        following  = new Following();
        following.setId(1L);
        following.setUser_id_1(1L);
        following.setUser_id_2(2L);
    }

    @Test
    public void saveFollowing() {
        Mockito.when(followingRepositoryMock.save(following)).thenReturn(following);

        this.followingService.save(following);
        Mockito.verify(followingRepositoryMock).save(following);
    }

    @Test
    public void deleteFollowing() {
        Mockito.when(followingService.findById1Id2(1L, 2L)).thenReturn(following);

        Mockito.doAnswer((i) -> {
            Assert.assertEquals(following, i.getArgument(0));
            return null;
        }).when(followingRepositoryMock).delete(following);

        this.followingService.delete(following);
    }

    @Test
    public void findById1Id2() {
        Mockito.when(followingRepositoryMock.findById1Id2(1L, 2L)).thenReturn(following);

        Assert.assertEquals(following, followingService.findById1Id2(1L, 2L));
    }

}
