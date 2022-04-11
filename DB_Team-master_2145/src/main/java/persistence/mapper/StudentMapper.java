package persistence.mapper;
import jdk.jfr.BooleanFlag;
import org.apache.ibatis.annotations.*;
import persistence.DTO.StudentDTO;
import java.util.List;
public interface StudentMapper {

    @Select("SELECT * FROM STUDENT")
    @Results(id="StudentResultSet",value={
            @Result(property = "stunum",column = "stu_num"),
            @Result(property = "stuid",column = "stu_id"),
            @Result(property = "stupass",column = "stu_pass"),
            @Result(property = "stuname",column = "stu_name"),
            @Result(property = "stuage",column = "stu_age"),
            @Result(property = "fmanid",column = "man_id"),
            @Result(property = "stugender",column = "stu_gender"),
            @Result(property = "stugrade",column = "stu_grade")
    })
    List<StudentDTO> getallstudent();

    @Select("SELECT COUNT(*) FROM STUDENT WHERE stu_id=#{id} AND stu_pass=#{password}")
    String check_by_stu_id_and_password(@Param("id") String id,@Param("password") String password);

    @Select("SELECT * FROM STUDENT WHERE stu_id=#{id} AND stu_pass=#{password}")
    @ResultMap("StudentResultSet")
    StudentDTO getonestudent_with_id_and_password(@Param("id") String id,@Param("password") String password);

    @Select("SELECT * FROM STUDENT WHERE stu_num=#{num}")
    @ResultMap("StudentResultSet")
    StudentDTO getonestudent_with_id(@Param("num") int num);

    @Update("update student set stu_pass = #{password}  where stu_num = #{stunum}")
    void update_with_new_password(@Param("password") String password,@Param("stunum") int stunum);

    // 관리자 관련 부분 = 제작 허재훈
    @SelectProvider(type = StudentSQL.class, method = "s_select_id_condition")
    @ResultMap("StudentResultSet")
    StudentDTO select_student_id(StudentDTO studentDTO);

    @SelectProvider(type = StudentSQL.class, method = "s_select_num_condition")
    @ResultMap("StudentResultSet")
    StudentDTO select_student_num(StudentDTO studentDTO);

    @UpdateProvider(type = StudentSQL.class, method = "s_update_condition")
    void update_student(StudentDTO studentDTO);

    @SelectProvider(type = StudentSQL.class, method = "s_select_all_condition")
    @ResultMap("StudentResultSet")
    StudentDTO select_student_all(StudentDTO studentDTO);
}
