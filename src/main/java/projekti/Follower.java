package projekti;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Follower extends AbstractPersistable<Long> {
    
    @ManyToOne
    private Account theOneBeingFollowed;
    @ManyToOne
    private Account theOneWhoFollows; 
    private Boolean acceptedAsFollower;
    private LocalDateTime date;
    
}
