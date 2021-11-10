package projekti;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message extends AbstractPersistable<Long> {

    @ManyToOne
    private Account user;
    private String content;
    private LocalDateTime time;
    @ManyToMany
    private List<Account> likes;    
    @OneToMany
    private List<Comment> comments;
    @OneToMany
    private List<Comment> tenLatestCommentsToShow;

}
