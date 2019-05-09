package com.zxc.controller.common;

import com.zxc.controller.log.SystemLog;
import com.zxc.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@SessionAttributes({"username","teaid","stuid"})
public class LoginController {
    @Resource
    private UserService userService;
    @RequestMapping("login")
    public String login(){
        return "login";
    }

    @RequestMapping(value = "check",method = RequestMethod.POST)    //检查登录名和密码是否正确
    @SystemLog(module="登陆",methods="检查账号密码")
    public String checkAccount(@RequestParam("userid") int id,@RequestParam("userpass") String pass,Model model) {
       
    	if (userService.checkAccount(id, pass) == 2) {      //老师账号正确
            model.addAttribute("username",userService.getTeaNameById(id));   //返回姓名和id
            model.addAttribute("teaid",id);
            return "teacher/teacherIndex";
        }
        else if(userService.checkAccount(id, pass) == 1){   //学生账号正确
            model.addAttribute("username",userService.getStuNameById(id));
            model.addAttribute("stuid",id);
            return "student/studentIndex";
        }
        else if(userService.checkAccount(id, pass) == 3) {   //管理员账号正确
        	model.addAttribute("username",userService.getAdminNameById(id));
        	System.out.println(id);
            model.addAttribute("adminid",id);
            return "admin/adminIndex";
        }
        else{                                 //账号与密码不匹配,重新登陆
        	
            model.addAttribute("msg","密码错误");
            //这里不加redirect，否则前端取不到userid值
            return "login";
        }
    }

    @RequestMapping("exit")
    public String exit(HttpServletRequest request){
        request.getSession().invalidate();   //清空当前用户的session对象
        return "login";
    }
}
