package persistence.mapper;

import org.apache.ibatis.jdbc.SQL;
import persistence.DTO.ManagerDTO;

public class ManagerSQL {
    public String m_select_id_condition(ManagerDTO managerDTO) {
        SQL sql = new SQL() {{
            SELECT("man_id");
            FROM("manager");
            WHERE("man_id = #{manid}");
        }};
        return sql.toString();
    }

    public String m_select_pw_condition(ManagerDTO managerDTO) {
        SQL sql = new SQL() {{
            SELECT("man_pass");
            FROM("manager");
            WHERE("man_id = #{manid}");
            AND();
            WHERE("man_pass = #{manpass}");
        }};
        return sql.toString();
    }
}