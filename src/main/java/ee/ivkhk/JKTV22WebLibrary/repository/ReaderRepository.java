package ee.ivkhk.JKTV22WebLibrary.repository;



import ee.ivkhk.JKTV22WebLibrary.entity.Reader;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface ReaderRepository extends JpaRepository<Reader,Long> {

}
