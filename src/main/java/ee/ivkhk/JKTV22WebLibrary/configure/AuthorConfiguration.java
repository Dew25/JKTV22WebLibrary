package ee.ivkhk.JKTV22WebLibrary.configure;

import ee.ivkhk.JKTV22WebLibrary.entity.Author;
import ee.ivkhk.JKTV22WebLibrary.repository.AuthorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AuthorConfiguration {
//    @Bean
//    public CommandLineRunner commandLineRunner(AuthorRepository authorRepository){
//        return args ->{
//            authorRepository.saveAll(List.of(
//                  new Author("Лев", "Толстой"),
//                  new Author("Иван", "Тургенев")
//            ));
//        };
//    }
}
