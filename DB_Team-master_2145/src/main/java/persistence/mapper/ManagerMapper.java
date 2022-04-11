package persistence.mapper;

import org.apache.ibatis.annotations.*;
import persistence.DTO.ManagerDTO;

import java.util.List;

public interface ManagerMapper {

    @Select("SELECT * FROM MANAGER")
    @Results(id="managerSet",value = {
            @Result(property = "manid",column = "man_id"),
            @Result(property = "manpass",column = "man_pass"),
    })

    List<ManagerDTO> get_all_manager();

    /* 관리자 SQL 관련 부분 */
    @SelectProvider(type = ManagerSQL.class, method = "m_select_id_condition")
    @ResultMap("managerSet")
    ManagerDTO select_manager_id(ManagerDTO managerDTO);

    @SelectProvider(type = ManagerSQL.class, method = "m_select_pw_condition")
    @ResultMap("managerSet")
    ManagerDTO select_manager_pw(ManagerDTO managerDTO);

}
