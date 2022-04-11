package persistence.mapper;

import org.apache.ibatis.annotations.*;
import persistence.DTO.ProfessorDTO;

import java.util.List;

public interface ProfessorMapper {
    @Select("SELECT * FROM PROFESSOR")
    @Results(id="professorResultSet",value={
            @Result(property = "pronum",column = "pro_num"),
            @Result(property = "proid",column = "pro_id"),
            @Result(property = "propass",column = "pro_pass"),
            @Result(property = "proname",column = "pro_name"),
            @Result(property = "proage",column = "pro_age"),
            @Result(property = "fmanid",column = "man_id"),
            @Result(property = "progender",column = "pro_gender"),
            @Result(property = "prophonenumber",column = "pro_phonenumber")
    })
    List<ProfessorDTO> getAll();

    @Select("SELECT COUNT(*) FROM PROFESSOR WHERE pro_id=#{id} AND pro_pass=#{password}")
    String check_by_pro_id_and_password(@Param("id") String id, @Param("password") String password);

    @Select("SELECT * FROM PROFESSOR WHERE pro_id like #{proid}")
    @ResultMap("professorResultSet")
    ProfessorDTO select_one_pro(String proid);

    // 관리자 관련 부분 = 제작 허재훈
    @SelectProvider(type = ProfessorSQL.class, method = "p_select_id_condition")
    @ResultMap("professorResultSet")
    ProfessorDTO select_professor_id(ProfessorDTO professorDTO);

    @SelectProvider(type = ProfessorSQL.class, method = "p_select_num_condition")
    @ResultMap("professorResultSet")
    ProfessorDTO select_professor_num(ProfessorDTO professorDTO);

    @UpdateProvider(type = ProfessorSQL.class, method = "p_update_condition")
    void update_professor(ProfessorDTO professorDTO);

    @SelectProvider(type = ProfessorSQL.class, method = "p_select_all_condition")
    @ResultMap("professorResultSet")
    ProfessorDTO select_professor_all(ProfessorDTO professorDTO);
}
