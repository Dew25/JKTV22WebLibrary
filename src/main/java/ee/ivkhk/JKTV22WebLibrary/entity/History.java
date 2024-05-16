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
    @OneToOne
    private Book book;
    @OneToOne
    private MyUser myUser;
    @Temporal(TemporalType.DATE)
    private LocalDate takeOnDate;
    @Temporal(TemporalType.DATE)
    private LocalDate returnDate;
}
