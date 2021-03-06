package projekti;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account extends AbstractPersistable<Long> {

    @OneToMany(mappedBy = "user")
    private List<Message> messages;

    @OneToMany(mappedBy = "user")
    private List<Photo> photos;

    @NotEmpty
    @Size(min = 3, max = 30)
    private String name;
    @NotEmpty
    @Size(min = 3, max = 30)
    private String username;
    @NotEmpty
    @Size(min = 3, max = 30)
    private String profileUrl;
    @NotEmpty
    private String password;
    @OneToOne
    private Photo profilePicture;
    
}
