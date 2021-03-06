package com.zxc.dao;

import com.zxc.model.Course;
import com.zxc.model.Course_choose;
import com.zxc.model.Course_limit;
import com.zxc.model.Institution;

import java.util.List;

public interface CourseDao {
    public List<Course> queryCourseById(int id);
   // public List<Integer> queryInsIdByCourseId(int id);   //no
   // public String selectNameByInsId(int id);   //no
    public List<Institution> queryAllIns();
    public void insertCourse(Course course);
   // public void insertInsLimit(Course_limit course_limit);    //no
    public Course queryCourseInfoById(int id);
    public List<Integer> selectCourseLimit(int classId);
    public void updateCourse(Course course);
    public int selectMaxCourseId();
    public void updateInsLimit(Course_limit course_limit);
    public void deleteInsLimit(int id);   //no
    public void deleteCourseById(int id);
    public void deleteStuByClassId(int id);
    public void deleteLimitByClassId(int id);
    public List<Course_choose> queryStuIdByCourseId(int id);
    public void updateScore(Course_choose course_choose);
    public List<Course> queryAllCourse();
   // public List<Integer> selectProIdByClassId(int classId);
    public String selectTeaNameByTeaId(int id);
    public Course selectCourseByClassId(int id);
    public void addChooseNum(int id);
    public void addCourseChoose(Course_choose course_choose);
    public List<Integer> queryCourseIdByStuId(int id);
    public void downChooseNum(int id);
    public void deleteCourseChoose(Course_choose course_choose);
    public String selectScore(Course_choose course_choose);
	public List<Course> queryCourseBybixiu(String string);
	public List<Course> queryCourseByxuanxiu(String string);
	public List<Integer> selectAllCourseById(int stuId);
	public List<Institution> queryAllByIns(int insid);
	public void insertProLimit(Course_limit course_limit);
	public void deleteProLimit(int classId);
	public List<Integer> selectProIdByClassId(int classId);
	public String selectNameByProId(int id);
	public List<Course> queryCourseByName(String courseName);
	public int queryProIdByStuId(int stuId);
	public String selectClassifyByClassId(int classId);
	public List<Course> queryCourseByItem(String courseName);
	public List<Course> queryCourseByTongShi(String string);
	public String selectInsNameByTeaId(int teaId);
	public void insertWenCourse(Course course);
	public List<Integer> queryClassByProId(int proId);
	public int queryTeaByCourse(int classId);
	public void updateCourseCheck(Course course);
	public List<Course> queryAllCourseByAdmin();
	public List<Course> queryCourseByCheck(String classCheck);
}
