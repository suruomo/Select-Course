package com.zxc.controller.teacher;

import com.zxc.controller.common.LoginController;
import com.zxc.controller.log.SystemLog;
import com.zxc.model.Student;
import com.zxc.model.Teacher;
import com.zxc.service.CourseService;
import com.zxc.service.MessageService;
import com.zxc.service.PageService;
import com.zxc.service.UserService;
import com.zxc.service.impl.CourseServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    @Resource
    private MessageService messageService;
    @RequestMapping("/teacherIndex")  //教师index页面
    @SystemLog(module="教师模块",methods="首页")
    public String studentIndex(Model model){
    	model.addAttribute("message",messageService.queryTeaMessage());
        return "teacher/teacherIndex";
    }

    @RequestMapping("/teacherInfo")   //教师个人信息
    @SystemLog(module="教师模块",methods="个人信息页面")
    public String studentInfo(@RequestParam("teaid") int id, Model model){
        model.addAttribute("teacher",userService.getTeaInfoById(id));
        return "teacher/teacherInfo";
    }

    @RequestMapping("/editTeaPass")   //教师点击进入修改密码界面
    @SystemLog(module="教师模块",methods="修改密码页面")
    public String editTeaPass(){
        return "teacher/editTeaPass";
    }

    @RequestMapping("/changeTeaPass")   //响应修改密码操作
    @SystemLog(module="教师模块",methods="修改个人信息-数据库")
    public String changPass(@RequestParam("prepass") String prepass, @RequestParam("nowpass") String nowpass, Model model, HttpServletRequest request){
        int id=(int)request.getSession().getAttribute("id");
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
    @SystemLog(module="教师模块",methods="查看课程列表")
    public String courseList(@Param("page") int page, Model model,HttpServletRequest request){
    	model.addAttribute("teacher", userService.getTeaInfoById((int)request.getSession().getAttribute("id")));
        model.addAttribute("paging",pageService.subList(page,courseService.queryAllById((int)request.getSession().getAttribute("id"))));
        return "teacher/courseList";
    }

    @RequestMapping("/scoreList")   
    @SystemLog(module="教师模块",methods="查看课程列表")
    public String scoreList(@Param("page") int page, Model model,HttpServletRequest request){
    	model.addAttribute("teacher", userService.getTeaInfoById((int)request.getSession().getAttribute("id")));
        model.addAttribute("paging",pageService.subList(page,courseService.queryAllById((int)request.getSession().getAttribute("id"))));
        return "teacher/scoreList";
    }
    @RequestMapping("/insertCourse")    //录入非体育课程
    @SystemLog(module="教师模块",methods="录入课程页面")
    public String insertCourse(Model model){
        //model.addAttribute("insList",courseService.queryAllIns());
        model.addAttribute("jisuanjiList",courseService.queryAllproByIns(1001));
        model.addAttribute("yiList",courseService.queryAllproByIns(1002));
        model.addAttribute("guanliList",courseService.queryAllproByIns(1004));
        model.addAttribute("wenList",courseService.queryAllproByIns(1006));
        model.addAttribute("jingjiList",courseService.queryAllproByIns(1005));
        model.addAttribute("liList",courseService.queryAllproByIns(1007));
        return "teacher/insertCourse";
    }
    @RequestMapping("/insertTiCourse")    //录入体育课程
    @SystemLog(module="教师模块",methods="录入课程页面")
    public String insertTiCourse(Model model){
        //model.addAttribute("insList",courseService.queryAllIns());
        model.addAttribute("jisuanjiList",courseService.queryAllproByIns(1001));
        model.addAttribute("yiList",courseService.queryAllproByIns(1002));
        model.addAttribute("guanliList",courseService.queryAllproByIns(1004));
        model.addAttribute("wenList",courseService.queryAllproByIns(1006));
        model.addAttribute("jingjiList",courseService.queryAllproByIns(1005));
        model.addAttribute("liList",courseService.queryAllproByIns(1007));
        return "teacher/insertTiCourse";
    }

    @RequestMapping("/editCourse")    //修改课程
    @SystemLog(module="教师模块",methods="修改课程页面")
    public String editCourse(@Param("courseid") int courseid, Model model){
        model.addAttribute("courseInfo",courseService.queryInfoById(courseid));
        //model.addAttribute("checkIns",courseService.selectCourseLimit(courseid));
        model.addAttribute("checkpro",courseService.selectCourseLimit(courseid));
        model.addAttribute("jisuanjiList",courseService.queryAllproByIns(1001));
        model.addAttribute("yiList",courseService.queryAllproByIns(1002));
        model.addAttribute("guanliList",courseService.queryAllproByIns(1004));
        model.addAttribute("wenList",courseService.queryAllproByIns(1006));
        model.addAttribute("jingjiList",courseService.queryAllproByIns(1005));
        model.addAttribute("liList",courseService.queryAllproByIns(1007));
        return "teacher/editCourse";
    }

    @RequestMapping("/insertTiCourseSuccess")    //插入体育课程成功
    @SystemLog(module="教师模块",methods="录入课程-数据库")
    public String insertTiCourseSuccess(@Param("content") String content,@Param("page") int page, Model model, HttpServletRequest request)throws UnsupportedEncodingException{
    	System.out.println(content);
    	String[] det= URLDecoder.decode(URLDecoder.decode(content,"utf-8"),"utf-8").split("\\|");
        //获取插入后的课程编号
    	for(int i=0;i<det.length;i++) {
    		System.out.println(det[i]);
    	}
        int courseId=courseService.insertCourse(det[0],det[1],det[3],det[4],det[5],det[6],det[7],det[8],det[9],"待审核",(int)request.getSession().getAttribute("id"));
        courseService.insertProLimit(det[2],courseId);
        model.addAttribute("teacher", userService.getTeaInfoById((int)request.getSession().getAttribute("id")));
        model.addAttribute("paging",pageService.subList(page,courseService.queryAllById((int)request.getSession().getAttribute("id"))));
        return "teacher/courseList";
    }
    @RequestMapping("/insertCourseSuccess")    //插入文化课程成功
    @SystemLog(module="教师模块",methods="录入课程-数据库")
    public String insertCourseSuccess(@Param("content") String content,@Param("page") int page, Model model, HttpServletRequest request)throws UnsupportedEncodingException{
    	System.out.println(content);
    	String[] det= URLDecoder.decode(URLDecoder.decode(content,"utf-8"),"utf-8").split("\\|");
        //获取插入后的课程编号
    	for(int i=0;i<det.length;i++) {
    		System.out.println(det[i]);
    	}
        int courseId=courseService.insertWenCourse(det[0],det[1],det[3],det[4],det[5],det[6],det[7],det[8],"待审核",(int)request.getSession().getAttribute("id"));
        courseService.insertProLimit(det[2],courseId);
        model.addAttribute("teacher", userService.getTeaInfoById((int)request.getSession().getAttribute("id")));
        model.addAttribute("paging",pageService.subList(page,courseService.queryAllById((int)request.getSession().getAttribute("id"))));
        return "teacher/courseList";
    }

    @RequestMapping("/updateCourseSuccess")   //修改课程成功
    @SystemLog(module="教师模块",methods="修改课程-数据库")
    public String updateCourseSuccess(@Param("content") String content,@Param("page") int page, Model model, HttpServletRequest request)throws UnsupportedEncodingException{
        String[] det= URLDecoder.decode(URLDecoder.decode(content,"utf-8"),"utf-8").split("\\|");
        for(int i=0;i<det.length;i++) {
        	System.out.println(det[i]);
        }
        int courseId=courseService.updateCourse(Integer.parseInt(det[0]),det[1],det[3],det[4],det[5],det[6],det[7],det[8],det[9],det[10],"待审核",(int)request.getSession().getAttribute("id"));
        System.out.println(det[2]);
        courseService.updateProLimit(det[2],courseId);
        model.addAttribute("teacher", userService.getTeaInfoById((int)request.getSession().getAttribute("id")));
        model.addAttribute("paging",pageService.subList(page,courseService.queryAllById((int)request.getSession().getAttribute("id"))));
        return "teacher/courseList";
    }

    @RequestMapping("/deleteCourse")   //删除课程
    @SystemLog(module="教师模块",methods="删除课程-数据库")
    public String deleteCourse(@Param("courseid") int courseid, Model model,HttpServletRequest request){
        courseService.deleteCourse(courseid);
        model.addAttribute("paging",pageService.subList(1,courseService.queryAllById((int)request.getSession().getAttribute("id"))));
        model.addAttribute("teacher", userService.getTeaInfoById((int)request.getSession().getAttribute("id")));
        return "teacher/courseList";
    }

    @RequestMapping("/detailCourse")    //查看课程详情
    public String detailCourse(@Param("courseid") int courseid,@Param("page") int page, Model model,HttpServletRequest request){
        model.addAttribute("paging",pageService.subList(page,courseService.queryStuByCourseId(courseid)));
        return "teacher/courseDetail";
    }

    @RequestMapping("/updateScore")   //修改成绩
    @SystemLog(module="教师模块",methods="录入成绩-数据库")
    public String updateScore(@Param("courseid") int courseid,@Param("stuId") int stuId,@Param("score") String score,@Param("page") Integer page,Model model){
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
    @SystemLog(module="教师模块",methods="删除选课-数据库")
    public String deleteStuCourse(@Param("stuid") int stuid,@Param("courseid") int courseid,Model model){
        courseService.deleteCourseChoose(stuid,courseid);
        model.addAttribute("paging",pageService.subList(1,courseService.queryStuByCourseId(courseid)));
        return "teacher/courseDetail";
    }
    @RequestMapping("updateInfoById")   //修改教师个人信息
    @SystemLog(module="教师模块",methods="修改个人信息-数据库")
    public String updateInfoById(@RequestParam("tele") String tele,@RequestParam("address") String address,Model model,HttpServletRequest request) {
    	int id=(int)request.getSession().getAttribute("id");  //从当前会话获取stuid
    	 Teacher teacher=new Teacher();
    	 teacher.setTeaId(id);
    	 teacher.setTele(tele);
    	 teacher.setAddress(address);
         userService.changeTeaInfo(teacher);
         model.addAttribute("teacher",userService.getTeaInfoById(id));
         return "teacher/teacherInfo";
    	
    }
}
