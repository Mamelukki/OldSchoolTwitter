package projekti;

import java.time.LocalDateTime;
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
    
    @PostMapping("/accounts/{profileUrl}/messages/{id}/comment")
    public String addCommentToMessage(@PathVariable String profileUrl, @PathVariable Long id, @RequestParam String content) {
        Message message = messageRepository.getOne(id);
        Account visitedAccount = accountRepository.findByProfileUrl(profileUrl);
        Account account = currentUserService.getCurrentUser();
        
        Comment comment = new Comment();
        comment.setContent(content.trim());
        comment.setUser(account);
        comment.setTime(LocalDateTime.now());
        
        List<Comment> comments = message.getComments();
        comments.add(comment);
        
        commentRepository.save(comment);
        messageRepository.save(message);
        
        return "redirect:/accounts/" + visitedAccount.getProfileUrl();
    }
    
    @PostMapping("/accounts/{profileUrl}/photos/{id}/comment")
    public String addCommentToPhoto(@PathVariable String profileUrl, @PathVariable Long id, @RequestParam String content) {
        Photo photo = photoRepository.getOne(id);
        Account account = accountRepository.findByProfileUrl(profileUrl);
        
        Comment comment = new Comment();
        comment.setContent(content.trim());
        
        Account currentUser = currentUserService.getCurrentUser();
        
        comment.setUser(currentUser);
        List<Comment> comments = photo.getComments();
        comments.add(comment);
        
        commentRepository.save(comment);
        photoRepository.save(photo);
        
        return "redirect:/accounts/" + account.getProfileUrl() + "/photos";
    }
}
