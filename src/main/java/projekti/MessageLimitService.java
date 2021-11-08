package projekti;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// There should be a better way to do this but it will do for now because of the time limit

@Service
public class MessageLimitService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CurrentUserService currentUserService;

    @Autowired
    private FollowerRepository followerRepository;

    public List<Message> getLast25Messages(Account account, List<Follower> following) {
        List<Message> messages = account.getMessages();

        // Find the messages of the people who the user is following and add them to the list of messages
        getMessagesFromTheOnesBeingFollowed(following, messages);
        // Sort messages by sending time
        sortBySendTime(messages);

        // Add max 25 latest messages to list
        List<Message> messagesToShow = new ArrayList<>();
        if (messages != null) {
            for (int i = 0; i < messages.size(); i++) {
                if (messagesToShow.size() == 25) {
                    break;
                }
                messagesToShow.add(messages.get(i));
            }
        }
        return messagesToShow;
    }
    
    private List<Message> sortBySendTime(List<Message> messages) {
        if (messages != null) {
            Collections.sort(messages, (message1, message2) -> {
                if (message1.getTime().isBefore(message2.getTime())) {
                    return 1;
                } else {
                    return -1;
                }
            });
        }
        return messages;
    }
    
    private List<Message> getMessagesFromTheOnesBeingFollowed(List<Follower> following, List<Message> messages) {
        for (Follower follower : following) {
            if (follower.getAcceptedAsFollower() == true) {
                for (Message message : follower.getTheOneBeingFollowed().getMessages()) {
                    messages.add(message);
                }
            }
        }

        return messages;
    }

}
