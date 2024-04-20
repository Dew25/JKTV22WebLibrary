package ee.ivkhk.JKTV22WebLibrary.contollers;

import ee.ivkhk.JKTV22WebLibrary.entity.Author;
import ee.ivkhk.JKTV22WebLibrary.entity.Book;
import ee.ivkhk.JKTV22WebLibrary.entity.Cover;
import ee.ivkhk.JKTV22WebLibrary.repository.AuthorRepository;
import ee.ivkhk.JKTV22WebLibrary.repository.BookRepository;
import ee.ivkhk.JKTV22WebLibrary.repository.CoverRepository;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.imgscalr.Scalr.OP_ANTIALIAS;
import static org.imgscalr.Scalr.resize;

@Controller
public class BookController {
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
        List<Book> listBooks = (List<Book>) bookRepository.findAll();
        model.addAttribute("listBooks", listBooks);
        return "index";
    }

    @GetMapping("/book")
    public String book(Model model) {
        List<Author> listAuthors = (List<Author>) authorRepository.findAll();
        model.addAttribute("listAuthors", listAuthors);
        return "addBook";
    }

    @PostMapping("/book")
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
                return "redirect:/book";
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
        return "redirect:/";

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

    @GetMapping("/book/{id}")
    public String showBook(@PathVariable("id") Long id, Model model) {
        Optional<Book> item = bookRepository.findById(id);
        if(item.isPresent()){
            model.addAttribute("book",item.get());
        }else{
            model.addAttribute("info", "Не выбрана книга");
            return "redirect:/";
        }
        return "book";
    }
}