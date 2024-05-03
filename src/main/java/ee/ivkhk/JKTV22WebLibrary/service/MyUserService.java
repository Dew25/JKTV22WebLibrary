package ee.ivkhk.JKTV22WebLibrary.service;


import ee.ivkhk.JKTV22WebLibrary.entity.MyUser;
import ee.ivkhk.JKTV22WebLibrary.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyUserService {
    @Autowired
    private PasswordEncoder passwordEcoder;

    @Autowired
    private MyUserRepository myUserRepository;
    public void registration(MyUser myUser){
        myUser.setPassword(passwordEcoder.encode(myUser.getPassword()));
        myUserRepository.save(myUser);
    }

    public List<MyUser> allUsers(){
        return myUserRepository.findAll();
    }
    public void save(MyUser myUser){
        myUserRepository.save(myUser);
    }
}
