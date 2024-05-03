package ee.ivkhk.JKTV22WebLibrary.contollers;

import ee.ivkhk.JKTV22WebLibrary.entity.Author;
import ee.ivkhk.JKTV22WebLibrary.repository.AuthorRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorController {
    @Autowired
    AuthorRepository authorRepository;
    @GetMapping("/author")
    public String addAuthorForm(Model model){
        return "authors/authors";
    }
    @PostMapping("/author")
    public String addAuthorForm(
            @RequestParam String firstname,
            @RequestParam String lastname,
            Model model){
        Author author = new Author();
        author.setFirstname(firstname);
        author.setLastname(lastname);
        authorRepository.save(author);
        model.addAttribute("info","Автор создан");
        return "redirect:/authors/authors";
    }

}
