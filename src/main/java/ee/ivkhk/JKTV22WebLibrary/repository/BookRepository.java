package ee.ivkhk.JKTV22WebLibrary.repository;

import ee.ivkhk.JKTV22WebLibrary.entity.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book,Long> {
}
