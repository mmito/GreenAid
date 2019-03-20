package app.services;

import app.models.Following;
import app.repository.FollowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowingServiceImpl {

    @Autowired
    FollowingRepository followingRepository;

    public void save(Following following) {

        followingRepository.save(following);

    }

}
