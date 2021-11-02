package projekti;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    @GetMapping("/messages")
    public String view(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account account = accountRepository.findByUsername(username);

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

    @PostMapping("/messages")
    public String add(@RequestParam String content) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account account = accountRepository.findByUsername(username);
        
        if (content != null && !content.trim().isEmpty()) {
            Message message = new Message();
            message.setContent(content.trim());

            message.setUser(account);
            List<Message> messages = account.getMessages();
            messages.add(message);
            message.setTime(LocalDateTime.now());
            account.setMessages(messages);

            accountRepository.save(account);
            messageRepository.save(message);
        }

        return "redirect:/accounts/" + account.getProfileUrl();
    }

    @PostMapping("/messages/{id}/like")
    public String addLike(@PathVariable Long id) {
        Message message = messageRepository.getOne(id);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account account = accountRepository.findByUsername(username);

        List<Account> likes = message.getLikes();

        if (!likes.contains(account)) {
            likes.add(account);
            message.setLikes(likes);
            messageRepository.save(message);
        }

        return "redirect:/accounts/" + account.getProfileUrl();
    }
}
