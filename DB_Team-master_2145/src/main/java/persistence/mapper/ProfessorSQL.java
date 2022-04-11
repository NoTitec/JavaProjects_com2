package persistence.mapper;

import org.apache.ibatis.jdbc.SQL;
import persistence.DTO.ProfessorDTO;

public class ProfessorSQL {
    public String p_select_id_condition(ProfessorDTO professorDTO) {
        SQL sql = new SQL() {{
            SELECT("pro_id");
            FROM("professor");
            WHERE("pro_id = #{proid}");
        }};
        return sql.toString();
    }

    public String p_select_num_condition(ProfessorDTO professorDTO) {
        SQL sql = new SQL() {{
            SELECT("pro_num");
            FROM("professor");
            WHERE("pro_num = #{pronum}");
        }};
        return sql.toString();
    }

    public String p_update_condition(ProfessorDTO professorDTO) {
        SQL sql = new SQL() {{
            UPDATE("professor");
            SET ("pro_id = #{proid}, pro_pass = #{propass}");
            WHERE ("pro_num = #{pronum}");
        }};
        return sql.toString();
    }

    public String p_select_all_condition(ProfessorDTO professorDTO) {
        SQL sql = new SQL() {{
            SELECT("*");
            FROM("professor");
            WHERE("pro_num = #{pronum}");
        }};
        return sql.toString();
    }
}