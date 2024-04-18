package ee.ivkhk.JKTV22WebLibrary.entity;


import jakarta.persistence.*;

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
    @OneToMany
    private List<Author>  authors;

    public Book() {
    }

    public Book(String title, Integer publishedYear, Integer quantity, Integer count, List<Author> authors) {
        this.title = title;
        this.publishedYear = publishedYear;
        this.quantity = quantity;
        this.count = count;
        this.authors = authors;
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

    public List<Author> getAuthor() {
        return authors;
    }

    public void setAuthor(List<Author> authors) {
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
