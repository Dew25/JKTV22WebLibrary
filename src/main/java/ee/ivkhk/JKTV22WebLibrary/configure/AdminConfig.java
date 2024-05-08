package ee.ivkhk.JKTV22WebLibrary.configure;


import ee.ivkhk.JKTV22WebLibrary.entity.MyUser;
import ee.ivkhk.JKTV22WebLibrary.entity.Reader;
import ee.ivkhk.JKTV22WebLibrary.service.MyUserService;
import ee.ivkhk.JKTV22WebLibrary.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminConfig {
    @Autowired
    private MyUserService myUserService;
    @Autowired
    private ReaderService readerService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Bean
    public CommandLineRunner insertSuperUser() {
        return args -> {
            if (!(myUserService.allUsers().size() > 0)) {
                MyUser admin = new MyUser();
                admin.getRoles().add("ADMIN");
                admin.getRoles().add("MANAGER");
                admin.getRoles().add("USER");
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("12345"));
                Reader reader = new Reader();
                reader.setFirstname("Juri");
                reader.setLastname("Melnikov");
                reader.setPhone("5654565456");
                admin.setReader(reader);
                myUserService.save(admin);
            }
        };
    }
}
