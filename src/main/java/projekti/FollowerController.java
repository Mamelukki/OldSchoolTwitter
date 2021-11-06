package projekti;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FollowerController {

    @Autowired
    private FollowerRepository followerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CurrentUserService currentUserService;

    @PostMapping("/accounts/{id}/followRequest")
    public String sendFollowRequest(@PathVariable Long id) {
        Account account = currentUserService.getCurrentUser();
        Account accountToFollow = accountRepository.getOne(id);
        Follower newFollower = new Follower(accountToFollow, account, false, null);
        List<Follower> followers = followerRepository.findByTheOneBeingFollowed(accountToFollow);

        int counter = 0;
        for (Follower follower : followers) {
            if (follower.getTheOneWhoFollows().equals(account)) {
                counter++;
            }
        }

        if (counter == 0) {
            followerRepository.save(newFollower);
        }

        return "redirect:/accounts/" + account.getProfileUrl();
    }

    @PostMapping("/accounts/{id}/handleFollowRequest")
    public String handleFollowRequest(@PathVariable Long id, @RequestParam String value) {
        Account account = currentUserService.getCurrentUser();

        Follower follower = followerRepository.getOne(id);

        if (value.equals("accept")) {
            follower.setAcceptedAsFollower(true);
            follower.setDate(LocalDateTime.now());
            followerRepository.save(follower);
        } else {
            followerRepository.delete(follower);
        }

        return "redirect:/accounts/" + account.getProfileUrl();
    }

}
