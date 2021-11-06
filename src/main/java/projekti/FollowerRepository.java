package projekti;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowerRepository extends JpaRepository<Follower, Long> {
    List<Follower> findByTheOneBeingFollowed(Account account);
    List<Follower> findByTheOneWhoFollows(Account account);
}
