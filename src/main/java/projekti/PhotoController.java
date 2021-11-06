package projekti;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @Autowired
    private CurrentUserService currentUserService;

    @GetMapping("/photos")
    public String home(Model model) {
        Account account = currentUserService.getCurrentUser();
        /*
        model.addAttribute("photos", account.getPhotos());
        model.addAttribute("count", account.getPhotos().size());
         */
        model.addAttribute("currentUser", account);

        return "photos";
    }

    @PostMapping("/photos")
    public String save(@RequestParam("file") MultipartFile file, @RequestParam String description) throws IOException {
        Account account = currentUserService.getCurrentUser();

        if (account.getPhotos().size() < 10) {
            if (file.getContentType().equals("image/gif") || file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/jpg") || file.getContentType().equals("image/png")) {
                Photo photo = new Photo(file.getBytes(), description, account, new ArrayList<>(), new ArrayList<>());
                photoRepository.save(photo);
                List<Photo> photos = account.getPhotos();
                photos.add(photo);
                accountRepository.save(account);
            }
        }
        return "redirect:/photos";
    }

    @PostMapping("/photos/profilePicture/{id}")
    public String addProfilePicture(@PathVariable Long id) {
        Account account = currentUserService.getCurrentUser();
        Photo photo = photoRepository.getOne(id);

        account.setProfilePicture(photo);
        accountRepository.save(account);
        return "redirect:/photos";
    }

    @DeleteMapping("/photos/{id}/delete")
    public String deletePicture(@PathVariable Long id) {
        Account account = currentUserService.getCurrentUser();
        Photo photo = photoRepository.getOne(id);

        if (account.getProfilePicture().equals(photo)) {
            account.setProfilePicture(null);
        }

        List<Photo> photos = account.getPhotos();
        photos.remove(photo);
        accountRepository.save(account);
        photoRepository.delete(photo);
        return "redirect:/photos";
    }

    @PostMapping("/photos/{id}/like")
    public String addLike(@PathVariable Long id) {
        Account account = currentUserService.getCurrentUser();
        Photo photo = photoRepository.getOne(id);

        List<Account> likes = photo.getLikes();

        if (!likes.contains(account)) {
            likes.add(account);
            photoRepository.save(photo);
        }

        return "redirect:/photos";
    }

    @GetMapping(path = "/photos/{id}", produces = "image/*")
    @ResponseBody
    public byte[] getContent(@PathVariable Long id) {
        return photoRepository.getOne(id).getContent();
    }
}
