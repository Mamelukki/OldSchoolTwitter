package projekti;

import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Photo extends AbstractPersistable<Long> {

    
    // @Basic(fetch = FetchType.LAZY)
    @Lob
    private byte[] content;
    private String description; 
    @ManyToOne
    private Account user;
    @ManyToMany
    private List<Account> likes;  
    @OneToMany
    private List<Comment> comments;
    
}
