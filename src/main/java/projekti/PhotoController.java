package projekti;

import java.awt.image.BufferedImage;
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

    @GetMapping("/accounts/{profileUrl}/photos")
    public String home(Model model, @PathVariable String profileUrl) {
        Account account = accountRepository.findByProfileUrl(profileUrl);
        Account currentUser = currentUserService.getCurrentUser();
        model.addAttribute("photos", account.getPhotos());
        model.addAttribute("account", account);
        model.addAttribute("count", account.getPhotos().size());
        model.addAttribute("currentUser", currentUser);
        return "photos";
    }

    @PostMapping("accounts/{profileUrl}/photos")
    public String save(@PathVariable String profileUrl, @RequestParam("file") MultipartFile file, @RequestParam String description) throws IOException {
        Account account = accountRepository.findByProfileUrl(profileUrl);

        if (account.getPhotos().size() < 10) {
            if (file.getContentType().equals("image/gif") || file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/jpg") || file.getContentType().equals("image/png")) {
                Photo photo = new Photo(file.getBytes(), description, account, new ArrayList<>(), new ArrayList<>());
                photoRepository.save(photo);
                List<Photo> photos = account.getPhotos();
                photos.add(photo);
                accountRepository.save(account);
            }
        }
        return "redirect:/accounts/" + account.getProfileUrl() + "/photos";
    }

    @PostMapping("/accounts/{profileUrl}/photos/profilePicture/{id}")
    public String addProfilePicture(@PathVariable Long id) {
        Account account = currentUserService.getCurrentUser();
        Photo photo = photoRepository.getOne(id);

        account.setProfilePicture(photo);
        accountRepository.save(account);
        return "redirect:/accounts/" + account.getProfileUrl()  + "/photos";
    }

    @DeleteMapping("/accounts/{profileUrl}/photos/{id}/delete")
    public String deletePicture(@PathVariable String profileUrl, @PathVariable Long id) {
        Account account = currentUserService.getCurrentUser();
        Photo photo = photoRepository.getOne(id);

        if (account.getProfilePicture() != null && account.getProfilePicture().equals(photo)) {
            account.setProfilePicture(null);
        }

        List<Photo> photos = account.getPhotos();
        photos.remove(photo);
        accountRepository.save(account);
        photoRepository.delete(photo);
        return "redirect:/accounts/" + account.getProfileUrl()  + "/photos";
    }

    @PostMapping("/accounts/{profileUrl}/photos/{id}/like")
    public String addLike(@PathVariable String profileUrl, @PathVariable Long id) {
        Account currentUser = currentUserService.getCurrentUser();
        Account account = accountRepository.findByProfileUrl(profileUrl);
        
        Photo photo = photoRepository.getOne(id);

        List<Account> likes = photo.getLikes();

        if (!likes.contains(currentUser)) {
            likes.add(currentUser);
            photoRepository.save(photo);
        }

        return "redirect:/accounts/" + account.getProfileUrl() + "/photos";
    }

    @GetMapping(path = "/accounts/{profileUrl}/photos/{id}", produces = "image/*")
    @ResponseBody
    public byte[] getContent(@PathVariable Long id) {
        return photoRepository.getOne(id).getContent();
    }
}
