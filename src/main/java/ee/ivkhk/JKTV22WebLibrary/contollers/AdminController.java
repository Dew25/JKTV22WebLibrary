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
public class AdminController {
    public static enum Roles {ADMIN,MANAGER,USER};
    @Autowired
    private MyUserService myUserService;
    @GetMapping("/admin/panel")
    public String showAdminPanelForm(Model model){
        model.addAttribute("listUsers",myUserService.allUsers());
        model.addAttribute("roles",Roles.values());
        return "administrators/adminPanel";
    }

    @PostMapping("/admin/addOrRemoveRole")
    public String edotUserRole(
            Model model,
            @RequestParam(defaultValue = "") String userId,
            @RequestParam(defaultValue = "") String roleName,
            @RequestParam String btnChange

    ){
        if(userId == null || userId.isEmpty() || roleName == null || roleName.isEmpty()){
            model.addAttribute("info","Выберите пользователя и роль");
            return  showAdminPanelForm(model);
        }
        MyUser myUser = myUserService.findById(Long.parseLong(userId));
        if(!myUser.getRoles().contains(roleName) && btnChange.equals("add")){
            myUser.getRoles().add(roleName);
            model.addAttribute("info","Пользователю "+myUser.getUsername()+" добавлена роль "+roleName);
        }else if(myUser.getRoles().contains(roleName) && btnChange.equals("delete")){
            myUser.getRoles().remove(roleName);
            model.addAttribute("info","Пользователю "+myUser.getUsername()+" удалена роль "+roleName);
        }else{
            model.addAttribute("info","Изменения нет");
        }
        myUserService.save(myUser);
        //showAdminPanelForm(model);
        return  showAdminPanelForm(model);
    }
}
