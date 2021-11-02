package projekti;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommentController {
    
    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired 
    private MessageRepository messageRepository;
    
    @PostMapping("/messages/{id}/comment")
    public String addComment(@PathVariable Long id, @RequestParam String content) {
        Message message = messageRepository.getOne(id);
        
        Comment comment = new Comment();
        comment.setContent(content.trim());
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account account = accountRepository.findByUsername(username);
        
        comment.setUser(account);
        comment.setMessage(message);
        List<Comment> comments = message.getComments();
        comments.add(comment);
        message.setComments(comments);
        
        commentRepository.save(comment);
        messageRepository.save(message);
        
        return "redirect:/accounts/" + account.getProfileUrl();
    }
}
