package ee.ivkhk.JKTV22WebLibrary.contollers;

import ee.ivkhk.JKTV22WebLibrary.entity.*;
import ee.ivkhk.JKTV22WebLibrary.repository.AuthorRepository;
import ee.ivkhk.JKTV22WebLibrary.repository.BookRepository;
import ee.ivkhk.JKTV22WebLibrary.repository.CoverRepository;
import ee.ivkhk.JKTV22WebLibrary.repository.HistoryRepository;
import ee.ivkhk.JKTV22WebLibrary.security.MyUserDetails;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

import static org.imgscalr.Scalr.resize;

@Controller
public class BookController {
    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private CoverRepository coverRepository;
    @Value("${uploadDir}")
    private String uploadPath;
    @Value("${temporal}")
    private String temporal;

    @GetMapping("/")
    public String home(Model model) {
        return "index";
    }
    @GetMapping("/user/books")
    public String books(Model model) {
        List<Book> listBooks = (List<Book>) bookRepository.findAll();
        model.addAttribute("listBooks", listBooks);
        return "books/books";
    }

    @GetMapping("/manager/book")
    public String addBookForm(Model model) {
        List<Author> listAuthors = (List<Author>) authorRepository.findAll();
        model.addAttribute("listAuthors", listAuthors);
        return "books/addBookForm";
    }

    @PostMapping("/manager/book")
    public String addBook(
            @RequestParam String bookName,
            @RequestParam String[] bookAuthors,
            @RequestParam String publishedYear,
            @RequestParam String quantity,
            @RequestParam("file") MultipartFile file,
            Model model) {
        List<Author> authors = new ArrayList<>();
        for (String authorId : bookAuthors) {
            Optional<Author> itemAuthor = authorRepository.findById(Long.parseLong(authorId));
            itemAuthor.ifPresent(authors::add);
        }
        try {
            if (file == null) {
                model.addAttribute("info", "Книга не добавлена. Выберите файл обложки");
                return "redirect:/manager/book";
            }
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            String uuid = UUID.randomUUID().toString();
            //сохраняем в папку Windows/Temp
            String temporalFileName = temporal + File.separator + uuid + "." + file.getOriginalFilename();
            file.transferTo(new File(temporalFileName));
            File templateOriginalFuleName = new File(temporalFileName);
            //считываем
            BufferedImage originalImage = ImageIO.read(templateOriginalFuleName);
            //изменяем
            BufferedImage resizedImageFull = Scalr.resize(originalImage, Scalr.Mode.FIT_TO_WIDTH, 400);
            //изменяем имя файла и путь
            String resultFilenameFull = uploadPath + File.separator + uuid + "." + file.getOriginalFilename();
            //записываем на диск
            ImageIO.write(resizedImageFull, "jpg", new File(resultFilenameFull));
//            file.transferTo(new File(resultFilenameMin));
            //изменяем на маленький размер
            BufferedImage resizedImageMin = Scalr.resize(originalImage, Scalr.Mode.FIT_TO_WIDTH, 100);
            // получаем новый uuid
            uuid = UUID.randomUUID().toString();
            // получае имя маленького файла
            String resultFilenameMin = uploadPath + File.separator + uuid + "." + file.getOriginalFilename();
            // Сохраняем файл на диск
            ImageIO.write(resizedImageMin, "jpg", new File(resultFilenameMin));
            //file.transferTo(new File(resultFilenameMin));
            Cover cover = new Cover(file.getOriginalFilename(), resultFilenameFull, resultFilenameMin);
            coverRepository.save(cover);
            Book book = new Book(bookName, Integer.parseInt(publishedYear), Integer.parseInt(quantity), Integer.parseInt(quantity), authors, cover);
            bookRepository.save(book);
            templateOriginalFuleName.delete();
        } catch (IOException e) {
            System.out.println("Ошибка загрузки файла: " + e.getMessage());
        }
        model.addAttribute("info", "Книга добавлена");
        return books(model);

    }


    @GetMapping("/img/{id}/{size}")
    @ResponseBody
    public byte[] insertCover(@PathVariable("id") Long id, @PathVariable("size") String size) throws RuntimeException {
        Cover cover = null;
        Optional<Cover> item = coverRepository.findById(id);
        if (item.isPresent()) cover = item.get();
        File file;
        switch (size) {
            case "full":
                file = new File(cover.getPathToFullCover());
                break;
            case "min":
                file = new File(cover.getPathToMinCover());
                break;
            default:
                file = new File(cover.getPathToMinCover());
        }

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

    @GetMapping("/user/book/{id}")
    public String showBook(@PathVariable("id") Long id, Model model,@AuthenticationPrincipal MyUserDetails myUserDetais) {
        Optional<Book> item = bookRepository.findById(id);
        if(item.isPresent()){
            model.addAttribute("book",item.get());
            List<History> readedBookHistories = historyRepository.fingHisoryByBook(item.get(),myUserDetais.getMyUser());
            if(readedBookHistories.size() > 0){
                model.addAttribute("reading",false);
            }else{
                model.addAttribute("reading",true);
            }
        }else{
            model.addAttribute("info", "Не выбрана книга");
            return "redirect:/";
        }
        return "books/book";
    }
    @GetMapping("/user/book/read/{id}")
    public String readBook(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal MyUserDetails userDetais) {
        Optional<Book> item = bookRepository.findById(id);
        if(item.isPresent()){
            History history = new History();
            Book book = item.get();
            history.setBook(book);
            MyUser myUser = userDetais.getMyUser();
            history.setMyUser(myUser);
            history.setTakeOnDate(LocalDate.now());
            historyRepository.save(history);
            model.addAttribute("book",item.get());
        }else{
            model.addAttribute("info", "Не выбрана книга");
            return "redirect:/";
        }
        return "redirect:/user/books";
    }
}