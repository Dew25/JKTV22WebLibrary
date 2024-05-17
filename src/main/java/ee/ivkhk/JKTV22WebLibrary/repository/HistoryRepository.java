package ee.ivkhk.JKTV22WebLibrary.repository;


import ee.ivkhk.JKTV22WebLibrary.entity.Book;
import ee.ivkhk.JKTV22WebLibrary.entity.History;
import ee.ivkhk.JKTV22WebLibrary.entity.MyUser;
import ee.ivkhk.JKTV22WebLibrary.entity.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HistoryRepository extends JpaRepository<History,Long> {
    @Query("SELECT h FROM History h WHERE h.book = :book AND h.returnDate is null AND h.myUser = :myUser")
    public List<History> fingHistoryByBook(Book book, MyUser myUser);

    Optional<History> findHistoryByBookAndMyUserAndReturnDateIsNull(Book book, MyUser myUser);
}
