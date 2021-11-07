package projekti;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CurrentUserService currentUserService;

    @Autowired
    private FollowerRepository followerRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @GetMapping("/accounts")
    public String list(Model model) {
        Account account = currentUserService.getCurrentUser();
        List<Follower> followers = followerRepository.findByTheOneBeingFollowed(account);
        List<Follower> followRequests = new ArrayList<>();
        for (Follower follower : followers) {
            if (follower.getAcceptedAsFollower() == false) {
                followRequests.add(follower);
            }
        }
        model.addAttribute("currentUser", account);
        model.addAttribute("followRequests", followRequests);

        return "accounts";
    }

    @GetMapping("/accounts/{profileUrl}")
    public String viewProfile(Model model, @PathVariable String profileUrl) {
        Account account = currentUserService.getCurrentUser();

        List<Message> messages = null;

        List<Follower> followers = followerRepository.findByTheOneBeingFollowed(account);
        List<Follower> following = followerRepository.findByTheOneWhoFollows(account);

        if (account.getMessages() != null) {
            messages = account.getMessages();
            Collections.sort(messages, (message1, message2) -> {
                if (message1.getDate().isBefore(message2.getDate())) {
                    return 1;
                } else {
                    return -1;
                }
            });
        }
        List<Photo> photos = photoRepository.findByUser(account);

        model.addAttribute("account", account);
        model.addAttribute("messages", messages);
        model.addAttribute("currentUser", account);
        model.addAttribute("followers", followers);
        model.addAttribute("following", following);
        model.addAttribute("photos", photos);

        return "profile";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String add(@RequestParam String name, @RequestParam String username, @RequestParam String profileUrl, @RequestParam String password) {
        if (accountRepository.findByUsername(username) != null) {
            return "redirect:/register";
        }

        Account a = new Account(new ArrayList<>(), new ArrayList<>(), name, username, profileUrl, passwordEncoder.encode(password), null);
        accountRepository.save(a);
        return "redirect:/accounts/" + profileUrl;
    }

    @GetMapping("/accounts/search")
    public String searchUsers(Model model, @RequestParam String search) {
        List<Account> accounts = accountRepository.findAll();
        // Delete the current user from the list of accounts so that it doesn't appear in the search results
        Account currentUser = currentUserService.getCurrentUser();
        accounts.remove(currentUser);

        List<Account> matchingUsers = new ArrayList<>();

        for (Account account : accounts) {
            if (account.getName().contains(search)) {
                matchingUsers.add(account);
            }
        }

        model.addAttribute("searchResults", matchingUsers);
        model.addAttribute("currentUser", currentUser);
        return "accounts";
    }
}
