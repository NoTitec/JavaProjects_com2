import persistence.DAO.RegistDAO;
import persistence.DTO.AppliedregistDTO;
import persistence.DTO.CreatedsubjectDTO;
import persistence.DTO.StudentDTO;
import persistence.DTO.SubjectDTO;
import persistence.MybatisConnectionFactory;
import service.RegistService;
import view.RegistView;
import service.Mprotocol;
import view.ManagerView;

import javax.imageio.IIOException;
import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.List;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //
        ServerSocket s = null;
        Socket conn = null;

        try {
            s = new ServerSocket();
            s.setReuseAddress(true);
            InetAddress Address = InetAddress.getLocalHost();
            System.out.println(Address.getHostAddress());
            SocketAddress addr = new InetSocketAddress(Address, 3000);
            s.bind(addr);
            System.out.println("waiting for client");

            while (true) {
                conn = s.accept();
                System.out.println("from ip:" + conn.getInetAddress().getHostAddress() + "port:" + conn.getPort());

                new client_handle(conn).start();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        try {
            s.close();
        } catch (IOException ioException) {
            System.err.println("unable to close");
        }
        //

        ///
        //밑에 주석처리문장있던위치
        ///
    }
}

class client_handle extends Thread {//모든 clint 공유변수
    private Socket conn;
    boolean program_stop = false;
    RegistDAO registDAO = new RegistDAO(MybatisConnectionFactory.getSqlSessionFactory());
    RegistService registService = new RegistService(registDAO);
    RegistView registView = new RegistView();
    ManagerView managerView = new ManagerView();
    client_handle(Socket conn) {
        this.conn = conn;
    }

    public void run() {//각 클라이언트의 지역 변수
        //List<CreatedsubjectDTO> r = registService.getmysubject("kumid","kumpass");
        //registView.print_stu_subject(r);
        //List<CreatedsubjectDTO> r = registDAO.get_grade_created_subject(2);
        //r.stream().forEach(v -> System.out.println("교과목정보 =" + v.toString()));
        InputStream is;
        BufferedInputStream bis;//버퍼수신스트림
        OutputStream os;
        BufferedOutputStream bos;//버퍼송신스트림

        int stunum=0;//로그인 성공시 학번저장
        String stuid="";//학생로그인성공시 아이디저장
        String stupwd="";//학생로그인성공시 비밀번호저장
        int stugrade=0;//학생로그인성공시 학생학년저장
        int clistcount;//과목목록개수정보저장
        StudentDTO getstudent;//학생정보저장
        CreatedsubjectDTO getsubject;//과목정보저장
        List<CreatedsubjectDTO> clist=null;//과목 정보 리스트저장
        String subjectplaninfo="";//과목 강의계획서 정보 저장
        List<AppliedregistDTO> alist=null;//수강신청정보리스트저장
        boolean clientout = false;//클라이언트종료변수
        int count = 0;//로그인시도횟수
        // 관리자 관련 변수 = 제작 허재훈
        boolean program_stop = false;
        try {
            is = conn.getInputStream();
            bis = new BufferedInputStream(is);
            os = conn.getOutputStream();
            bos = new BufferedOutputStream(os);


            Rprotocol proto = new Rprotocol(Rprotocol.ACCOUNT_INFO_REQ, Rprotocol.PT_UNDEFINED, Rprotocol.WHO_INFO_REQ);
            bos.write(proto.getPacket());
            bos.flush();


            Mprotocol mprotocol = new Mprotocol(Mprotocol.ACCOUNT_INFO_REQ, Mprotocol.PT_UNDEFINED, Mprotocol.WHO_INFO_REQ);
            /*
            bos.write(mprotocol.getPacket());
            bos.flush();
            */
            do {
                proto = new Rprotocol();//프로토콜객체
                mprotocol = new Mprotocol();        // 새 프로토콜 객체 생성 (기본 생성자)
                byte[] buf = proto.getPacket();

                bis.read(buf);//패킷수신
                int packetType = buf[0];
                int packetstuType = buf[1];
                int packetCode = buf[2];
                //받은 패킷의 타입,학생타입,코드뭔지 출력
                System.out.print("type " + packetType);
                System.out.print(" stutype " + packetstuType);
                System.out.println(" code " + packetCode);
                byte[] slicecount;//4바이트데이터길이배열
                int datalen;//데이터길이
                byte[] data = new byte[0];//데이터배열
                byte[] temp;
                int pos=3;//temp 인덱싱변수
                String temps;
                //수신받은 패킷에서 type,stutype,code따라 동작수행
                if (packetstuType == -1) {//학생관련타입아님
                    if(packetCode == -1 && packetType == -1)
                        clientout = true;
                    switch (packetType) {
                        case Mprotocol.MAN_SUBJECT_SET_ANS:
                            switch (packetCode) {
                                case Mprotocol.M_SUB_INS_ANS:
                                    SubjectDTO subjectDTOinsert = new SubjectDTO();
                                    subjectDTOinsert.setSubcode(mprotocol.get_single_tuple(
                                            mprotocol.LEN_SUB_CODE, buf,
                                            mprotocol.LEN_PROTOCOL_TYPE + mprotocol.STU_LEN_PROTOCOL_TYPE + mprotocol.LEN_CODE
                                    ));
                                    subjectDTOinsert.setSubname(mprotocol.get_single_tuple(
                                            mprotocol.LEN_SUB_NAME, buf,
                                            mprotocol.LEN_PROTOCOL_TYPE + mprotocol.STU_LEN_PROTOCOL_TYPE + mprotocol.LEN_CODE +
                                                    mprotocol.LEN_SUB_CODE
                                    ));
                                    subjectDTOinsert.setSubinfo(mprotocol.get_single_tuple(
                                            mprotocol.LEN_SUB_INFO, buf,
                                            Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                    Mprotocol.LEN_SUB_CODE + Mprotocol.LEN_SUB_NAME
                                    ));
                                    subjectDTOinsert.setSubgrade(mprotocol.get_single_int( buf,
                                            Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                    Mprotocol.LEN_SUB_CODE + Mprotocol.LEN_SUB_NAME + Mprotocol.LEN_SUB_INFO
                                    ));
                                    subjectDTOinsert.setFmanid(mprotocol.get_single_tuple(
                                            Mprotocol.LEN_ID, buf,
                                            Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                    Mprotocol.LEN_SUB_CODE + Mprotocol.LEN_SUB_NAME + Mprotocol.LEN_SUB_INFO + Mprotocol.LEN_SUB_GRADE
                                    ));
                                    bos.write(managerView.create_subject(subjectDTOinsert));
                                    bos.flush();
                                    break;
                                case Mprotocol.M_SUB_CODE_ANS:
                                    SubjectDTO subjectDTOcode = new SubjectDTO();
                                    subjectDTOcode.setSubcode(mprotocol.get_single_tuple(
                                            mprotocol.LEN_SUB_CODE, buf,
                                            mprotocol.LEN_PROTOCOL_TYPE + mprotocol.STU_LEN_PROTOCOL_TYPE + mprotocol.LEN_CODE
                                    ));
                                    bos.write(managerView.update_subject_code_req(subjectDTOcode));
                                    bos.flush();
                                    break;
                                case Mprotocol.M_SUB_REC_ANS:
                                    SubjectDTO subjectDTOrecord = new SubjectDTO();

                                    subjectDTOrecord.setSubname(mprotocol.get_single_tuple(
                                            mprotocol.LEN_SUB_NAME, buf,
                                            mprotocol.LEN_PROTOCOL_TYPE + mprotocol.STU_LEN_PROTOCOL_TYPE + mprotocol.LEN_CODE
                                    ));
                                    subjectDTOrecord.setSubinfo(mprotocol.get_single_tuple(
                                            mprotocol.LEN_SUB_INFO, buf,
                                            Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                    Mprotocol.LEN_SUB_NAME
                                    ));
                                    subjectDTOrecord.setSubgrade(mprotocol.get_single_int( buf,
                                            Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                    Mprotocol.LEN_SUB_NAME + Mprotocol.LEN_SUB_INFO
                                    ));
                                    subjectDTOrecord.setFmanid(mprotocol.get_single_tuple(
                                            Mprotocol.LEN_ID, buf,
                                            Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                    Mprotocol.LEN_SUB_NAME + Mprotocol.LEN_SUB_INFO + Mprotocol.LEN_SUB_GRADE
                                    ));
                                    bos.write(managerView.update_subject_record_req(subjectDTOrecord));
                                    bos.flush();

                                    break;
                                case Mprotocol.M_SUB_DEL_ANS:
                                    SubjectDTO subjectDTOdelete = new SubjectDTO();
                                    subjectDTOdelete.setSubcode(mprotocol.get_single_tuple(
                                            mprotocol.LEN_SUB_CODE, buf,
                                            mprotocol.LEN_PROTOCOL_TYPE + mprotocol.STU_LEN_PROTOCOL_TYPE + mprotocol.LEN_CODE
                                    ));
                                    bos.write(managerView.delete_subject(subjectDTOdelete));
                                    bos.flush();
                                    break;
                            }
                            break;
                        case Mprotocol.MAN_TERM_SET_ANS:
                            switch (packetCode) {
                                case Mprotocol.M_PLAN_TIME_ANS:
                                    int pyear, pmonth, pday;
                                    CreatedsubjectDTO createdsubjectDTOplanupdate = new CreatedsubjectDTO();
                                    pyear = mprotocol.get_single_int(buf);
                                    pmonth = mprotocol.get_single_int(buf, 3 + 4);
                                    pday = mprotocol.get_single_int(buf, 3 + 4 + 4);
                                    createdsubjectDTOplanupdate.setPlanstartdate(LocalDate.of(pyear, pmonth, pday));
                                    pyear = mprotocol.get_single_int(buf, 3 + 4 + 4 + 4);
                                    pmonth = mprotocol.get_single_int(buf, 3 + 4 + 4 + 4 + 4);
                                    pday = mprotocol.get_single_int(buf, 3 + 4 + 4 + 4 + 4 + 4);
                                    createdsubjectDTOplanupdate.setPlanenddate(LocalDate.of(pyear, pmonth, pday));
                                    bos.write(managerView.update_cs_plan_time(createdsubjectDTOplanupdate));
                                    bos.flush();
                                    break;
                                case Mprotocol.M_APPLY_TIME_ANS:
                                    int ayear, amonth, aday;
                                    CreatedsubjectDTO createdsubjectDTOapplyupdate = new CreatedsubjectDTO();
                                    createdsubjectDTOapplyupdate.setCreatedsubgrade(mprotocol.get_single_int(buf));
                                    ayear = mprotocol.get_single_int(buf, 3 + 4 );
                                    amonth = mprotocol.get_single_int(buf, 3 + 4 + 4);
                                    aday = mprotocol.get_single_int(buf, 3 + 4 + 4 + 4);
                                    createdsubjectDTOapplyupdate.setApplystartdate(LocalDate.of(ayear, amonth, aday));
                                    ayear = mprotocol.get_single_int(buf, 3 + 4 + 4 + 4 + 4);
                                    amonth = mprotocol.get_single_int(buf, 3 + 4 + 4 + 4 + 4 + 4);
                                    aday = mprotocol.get_single_int(buf, 3 + 4 + 4 + 4 + 4 + 4 + 4);
                                    createdsubjectDTOapplyupdate.setApplyenddate(LocalDate.of(ayear, amonth, aday));
                                    bos.write(managerView.update_cs_apply_time(createdsubjectDTOapplyupdate));
                                    bos.flush();
                                    break;
                            }
                            break;
                        case Mprotocol.MAN_INFO_ANS:
                            switch (packetCode) {
                                case Mprotocol.M_PRO_CALL_NUM_ANS:
                                    bos.write(managerView.check_professor_num
                                            (mprotocol.get_single_tuple
                                                    (mprotocol.LEN_SUB_CODE, buf)));
                                    bos.flush();
                                    bos.write(mprotocol.getPacket(Mprotocol.ACCOUNT_INFO_RESULT, Mprotocol.PT_UNDEFINED, Mprotocol.SUCCESS));
                                    bos.flush();
                                    break;
                                case Mprotocol.M_STU_CALL_NUM_ANS:
                                    bos.write(managerView.check_student_num
                                            (mprotocol.get_single_int
                                                    (buf)));
                                    bos.flush();
                                    bos.write(mprotocol.getPacket(Mprotocol.ACCOUNT_INFO_RESULT, Mprotocol.PT_UNDEFINED, Mprotocol.SUCCESS));
                                    bos.flush();
                                    break;
                                case Mprotocol.M_CS_CALL_NUM_ANS:
                                    bos.write(managerView.check_cs_num
                                            (mprotocol.get_single_tuple
                                                    (mprotocol.LEN_CS_CODE, buf)));
                                    bos.flush();
                                    bos.write(mprotocol.getPacket(Mprotocol.ACCOUNT_INFO_RESULT, Mprotocol.PT_UNDEFINED, Mprotocol.SUCCESS));
                                    bos.flush();
                                    break;
                            }
                            break;
                        case Mprotocol.MAN_MENU_ANS:
                            switch(packetCode) {
                                case Mprotocol.M_MENU_PRO_CREATE:
                                    bos.write(mprotocol.getPacket(Mprotocol.ACCOUNT_INFO_REQ, Mprotocol.PT_UNDEFINED, Mprotocol.M_PRO_ID_REQ));
                                    bos.flush();
                                    break;
                                case Mprotocol.M_MENU_STU_CREATE:
                                    bos.write(mprotocol.getPacket(Mprotocol.ACCOUNT_INFO_REQ, Mprotocol.PT_UNDEFINED, Mprotocol.M_STU_ID_REQ));
                                    bos.flush();
                                    break;
                                case Mprotocol.M_MENU_SUB_CREATE:
                                    bos.write(mprotocol.getPacket(Mprotocol.MAN_SUBJECT_SET_REQ, Mprotocol.PT_UNDEFINED, Mprotocol.M_SUB_INS_REQ));
                                    bos.flush();
                                    break;
                                case Mprotocol.M_MENU_SUB_UPDATE:
                                    bos.write(mprotocol.getPacket(Mprotocol.MAN_SUBJECT_SET_REQ, Mprotocol.PT_UNDEFINED, Mprotocol.M_SUB_CODE_REQ));
                                    bos.flush();
                                    break;
                                case Mprotocol.M_MENU_SUB_DELETE:
                                    bos.write(mprotocol.getPacket(Mprotocol.MAN_SUBJECT_SET_REQ, Mprotocol.PT_UNDEFINED, Mprotocol.M_SUB_DEL_REQ));
                                    bos.flush();
                                    break;
                                case Mprotocol.M_MENU_PLAN_UPDATE:
                                    bos.write(mprotocol.getPacket(Mprotocol.MAN_TERM_SET_REQ, Mprotocol.PT_UNDEFINED, Mprotocol.M_PLAN_TIME_ANS));
                                    bos.flush();
                                    break;
                                case Mprotocol.M_MENU_APPLY_UPDATE:
                                    bos.write(mprotocol.getPacket(Mprotocol.MAN_TERM_SET_REQ, Mprotocol.PT_UNDEFINED, Mprotocol.M_APPLY_TIME_ANS));
                                    bos.flush();
                                    break;
                                case Mprotocol.M_MENU_PRO_INFO:
                                    bos.write(mprotocol.getPacket(Mprotocol.MAN_INFO_REQ, Mprotocol.PT_UNDEFINED, Mprotocol.M_PRO_CALL_NUM_REQ));
                                    bos.flush();
                                    break;
                                case Mprotocol.M_MENU_STU_INFO:
                                    bos.write(mprotocol.getPacket(Mprotocol.MAN_INFO_REQ, Mprotocol.PT_UNDEFINED, Mprotocol.M_STU_CALL_NUM_REQ));
                                    bos.flush();
                                    break;
                                case Mprotocol.M_MENU_CS_INFO:
                                    bos.write(mprotocol.getPacket(Mprotocol.MAN_INFO_REQ, Mprotocol.PT_UNDEFINED, Mprotocol.M_CS_CALL_NUM_REQ));
                                    bos.flush();
                                    break;
                            }
                            break;
                        case Rprotocol.ACCOUNT_INFO_RESULT:
                            switch (packetCode) {
                                case Rprotocol.STU_LOGIN_FAIL_CODE:
                                    System.out.println("해당클라이언트3번이상 실패로 종료함");
                                    clientout = true;

                            }

                            //------------------------------------------
                        case Rprotocol.ACCOUNT_INFO_ANS:

                            switch (packetCode) {
                                case Mprotocol.M_MAN_ID_ANS:    // 관리자 ID
                                    bos.write(managerView.check_manager_id  // ID를 비교해서 분기문을 생성 후 패킷 전송
                                            (mprotocol.get_single_tuple  // 패킷에서 man_id만 추출
                                                    (Mprotocol.LEN_ID, buf)));
                                    bos.flush();
                                    break;
                                case Mprotocol.M_MAN_PW_ANS:    // 관리자 PW
                                    bos.write(managerView.check_manager_pw  // PW를 비교해서 분기문을 생성 후 패킷 전송
                                            (mprotocol.get_single_tuple // 패킷에서 man_pw만 추출
                                                    (Mprotocol.LEN_PW, buf)));
                                    bos.flush();
                                    break;
                                case Mprotocol.M_PRO_ID_ANS:    // 교수 ID
                                    bos.write(managerView.check_professor_id // ID를 비교해서 분기문을 생성 후 패킷 전송
                                            (mprotocol.get_single_tuple // 패킷에서 pro_id만 추출
                                                    (Mprotocol.LEN_ID, buf)));
                                    bos.flush();
                                    break;
                                case Mprotocol.M_PRO_PW_ANS:    // 교수 PW
                                    bos.write(managerView.check_professor_pw // PW를 비교해서 분기문을 생성 후 패킷 전송
                                            (mprotocol.get_single_tuple // 패킷에서 pro_pw만 추출
                                                    (Mprotocol.LEN_PW, buf)));
                                    bos.flush();
                                    break;
                                case Mprotocol.M_PRO_NUM_ANS:    // 교수 번호
                                    bos.write(managerView.update_professor_account   // 맞는 교수 번호 SELECT 확인 후 UPDATE 실행
                                            (mprotocol.get_single_tuple // 패킷에서 pro_num만 추출
                                                    (Mprotocol.LEN_NUM, buf)));
                                    bos.flush();
                                    break;
                                case Mprotocol.M_STU_ID_ANS:    // 학생 ID
                                    bos.write(managerView.check_student_id // ID를 비교해서 분기문을 생성 후 패킷 전송
                                            (mprotocol.get_single_tuple // 패킷에서 stu_id만 추출
                                                    (Mprotocol.LEN_ID, buf)));
                                    bos.flush();
                                    break;
                                case Mprotocol.M_STU_PW_ANS:    // 학생 PW
                                    bos.write(managerView.check_student_pw // PW를 비교해서 분기문을 생성 후 패킷 전송
                                            (mprotocol.get_single_tuple // 패킷에서 stu_pw만 추출
                                                    (Mprotocol.LEN_PW, buf)));
                                    bos.flush();
                                    break;
                                case Mprotocol.M_STU_NUM_ANS:    // 학생 번호
                                    bos.write(managerView.update_student_account   // 맞는 학생 번호 SELECT 확인 후 UPDATE 실행
                                            (mprotocol.get_single_int // 패킷에서 stu_num만 추출
                                                    (buf)));
                                    bos.flush();
                                    break;
                                case Rprotocol.STU_ID_PWD_ANS_CODE:
                                    System.out.println("클라이언트가 idpwd보냄");
                                    String[] studentidpwd = new String[2];//클라가보낸 id,pwd저장
                                    int k = 3;
                                    for (int i = 0; i < 2; i++) {

                                        slicecount = Arrays.copyOfRange(buf, k, k + 4);
                                        datalen = proto.byteArrayToInt(slicecount);
                                        System.out.println(datalen);
                                        k = k + 4;
                                        data = Arrays.copyOfRange(buf, k, k + datalen);
                                        studentidpwd[i] = new String(data);
                                        k = k + datalen;
                                    }
                                    //추출한 id pwd로 db에서 일치학생있는지 확인 3번까지 허용
                                    if (count == 3) {
                                        proto = new Rprotocol(Rprotocol.ACCOUNT_INFO_RESULT, Rprotocol.PT_UNDEFINED, Rprotocol.STU_LOGIN_FAIL_CODE);
                                        bos.write(proto.getPacket());
                                        bos.flush();
                                        break;
                                    }
                                    if (count < 3) {
                                        String check = registService.check_student(studentidpwd[0], studentidpwd[1]);
                                        if (check.equals("0")) {
                                            System.out.println("일치정보없음");
                                            count++;
                                            proto = new Rprotocol(Rprotocol.ACCOUNT_INFO_REQ, Rprotocol.PT_UNDEFINED, Rprotocol.STU_ID_PWD_REQ_CODE);
                                            bos.write(proto.getPacket());
                                            bos.flush();
                                            break;
                                        } else {
                                            System.out.println("로그인성공");
                                            proto = new Rprotocol(Rprotocol.ACCOUNT_INFO_RESULT, Rprotocol.PT_UNDEFINED, Rprotocol.STU_LOGIN_SUCCESS_CODE);
                                            getstudent = registDAO.get_student_by_id_password(studentidpwd[0], studentidpwd[1]);
                                            stuid = studentidpwd[0];
                                            stupwd = studentidpwd[1];
                                            stugrade = getstudent.getStugrade();
                                            stunum=getstudent.getStunum();
                                            bos.write(proto.getPacket());
                                            bos.flush();
                                            break;
                                        }
                                    }
                                    break;
                                /*case Rprotocol.WHO_INFO_ANS:
                                    slicecount = Arrays.copyOfRange(buf, 3, 3 + 4);
                                    datalen = proto.byteArrayToInt(slicecount);
                                    data = Arrays.copyOfRange(buf, 7, 7 + datalen);
                                    String roll = new String(data);
                                    if (roll.equals("1")) {
                                        proto = new Rprotocol(Rprotocol.ACCOUNT_INFO_REQ, Rprotocol.PT_UNDEFINED, Rprotocol.WHO_INFO_REQ);
                                        bos.write(proto.getPacket());
                                        bos.flush();
                                    } else if (roll.equals("2")) {
                                        proto = new Rprotocol(Rprotocol.ACCOUNT_INFO_REQ, Rprotocol.PT_UNDEFINED, Rprotocol.WHO_INFO_REQ);
                                        bos.write(proto.getPacket());
                                        bos.flush();
                                    } else if (roll.equals("3")) {
                                        proto = new Rprotocol(Rprotocol.ACCOUNT_INFO_REQ, Rprotocol.PT_UNDEFINED, Rprotocol.STU_ID_PWD_REQ_CODE);
                                        bos.write(proto.getPacket());
                                        bos.flush();
                                    } else {
                                        System.out.println("somthing wrong");
                                    }
                                    break;*/

                            }
                            break;
                            //--------------------------------
                        case Rprotocol.MENU_REQ:
                            //로그인성공은 아니지만 클라이언트가 메뉴요청 패킷보내면 이패킷을 만들어 보낸다 그러면클라이언트는 메뉴출력을 한다
                            proto = new Rprotocol(Rprotocol.ACCOUNT_INFO_RESULT, Rprotocol.PT_UNDEFINED, Rprotocol.STU_LOGIN_SUCCESS_CODE);
                            bos.write(proto.getPacket());
                            bos.flush();
                            break;
                    }
                } else {//학생관련 타입
                    switch (packetstuType) {
                        case Rprotocol.REGIST_REQ:
                            switch (packetCode){
                                case Rprotocol.REGIST_REQ_CODE:
                                    System.out.println("클라이언트가 수강신청요청보냄");
                                    //현재학생학년과같은 개설교과목 목록 전송
                                    clist=registDAO.get_grade_created_subject(stugrade);//학생학년과일치과목목록
                                    clistcount=clist.size();//과목개수
                                    temp=new byte[Rprotocol.LEN_MAX];//과목수,과목코드,과목이름저장할임시배열
                                    System.arraycopy(proto.intto4byte(clistcount),0,temp,0,proto.intto4byte(clistcount).length);
                                    pos=4;
                                    for (CreatedsubjectDTO one:clist) {
                                        int onesubjectcodelen=one.getCreatedsubcode().length();
                                        System.arraycopy(proto.intto4byte(onesubjectcodelen),0,temp,pos,proto.intto4byte(onesubjectcodelen).length);
                                        pos+=4;
                                        System.arraycopy(one.getCreatedsubcode().getBytes(),0,temp,pos,one.getCreatedsubcode().getBytes().length);

                                        pos+=one.getCreatedsubcode().getBytes().length;
                                        //한글데이터는 길이계산이다르므로 반드시 getBytes.length로 길이정보획득해야함----------------------------
                                        byte[] subnamebyte=one.getCreatedsubname().getBytes();
                                        int onesubjectnamelen=subnamebyte.length;
                                        System.arraycopy(proto.intto4byte(onesubjectnamelen),0,temp,pos,proto.intto4byte(onesubjectnamelen).length);
                                        pos+=4;
                                        System.arraycopy(one.getCreatedsubname().getBytes(),0,temp,pos,one.getCreatedsubname().getBytes().length);
                                        pos+=one.getCreatedsubname().getBytes().length;
                                    }
                                    proto.setPacket_type_and_code(Rprotocol.PT_UNDEFINED, Rprotocol.CREATE_SUBJECT_INFO_ANS, Rprotocol.CREAT_SUBJECT_GRADE_CODE);
                                    System.out.println();
                                    proto.setPacket(Rprotocol.PT_UNDEFINED, Rprotocol.CREATE_SUBJECT_INFO_ANS, Rprotocol.CREAT_SUBJECT_GRADE_CODE, temp);

                                    bos.write(proto.getPacket());
                                    bos.flush();
                                    break;

                                case Rprotocol.REGIST_CANCEL_CODE:
                                    System.out.println("클라이언트가 수강취소요청보냄");
                                    temp=new byte[Rprotocol.LEN_MAX];//과목수,과목코드,과목이름저장할임시배열
                                    clist=registService.getmysubject(stuid,stupwd);
                                    clistcount=clist.size();
                                    System.arraycopy(proto.intto4byte(clistcount),0,temp,0,proto.intto4byte(clistcount).length);
                                    pos=4;
                                    for (CreatedsubjectDTO one:clist) {
                                        int onesubjectcodelen=one.getCreatedsubcode().length();
                                        System.arraycopy(proto.intto4byte(onesubjectcodelen),0,temp,pos,proto.intto4byte(onesubjectcodelen).length);
                                        pos+=4;
                                        System.arraycopy(one.getCreatedsubcode().getBytes(),0,temp,pos,one.getCreatedsubcode().getBytes().length);
                                        pos+=one.getCreatedsubcode().getBytes().length;

                                        //한글데이터는 길이계산이다르므로 반드시 getBytes.length로 길이정보획득해야함----------------------------
                                        byte[] subnamebyte=one.getCreatedsubname().getBytes();
                                        int onesubjectnamelen=subnamebyte.length;
                                        System.arraycopy(proto.intto4byte(onesubjectnamelen),0,temp,pos,proto.intto4byte(onesubjectnamelen).length);
                                        pos+=4;
                                        System.arraycopy(one.getCreatedsubname().getBytes(),0,temp,pos,one.getCreatedsubname().getBytes().length);
                                        pos+=one.getCreatedsubname().getBytes().length;
                                    }
                                    proto.setPacket_type_and_code(Rprotocol.PT_UNDEFINED, Rprotocol.MY_REGIST_ANS, Rprotocol.SUBJECT_CODE_INFO_CODE);
                                    System.out.println();
                                    proto.setPacket(Rprotocol.PT_UNDEFINED, Rprotocol.MY_REGIST_ANS, Rprotocol.SUBJECT_CODE_INFO_CODE, temp);

                                    bos.write(proto.getPacket());
                                    bos.flush();
                                    break;
                            }
                            break;
                            //-----------------------------------
                        case Rprotocol.SEL_SUBJECT_ANS:
                            switch (packetCode){
                                case Rprotocol.SELECT_REGIST_SUBJECT_CODE:
                                    datalen=proto.byteArrayToInt(Arrays.copyOfRange(buf,pos,pos+4));//4byte 길이정보 int 변환
                                    pos+=4;//4byte 읽었으므로 pos를 4만큼 증가

                                    temp = Arrays.copyOfRange(buf, pos, pos+datalen);//추출한길이만큼읽어 코드추출
                                     temps= new String(temp);//추출 과목코드를  string으로 변환하여 저장

                                    System.out.println("클라가선택한 코드:"+temps);
                                    int registcheck=registService.regist_check(stuid, stupwd,temps);//수강가능한지 체크 신청기간x 2, 시간표와 중복 3
                                    if(registcheck==2){
                                        proto.setPacket_type_and_code(Rprotocol.PT_UNDEFINED, Rprotocol.REGIST_RESULT, Rprotocol.NOT_REGIST_DAY_CODE);

                                        proto.setPacket(Rprotocol.PT_UNDEFINED, Rprotocol.REGIST_RESULT, Rprotocol.NOT_REGIST_DAY_CODE);

                                        bos.write(proto.getPacket());
                                        bos.flush();
                                        break;
                                    }
                                    else if(registcheck==3){
                                        proto.setPacket_type_and_code(Rprotocol.PT_UNDEFINED, Rprotocol.REGIST_RESULT, Rprotocol.DUP_TIME_CODE);

                                        proto.setPacket(Rprotocol.PT_UNDEFINED, Rprotocol.REGIST_RESULT, Rprotocol.DUP_TIME_CODE);

                                        bos.write(proto.getPacket());
                                        bos.flush();
                                        break;
                                    }

                                    synchronized (this){
                                    int registresult=registService.regist_excute(stuid,stupwd,temps);//수강신청 synchronize 성공 1, 인원초과 4
                                        if(registresult==4){
                                            proto.setPacket_type_and_code(Rprotocol.PT_UNDEFINED, Rprotocol.REGIST_RESULT, Rprotocol.MAX_STU_CODE);
                                            proto.setPacket(Rprotocol.PT_UNDEFINED, Rprotocol.REGIST_RESULT, Rprotocol.MAX_STU_CODE);
                                            bos.write(proto.getPacket());
                                            bos.flush();
                                            break;
                                        }
                                        else if(registresult==1){
                                            proto.setPacket_type_and_code(Rprotocol.PT_UNDEFINED, Rprotocol.REGIST_RESULT, Rprotocol.REGIST_SUCESS_CODE);
                                            proto.setPacket(Rprotocol.PT_UNDEFINED, Rprotocol.REGIST_RESULT, Rprotocol.REGIST_SUCESS_CODE);
                                            bos.write(proto.getPacket());
                                            bos.flush();
                                            break;
                                        }
                                    }

                                    break;
                                case Rprotocol.SELECT_CANCLE_SUBJECT_CODE:
                                    datalen=proto.byteArrayToInt(Arrays.copyOfRange(buf,pos,pos+4));//4byte 길이정보 int 변환
                                    pos+=4;//4byte 읽었으므로 pos를 4만큼 증가

                                    temp = Arrays.copyOfRange(buf, pos, pos+datalen);//추출한길이만큼읽어 코드추출
                                    temps= new String(temp);//추출 과목코드를  string으로 변환하여 저장

                                    System.out.println("클라가선택한 코드:"+temps);
                                    registService.deletemysubject(stuid,stupwd,temps);
                                    System.out.println("수강취소완료");
                                    proto.setPacket(Rprotocol.PT_UNDEFINED, Rprotocol.REGIST_CANSEL_RESULT, Rprotocol.PT_UNDEFINED);
                                    bos.write(proto.getPacket());
                                    bos.flush();
                                    break;
                                case Rprotocol.SELECT_PLAN_SUBJECT_CODE:
                                    System.out.println("학생클라이언트가 강의조회하고자하는 코드보냄");
                                    datalen=proto.byteArrayToInt(Arrays.copyOfRange(buf,pos,pos+4));//4byte 길이정보 int 변환
                                    pos+=4;//4byte 읽었으므로 pos를 4만큼 증가

                                    temp = Arrays.copyOfRange(buf, pos, pos+datalen);//추출한길이만큼읽어 코드추출
                                    temps= new String(temp);//추출 과목코드를  string으로 변환하여 저장
                                    System.out.println("클라가선택한 코드:"+temps);
                                    getsubject=registDAO.get_one_by_created_code(temps);
                                    subjectplaninfo=getsubject.getClassplan();
                                    byte[] subjectplaninfobyte=subjectplaninfo.getBytes();
                                    int onesubjectplsnlen=subjectplaninfobyte.length;
                                    pos=0;
                                    data=new byte[Rprotocol.LEN_MAX];
                                    System.arraycopy(proto.intto4byte(onesubjectplsnlen),0,data,pos,proto.intto4byte(onesubjectplsnlen).length);
                                    pos+=4;
                                    System.arraycopy(subjectplaninfo.getBytes(),0,data,pos,subjectplaninfo.getBytes().length);
                                    pos+=subjectplaninfo.getBytes().length;
                                    proto.setPacket(Rprotocol.PT_UNDEFINED, Rprotocol.SUBJECT_PLAN_RESULT, Rprotocol.PT_UNDEFINED,data);
                                    bos.write(proto.getPacket());
                                    bos.flush();
                                    break;
                            }
                            break;
                            //---------------------------------
                        case Rprotocol.STU_INFO_UPDATE_REQ:
                            switch (packetCode){
                                case Rprotocol.PWD_UPDATE_REQ_CODE:
                                    System.out.println("학생클라이언트가 비밀번호 변경 요청 보냄");
                                    proto.setPacket(Rprotocol.PT_UNDEFINED, Rprotocol.STU_INFO_UPDATE_REQ, Rprotocol.PWD_UPDATE_IN_REQ_CODE);
                                    bos.write(proto.getPacket());
                                    bos.flush();
                            }
                            break;
                            //-------------------------------------
                        case Rprotocol.STU_INFO_UPDATE_ANS:
                            System.out.println("클라이언트가 변경할 비밀번호 보냄");
                            datalen=proto.byteArrayToInt(Arrays.copyOfRange(buf,pos,pos+4));//4byte 길이정보 int 변환
                            pos+=4;//4byte 읽었으므로 pos를 4만큼 증가

                            temp = Arrays.copyOfRange(buf, pos, pos+datalen);//추출한길이만큼읽어 코드추출
                            temps= new String(temp);//추출 비밀번호를  string으로 변환하여 저장
                            //db에서 학생학번위치찾아 받은 비밀번호로 변경
                            registDAO.update_cur_student_password(temps,stunum);
                            stupwd=temps;

                            proto.setPacket(Rprotocol.PT_UNDEFINED, Rprotocol.STU_INFO_UPDATE_RESULT, Rprotocol.PT_UNDEFINED);
                            bos.write(proto.getPacket());
                            bos.flush();
                            break;
                            //-----------------------------------------
                        case Rprotocol.CREATE_SUBJECT_INFO_REQ:
                            switch (packetCode){
                                case Rprotocol.SIMPLE_LOOK_CODE:
                                    System.out.println("학생클라이언트가 전체개설교과목 목록 요청 보냄");
                                    clist=registDAO.get_all_created_subject();//전체 개설 과목목록
                                    clistcount=clist.size();//과목개수
                                    temp=new byte[Rprotocol.LEN_MAX];//과목수,과목코드,과목이름저장할임시배열
                                    System.arraycopy(proto.intto4byte(clistcount),0,temp,0,proto.intto4byte(clistcount).length);
                                    pos=4;
                                    for (CreatedsubjectDTO one:clist) {
                                        int onesubjectcodelen=one.getCreatedsubcode().length();
                                        System.arraycopy(proto.intto4byte(onesubjectcodelen),0,temp,pos,proto.intto4byte(onesubjectcodelen).length);
                                        pos+=4;
                                        System.arraycopy(one.getCreatedsubcode().getBytes(),0,temp,pos,one.getCreatedsubcode().getBytes().length);

                                        pos+=one.getCreatedsubcode().getBytes().length;
                                        //한글데이터는 길이계산이다르므로 반드시 getBytes.length로 길이정보획득해야함----------------------------
                                        byte[] subnamebyte=one.getCreatedsubname().getBytes();
                                        int onesubjectnamelen=subnamebyte.length;
                                        System.arraycopy(proto.intto4byte(onesubjectnamelen),0,temp,pos,proto.intto4byte(onesubjectnamelen).length);
                                        pos+=4;
                                        System.arraycopy(one.getCreatedsubname().getBytes(),0,temp,pos,one.getCreatedsubname().getBytes().length);
                                        pos+=one.getCreatedsubname().getBytes().length;
                                    }
                                    proto.setPacket_type_and_code(Rprotocol.PT_UNDEFINED, Rprotocol.CREATE_SUBJECT_INFO_ANS, Rprotocol.CREAT_SUBJECT_GRADE_ALL_CODE);
                                    System.out.println();
                                    proto.setPacket(Rprotocol.PT_UNDEFINED, Rprotocol.CREATE_SUBJECT_INFO_ANS, Rprotocol.CREAT_SUBJECT_GRADE_ALL_CODE, temp);

                                    bos.write(proto.getPacket());
                                    bos.flush();
                                    break;
                                case Rprotocol.SUBJECT_PLAN_REQ_CODE:
                                    System.out.println("학생클라이언트가 전체개설교과목 목록 요청 보냄");
                                    clist=registDAO.get_all_created_subject();//전체 개설 과목목록
                                    clistcount=clist.size();//과목개수
                                    temp=new byte[Rprotocol.LEN_MAX];//과목수,과목코드,과목이름저장할임시배열
                                    System.arraycopy(proto.intto4byte(clistcount),0,temp,0,proto.intto4byte(clistcount).length);
                                    pos=4;
                                    for (CreatedsubjectDTO one:clist) {
                                        int onesubjectcodelen=one.getCreatedsubcode().length();
                                        System.arraycopy(proto.intto4byte(onesubjectcodelen),0,temp,pos,proto.intto4byte(onesubjectcodelen).length);
                                        pos+=4;
                                        System.arraycopy(one.getCreatedsubcode().getBytes(),0,temp,pos,one.getCreatedsubcode().getBytes().length);

                                        pos+=one.getCreatedsubcode().getBytes().length;
                                        //한글데이터는 길이계산이다르므로 반드시 getBytes.length로 길이정보획득해야함----------------------------
                                        byte[] subnamebyte=one.getCreatedsubname().getBytes();
                                        int onesubjectnamelen=subnamebyte.length;
                                        System.arraycopy(proto.intto4byte(onesubjectnamelen),0,temp,pos,proto.intto4byte(onesubjectnamelen).length);
                                        pos+=4;
                                        System.arraycopy(one.getCreatedsubname().getBytes(),0,temp,pos,one.getCreatedsubname().getBytes().length);
                                        pos+=one.getCreatedsubname().getBytes().length;
                                    }
                                    proto.setPacket_type_and_code(Rprotocol.PT_UNDEFINED, Rprotocol.CREATE_SUBJECT_INFO_ANS, Rprotocol.CREAT_SUBJECT_GRADE_ALL_AND_PLAN_CODE);
                                    System.out.println();
                                    proto.setPacket(Rprotocol.PT_UNDEFINED, Rprotocol.CREATE_SUBJECT_INFO_ANS, Rprotocol.CREAT_SUBJECT_GRADE_ALL_AND_PLAN_CODE, temp);

                                    bos.write(proto.getPacket());
                                    bos.flush();
                                    break;
                            }
                            break;
                            //-----------------------------------------------
                        case Rprotocol.MY_TIMETABLE_REQ:
                            clist=registService.getmysubject(stuid,stupwd);//학생자신이 수강하고있는 과목 list get
                            clistcount=clist.size();//과목개수
                            temp=new byte[Rprotocol.LEN_MAX];//과목수,과목코드,과목이름저장할임시배열
                            System.arraycopy(proto.intto4byte(clistcount),0,temp,0,proto.intto4byte(clistcount).length);
                            pos=4;
                            for (CreatedsubjectDTO one:clist) {
                                //1개교과목 요일정보temp저장
                                int day=one.getSubday();
                                System.arraycopy(proto.intto4byte(day),0,temp,pos,proto.intto4byte(day).length);
                                pos+=4;
                                //1개교과목 시작시간정보temp저장
                                byte[] substarttimebyte=one.getClassstarttime().toString().getBytes();
                                int onesubjectstarttimelen=substarttimebyte.length;
                                System.arraycopy(proto.intto4byte(onesubjectstarttimelen),0,temp,pos,proto.intto4byte(onesubjectstarttimelen).length);
                                pos+=4;
                                System.arraycopy(substarttimebyte,0,temp,pos,substarttimebyte.length);
                                pos+=one.getClassstarttime().toString().getBytes().length;
                                //1개교과목 종료시간정보temp저장
                                byte[] subendtimebyte=one.getClassendtime().toString().getBytes();
                                int onesubjectendtimelen=subendtimebyte.length;
                                System.arraycopy(proto.intto4byte(onesubjectendtimelen),0,temp,pos,proto.intto4byte(onesubjectendtimelen).length);
                                pos+=4;
                                System.arraycopy(subendtimebyte,0,temp,pos,subendtimebyte.length);
                                pos+=one.getClassendtime().toString().getBytes().length;
                            }
                            proto.setPacket_type_and_code(Rprotocol.PT_UNDEFINED, Rprotocol.MY_REGIST_ANS, Rprotocol.SUBJECT_TIME_INFO_CODE);
                            System.out.println();
                            proto.setPacket(Rprotocol.PT_UNDEFINED, Rprotocol.MY_REGIST_ANS, Rprotocol.SUBJECT_TIME_INFO_CODE, temp);

                            bos.write(proto.getPacket());
                            bos.flush();
                            break;

                    }
                }
            } while (!clientout);
            conn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

        /*RegistDAO registDAO = new RegistDAO(MybatisConnectionFactory.getSqlSessionFactory());
        RegistService registService = new RegistService(registDAO);
        RegistView registView = new RegistView();

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("원하는 대분류 기능 선택 종료는 -1 입력");
            System.out.println("1.학생 crd 2.교과목 crd 3.개설 교과목 crd,수강신청기간변경  4.수강신청관련");
            int startmenu = sc.nextInt();
            if(startmenu==-1){
                break;
            }

            switch (startmenu) {
                case 1:
                    System.out.println("원하는 소분류 기능 선택");
                    int firstmenu = sc.nextInt();
                    switch (firstmenu) {

                    }
                    break;
                case 2:
                    System.out.println("원하는 소분류 기능 선택");
                    int secondmenu = sc.nextInt();
                    switch (secondmenu) {

                    }
                    break;
                case 3:
                    System.out.println("원하는 소분류 기능 선택");
                    int thirdmenu = sc.nextInt();
                    switch (thirdmenu) {

                    }
                    break;
                case 4:
                    System.out.println("원하는 소분류 기능 선택");
                    System.out.println("1.수강신청2.수강조회3.수강삭제 4.교수 자신과목 신청학생 조회");

                    int fourthmenu = sc.nextInt();
                    String id = "kumid";
                    String password = "kumpass";
                    switch (fourthmenu) {
                        case 1:
                            //학생아이디,비밀번호 입력가정
                            System.out.println("아이디로" + id + "입력됨");
                            System.out.println("비밀번호로" + password + "입력됨");
                            registService.regist(id, password);
                            System.out.println("신청서비스종료");
                            break;
                        case 2:
                            //학생아이디,비밀번호 입력가정
                            System.out.println("아이디로" + id + "입력됨");
                            System.out.println("비밀번호로" + password + "입력됨");
                            List<CreatedsubjectDTO> r = registService.getmysubject(id, password);
                            registView.print_stu_subject(r);
                            System.out.println("조회서비스 종료");
                            break;
                        case 3:
                            //학생아이디,비밀번호 입력가정
                            System.out.println("아이디로" + id + "입력됨");
                            System.out.println("비밀번호로" + password + "입력됨");
                            registService.deletemysubject(id, password);
                            System.out.println("삭제서비스 종료");
                            break;
                        case 4:
                            //교수아이디,비밀번호 입력가정
                            System.out.println("교과목 수강신청학생목록조회");
                            String proid="kimsung";
                            String propass="kimpass";
                            System.out.println("아이디로" + proid + "입력됨");
                            System.out.println("비밀번호로" + propass + "입력됨");

                                List<StudentDTO> onepagestudent =registService.pageselect(proid,propass);
                                if(onepagestudent.size()==0)
                                {

                                    break;
                                }
                                else
                                {
                                    registView.print_stu_list(onepagestudent);
                                }

                            break;
                    }
                    break;
            }
        }*/
