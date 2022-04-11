package persistence.mapper;

import org.apache.ibatis.jdbc.SQL;
import persistence.DTO.CreatedsubjectDTO;

public class CreatedsubjectSQL {
    public String cs_update_plan_time(CreatedsubjectDTO createdsubjectDTO) {
        SQL sql = new SQL() {{
            UPDATE("createdsubject");
            SET("plan_start_date = #{planstartdate}," +
                    "plan_end_date = #{planenddate}");
        }};
        return sql.toString();
    }
    public String cs_update_apply_time(CreatedsubjectDTO createdsubjectDTO) {
        SQL sql = new SQL() {{
            UPDATE("createdsubject");
            SET("apply_start_date = #{applystartdate}," +
                    "apply_end_date = #{applyenddate}");
            WHERE("created_sub_grade = #{createdsubgrade}");
        }};
        return sql.toString();
    }

    public String cs_select_all(CreatedsubjectDTO createdsubjectDTO) {
        SQL sql = new SQL() {{
            SELECT("*");
            FROM("createdsubject");
            WHERE("created_subcode = #{createdsubcode}");
        }};
        return sql.toString();
    }
}
