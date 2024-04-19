package ee.ivkhk.JKTV22WebLibrary.entity;


import jakarta.persistence.*;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Integer publishedYear;
    private Integer quantity;
    private Integer count;
    @ManyToMany
    private List<Author>  authors;
    @OneToOne
    private Cover cover;

    public Book() {
    }

    public Book(String title, Integer publishedYear, Integer quantity, Integer count, List<Author> authors, Cover cover) {
        this.title = title;
        this.publishedYear = publishedYear;
        this.quantity = quantity;
        this.count = count;
        this.authors = authors;
        this.cover = cover;
    }

//    public byte[] getCover() {
//        // Создаем объект File для файла изображения
//        File file = new File(cover.getPathToCover());
//        // Проверяем, существует ли файл
//        if (!file.exists()) {
//            try {
//                throw new IOException("Файл не найден: " + cover.getFileName());
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        // Читаем содержимое файла в массив байт
//        try (FileInputStream fis = new FileInputStream(file);
//             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
//            byte[] buffer = new byte[1024];
//            int bytesRead;
//            while ((bytesRead = fis.read(buffer)) != -1) {
//                bos.write(buffer, 0, bytesRead);
//            }
//            return bos.toByteArray();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(Integer publishedYear) {
        this.publishedYear = publishedYear;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) && Objects.equals(title, book.title) && Objects.equals(publishedYear, book.publishedYear) && Objects.equals(quantity, book.quantity) && Objects.equals(count, book.count) && Objects.equals(authors, book.authors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, publishedYear, quantity, count, authors);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", publishedYear=" + publishedYear +
                ", quantity=" + quantity +
                ", count=" + count +
                ", authors=" + Arrays.toString(authors.toArray()) +
                '}';
    }
}
