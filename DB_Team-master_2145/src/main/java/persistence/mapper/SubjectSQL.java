package persistence.mapper;

import org.apache.ibatis.jdbc.SQL;
import persistence.DTO.SubjectDTO;

public class SubjectSQL {
    public String sub_select_code(SubjectDTO subjectDTO) {
        SQL sql = new SQL() {{
            SELECT("sub_code");
            FROM("subject");
            WHERE("sub_code = #{subcode}");
        }};
        return sql.toString();
    }

    public String sub_insert(SubjectDTO subjectDTO) {
        SQL sql = new SQL() {{
            INSERT_INTO("subject");
            VALUES("sub_code," +
                            "sub_name," +
                            "sub_info," +
                            "sub_grade," +
                            "man_id",
                    "#{subcode}," +
                            "#{subname}," +
                            "#{subinfo}," +
                            "#{subgrade}," +
                            "#{fmanid}");
        }};
        return sql.toString();
    }

    public String sub_update(SubjectDTO subjectDTO) {
        SQL sql = new SQL() {{
            UPDATE("subject");
            if(subjectDTO.getSubname() != "0") {
                SET("sub_name = #{subname}");
            }
            if(subjectDTO.getSubinfo() != "0") {
                SET("sub_info = #{subinfo}");
            }
            if(subjectDTO.getSubgrade() != 0) {
                SET("sub_grade = #{subgrade}");
            }
            if(subjectDTO.getFmanid() != "0") {
                SET("man_id = #{fmanid}");
            }
            WHERE("sub_code = #{subcode}");
        }};
        return sql.toString();
    }

    public String sub_delete(SubjectDTO subjectDTO) {
        SQL sql = new SQL() {{
            DELETE_FROM("subject");
            WHERE("sub_code = #{subcode}");
        }};
        return sql.toString();
    }


}
