package projekti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private CurrentUserService currentUserService;
    
    @GetMapping("*")
    public String home(Model model) {
        Account account = currentUserService.getCurrentUser();
        model.addAttribute("currentUser", account);
        return "index";
    }
}
