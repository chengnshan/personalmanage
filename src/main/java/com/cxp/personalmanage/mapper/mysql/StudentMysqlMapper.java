package com.cxp.personalmanage.mapper.mysql;

import com.cxp.personalmanage.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

//@Mapper
public interface StudentMysqlMapper {
    @Select("SELECT id,student_id,\"name\",class_id from students")
    List<Student> findAll();

    List<Student> findStudentByCondition(Map<String,Object> param);
}
