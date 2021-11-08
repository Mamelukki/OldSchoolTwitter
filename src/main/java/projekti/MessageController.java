package projekti;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private CurrentUserService currentUserService;

    @GetMapping("/messages")
    public String view(Model model) {
        Account account = currentUserService.getCurrentUser();

        List<Comment> comments = commentRepository.findAll();
        List<Comment> commentsByUser = new ArrayList();
        for (Comment comment : comments) {
            if (comment.getUser().equals(account)) {
                commentsByUser.add(comment);
            }
        }

        model.addAttribute("messages", messageRepository.findAll());
        model.addAttribute("comments", commentsByUser);
        return "messages";
    }

    @PostMapping("/accounts/{profileUrl}/messages")
    public String add(@PathVariable String profileUrl, @RequestParam String content) {
        Account account = currentUserService.getCurrentUser();
        Account visitedAccount = accountRepository.findByProfileUrl(profileUrl);
        
        if (content != null && !content.trim().isEmpty()) {
            Message message = new Message();
            message.setContent(content.trim());
            message.setTime(LocalDateTime.now());
            message.setUser(account);
            List<Message> messages = visitedAccount.getMessages();
            messages.add(message);

            accountRepository.save(account);
            accountRepository.save(visitedAccount);
            messageRepository.save(message);
        }

        return "redirect:/accounts/" + visitedAccount.getProfileUrl();
    }

    @PostMapping("/accounts/{profileUrl}/messages/{id}/like")
    public String addLike(@PathVariable String profileUrl, @PathVariable Long id) {
        Message message = messageRepository.getOne(id);
        Account account = currentUserService.getCurrentUser();
        Account visitedAccount = accountRepository.findByProfileUrl(profileUrl);

        List<Account> likes = message.getLikes();

        if (!likes.contains(account)) {
            likes.add(account);
            messageRepository.save(message);
        }

        return "redirect:/accounts/" + visitedAccount.getProfileUrl();
    }
}
