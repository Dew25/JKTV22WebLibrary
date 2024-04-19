package ee.ivkhk.JKTV22WebLibrary.contollers;

import ee.ivkhk.JKTV22WebLibrary.entity.Author;
import ee.ivkhk.JKTV22WebLibrary.entity.Book;
import ee.ivkhk.JKTV22WebLibrary.entity.Cover;
import ee.ivkhk.JKTV22WebLibrary.repository.AuthorRepository;
import ee.ivkhk.JKTV22WebLibrary.repository.BookRepository;
import ee.ivkhk.JKTV22WebLibrary.repository.CoverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class BookController {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private CoverRepository coverRepository;
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
            @RequestParam("file") MultipartFile file,
            Model model){
        List<Author> authors = new ArrayList<>();
        for (String authorId : bookAuthors){
            Optional<Author> itemAuthor = authorRepository.findById(Long.parseLong(authorId));
            itemAuthor.ifPresent(authors::add);
        }
        Cover cover = null;
        try {
            // Сохраняем файл на диск
            String uploadDir = "D:\\UploadDir\\JKTV22WebLibrary";
            String fileName = file.getOriginalFilename();
            String filePath = uploadDir + File.separator + fileName;
            File dest = new File(filePath);
            if(dest.mkdirs()){
                System.out.println("Директория создана");
            }else{
                System.out.println("Директория существует");
            }
            file.transferTo(dest);
            cover = new Cover(fileName,filePath);
            coverRepository.save(cover);
            Book book = new Book(bookName,Integer.parseInt(publishedYear), Integer.parseInt(quantity),Integer.parseInt(quantity),authors, cover);
            bookRepository.save(book);
        } catch (IOException e) {
            System.out.println("Ошибка загрузки файла: " + e.getMessage());
        }
        model.addAttribute("info","Книга добавлена");
        return "redirect:/";

    }
    @GetMapping("/image/{id}")
    public byte[] insertCover(@PathVariable("id") Long id) throws RuntimeException {
        Cover cover = null;
        Optional<Cover> item = coverRepository.findById(id);
        if (item.isPresent()) cover = item.get();
        File file = new File(cover.getPathToCover());
        // Читаем содержимое файла в массив байт
        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
//    @GetMapping("/book")
//    public String getBook(Model model){
//        model.addAttribute("title","Открыта книга");
//        model.addAttribute("caption","Отцы и дети");
//        model.addAttribute("result", "Статус: "+HttpStatus.OK);
//        return "home";
//    }
}
