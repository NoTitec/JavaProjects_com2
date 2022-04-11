package persistence.DAO;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.DTO.*;
import persistence.mapper.*;

import java.util.List;

public class ManagerDAO {

    private SqlSessionFactory sqlSessionFactory = null;

    public ManagerDAO(SqlSessionFactory sqlSessionFactory) { this.sqlSessionFactory = sqlSessionFactory; }

    /* ManagerDAO SELECT */

    public ManagerDTO m_select_id_with_annotation(ManagerDTO managerDTO) {
        SqlSession session = sqlSessionFactory.openSession();
        ManagerMapper mapper = session.getMapper(ManagerMapper.class);
        return mapper.select_manager_id(managerDTO);
    }

    public ManagerDTO m_select_pw_with_annotation(ManagerDTO managerDTO) {
        SqlSession session = sqlSessionFactory.openSession();
        ManagerMapper mapper = session.getMapper(ManagerMapper.class);
        return mapper.select_manager_pw(managerDTO);
    }

    public ProfessorDTO p_select_id_with_annotation(ProfessorDTO professorDTO) {
        SqlSession session = sqlSessionFactory.openSession();
        ProfessorMapper mapper = session.getMapper(ProfessorMapper.class);
        return mapper.select_professor_id(professorDTO);
    }

    public ProfessorDTO p_select_num_with_annotation(ProfessorDTO professorDTO) {
        SqlSession session = sqlSessionFactory.openSession();
        ProfessorMapper mapper = session.getMapper(ProfessorMapper.class);
        return mapper.select_professor_num(professorDTO);
    }

    public void p_update_pro_with_annotation(ProfessorDTO professorDTO) {
        SqlSession session = sqlSessionFactory.openSession();
        ProfessorMapper mapper = session.getMapper(ProfessorMapper.class);
        try {
            mapper.update_professor(professorDTO);
            session.commit();
        }
        finally {
            session.close();
        }

    }

    public ProfessorDTO p_select_all_with_annotation(ProfessorDTO professorDTO) {
        SqlSession session = sqlSessionFactory.openSession();
        ProfessorMapper mapper = session.getMapper(ProfessorMapper.class);
        return mapper.select_professor_all(professorDTO);
    }

    public StudentDTO s_select_id_with_annotation(StudentDTO studentDTO) {
        SqlSession session = sqlSessionFactory.openSession();
        StudentMapper mapper = session.getMapper(StudentMapper.class);
        return mapper.select_student_id(studentDTO);
    }

    public StudentDTO s_select_num_with_annotation(StudentDTO studentDTO) {
        SqlSession session = sqlSessionFactory.openSession();
        StudentMapper mapper = session.getMapper(StudentMapper.class);
        return mapper.select_student_num(studentDTO);
    }

    public void s_update_stu_with_annotation(StudentDTO studentDTO) {
        SqlSession session = sqlSessionFactory.openSession();
        StudentMapper mapper = session.getMapper(StudentMapper.class);
        try {
            mapper.update_student(studentDTO);
            session.commit();
        }
        finally {
            session.close();
        }

    }

    public StudentDTO s_select_all_with_annotation(StudentDTO studentDTO) {
        SqlSession session = sqlSessionFactory.openSession();
        StudentMapper mapper = session.getMapper(StudentMapper.class);
        return mapper.select_student_all(studentDTO);
    }

    public SubjectDTO sub_select_code_with_annotation(SubjectDTO subjectDTO) {
        SqlSession session = sqlSessionFactory.openSession();
        SubjectMapper mapper = session.getMapper(SubjectMapper.class);
        return mapper.select_subject_code(subjectDTO);
    }

    public void sub_insert_with_annotation(SubjectDTO subjectDTO) {
        SqlSession session = sqlSessionFactory.openSession();
        SubjectMapper mapper = session.getMapper(SubjectMapper.class);
        try {
            mapper.insert_subject(subjectDTO);
            session.commit();
        }
        finally {
            session.close();
        }
    }

    public void sub_update_with_annotation(SubjectDTO subjectDTO) {
        SqlSession session = sqlSessionFactory.openSession();
        SubjectMapper mapper = session.getMapper(SubjectMapper.class);
        try {
            mapper.update_subject(subjectDTO);
            session.commit();
        }
        finally {
            session.close();
        }
    }

    public void sub_delete_with_annotation(SubjectDTO subjectDTO) {
        SqlSession session = sqlSessionFactory.openSession();
        SubjectMapper mapper = session.getMapper(SubjectMapper.class);
        try {
            mapper.delete_subject(subjectDTO);
            session.commit();
        }
        finally {
            session.close();
        }
    }

    public void cs_update_plan_time_with_annotation(CreatedsubjectDTO createdsubjectDTO) {
        SqlSession session = sqlSessionFactory.openSession();
        CreatedsubjectMapper mapper = session.getMapper(CreatedsubjectMapper.class);
        try {
            mapper.update_createdsubject_plan_time(createdsubjectDTO);
            session.commit();
        }
        finally {
            session.close();
        }
    }

    public void cs_update_apply_time_with_annotation(CreatedsubjectDTO createdsubjectDTO) {
        SqlSession session = sqlSessionFactory.openSession();
        CreatedsubjectMapper mapper = session.getMapper(CreatedsubjectMapper.class);
        try {
            mapper.update_createdsubject_apply_time(createdsubjectDTO);
            session.commit();
        }
        finally {
            session.close();
        }
    }

    public CreatedsubjectDTO cs_select_all_with_annotation(CreatedsubjectDTO createdsubjectDTO) {
        SqlSession session = sqlSessionFactory.openSession();
        CreatedsubjectMapper mapper = session.getMapper(CreatedsubjectMapper.class);
        return mapper.select_createdsubject_all(createdsubjectDTO);
    }
}

