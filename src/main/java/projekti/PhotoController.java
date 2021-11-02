package projekti;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PhotoController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @GetMapping("/photos")
    public String home(Model model) {
        Account account = getCurrentUser();
        model.addAttribute("photos", account.getPhotos());
        model.addAttribute("count", account.getPhotos().size());
        model.addAttribute("currentUser", getCurrentUser());
        return "photos";
    }

    @PostMapping("/photos")
    public String save(@RequestParam("file") MultipartFile file, @RequestParam String description) throws IOException {
        Account account = getCurrentUser();

        if (account.getPhotos().size() < 10) {
            if (file.getContentType().equals("image/gif") || file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/jpg") || file.getContentType().equals("image/png")) {
                Photo photo = new Photo(file.getBytes(), description, account);
                photoRepository.save(photo);
                List<Photo> photos = account.getPhotos();
                photos.add(photo);
                account.setPhotos(photos);
                accountRepository.save(account);
            }
        }
        return "redirect:/photos";
    }
    
    @PostMapping("/photos/profilePicture/{id}")
    public String addProfilePicture(@PathVariable Long id) {
        Account account = getCurrentUser();
        Photo photo = photoRepository.getOne(id);

        account.setProfilePicture(photo);
        accountRepository.save(account);
        return "redirect:/photos";
    }
    
    @GetMapping(path = "/photos/{id}", produces = "image/*")
    @ResponseBody
    public byte[] getContent(@PathVariable Long id) {
        return photoRepository.getOne(id).getContent();
    }
    
    public Account getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Account account = accountRepository.findByUsername(username);
        return account;
    }
}