package com.zxc.controller.teacher;

import com.zxc.model.Student;
import com.zxc.model.Teacher;
import com.zxc.service.CourseService;
import com.zxc.service.PageService;
import com.zxc.service.UserService;
import com.zxc.service.impl.CourseServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Controller
@RequestMapping("teacher")
public class TeacherController {
    @Resource
    private UserService userService;
    @Resource
    private CourseService courseService;
    @Resource
    private PageService pageService;

    @RequestMapping("/teacherIndex")  //教师index页面
    public String studentIndex(){
        return "teacher/teacherIndex";
    }

    @RequestMapping("/teacherInfo")   //教师个人信息
    public String studentInfo(@RequestParam("teaid") int id, Model model){
        model.addAttribute("teacher",userService.getTeaInfoById(id));
        return "teacher/teacherInfo";
    }

    @RequestMapping("/editTeaPass")   //教师点击进入修改密码界面
    public String editTeaPass(){
        return "teacher/editTeaPass";
    }

    @RequestMapping("/changeTeaPass")   //响应修改密码操作
    public String changPass(@RequestParam("prepass") String prepass, @RequestParam("nowpass") String nowpass, Model model, HttpServletRequest request){
        int id=(int)request.getSession().getAttribute("teaid");
        if(userService.checkAccount(id,prepass)==0){          //查询与原密码不符，重新输入
            model.addAttribute("msg","原始密码输入错误!");
            return "teacher/editTeaPass";
        }
        else{
            Teacher teacher=new Teacher();    //修改成功
            teacher.setTeaId(id);
            teacher.setTeaPass(nowpass);
            userService.changeTeaPass(teacher);   //更新数据库
            model.addAttribute("teacher",userService.getTeaInfoById(id));
            return "teacher/teacherInfo";
        }
    }

    @RequestMapping("/courseList")    //查看所有课程列表
    public String courseList(@Param("page") int page, Model model,HttpServletRequest request){
        model.addAttribute("paging",pageService.subList(page,courseService.queryAllById((int)request.getSession().getAttribute("teaid"))));
        return "teacher/courseList";
    }

    @RequestMapping("/insertCourse")    //录入课程
    public String insertCourse(Model model){
        model.addAttribute("insList",courseService.queryAllIns());
        return "teacher/insertCourse";
    }

    @RequestMapping("/editCourse")    //修改课程
    public String editCourse(@Param("courseid") int courseid, Model model){
        model.addAttribute("courseInfo",courseService.queryInfoById(courseid));
        model.addAttribute("checkIns",courseService.selectCourseLimit(courseid));
        model.addAttribute("insList",courseService.queryAllIns());
        return "teacher/editCourse";
    }

    @RequestMapping("/insertCourseSuccess")    //插入课程成功
    public String insertCourseSuccess(@Param("content") String content,@Param("page") int page, Model model, HttpServletRequest request)throws UnsupportedEncodingException{
        String[] det= URLDecoder.decode(URLDecoder.decode(content,"utf-8"),"utf-8").split("\\|");
        //获取插入后的课程编号
        int courseId=courseService.insertCourse(det[0],det[1],(int)request.getSession().getAttribute("teaid"));
        courseService.insertInsLimit(det[2],courseId);
        model.addAttribute("paging",pageService.subList(page,courseService.queryAllById((int)request.getSession().getAttribute("teaid"))));
        return "teacher/courseList";
    }

    @RequestMapping("/updateCourseSuccess")   //修改课程成功
    public String updateCourseSuccess(@Param("content") String content,@Param("page") int page, Model model, HttpServletRequest request)throws UnsupportedEncodingException{
        String[] det= URLDecoder.decode(URLDecoder.decode(content,"utf-8"),"utf-8").split("\\|");
        System.out.println(det[0]+" "+det[1]+" "+det[2]);
        int courseId=courseService.updateCourse(det[0],det[1],(int)request.getSession().getAttribute("teaid"));
        System.out.println(det[2]);
        courseService.updateInsLimit(det[2],courseId);
        model.addAttribute("paging",pageService.subList(page,courseService.queryAllById((int)request.getSession().getAttribute("teaid"))));
        return "teacher/courseList";
    }

    @RequestMapping("/deleteCourse")   //删除课程
    public String deleteCourse(@Param("courseid") int courseid, Model model,HttpServletRequest request){
        courseService.deleteCourse(courseid);
        model.addAttribute("paging",pageService.subList(1,courseService.queryAllById((int)request.getSession().getAttribute("teaid"))));
        return "teacher/courseList";
    }

    @RequestMapping("/detailCourse")    //查看课程详情
    public String detailCourse(@Param("courseid") int courseid,@Param("page") int page, Model model,HttpServletRequest request){
        model.addAttribute("paging",pageService.subList(page,courseService.queryStuByCourseId(courseid)));
        return "teacher/courseDetail";
    }

    @RequestMapping("/updateScore")   //修改成绩
    public String updateScore(@Param("courseid") int courseid,@Param("stuId") int stuId,@Param("score") int score,@Param("page") Integer page,Model model){
        courseService.updateScore(courseid,stuId,score);
        model.addAttribute("paging",pageService.subList(page,courseService.queryStuByCourseId(courseid)));
        return "teacher/courseDetail";
    }

    @RequestMapping("/searchStu")    //根据学生id查找学生
    public String searchStu(@Param("stuid") int stuid, @Param("courseid") int courseid, Model model){
        int page=1;
        model.addAttribute("paging",pageService.subList(page,courseService.queryStuByStuId(courseid,stuid)));
        return "teacher/courseDetail";
    }

    @RequestMapping("/deleteStuCourse")   //删除学生选课
    public String deleteStuCourse(@Param("stuid") int stuid,@Param("courseid") int courseid,Model model){
        courseService.deleteCourseChoose(stuid,courseid);
        model.addAttribute("paging",pageService.subList(1,courseService.queryStuByCourseId(courseid)));
        return "teacher/courseDetail";
    }
    @RequestMapping("updateInfoById")   //修改教师个人信息
    public String updateInfoById(@RequestParam("tele") String tele,@RequestParam("address") String address,Model model,HttpServletRequest request) {
    	int id=(int)request.getSession().getAttribute("teaid");  //从当前会话获取stuid
    	 Teacher teacher=new Teacher();
    	 teacher.setTeaId(id);
    	 teacher.setTele(tele);
    	 teacher.setAddress(address);
         userService.changeTeaInfo(teacher);
         model.addAttribute("teacher",userService.getTeaInfoById(id));
         return "teacher/teacherInfo";
    	
    }
}