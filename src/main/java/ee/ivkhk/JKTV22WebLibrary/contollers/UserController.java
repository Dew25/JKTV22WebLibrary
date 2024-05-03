package ee.ivkhk.JKTV22WebLibrary.contollers;


import ee.ivkhk.JKTV22WebLibrary.entity.MyUser;
import ee.ivkhk.JKTV22WebLibrary.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

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
            @RequestParam(name = "username") String username,
            @RequestParam String password){
        MyUser myUser = new MyUser();
        myUser.setUsername(username);
        myUser.setPassword(passwordEncoder.encode(password));
        myUser.getRoles().add("USER");
        myUserService.save(myUser);
        model.addAttribute("info","Добавлен пользователь "+myUser.getUsername());
        return "redirect:/";
    }
}
