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

    public void delete(Following following) {

        followingRepository.delete(following);

    }

    public Following findById1Id2(long user_id_1, long user_id_2) {

        return followingRepository.findById1Id2(user_id_1, user_id_2);

    }

}
