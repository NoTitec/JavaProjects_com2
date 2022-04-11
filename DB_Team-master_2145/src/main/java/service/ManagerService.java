package service;

import persistence.DAO.ManagerDAO;
import persistence.DTO.*;
import persistence.MybatisConnectionFactory;

import java.util.List;

public class ManagerService {
    // 네트워크 관련 필드
    protected byte[] packet;
    private static String man_pw_cmp_id;    // pw와 비교하기 위해 id를 저장하는 필드
    private static int pw_false = 0;
    private static String pro_id;
    private static String pro_pw;
    private static String stu_id;
    private static String stu_pw;
    private static String sub_code;
    // 데이터베이스 관련 필드
    ManagerDAO managerDAO = new ManagerDAO(MybatisConnectionFactory.getSqlSessionFactory());

    // 데이터 베이스 관련 서비스
    public ManagerDTO m_work_id_check(ManagerDTO managerDTOcheckid) {
        return managerDAO.m_select_id_with_annotation(managerDTOcheckid);
    }

    public ManagerDTO m_work_pw_check(ManagerDTO managerDTOcheckpw) {
        managerDTOcheckpw.setManid(man_pw_cmp_id);
        return managerDAO.m_select_pw_with_annotation(managerDTOcheckpw);
    }

    public ProfessorDTO p_work_id_check(ProfessorDTO professorDTOcheckid) {
        return managerDAO.p_select_id_with_annotation(professorDTOcheckid);
    }

    public ProfessorDTO p_work_num_check(ProfessorDTO professorDTOupdateaccount) {
        return managerDAO.p_select_num_with_annotation(professorDTOupdateaccount);
    }

    public void p_work_pro_update(ProfessorDTO professorDTOupdateaccount) {
        professorDTOupdateaccount.setProid(pro_id);
        professorDTOupdateaccount.setPropass(pro_pw);
        managerDAO.p_update_pro_with_annotation(professorDTOupdateaccount);
    }

    public ProfessorDTO p_work_select_all(ProfessorDTO professorDTOselectall) {
        return managerDAO.p_select_all_with_annotation(professorDTOselectall);
    }

    public StudentDTO s_work_id_check(StudentDTO studentDTOcheckid) {
        return managerDAO.s_select_id_with_annotation(studentDTOcheckid);
    }

    public StudentDTO s_work_num_check(StudentDTO studentDTOupdateaccount) {
        return managerDAO.s_select_num_with_annotation(studentDTOupdateaccount);
    }

    public void s_work_stu_update(StudentDTO studentDTOupdateaccount) {
        studentDTOupdateaccount.setStuid(stu_id);
        studentDTOupdateaccount.setStupass(stu_pw);
        managerDAO.s_update_stu_with_annotation(studentDTOupdateaccount);
    }

    public StudentDTO s_work_select_all(StudentDTO studentDTOselectall) {
        return managerDAO.s_select_all_with_annotation(studentDTOselectall);
    }

    public SubjectDTO sub_work_check(SubjectDTO subjectDTOcheck) {
        return managerDAO.sub_select_code_with_annotation(subjectDTOcheck);
    }

    public void sub_work_insert(SubjectDTO subjectDTOinsert) {
        managerDAO.sub_insert_with_annotation(subjectDTOinsert);
    }

    public void sub_work_update(SubjectDTO subjectDTOupdate) {
        subjectDTOupdate.setSubcode(sub_code);
        managerDAO.sub_update_with_annotation(subjectDTOupdate);
    }

    public void sub_work_delete(SubjectDTO subjectDTOdelete) {
        managerDAO.sub_delete_with_annotation(subjectDTOdelete);
    }

    public void cs_work_update_plan_time(CreatedsubjectDTO createdsubjectDTOplanupdate) {
        managerDAO.cs_update_plan_time_with_annotation(createdsubjectDTOplanupdate);
    }

    public void cs_work_update_apply_time(CreatedsubjectDTO createdsubjectDTOapplyupdate) {
        managerDAO.cs_update_apply_time_with_annotation(createdsubjectDTOapplyupdate);
    }
    public CreatedsubjectDTO cs_work_select_all(CreatedsubjectDTO createdsubjectDTOselectall) {
        return managerDAO.cs_select_all_with_annotation(createdsubjectDTOselectall);
    }
    // 네트워크 관련 서비스
    public byte[] m_set_protocol_id_check(ManagerDTO managerDTO) {

        Mprotocol mprotocol = new Mprotocol();
        try {
            man_pw_cmp_id = managerDTO.getManid();
        }
        catch (NullPointerException e){
            // 아이디가 틀리면 프로토콜 1,1 (아이디 재확인)
            packet = mprotocol.getPacket(Mprotocol.ACCOUNT_INFO_REQ, Mprotocol.PT_UNDEFINED, Mprotocol.M_MAN_ID_REQ);
            return packet;
        }
            // 아이디가 맞으면 프로토콜 2,1 (비밀번호 확인)
        packet = mprotocol.getPacket(Mprotocol.ACCOUNT_INFO_REQ, Mprotocol.PT_UNDEFINED, Mprotocol.M_MAN_PW_REQ);
        return packet;

    }

    public byte[] m_set_protocol_pw_check(ManagerDTO managerDTO) {
        String password;
        Mprotocol mprotocol = new Mprotocol();
        try {
            password = managerDTO.getManpass();
        }
        catch (NullPointerException e) {
            // 비밀번호 값 자체가 존재하지 않을 시
            pw_false += 1;
            if (pw_false < 2)
                packet = mprotocol.getPacket(Mprotocol.ACCOUNT_INFO_REQ, Mprotocol.PT_UNDEFINED, Mprotocol.M_MAN_PW_REQ);
            else
                packet = mprotocol.getPacket(Mprotocol.ACCOUNT_INFO_RESULT, Mprotocol.PT_UNDEFINED, Mprotocol.MAN_LOGIN_FAIL_CODE);

            return packet;
        }

        packet = mprotocol.getPacket(Mprotocol.ACCOUNT_INFO_RESULT, Mprotocol.PT_UNDEFINED, Mprotocol.MAN_LOGIN_SUCCESS_CODE);

        return packet;



    }
    public byte[] p_set_protocol_id_check(ProfessorDTO professorDTO, String id) {

        pro_id = id;

        Mprotocol mprotocol = new Mprotocol();
        try {
           String tmp = professorDTO.getProid();
        }
        catch (NullPointerException e){
            packet = mprotocol.getPacket(Mprotocol.ACCOUNT_INFO_REQ, Mprotocol.PT_UNDEFINED, Mprotocol.M_PRO_PW_REQ);
            return packet;
        }

        packet = mprotocol.getPacket(Mprotocol.ACCOUNT_INFO_REQ, Mprotocol.PT_UNDEFINED, Mprotocol.M_PRO_ID_REQ);
        return packet;
    }

    public byte[] p_set_protocol_pw_check(String pw) {

        pro_pw = pw;

        Mprotocol mprotocol = new Mprotocol();
        packet = mprotocol.getPacket(Mprotocol.ACCOUNT_INFO_REQ, Mprotocol.PT_UNDEFINED, Mprotocol.M_PRO_NUM_REQ);
        return packet;
    }

    public byte[] p_set_protocol_num_check() {

        Mprotocol mprotocol = new Mprotocol();
        packet = mprotocol.getPacket(Mprotocol.ACCOUNT_INFO_REQ, Mprotocol.PT_UNDEFINED, Mprotocol.M_PRO_NUM_REQ);
        return packet;
    }

    public byte[] p_set_protocol_pro_update() {
        Mprotocol mprotocol = new Mprotocol();
        packet = mprotocol.getPacket(Mprotocol.ACCOUNT_INFO_RESULT, Mprotocol.PT_UNDEFINED, Mprotocol.SUCCESS);
        System.out.println("교수 계정 업데이트 성공");
        return packet;
    }

    public byte[] p_set_protocol_num_retry() {
        Mprotocol mprotocol = new Mprotocol();
        packet = mprotocol.getPacket(Mprotocol.MAN_INFO_REQ, Mprotocol.PT_UNDEFINED, Mprotocol.M_PRO_CALL_NUM_REQ);
        System.out.println("교수 번호 재요청");
        return packet;
    }

    public byte[] p_set_protocol_return_select_all(ProfessorDTO professorDTOselectall) {
        Mprotocol mprotocol = new Mprotocol(Mprotocol.MAN_INFO_RESULT, Mprotocol.PT_UNDEFINED, Mprotocol.M_PRO_CALL_REC_REQ);
        mprotocol.set_single_tuple(professorDTOselectall.getPronum(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE);
        mprotocol.set_single_tuple(professorDTOselectall.getProid(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                Mprotocol.LEN_SUB_CODE);
        mprotocol.set_single_tuple(professorDTOselectall.getPropass(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                        Mprotocol.LEN_SUB_CODE + Mprotocol.LEN_PRO_ID);
        mprotocol.set_single_tuple(professorDTOselectall.getProname(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                        Mprotocol.LEN_SUB_CODE + Mprotocol.LEN_PRO_ID + Mprotocol.LEN_PRO_PASS);
        mprotocol.set_single_int(professorDTOselectall.getProage(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                        Mprotocol.LEN_SUB_CODE + Mprotocol.LEN_PRO_ID + Mprotocol.LEN_PRO_PASS + Mprotocol.LEN_PRO_NAME);
        mprotocol.set_single_tuple(professorDTOselectall.getFmanid(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                        Mprotocol.LEN_SUB_CODE + Mprotocol.LEN_PRO_ID + Mprotocol.LEN_PRO_PASS + Mprotocol.LEN_PRO_NAME +
                Mprotocol.LEN_PRO_AGE);
        mprotocol.set_single_tuple(professorDTOselectall.getProgender(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                        Mprotocol.LEN_SUB_CODE + Mprotocol.LEN_PRO_ID + Mprotocol.LEN_PRO_PASS + Mprotocol.LEN_PRO_NAME +
                        Mprotocol.LEN_PRO_AGE + Mprotocol.LEN_MAN_ID);
        mprotocol.set_single_tuple(professorDTOselectall.getProphonenumber(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                        Mprotocol.LEN_SUB_CODE + Mprotocol.LEN_PRO_ID + Mprotocol.LEN_PRO_PASS + Mprotocol.LEN_PRO_NAME +
                        Mprotocol.LEN_PRO_AGE + Mprotocol.LEN_MAN_ID + Mprotocol.LEN_PRO_GENDER);
        packet = mprotocol.getPacket();
        System.out.println("교수 레코드 값 클라이언트에게 전달");
        return packet;

    }

    public byte[] s_set_protocol_id_check(StudentDTO studentDTO, String id) {

        stu_id = id;

        Mprotocol mprotocol = new Mprotocol();
        try {
            String tmp = studentDTO.getStuid();
        }
        catch (NullPointerException e){
            packet = mprotocol.getPacket(Mprotocol.ACCOUNT_INFO_REQ, Mprotocol.PT_UNDEFINED, Mprotocol.M_STU_PW_REQ);
            return packet;
        }

        packet = mprotocol.getPacket(Mprotocol.ACCOUNT_INFO_REQ, Mprotocol.PT_UNDEFINED, Mprotocol.M_STU_ID_REQ);
        return packet;
    }

    public byte[] s_set_protocol_pw_check(String pw) {

        stu_pw = pw;

        Mprotocol mprotocol = new Mprotocol();
        packet = mprotocol.getPacket(Mprotocol.ACCOUNT_INFO_REQ, Mprotocol.PT_UNDEFINED, Mprotocol.M_STU_NUM_REQ);
        return packet;
    }

    public byte[] s_set_protocol_num_check() {

        Mprotocol mprotocol = new Mprotocol();
        packet = mprotocol.getPacket(Mprotocol.ACCOUNT_INFO_REQ, Mprotocol.PT_UNDEFINED, Mprotocol.M_STU_NUM_REQ);
        return packet;
    }

    public byte[] s_set_protocol_stu_update() {
        Mprotocol mprotocol = new Mprotocol();
        packet = mprotocol.getPacket(Mprotocol.ACCOUNT_INFO_RESULT, Mprotocol.PT_UNDEFINED, Mprotocol.SUCCESS);
        System.out.println("학생 계정 업데이트 성공");
        return packet;
    }

    public byte[] sub_set_protocol_create_result() {
        Mprotocol mprotocol = new Mprotocol();
        packet = mprotocol.getPacket(Mprotocol.ACCOUNT_INFO_RESULT, Mprotocol.PT_UNDEFINED, Mprotocol.SUCCESS);
        System.out.println("교과목 생성 성공");
        return packet;
    }

    public byte[] s_set_protocol_num_retry() {
        Mprotocol mprotocol = new Mprotocol();
        packet = mprotocol.getPacket(Mprotocol.MAN_INFO_REQ, Mprotocol.PT_UNDEFINED, Mprotocol.M_STU_CALL_NUM_REQ);
        System.out.println("학생 번호 재요청");
        return packet;
    }

    public byte[] s_set_protocol_return_select_all(StudentDTO studentDTOselectall) {
        Mprotocol mprotocol = new Mprotocol(Mprotocol.MAN_INFO_RESULT, Mprotocol.PT_UNDEFINED, Mprotocol.M_STU_CALL_REC_REQ);
        mprotocol.set_single_int(studentDTOselectall.getStunum(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE);
        mprotocol.set_single_tuple(studentDTOselectall.getStuid(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                        Mprotocol.LEN_STU_NUM);
        mprotocol.set_single_tuple(studentDTOselectall.getStupass(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                        Mprotocol.LEN_STU_NUM + Mprotocol.LEN_STU_ID);
        mprotocol.set_single_tuple(studentDTOselectall.getStuname(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                        Mprotocol.LEN_STU_NUM + Mprotocol.LEN_STU_ID + Mprotocol.LEN_STU_PASS);
        mprotocol.set_single_int(studentDTOselectall.getStuage(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                        Mprotocol.LEN_STU_NUM + Mprotocol.LEN_STU_ID + Mprotocol.LEN_STU_PASS + Mprotocol.LEN_STU_NAME);
        mprotocol.set_single_tuple(studentDTOselectall.getFmanid(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                        Mprotocol.LEN_STU_NUM + Mprotocol.LEN_STU_ID + Mprotocol.LEN_STU_PASS + Mprotocol.LEN_STU_NAME +
                        Mprotocol.LEN_STU_AGE);
        mprotocol.set_single_tuple(studentDTOselectall.getStugender(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                        Mprotocol.LEN_STU_NUM + Mprotocol.LEN_STU_ID + Mprotocol.LEN_STU_PASS + Mprotocol.LEN_STU_NAME +
                        Mprotocol.LEN_STU_AGE + Mprotocol.LEN_MAN_ID);
        mprotocol.set_single_int(studentDTOselectall.getStugrade(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                        Mprotocol.LEN_STU_NUM + Mprotocol.LEN_STU_ID + Mprotocol.LEN_STU_PASS + Mprotocol.LEN_STU_NAME +
                        Mprotocol.LEN_STU_AGE + Mprotocol.LEN_MAN_ID + Mprotocol.LEN_STU_GENDER);
        packet = mprotocol.getPacket();
        System.out.println("학생 레코드 값 클라이언트에게 전달");
        return packet;

    }

    public byte[] sub_set_protocol_create_retry() {
        Mprotocol mprotocol = new Mprotocol();
        packet = mprotocol.getPacket(Mprotocol.MAN_SUBJECT_SET_REQ, Mprotocol.PT_UNDEFINED, Mprotocol.M_SUB_INS_REQ);
        System.out.println("교과목 재요청");
        return packet;
    }

    public byte[] sub_set_protocol_code_retry() {
        Mprotocol mprotocol = new Mprotocol();
        packet = mprotocol.getPacket(Mprotocol.MAN_SUBJECT_SET_REQ, Mprotocol.PT_UNDEFINED, Mprotocol.M_SUB_CODE_REQ);
        System.out.println("코드 재요청");
        return packet;
    }

    public byte[] sub_set_protocol_code_result(String code) {
        sub_code = code;

        Mprotocol mprotocol = new Mprotocol();
        packet = mprotocol.getPacket(Mprotocol.MAN_SUBJECT_SET_REQ, Mprotocol.PT_UNDEFINED, Mprotocol.M_SUB_REC_REQ);
        System.out.println("레코드 요청");
        return packet;
    }

    public byte[] sub_set_protocol_code_update() {
        Mprotocol mprotocol = new Mprotocol();
        packet = mprotocol.getPacket(Mprotocol.ACCOUNT_INFO_RESULT, Mprotocol.PT_UNDEFINED, Mprotocol.SUCCESS);
        System.out.println("교과목 수정 성공");
        return packet;
    }

    public byte[] sub_set_protocol_delete_retry() {
        Mprotocol mprotocol = new Mprotocol();
        packet = mprotocol.getPacket(Mprotocol.MAN_SUBJECT_SET_REQ, Mprotocol.PT_UNDEFINED, Mprotocol.M_SUB_DEL_REQ);
        System.out.println("삭제할 레코드의 코드 재요청");
        return packet;
    }

    public byte[] sub_set_protocol_delete_result() {
        Mprotocol mprotocol = new Mprotocol();
        packet = mprotocol.getPacket(Mprotocol.ACCOUNT_INFO_RESULT, Mprotocol.PT_UNDEFINED, Mprotocol.SUCCESS);
        System.out.println("교과목 삭제 성공");
        return packet;
    }

    public byte[] cs_set_protocol_update_result() {
        Mprotocol mprotocol = new Mprotocol();
        packet = mprotocol.getPacket(Mprotocol.ACCOUNT_INFO_RESULT, Mprotocol.PT_UNDEFINED, Mprotocol.SUCCESS);
        System.out.println("입력 기간 업데이트 성공");
        return packet;
    }

    public byte[] cs_set_protocol_num_retry() {
        Mprotocol mprotocol = new Mprotocol();
        packet = mprotocol.getPacket(Mprotocol.MAN_INFO_REQ, Mprotocol.PT_UNDEFINED, Mprotocol.M_CS_CALL_NUM_REQ);
        System.out.println("개설 교과목 코드 재요청");
        return packet;
    }

    public byte[] cs_set_protocol_return_select_all(CreatedsubjectDTO createdsubjectDTOselectall) {
        Mprotocol mprotocol = new Mprotocol(Mprotocol.MAN_INFO_RESULT, Mprotocol.PT_UNDEFINED, Mprotocol.M_CS_CALL_REC_REQ);
        mprotocol.set_single_tuple(createdsubjectDTOselectall.getCreatedsubcode(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE);
        mprotocol.set_single_tuple(createdsubjectDTOselectall.getCreatedsubname(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                Mprotocol.LEN_CS_CODE);
        mprotocol.set_single_tuple(createdsubjectDTOselectall.getCreatedsubinfo(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                        Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME);
        mprotocol.set_single_int(createdsubjectDTOselectall.getCreatedsubgrade(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                        Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO);
        mprotocol.set_single_int(createdsubjectDTOselectall.getStumax(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                        Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE);
        mprotocol.set_single_tuple(createdsubjectDTOselectall.getRoom(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                        Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                Mprotocol.LEN_CS_STU_MAX);
        mprotocol.set_single_int(createdsubjectDTOselectall.getClassstarttime().getHour(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                        Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                        Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM);
        mprotocol.set_single_int(createdsubjectDTOselectall.getClassstarttime().getMinute(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                        Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                        Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM + 4);
        mprotocol.set_single_int(createdsubjectDTOselectall.getClassstarttime().getSecond(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                        Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                        Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM + 4 + 4);
        mprotocol.set_single_int(createdsubjectDTOselectall.getClassendtime().getHour(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                        Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                        Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM + 4 + 4 + 4);
        mprotocol.set_single_int(createdsubjectDTOselectall.getClassendtime().getMinute(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                        Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                        Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM + 4 + 4 + 4 + 4);
        mprotocol.set_single_int(createdsubjectDTOselectall.getClassendtime().getSecond(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                        Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                        Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM + 4 + 4 + 4 + 4 + 4);
        mprotocol.set_single_tuple(createdsubjectDTOselectall.getClassplan(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                        Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                        Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM + 4 + 4 + 4 + 4 + 4 + 4);
        mprotocol.set_single_int(createdsubjectDTOselectall.getPlanstartdate().getYear(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                        Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                        Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM + 4 + 4 + 4 + 4 + 4 + 4 +
                        Mprotocol.LEN_CS_PLAN );
        mprotocol.set_single_int(createdsubjectDTOselectall.getPlanstartdate().getMonthValue(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                        Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                        Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM + 4 + 4 + 4 + 4 + 4 + 4 +
                        Mprotocol.LEN_CS_PLAN + 4);
        mprotocol.set_single_int(createdsubjectDTOselectall.getPlanstartdate().getDayOfMonth(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                        Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                        Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM + 4 + 4 + 4 + 4 + 4 + 4 +
                        Mprotocol.LEN_CS_PLAN + 4 + 4);
        mprotocol.set_single_int(createdsubjectDTOselectall.getPlanenddate().getYear(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                        Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                        Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM + 4 + 4 + 4 + 4 + 4 + 4 +
                        Mprotocol.LEN_CS_PLAN + 4 + 4 + 4);
        mprotocol.set_single_int(createdsubjectDTOselectall.getPlanenddate().getMonthValue(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                        Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                        Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM + 4 + 4 + 4 + 4 + 4 + 4 +
                        Mprotocol.LEN_CS_PLAN + 4 + 4 + 4 + 4);
        mprotocol.set_single_int(createdsubjectDTOselectall.getPlanenddate().getDayOfMonth(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                        Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                        Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM + 4 + 4 + 4 + 4 + 4 + 4 +
                        Mprotocol.LEN_CS_PLAN + 4 + 4 + 4 + 4 + 4);
        mprotocol.set_single_int(createdsubjectDTOselectall.getApplystartdate().getYear(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                        Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                        Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM + 24 +
                        Mprotocol.LEN_CS_PLAN + 24);
        mprotocol.set_single_int(createdsubjectDTOselectall.getApplystartdate().getMonthValue(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                        Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                        Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM + 24 +
                Mprotocol.LEN_CS_PLAN + 24 + 4);
        mprotocol.set_single_int(createdsubjectDTOselectall.getApplystartdate().getDayOfMonth(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                        Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                        Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM + 24 +
                Mprotocol.LEN_CS_PLAN + 24 + 4 + 4);
        mprotocol.set_single_int(createdsubjectDTOselectall.getApplyenddate().getYear(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                        Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                        Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM + 24 +
                        Mprotocol.LEN_CS_PLAN + 24 + 4 + 4 + 4);
        mprotocol.set_single_int(createdsubjectDTOselectall.getApplyenddate().getMonthValue(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                        Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                        Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM + 24 +
                        Mprotocol.LEN_CS_PLAN + 24 + 4 + 4 + 4 + 4);
        mprotocol.set_single_int(createdsubjectDTOselectall.getApplyenddate().getDayOfMonth(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                        Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                        Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM + 24 +
                        Mprotocol.LEN_CS_PLAN + 24 + 4 + 4 + 4 + 4 + 4);
        mprotocol.set_single_tuple(createdsubjectDTOselectall.getFmanid(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                        Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                        Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM + 24 +
                        Mprotocol.LEN_CS_PLAN + 24 + 4 + 4 + 4 + 4 + 4 + 4);
        mprotocol.set_single_tuple(createdsubjectDTOselectall.getFpronum(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                        Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                        Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM + 24 +
                        Mprotocol.LEN_CS_PLAN + 24 + 4 + 4 + 4 + 4 + 4 + 4 + Mprotocol.LEN_MAN_ID);
        mprotocol.set_single_int(createdsubjectDTOselectall.getSubday(),
                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                        Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                        Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM + 24 +
                        Mprotocol.LEN_CS_PLAN + 24 + 4 + 4 + 4 + 4 + 4 + 4 + Mprotocol.LEN_MAN_ID + Mprotocol.LEN_PRO_NUM);


        packet = mprotocol.getPacket();
        System.out.println("교수 레코드 값 클라이언트에게 전달");
        return packet;

    }


}
