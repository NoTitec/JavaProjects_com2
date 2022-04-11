package persistence.mapper;

import org.apache.ibatis.annotations.*;
import persistence.DTO.SubjectDTO;

import java.util.List;

public interface SubjectMapper {

    @Select("SELECT * FROM SUBJECT")
    @Results(id="SubjectResultSet", value={
            @Result(property = "subcode", column = "sub_code"),
            @Result(property = "subname", column = "sub_name"),
            @Result(property = "subinfo", column = "sub_info"),
            @Result(property = "subgrade", column = "sub_grade"),
            @Result(property = "fmanid", column = "man_id")
    })
    List<SubjectDTO> getallsubject();

    // 관리자 관련 부분 = 제작 허재훈
    // 생성 요청에서 code 존재 확인할 때, 수정할 튜플 위치, 삭제할 튜플 위치
    @SelectProvider(type = SubjectSQL.class, method = "sub_select_code")
    @ResultMap("SubjectResultSet")
    SubjectDTO select_subject_code(SubjectDTO subjectDTO);

    @InsertProvider(type = SubjectSQL.class, method = "sub_insert")
    void insert_subject(SubjectDTO subjectDTO);

    @UpdateProvider(type = SubjectSQL.class, method = "sub_update")
    void update_subject(SubjectDTO subjectDTO);

    @DeleteProvider(type = SubjectSQL.class, method = "sub_delete")
    void delete_subject(SubjectDTO subjectDTO);
}
