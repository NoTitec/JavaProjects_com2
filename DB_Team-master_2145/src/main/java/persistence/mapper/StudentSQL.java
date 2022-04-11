package persistence.mapper;

import org.apache.ibatis.jdbc.SQL;
import persistence.DTO.StudentDTO;

public class StudentSQL {
    public String s_select_id_condition(StudentDTO studentDTO) {
        SQL sql = new SQL() {{
            SELECT("stu_id");
            FROM("student");
            WHERE("stu_id = #{stuid}");
        }};
        return sql.toString();
    }

    public String s_select_num_condition(StudentDTO studentDTO) {
        SQL sql = new SQL() {{
            SELECT("stu_num");
            FROM("student");
            WHERE("stu_num = #{stunum}");
        }};
        return sql.toString();
    }

    public String s_update_condition(StudentDTO studentDTO) {
        SQL sql = new SQL() {{
            UPDATE("student");
            SET ("stu_id = #{stuid}, stu_pass = #{stupass}");
            WHERE ("stu_num = #{stunum}");
        }};
        return sql.toString();
    }

    public String s_select_all_condition(StudentDTO studentDTO) {
        SQL sql = new SQL() {{
            SELECT("*");
            FROM("student");
            WHERE("stu_num = #{stunum}");
        }};
        return sql.toString();
    }
}
