package ee.ivkhk.JKTV22WebLibrary.contollers;


import ee.ivkhk.JKTV22WebLibrary.entity.MyUser;
import ee.ivkhk.JKTV22WebLibrary.entity.Reader;
import ee.ivkhk.JKTV22WebLibrary.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MyUserController {

    @Autowired
    private MyUserService myUserService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("/registration")
    public String showRegistrationForm(Model model){
        model.addAttribute("info","Hello User JKTV22");
        return "registration";
    }

    @PostMapping("/registration")
    public String addNewUser(
            Model model,
            @RequestParam(name = "login") String username,
            @RequestParam String password,
            @RequestParam String firstname,
            @RequestParam String lastname,
            @RequestParam String phone
    ){
        MyUser myUser = new MyUser();
        myUser.setUsername(username);
        myUser.setPassword(passwordEncoder.encode(password));
        myUser.getRoles().add("USER");
        Reader reader = new Reader();
        reader.setFirstname(firstname);
        reader.setLastname(lastname);
        reader.setPhone(phone);
        myUser.setReader(reader);
        myUserService.save(myUser);
        model.addAttribute("info","Добавлен пользователь "+myUser.getUsername());
        return "redirect:/";
    }
    @GetMapping("/login")
    public String loginForm(Model model){
        return "login";
    }
}
