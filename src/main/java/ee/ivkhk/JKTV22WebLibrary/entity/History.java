package ee.ivkhk.JKTV22WebLibrary.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;


@Entity
@Data
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "book_id",unique = false)
    private Book book;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "my_user_id",unique = false)
    private MyUser myUser;
    @Temporal(TemporalType.DATE)
    private LocalDate takeOnDate;
    @Temporal(TemporalType.DATE)
    private LocalDate returnDate;
}
