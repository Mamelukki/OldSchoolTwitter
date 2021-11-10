package projekti;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    private MessageLimitService messageLimitService;

    @Autowired
    private PhotoRepository photoRepository;

    @GetMapping("/accounts")
    public String list(Model model) {
        return "accounts";
    }

    @GetMapping("/accounts/{profileUrl}")
    public String viewProfile(Model model, @PathVariable String profileUrl) {
        Account currentUser = currentUserService.getCurrentUser();
        Account account = accountRepository.findByProfileUrl(profileUrl);

        List<Follower> followers = followerRepository.findByTheOneBeingFollowed(account);
        List<Follower> following = followerRepository.findByTheOneWhoFollows(account);

        List<Message> messagesToShow = messageLimitService.getLatest25Messages(account, following);
        List<Photo> photos = photoRepository.findByUser(account);

        System.out.println(currentUser.getUsername());
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("account", account);
        model.addAttribute("messages", messagesToShow);
        model.addAttribute("followers", followers);
        model.addAttribute("following", following);
        model.addAttribute("photos", photos);

        return "profile";
    }

    @GetMapping("/register")
    public String register(@ModelAttribute Account account) {
        return "register";
    }

    @PostMapping("/register")
    public String add(@Valid @ModelAttribute Account account, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        if (accountRepository.findByUsername(account.getUsername()) != null) {
            return "redirect:/register";
        }

        account.setMessages(new ArrayList<>());
        account.setPhotos(new ArrayList<>());
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountRepository.save(account);
        return "redirect:/accounts/" + account.getProfileUrl();
    }

    @GetMapping("/accounts/search")
    public String searchUsers(Model model, @RequestParam String search) {
        List<Account> accounts = accountRepository.findAll();
        // Delete the current user from the list of accounts so that it doesn't appear in the search results
        Account currentUser = currentUserService.getCurrentUser();
        accounts.remove(currentUser);

        List<Account> matchingUsers = new ArrayList<>();

        for (Account account : accounts) {
            if (account.getName().toLowerCase().contains(search.toLowerCase())) {
                matchingUsers.add(account);
            }
        }
        
        model.addAttribute("searchResults", matchingUsers);
        model.addAttribute("currentUser", currentUser);
        return "accounts";
    }
}
