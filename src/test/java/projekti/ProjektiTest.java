package projekti;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjektiTest {


    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private AccountRepository accountRepository;

    // ... testit jne
    @Test
    public void noTests() {
        // projektia varten ei ole automaattisia testejä
    }
}
