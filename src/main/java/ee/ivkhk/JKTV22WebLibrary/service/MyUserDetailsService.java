package ee.ivkhk.JKTV22WebLibrary.service;


import ee.ivkhk.JKTV22WebLibrary.entity.MyUser;
import ee.ivkhk.JKTV22WebLibrary.security.MyUserDetails;
import ee.ivkhk.JKTV22WebLibrary.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private MyUserRepository myUserRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> myUser = myUserRepository.findByUsername(username);
        return myUser.map(MyUserDetails::new)
                .orElseThrow(()->new UsernameNotFoundException(username+ " not found"));
    }
}
