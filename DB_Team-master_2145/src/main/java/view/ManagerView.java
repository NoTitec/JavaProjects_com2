package view;

import persistence.DTO.*;
import service.ManagerService;
import service.Mprotocol;

import java.util.List;
import java.util.Scanner;

public class ManagerView {

    ManagerService managerService = new ManagerService();

    // 관리자 ID 확인
    public void m_select(byte[] recvData) {
        ManagerDTO managerDTOselect = new ManagerDTO();
        managerDTOselect.setManid(new String(recvData));
        
        // Service
        managerService.m_work_id_check(managerDTOselect);

        System.out.println("managerDTOselect.toString() = " + managerDTOselect.toString());
    }

    // 관리자 로그인 프로토콜 받기
    public byte[] check_manager_id(String id)
    {
        ManagerDTO managerDTOcheckid = new ManagerDTO();
        System.out.println("관리자 아이디 입력 받는 중... ");
        managerDTOcheckid.setManid(id);
        // Service
        ManagerDTO managerDTO = managerService.m_work_id_check(managerDTOcheckid);  // 데이터 확인
        return managerService.m_set_protocol_id_check(managerDTO);  // 프로토콜 송신
    }

    public byte[] check_manager_pw(String pw)
    {
        ManagerDTO managerDTOcheckpw = new ManagerDTO();
        System.out.println("관리자 비밀 번호 입력 받는 중...");
        managerDTOcheckpw.setManpass(pw);

        //Service
        ManagerDTO managerDTO = managerService.m_work_pw_check(managerDTOcheckpw);
        return managerService.m_set_protocol_pw_check(managerDTO);
    }

    public byte[] check_professor_id(String id)
    {
        ProfessorDTO professorDTOcheckid = new ProfessorDTO();
        System.out.println("교수 아이디 입력 받는 중... ");
        professorDTOcheckid.setProid(id);
        // Service
        ProfessorDTO professorDTO = managerService.p_work_id_check(professorDTOcheckid);  // 데이터 확인
        return managerService.p_set_protocol_id_check(professorDTO, professorDTOcheckid.getProid());  // 프로토콜 송신
    }

    public byte[] check_professor_pw(String pw)
    {
        ProfessorDTO professorDTOcheckpw = new ProfessorDTO();
        System.out.println("교수 비밀번호 입력 받는 중... ");
        professorDTOcheckpw.setPropass(pw);
        // Service
        return managerService.p_set_protocol_pw_check(professorDTOcheckpw.getPropass());  // 프로토콜 송신
    }

    public byte[] update_professor_account(String num)
    {
        ProfessorDTO professorDTOupdateaccount = new ProfessorDTO();
        System.out.println("교수 번호 입력 받는 중... ");
        professorDTOupdateaccount.setPronum(num);

        // Service
        ProfessorDTO professorDTO = managerService.p_work_num_check(professorDTOupdateaccount); // 데이터 확인
        try {
            String tmp = professorDTO.getPronum();
        }

        catch (NullPointerException e) {    // 만약 교수 번호가 존재하지 않으면 재요청
            return managerService.p_set_protocol_num_check();
        }

        managerService.p_work_pro_update(professorDTOupdateaccount);   // 계정 업데이트
        return managerService.p_set_protocol_pro_update();  // 업데이트 성공 수신
    }

    public byte[] check_student_id(String id)
    {
        StudentDTO studentDTOcheckid = new StudentDTO();
        System.out.println("학생 아이디 입력 받는 중... ");
        studentDTOcheckid.setStuid(id);
        // Service
        StudentDTO studentDTO = managerService.s_work_id_check(studentDTOcheckid);  // 데이터 확인
        return managerService.s_set_protocol_id_check(studentDTO, studentDTOcheckid.getStuid());  // 프로토콜 송신
    }

    public byte[] check_student_pw(String pw)
    {
        StudentDTO studentDTOcheckpw = new StudentDTO();
        System.out.println("학생 비밀번호 입력 받는 중... ");
        studentDTOcheckpw.setStupass(pw);
        // Service
        return managerService.s_set_protocol_pw_check(studentDTOcheckpw.getStupass());  // 프로토콜 송신
    }

    public byte[] update_student_account(int num)
    {
        StudentDTO studentDTOupdateaccount = new StudentDTO();
        System.out.println("학생 번호 입력 받는 중... ");
        studentDTOupdateaccount.setStunum(num);

        // Service
        StudentDTO studentDTO = managerService.s_work_num_check(studentDTOupdateaccount); // 데이터 확인
        try {
            int tmp = studentDTO.getStunum();
        }

        catch (NullPointerException e) {    // 만약 학생 번호가 존재하지 않으면 재요청
            return managerService.s_set_protocol_num_check();
        }

        managerService.s_work_stu_update(studentDTOupdateaccount);   // 계정 업데이트
        return managerService.s_set_protocol_stu_update();  // 업데이트 성공 수신
    }

    public byte[] create_subject(SubjectDTO subjectDTOcreate)
    {
        System.out.println("새 교과목 레코드 생성 중...");

        // Service
        SubjectDTO subjectDTO = managerService.sub_work_check(subjectDTOcreate);
        try {
            String tmp = subjectDTO.getSubcode();
        }

        catch (NullPointerException e) {   // 만약 교과목 코드가 없으면 생성
            managerService.sub_work_insert(subjectDTOcreate);
            return managerService.sub_set_protocol_create_result();    // 성공
        }

        return managerService.sub_set_protocol_create_retry();    // 재요청
    }

    public byte[] update_subject_code_req(SubjectDTO subjectDTOcodereq)
    {
        System.out.println("수정할 교과목 레코드의 코드 요청 중...");

        // Service
        SubjectDTO subjectDTO = managerService.sub_work_check(subjectDTOcodereq);
        try {
            String tmp = subjectDTO.getSubcode();
        }

        catch (NullPointerException e) {    // 만약 교과목 코드가 없으면 재요청
            return managerService.sub_set_protocol_code_retry();  // 코드 재요청
        }

        return managerService.sub_set_protocol_code_result(subjectDTO.getSubcode());   // 코드 존재
    }
    //*/

    public byte[] update_subject_record_req(SubjectDTO subjectDTOrecordreq)
    {
        System.out.println("수정할 교과목 레코드 요청 중...");

        // Service
        managerService.sub_work_update(subjectDTOrecordreq);
        return managerService.sub_set_protocol_code_update();   // 업데이트 성공
    }

    public byte[] delete_subject(SubjectDTO subjectDTOdelete)
    {
        System.out.println("삭제할 교과목 레코드 요청 중...");

        // Service
        SubjectDTO subjectDTO = managerService.sub_work_check(subjectDTOdelete);
        try {
            String tmp = subjectDTO.getSubcode();
        }

        catch (NullPointerException e) {    // 코드가 일치하지 않으면 재요청
            return managerService.sub_set_protocol_delete_retry();
        }

        managerService.sub_work_delete(subjectDTOdelete);
        return managerService.sub_set_protocol_delete_result(); // 삭제 성공
    }

    public byte[] check_professor_num(String num)
    {
        System.out.println("교수 번호로 존재 여부 확인 중...");
        ProfessorDTO professorDTOcheckpronum = new ProfessorDTO();
        professorDTOcheckpronum.setPronum(num);
        // Service
        ProfessorDTO professorDTOselectall = managerService.p_work_select_all(professorDTOcheckpronum);

        try {
            String tmp = professorDTOselectall.getPronum();
        }

        catch (NullPointerException e) {    // 코드가 존재하지 않으면 재요청
            return managerService.p_set_protocol_num_retry();
        }

        return managerService.p_set_protocol_return_select_all(professorDTOselectall);
    }

    public byte[] check_student_num(int num)
    {
        System.out.println("학생 번호로 존재 여부 확인 중...");
        StudentDTO studentDTOcheckpronum = new StudentDTO();
        studentDTOcheckpronum.setStunum(num);
        // Service
        StudentDTO studentDTOselectall = managerService.s_work_select_all(studentDTOcheckpronum);

        try {
            int tmp = studentDTOselectall.getStunum();
        }

        catch (NullPointerException e) {    // 코드가 존재하지 않으면 재요청
            return managerService.s_set_protocol_num_retry();
        }

        return managerService.s_set_protocol_return_select_all(studentDTOselectall);
    }

    public byte[] check_cs_num(String cscode)
    {
        System.out.println("교수 번호로 존재 여부 확인 중...");
        CreatedsubjectDTO createdsubjectDTOcheckcscode = new CreatedsubjectDTO();
        createdsubjectDTOcheckcscode.setCreatedsubcode(cscode);
        // Service
        CreatedsubjectDTO createdsubjectDTOselectall = managerService.cs_work_select_all(createdsubjectDTOcheckcscode);

        try {
            String tmp = createdsubjectDTOselectall.getCreatedsubcode();
        }

        catch (NullPointerException e) {    // 코드가 존재하지 않으면 재요청
            return managerService.cs_set_protocol_num_retry();
        }

        return managerService.cs_set_protocol_return_select_all(createdsubjectDTOselectall);
    }

    public byte[] update_cs_plan_time(CreatedsubjectDTO createdsubjectDTOplanupdate)
    {
        System.out.println("강의 계획서 입력 기간 수정 중...");

        managerService.cs_work_update_plan_time(createdsubjectDTOplanupdate);

        return managerService.cs_set_protocol_update_result();
    }

    public byte[] update_cs_apply_time(CreatedsubjectDTO createdsubjectDTOapplyupdate)
    {
        System.out.println("수강 신청 기간 수정 중...");

        managerService.cs_work_update_apply_time(createdsubjectDTOapplyupdate);

        return managerService.cs_set_protocol_update_result();
    }
}
