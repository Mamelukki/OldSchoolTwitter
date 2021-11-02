package projekti;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @GetMapping("/accounts")
    public String list(Model model) {
        model.addAttribute("accounts", accountRepository.findAll());
        return "accounts";
    }

    @GetMapping("/accounts/{profileUrl}")
    public String viewProfile(Model model, @PathVariable String profileUrl) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Account account = accountRepository.findByUsername(username);
        List<Message> messages = account.getMessages();
        Collections.sort(messages, (message1, message2) -> {
            if (message1.getTime().isBefore(message2.getTime())) {
                return 1;
            } else {
                return -1;
            }
        });
        model.addAttribute("account", account);
        model.addAttribute("messages", messages);
        model.addAttribute("currentUser", account);

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

    /*
    @PostMapping("/accounts/{id}")
    public String addNewAccountToFollow(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        Account account = accountRepository.findByUsername(username);
        System.out.println(account);
        Account accountToFollow = accountRepository.getOne(id);
        System.out.println(accountToFollow);
        
        account.setFollowing(account.getFollowing());
        List<Account> following = account.getFollowing();
        following.add(accountToFollow);
        account.setFollowing(following);
        System.out.println(account.getFollowing());
        return "redirect:/accounts/";
    }
     */
}
