package ee.ivkhk.JKTV22WebLibrary.contollers;

import ee.ivkhk.JKTV22WebLibrary.entity.Author;
import ee.ivkhk.JKTV22WebLibrary.entity.Book;
import ee.ivkhk.JKTV22WebLibrary.repository.AuthorRepository;
import ee.ivkhk.JKTV22WebLibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class BookController {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @GetMapping("/")
    public String home(Model model){
        List<Book> listBooks = (List<Book>) bookRepository.findAll();
        model.addAttribute("listBooks",listBooks);
        return "index";
    }
    @GetMapping("/book")
    public String book(Model model){
        List<Author> listAuthors = (List<Author>) authorRepository.findAll();
        model.addAttribute("listAuthors",listAuthors);
        return "addBook";
    }
    @PostMapping("/book")
    public String addBook(
            @RequestParam String bookName,
            @RequestParam String[] bookAuthors,
            @RequestParam String publishedYear,
            @RequestParam String quantity,
            Model model){
        List<Author> authors = new ArrayList<>();
        for (String authorId : bookAuthors){
            Optional<Author> itemAuthor = authorRepository.findById(Long.parseLong(authorId));
            itemAuthor.ifPresent(authors::add);
        }
        Book book = new Book(bookName,Integer.parseInt(publishedYear), Integer.parseInt(quantity),Integer.parseInt(quantity),authors);
        bookRepository.save(book);
        model.addAttribute("info","Книга добавлена");
        return "home";

    }
//    @GetMapping("/book")
//    public String getBook(Model model){
//        model.addAttribute("title","Открыта книга");
//        model.addAttribute("caption","Отцы и дети");
//        model.addAttribute("result", "Статус: "+HttpStatus.OK);
//        return "home";
//    }
}
