package ee.ivkhk.JKTV22WebLibrary.service;


import ee.ivkhk.JKTV22WebLibrary.entity.MyUser;
import ee.ivkhk.JKTV22WebLibrary.entity.Reader;
import ee.ivkhk.JKTV22WebLibrary.repository.MyUserRepository;
import ee.ivkhk.JKTV22WebLibrary.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReaderService {
    @Autowired
    private PasswordEncoder passwordEcoder;

    @Autowired
    private ReaderRepository readerRepository;

    public List<Reader> allReaders(){
        return readerRepository.findAll();
    }
    public void save(Reader reader){
        readerRepository.save(reader);
    }
}
