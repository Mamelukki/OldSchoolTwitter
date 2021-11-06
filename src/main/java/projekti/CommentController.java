package projekti;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    
    @Autowired
    private PhotoRepository photoRepository;
    
    @Autowired
    private CurrentUserService currentUserService;
    
    @PostMapping("/messages/{id}/comment")
    public String addCommentToMessage(@PathVariable Long id, @RequestParam String content) {
        Message message = messageRepository.getOne(id);
        
        Comment comment = new Comment();
        comment.setContent(content.trim());
        
        Account account = currentUserService.getCurrentUser();
        
        comment.setUser(account);
        List<Comment> comments = message.getComments();
        comments.add(comment);
        
        commentRepository.save(comment);
        messageRepository.save(message);
        
        return "redirect:/accounts/" + account.getProfileUrl();
    }
    
    @PostMapping("/photos/{id}/comment")
    public String addCommentToPhoto(@PathVariable Long id, @RequestParam String content) {
        Photo photo = photoRepository.getOne(id);
        
        Comment comment = new Comment();
        comment.setContent(content.trim());
        
        Account account = currentUserService.getCurrentUser();
        
        comment.setUser(account);
        List<Comment> comments = photo.getComments();
        comments.add(comment);
        
        commentRepository.save(comment);
        photoRepository.save(photo);
        
        return "redirect:/photos";
    }
}
