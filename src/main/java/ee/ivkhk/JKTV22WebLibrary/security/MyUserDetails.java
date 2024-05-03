package ee.ivkhk.JKTV22WebLibrary.security;


import ee.ivkhk.JKTV22WebLibrary.entity.MyUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class MyUserDetails implements UserDetails {
    private MyUser myUser;

    public MyUserDetails(MyUser myUser) {
        this.myUser = myUser;
    }

    // Здесь хранятся роли для авторизации
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return myUser
                .getRoles()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {

        return myUser.getPassword();
    }

    @Override
    public String getUsername() {
        return myUser.getUsername();
    }
    //если аккаунд не истек - true
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    //если аккаунд не заблокирован - true
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    // и т.д.
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
