package ee.ivkhk.JKTV22WebLibrary.contollers;

import ee.ivkhk.JKTV22WebLibrary.entity.Book;
import ee.ivkhk.JKTV22WebLibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    @Autowired
    private BookRepository bookRepository;
    @GetMapping("/")
    public String home(Model model){
        return "addBook";
    }
    @PostMapping("/book")
    public String addBook(@RequestParam String caption, Model model){
        model.addAttribute("title","Добавлена кнга");
        model.addAttribute("caption", caption);
        model.addAttribute("result", "Статус: "+HttpStatus.OK);
        Book book = new Book(caption,2000,2,2,"Лев Толстой");
        bookRepository.save(book);
        return "home";

    }
    @GetMapping("/book")
    public String getBook(Model model){
        model.addAttribute("title","Открыта книга");
        model.addAttribute("caption","Отцы и дети");
        model.addAttribute("result", "Статус: "+HttpStatus.OK);
        return "home";
    }
}
