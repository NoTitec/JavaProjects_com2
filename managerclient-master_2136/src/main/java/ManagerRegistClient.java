import java.net.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class ManagerRegistClient {
    static int pw_false = 0;
    static boolean id_duple = false;

    public static void print_manager_menu() {
        System.out.println("실행할 메뉴를 선택하세요. (0 입력시 종료)");
        System.out.println("1. 교수 계정 생성");
        System.out.println("2. 학생 계정 생성");
        System.out.println("3. 교과목 생성");
        System.out.println("4. 교과목 수정");
        System.out.println("5. 교과목 삭제");
        System.out.println("6. 강의 계획서 입력 기간 변경");
        System.out.println("7. 학년별 수강 신청 기간 변경");
        System.out.println("8. 교수 정보 조회");
        System.out.println("9. 학생 정보 조회");
        System.out.println("10. 개설교과목 정보 조회");
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        InetAddress addr=InetAddress.getLocalHost();
        String localname=addr.getHostName();
        String localip=addr.getHostAddress();
        System.out.println(localname+localip);
        Socket socket =new Socket(localip,3000);
        System.out.println("connect");
        OutputStream os = socket.getOutputStream();
        InputStream is = socket.getInputStream();

        Mprotocol mprotocol = new Mprotocol();
        byte[] buf = mprotocol.getPacket();

        try {
            while (true) {
                is.read(buf);
                int packetType = buf[0];
                int code = buf[2];
                mprotocol.m_setPacket(packetType, Mprotocol.PT_UNDEFINED, code, buf);
                BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
                switch (packetType) {
                    case Mprotocol.ACCOUNT_INFO_RESULT:
                        switch (code) {
                            case Mprotocol.MAN_LOGIN_SUCCESS_CODE:
                                pw_false = 0;
                                System.out.println("로그인 성공");
                            case Mprotocol.SUCCESS:
                                print_manager_menu();
                                Scanner sc = new Scanner(System.in);
                                int menu = sc.nextInt();
                                switch (menu) {
                                    case 0:
                                        is.close();
                                        os.close();
                                        socket.close();
                                        System.exit(0);
                                        break;
                                    case 1:
                                        System.out.println("교수 계정 생성 프로토콜 전송");
                                        mprotocol = new Mprotocol(Mprotocol.MAN_MENU_ANS, Mprotocol.PT_UNDEFINED, Mprotocol.M_MENU_PRO_CREATE);
                                        os.write(mprotocol.getPacket());
                                        break;
                                    case 2:
                                        System.out.println("학생 계정 생성 프로토콜 전송");
                                        mprotocol = new Mprotocol(Mprotocol.MAN_MENU_ANS, Mprotocol.PT_UNDEFINED, Mprotocol.M_MENU_STU_CREATE);
                                        os.write(mprotocol.getPacket());
                                        break;
                                    case 3:
                                        System.out.println("교과목 생성 프로토콜 전송");
                                        mprotocol = new Mprotocol(Mprotocol.MAN_MENU_ANS, Mprotocol.PT_UNDEFINED, Mprotocol.M_MENU_SUB_CREATE);
                                        os.write(mprotocol.getPacket());
                                        break;
                                    case 4:
                                        System.out.println("교과목 수정 프로토콜 전송");
                                        mprotocol = new Mprotocol(Mprotocol.MAN_MENU_ANS, Mprotocol.PT_UNDEFINED, Mprotocol.M_MENU_SUB_UPDATE);
                                        os.write(mprotocol.getPacket());
                                        break;
                                    case 5:
                                        System.out.println("교과목 삭제 프로토콜 전송");
                                        mprotocol = new Mprotocol(Mprotocol.MAN_MENU_ANS, Mprotocol.PT_UNDEFINED, Mprotocol.M_MENU_SUB_DELETE);
                                        os.write(mprotocol.getPacket());
                                        break;
                                    case 6:
                                        System.out.println("강의 계획서 입력 기간 변경 프로토콜 전송");
                                        mprotocol = new Mprotocol(Mprotocol.MAN_MENU_ANS, Mprotocol.PT_UNDEFINED, Mprotocol.M_MENU_PLAN_UPDATE);
                                        os.write(mprotocol.getPacket());
                                        break;
                                    case 7:
                                        System.out.println("학년별 수강 신청 기간 변경 프로토콜 전송");
                                        mprotocol = new Mprotocol(Mprotocol.MAN_MENU_ANS, Mprotocol.PT_UNDEFINED, Mprotocol.M_MENU_APPLY_UPDATE);
                                        os.write(mprotocol.getPacket());
                                        break;
                                    case 8:
                                        System.out.println("교수 정보 조회 프로토콜 전송");
                                        mprotocol = new Mprotocol(Mprotocol.MAN_MENU_ANS, Mprotocol.PT_UNDEFINED, Mprotocol.M_MENU_PRO_INFO);
                                        os.write(mprotocol.getPacket());
                                        break;
                                    case 9:
                                        System.out.println("학생 정보 조회 프로토콜 전송");
                                        mprotocol = new Mprotocol(Mprotocol.MAN_MENU_ANS, Mprotocol.PT_UNDEFINED, Mprotocol.M_MENU_STU_INFO);
                                        os.write(mprotocol.getPacket());
                                        break;
                                    case 10:
                                        System.out.println("개설교과목 정보 조회 프로토콜 전송");
                                        mprotocol = new Mprotocol(Mprotocol.MAN_MENU_ANS, Mprotocol.PT_UNDEFINED, Mprotocol.M_MENU_CS_INFO);
                                        os.write(mprotocol.getPacket());
                                        break;
                                }
                                break;
                            case Mprotocol.MAN_LOGIN_FAIL_CODE:
                                pw_false = 0;
                                System.out.println("로그인 실패");
                                System.out.println("시스템을 종료합니다.");
                                os.close();
                                is.close();
                                socket.close();
                                System.exit(0);
                                break;
                        }
                        break;
                    case Mprotocol.ACCOUNT_INFO_REQ:
                        switch (code) {
                            case Mprotocol.M_MAN_ID_REQ:    // 관리자 ID
                            case Mprotocol.WHO_INFO_REQ:
                                System.out.println("서버가 관리자 아이디 정보 요청");
                                System.out.println("아이디 : ");
                                String man_id = userIn.readLine();

                                // 아이디 정보 생성 및 패킷 전송
                                mprotocol = new Mprotocol(Mprotocol.ACCOUNT_INFO_ANS, Mprotocol.PT_UNDEFINED, Mprotocol.M_MAN_ID_ANS);
                                mprotocol.set_single_tuple(man_id);
                                System.out.println("아이디 정보 전송");
                                os.write(mprotocol.getPacket());
                                break;
                            case Mprotocol.M_MAN_PW_REQ:    // 관리자 PW
                                System.out.println("서버가 관리자 비밀번호 정보 요청");
                                System.out.println("비밀번호 (" + pw_false + " 회 틀림) : ");
                                String man_pw = userIn.readLine();

                                // 비밀번호 정소 생성 및 패킷 전송
                                mprotocol = new Mprotocol(Mprotocol.ACCOUNT_INFO_ANS, Mprotocol.PT_UNDEFINED, Mprotocol.M_MAN_PW_ANS);
                                mprotocol.set_single_tuple(man_pw);
                                System.out.println("비밀번호 정보 전송");
                                pw_false += 1;
                                os.write(mprotocol.getPacket());
                                break;
                            case Mprotocol.M_PRO_ID_REQ:    // 교수 ID
                                if (id_duple)
                                    System.out.println("아이디가 중복되었습니다.");
                                System.out.println("서버가 교수 아이디 정보 요청");
                                System.out.println("아이디 : ");
                                String pro_id = userIn.readLine();

                                // 아이디 정보 생성 및 패킷 전송
                                mprotocol = new Mprotocol(Mprotocol.ACCOUNT_INFO_ANS, Mprotocol.PT_UNDEFINED, Mprotocol.M_PRO_ID_ANS);
                                mprotocol.set_single_tuple(pro_id);
                                System.out.println("아이디 정보 전송");
                                os.write(mprotocol.getPacket());
                                id_duple = true;
                                break;
                            case Mprotocol.M_PRO_PW_REQ:    // 교수 PW
                                id_duple = false;
                                System.out.println("서버가 교수 비밀번호 정보 요청");
                                System.out.println("비밀번호 : ");
                                String pro_pw = userIn.readLine();

                                // 비밀번호 정보 생성 및 패킷 전송
                                mprotocol = new Mprotocol(Mprotocol.ACCOUNT_INFO_ANS, Mprotocol.PT_UNDEFINED, Mprotocol.M_PRO_PW_ANS);
                                mprotocol.set_single_tuple(pro_pw);
                                System.out.println("비밀번호 정보 전송");
                                os.write(mprotocol.getPacket());
                                break;
                            case Mprotocol.M_PRO_NUM_REQ:    // 교수 번호
                                System.out.println("서버가 아이디, 비밀번호 업데이트할 교수 번호 정보 요청");
                                System.out.println("교수 번호 : ");
                                String pro_num = userIn.readLine();

                                // 교수 번호 정보 생성 및 패킷 전송
                                mprotocol = new Mprotocol(Mprotocol.ACCOUNT_INFO_ANS, Mprotocol.PT_UNDEFINED, Mprotocol.M_PRO_NUM_ANS);
                                mprotocol.set_single_tuple(pro_num);
                                System.out.println("교수 번호 정보 전송");
                                os.write(mprotocol.getPacket());
                                break;
                            case Mprotocol.M_STU_ID_REQ:    // 학생 ID
                                if (id_duple)
                                    System.out.println("아이디가 중복되었습니다.");
                                System.out.println("서버가 학생 아이디 정보 요청");
                                System.out.println("아이디 : ");
                                String stu_id = userIn.readLine();

                                // 아이디 정보 생성 및 패킷 전송
                                mprotocol = new Mprotocol(Mprotocol.ACCOUNT_INFO_ANS, Mprotocol.PT_UNDEFINED, Mprotocol.M_STU_ID_ANS);
                                mprotocol.set_single_tuple(stu_id);
                                System.out.println("아이디 정보 전송");
                                os.write(mprotocol.getPacket());
                                id_duple = true;
                                break;
                            case Mprotocol.M_STU_PW_REQ:    // 학생 PW
                                id_duple = false;
                                System.out.println("서버가 학생 비밀번호 정보 요청");
                                System.out.println("비밀번호 : ");
                                String stu_pw = userIn.readLine();

                                // 비밀번호 정보 생성 및 패킷 전송
                                mprotocol = new Mprotocol(Mprotocol.ACCOUNT_INFO_ANS, Mprotocol.PT_UNDEFINED, Mprotocol.M_STU_PW_ANS);
                                mprotocol.set_single_tuple(stu_pw);
                                System.out.println("비밀번호 정보 전송");
                                os.write(mprotocol.getPacket());
                                break;
                            case Mprotocol.M_STU_NUM_REQ:    // 학생 번호
                                System.out.println("서버가 아이디, 비밀번호 업데이트할 학생 번호 정보 요청");
                                System.out.println("학생 번호 : ");
                                String stu_num = userIn.readLine();

                                // 학생 번호 정보 생성 및 패킷 전송
                                mprotocol = new Mprotocol(Mprotocol.ACCOUNT_INFO_ANS, Mprotocol.PT_UNDEFINED, Mprotocol.M_STU_NUM_ANS);
                                mprotocol.set_single_int(Integer.parseInt(stu_num));
                                System.out.println("학생 번호 정보 전송");
                                os.write(mprotocol.getPacket());
                                break;
                        }
                        break;
                    case Mprotocol.MAN_SUBJECT_SET_REQ:
                        switch(code) {
                            case Mprotocol.M_SUB_INS_REQ:
                                mprotocol = new Mprotocol(Mprotocol.MAN_SUBJECT_SET_ANS, Mprotocol.PT_UNDEFINED, Mprotocol.M_SUB_INS_ANS);

                                System.out.println("서버가 생성할 교과목 레코드 정보 요청");

                                System.out.print("교과목 코드 정보 : ");
                                String sub_code = userIn.readLine();
                                mprotocol.set_single_tuple(sub_code, Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE);

                                System.out.print("교과목 이름 정보 : ");
                                String sub_name = userIn.readLine();
                                mprotocol.set_single_tuple(sub_name, Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                        Mprotocol.LEN_SUB_CODE);

                                System.out.print("교과목 정보 : ");
                                String sub_info = userIn.readLine();
                                mprotocol.set_single_tuple(sub_info, Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                        Mprotocol.LEN_SUB_CODE + Mprotocol.LEN_SUB_NAME);

                                System.out.print("교과목 학년 : ");
                                String sub_grade = userIn.readLine();
                                mprotocol.set_single_int(Integer.parseInt(sub_grade),
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                        Mprotocol.LEN_SUB_CODE + Mprotocol.LEN_SUB_NAME + Mprotocol.LEN_SUB_INFO);

                                System.out.print("교과목 관리자 아이디 : ");
                                String man_id = userIn.readLine();
                                mprotocol.set_single_tuple(man_id,
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                Mprotocol.LEN_SUB_CODE + Mprotocol.LEN_SUB_NAME + Mprotocol.LEN_SUB_INFO + Mprotocol.LEN_SUB_GRADE);

                                System.out.println("교과목 정보 전송");
                                os.write(mprotocol.getPacket());

                                break;
                            case Mprotocol.M_SUB_CODE_REQ:
                                mprotocol = new Mprotocol(Mprotocol.MAN_SUBJECT_SET_ANS, Mprotocol.PT_UNDEFINED, Mprotocol.M_SUB_CODE_ANS);

                                System.out.println("서버가 수정할 교과목 코드 정보 요청");

                                System.out.print("교과목 코드 정보 : ");
                                String sub_code_u = userIn.readLine();
                                mprotocol.set_single_tuple(sub_code_u, Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE);

                                System.out.println("교과목 코드 정보 전송");
                                os.write(mprotocol.getPacket());
                                break;
                            case Mprotocol.M_SUB_REC_REQ:
                                mprotocol = new Mprotocol(Mprotocol.MAN_SUBJECT_SET_ANS, Mprotocol.PT_UNDEFINED, Mprotocol.M_SUB_REC_ANS);

                                System.out.println("서버가 수정할 교과목 레코드 정보 요청");

                                System.out.print("교과목 이름 정보 : ");
                                String sub_name_u = userIn.readLine();
                                mprotocol.set_single_tuple(sub_name_u, Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE);

                                System.out.print("교과목 정보 : ");
                                String sub_info_u = userIn.readLine();
                                mprotocol.set_single_tuple(sub_info_u, Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                        Mprotocol.LEN_SUB_NAME);

                                System.out.print("교과목 학년 : ");
                                String sub_grade_u = userIn.readLine();
                                mprotocol.set_single_int(Integer.parseInt(sub_grade_u),
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                Mprotocol.LEN_SUB_NAME + Mprotocol.LEN_SUB_INFO);

                                System.out.print("교과목 관리자 아이디 : ");
                                String man_id_u = userIn.readLine();
                                mprotocol.set_single_tuple(man_id_u,
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                Mprotocol.LEN_SUB_NAME + Mprotocol.LEN_SUB_INFO + Mprotocol.LEN_SUB_GRADE);

                                System.out.println("교과목 정보 전송");
                                os.write(mprotocol.getPacket());
                                break;
                            case Mprotocol.M_SUB_DEL_REQ:
                                mprotocol = new Mprotocol(Mprotocol.MAN_SUBJECT_SET_ANS, Mprotocol.PT_UNDEFINED, Mprotocol.M_SUB_DEL_ANS);

                                System.out.println("서버가 삭제할 교과목 코드 정보 요청");

                                System.out.print("교과목 코드 정보 : ");
                                String sub_code_d = userIn.readLine();
                                mprotocol.set_single_tuple(sub_code_d, Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE);

                                System.out.println("교과목 코드 정보 전송");
                                os.write(mprotocol.getPacket());
                                break;
                        }
                        break;
                    case Mprotocol.MAN_TERM_SET_REQ:
                        switch(code) {
                            case Mprotocol.M_PLAN_TIME_REQ:
                                mprotocol = new Mprotocol(Mprotocol.MAN_TERM_SET_ANS, Mprotocol.PT_UNDEFINED, Mprotocol.M_PLAN_TIME_ANS);

                                System.out.print("변경할 강의 계획서 입력 시간 시작 년 입력 : ");
                                String pstartyear = userIn.readLine();
                                System.out.print("변경할 강의 계획서 입력 시간 시작 월 입력 : ");
                                String pstartmonth = userIn.readLine();
                                System.out.print("변경할 강의 계획서 입력 시간 시작 일 입력 : ");
                                String pstartday = userIn.readLine();
                                System.out.print("변경할 강의 계획서 입력 시간 종료 년 입력 : ");
                                String pendyear = userIn.readLine();
                                System.out.print("변경할 강의 계획서 입력 시간 종료 월 입력 : ");
                                String pendmonth = userIn.readLine();
                                System.out.print("변경할 강의 계획서 입력 시간 종료 일 입력 : ");
                                String pendday = userIn.readLine();
                                mprotocol.set_single_int(Integer.parseInt(pstartyear));
                                mprotocol.set_single_int(Integer.parseInt(pstartmonth),
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                        Mprotocol.LEN_YEAR);
                                mprotocol.set_single_int(Integer.parseInt(pstartday),
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                Mprotocol.LEN_YEAR + Mprotocol.LEN_MONTH);
                                mprotocol.set_single_int(Integer.parseInt(pendyear),
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                Mprotocol.LEN_YEAR + Mprotocol.LEN_MONTH + Mprotocol.LEN_DAY);
                                mprotocol.set_single_int(Integer.parseInt(pendmonth),
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                Mprotocol.LEN_YEAR + Mprotocol.LEN_MONTH + Mprotocol.LEN_DAY
                                + Mprotocol.LEN_YEAR);
                                mprotocol.set_single_int(Integer.parseInt(pendday),
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                Mprotocol.LEN_YEAR + Mprotocol.LEN_MONTH + Mprotocol.LEN_DAY
                                        + Mprotocol.LEN_YEAR + Mprotocol.LEN_MONTH);
                                os.write(mprotocol.getPacket());
                                break;


                            case Mprotocol.M_APPLY_TIME_REQ:
                                mprotocol = new Mprotocol(Mprotocol.MAN_TERM_SET_ANS, Mprotocol.PT_UNDEFINED, Mprotocol.M_APPLY_TIME_ANS);

                                System.out.print("수강 신청 시간 변경할 학년 입력 : ");
                                String agrade = userIn.readLine();
                                System.out.print("변경할 수강 신청 입력 시간 시작 년 입력 : ");
                                String astartyear = userIn.readLine();
                                System.out.print("변경할 수강 신청 입력 시간 시작 월 입력 : ");
                                String astartmonth = userIn.readLine();
                                System.out.print("변경할 수강 신청 입력 시간 시작 일 입력 : ");
                                String astartday = userIn.readLine();
                                System.out.print("변경할 수강 신청 입력 시간 종료 년 입력 : ");
                                String aendyear = userIn.readLine();
                                System.out.print("변경할 수강 신청 입력 시간 종료 월 입력 : ");
                                String aendmonth = userIn.readLine();
                                System.out.print("변경할 수강 신청 입력 시간 종료 일 입력 : ");
                                String aendday = userIn.readLine();

                                mprotocol.set_single_int(Integer.parseInt(agrade));
                                mprotocol.set_single_int(Integer.parseInt(astartyear),
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE + Mprotocol.LEN_CS_GRADE);
                                mprotocol.set_single_int(Integer.parseInt(astartmonth),
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE + Mprotocol.LEN_CS_GRADE +
                                        Mprotocol.LEN_YEAR);
                                mprotocol.set_single_int(Integer.parseInt(astartday),
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE + Mprotocol.LEN_CS_GRADE +
                                        Mprotocol.LEN_YEAR + Mprotocol.LEN_MONTH);
                                mprotocol.set_single_int(Integer.parseInt(aendyear),
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE + Mprotocol.LEN_CS_GRADE +
                                        Mprotocol.LEN_YEAR + Mprotocol.LEN_MONTH + Mprotocol.LEN_DAY);
                                mprotocol.set_single_int(Integer.parseInt(aendmonth),
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE + Mprotocol.LEN_CS_GRADE +
                                        Mprotocol.LEN_YEAR + Mprotocol.LEN_MONTH + Mprotocol.LEN_DAY
                                        + Mprotocol.LEN_YEAR);
                                mprotocol.set_single_int(Integer.parseInt(aendday),
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE + Mprotocol.LEN_CS_GRADE +
                                        Mprotocol.LEN_YEAR + Mprotocol.LEN_MONTH + Mprotocol.LEN_DAY
                                        + Mprotocol.LEN_YEAR + Mprotocol.LEN_MONTH);
                                os.write(mprotocol.getPacket());
                                break;
                        }
                        break;
                    case Mprotocol.MAN_INFO_REQ:
                        switch(code) {
                            case Mprotocol.M_PRO_CALL_NUM_REQ:
                                mprotocol = new Mprotocol(Mprotocol.MAN_INFO_ANS, Mprotocol.PT_UNDEFINED, Mprotocol.M_PRO_CALL_NUM_ANS);

                                System.out.print("조회할 교수의 번호 입력 : ");
                                String pro_id_call = userIn.readLine();

                                mprotocol.set_single_tuple(pro_id_call);

                                System.out.println("교수 번호 전송");
                                os.write(mprotocol.getPacket());
                                break;
                            case Mprotocol.M_STU_CALL_NUM_REQ:
                                mprotocol = new Mprotocol(Mprotocol.MAN_INFO_ANS, Mprotocol.PT_UNDEFINED, Mprotocol.M_STU_CALL_NUM_ANS);

                                System.out.print("조회할 학생의 번호 입력 : ");
                                String stu_id_call = userIn.readLine();

                                mprotocol.set_single_int(Integer.parseInt(stu_id_call));

                                System.out.println("학생 번호 전송");
                                os.write(mprotocol.getPacket());
                                break;
                            case Mprotocol.M_CS_CALL_NUM_REQ:
                                mprotocol = new Mprotocol(Mprotocol.MAN_INFO_ANS, Mprotocol.PT_UNDEFINED, Mprotocol.M_CS_CALL_NUM_ANS);

                                System.out.print("조회할 개설교과목의 번호 입력 : ");
                                String cs_code_call = userIn.readLine();

                                mprotocol.set_single_tuple(cs_code_call);

                                System.out.println("개설교과목 번호 전송");
                                os.write(mprotocol.getPacket());
                                break;
                        }
                        break;
                    case Mprotocol.MAN_INFO_RESULT:
                        switch(code) {
                            case Mprotocol.M_PRO_CALL_REC_REQ:
                                System.out.println("교수 조회 결과");
                                System.out.println("교수 번호(PK) : " + mprotocol.get_single_tuple(mprotocol.LEN_SUB_CODE, buf));
                                System.out.println("교수 아이디 : " + mprotocol.get_single_tuple(mprotocol.LEN_PRO_ID, buf,
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                Mprotocol.LEN_SUB_CODE));
                                System.out.println("교수 비밀번호 : " + mprotocol.get_single_tuple(mprotocol.LEN_PRO_PASS, buf,
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                Mprotocol.LEN_SUB_CODE + Mprotocol.LEN_PRO_ID));
                                System.out.println("교수 이름 : " + mprotocol.get_single_tuple(mprotocol.LEN_PRO_NAME, buf,
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                Mprotocol.LEN_SUB_CODE + Mprotocol.LEN_PRO_ID + Mprotocol.LEN_PRO_PASS));
                                System.out.println("교수 나이 : " + mprotocol.get_single_int(buf,
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                Mprotocol.LEN_SUB_CODE + Mprotocol.LEN_PRO_ID + Mprotocol.LEN_PRO_PASS + Mprotocol.LEN_PRO_NAME));
                                System.out.println("관리자 아이디(FK) : " + mprotocol.get_single_tuple(mprotocol.LEN_MAN_ID, buf,
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                Mprotocol.LEN_SUB_CODE + Mprotocol.LEN_PRO_ID + Mprotocol.LEN_PRO_PASS + Mprotocol.LEN_PRO_NAME +
                                                Mprotocol.LEN_PRO_AGE));
                                System.out.println("교수 성별 : " + mprotocol.get_single_tuple(mprotocol.LEN_PRO_GENDER, buf,
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                Mprotocol.LEN_SUB_CODE + Mprotocol.LEN_PRO_ID + Mprotocol.LEN_PRO_PASS + Mprotocol.LEN_PRO_NAME +
                                                Mprotocol.LEN_PRO_AGE + Mprotocol.LEN_MAN_ID));
                                System.out.println("교수 전화번호 : " + mprotocol.get_single_tuple(mprotocol.LEN_PRO_PHONE, buf,
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                Mprotocol.LEN_SUB_CODE + Mprotocol.LEN_PRO_ID + Mprotocol.LEN_PRO_PASS + Mprotocol.LEN_PRO_NAME +
                                                Mprotocol.LEN_PRO_AGE + Mprotocol.LEN_MAN_ID + Mprotocol.LEN_PRO_GENDER));
                                break;
                            case Mprotocol.M_STU_CALL_REC_REQ:
                                System.out.println("학생 조회 결과");
                                System.out.println("학생 번호(PK) : " + mprotocol.get_single_int(buf));
                                System.out.println("학생 아이디 : " + mprotocol.get_single_tuple(mprotocol.LEN_STU_ID, buf,
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                Mprotocol.LEN_STU_NUM));
                                System.out.println("학생 비밀번호 : " + mprotocol.get_single_tuple(mprotocol.LEN_STU_PASS, buf,
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                Mprotocol.LEN_STU_NUM + Mprotocol.LEN_STU_ID));
                                System.out.println("학생 이름 : " + mprotocol.get_single_tuple(mprotocol.LEN_STU_NAME, buf,
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                Mprotocol.LEN_STU_NUM + Mprotocol.LEN_STU_ID + Mprotocol.LEN_STU_PASS));
                                System.out.println("학생 나이 : " + mprotocol.get_single_int(buf,
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                Mprotocol.LEN_STU_NUM + Mprotocol.LEN_STU_ID + Mprotocol.LEN_STU_PASS + Mprotocol.LEN_STU_NAME));
                                System.out.println("관리자 아이디(FK) : " + mprotocol.get_single_tuple(mprotocol.LEN_MAN_ID, buf,
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                Mprotocol.LEN_STU_NUM + Mprotocol.LEN_STU_ID + Mprotocol.LEN_STU_PASS + Mprotocol.LEN_STU_NAME +
                                                Mprotocol.LEN_STU_AGE));
                                System.out.println("학생 성별 : " + mprotocol.get_single_tuple(mprotocol.LEN_STU_GENDER, buf,
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                Mprotocol.LEN_STU_NUM + Mprotocol.LEN_STU_ID + Mprotocol.LEN_STU_PASS + Mprotocol.LEN_STU_NAME +
                                                Mprotocol.LEN_STU_AGE + Mprotocol.LEN_MAN_ID));
                                System.out.println("학생 학년 : " + mprotocol.get_single_int(buf,
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                Mprotocol.LEN_STU_NUM + Mprotocol.LEN_STU_ID + Mprotocol.LEN_STU_PASS + Mprotocol.LEN_STU_NAME +
                                                Mprotocol.LEN_STU_AGE + Mprotocol.LEN_MAN_ID + Mprotocol.LEN_STU_GENDER));
                                break;
                            case Mprotocol.M_CS_CALL_REC_REQ:
                                int csyear, csmonth, csdate, cshour, csminute, cssecond;

                                System.out.println("개설교과목 조회 결과");
                                System.out.println("개설교과목 코드(PK) : " +
                                        mprotocol.get_single_tuple(mprotocol.LEN_CS_CODE, buf,
                                                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE));
                                System.out.println("개설교과목 이름 : " +
                                        mprotocol.get_single_tuple(mprotocol.LEN_CS_NAME, buf,
                                                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                        Mprotocol.LEN_CS_CODE));
                                System.out.println("개설교과목 정보 : " +
                                        mprotocol.get_single_tuple(mprotocol.LEN_CS_INFO, buf,
                                                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                        Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME ));
                                System.out.println("개설교과목 학년: " +
                                        mprotocol.get_single_int(buf,
                                                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                        Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO));
                                System.out.println("개설교과목 최대 인원 : " +
                                        mprotocol.get_single_int(buf,
                                                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                        Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE ));
                                System.out.println("개설교과목 강의실: " +
                                        mprotocol.get_single_tuple(mprotocol.LEN_CS_ROOM, buf,
                                                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                        Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                                                        Mprotocol.LEN_CS_STU_MAX));

                                cshour = mprotocol.get_single_int(buf,
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                                                Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM);
                                csminute = mprotocol.get_single_int(buf,
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                                                Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM + 4);

                                cssecond = mprotocol.get_single_int(buf,
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                                                Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM + 4 + 4);

                                System.out.println("개설교과목 수업 시작 시각 : " + LocalTime.of(cshour, csminute, cssecond));

                                cshour = mprotocol.get_single_int(buf,
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                                                Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM+ 12);
                                csminute = mprotocol.get_single_int(buf,
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                                                Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM + 4 + 12);
                                cssecond = mprotocol.get_single_int(buf,
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                                                Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM + 4 + 4 + 12);

                                System.out.println("개설교과목 수업 종료 시각: " + LocalTime.of(cshour, csminute, cssecond));
                                System.out.println("개설교과목 강의계획서 : " +
                                        mprotocol.get_single_tuple(mprotocol.LEN_CS_PLAN, buf,
                                                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                        Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                                                        Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM + 24
                                                ));

                                csyear = mprotocol.get_single_int(buf,
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                                                Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM + 4 + 4 + 4 + 4 + 4 + 4 +
                                                Mprotocol.LEN_CS_PLAN);
                                csmonth = mprotocol.get_single_int(buf,
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                                                Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM + 4 + 4 + 4 + 4 + 4 + 4 +
                                                Mprotocol.LEN_CS_PLAN + 4);
                                csdate = mprotocol.get_single_int(buf,
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                                                Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM + 4 + 4 + 4 + 4 + 4 + 4 +
                                                Mprotocol.LEN_CS_PLAN + 8 );

                                System.out.println("개설교과목 강의계획서 작성 시작 시각 : " + LocalDate.of(csyear, csmonth, csdate));

                                csyear = mprotocol.get_single_int(buf,
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                                                Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM + 4 + 4 + 4 + 4 + 4 + 4 +
                                                Mprotocol.LEN_CS_PLAN + 12);
                                csmonth = mprotocol.get_single_int(buf,
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                                                Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM + 4 + 4 + 4 + 4 + 4 + 4 +
                                                Mprotocol.LEN_CS_PLAN + 16);
                                csdate = mprotocol.get_single_int(buf,
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                                                Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM + 4 + 4 + 4 + 4 + 4 + 4 +
                                                Mprotocol.LEN_CS_PLAN + 20);

                                System.out.println("개설교과목 강의계획서 작성 종료 시각 : " + LocalDate.of(csyear, csmonth, csdate));

                                csyear = mprotocol.get_single_int(buf,
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                                                Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM + 4 + 4 + 4 + 4 + 4 + 4 +
                                                Mprotocol.LEN_CS_PLAN + 24);
                                csmonth = mprotocol.get_single_int(buf,
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                                                Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM + 4 + 4 + 4 + 4 + 4 + 4 +
                                                Mprotocol.LEN_CS_PLAN + 28);
                                csdate = mprotocol.get_single_int(buf,
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                                                Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM + 4 + 4 + 4 + 4 + 4 + 4 +
                                                Mprotocol.LEN_CS_PLAN + 32);

                                System.out.println("개설교과목 수강 신청 시작 시각 : " + LocalDate.of(csyear, csmonth, csdate));

                                csyear = mprotocol.get_single_int(buf,
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                                                Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM + 4 + 4 + 4 + 4 + 4 + 4 +
                                                Mprotocol.LEN_CS_PLAN + 36);
                                csmonth = mprotocol.get_single_int(buf,
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                                                Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM + 4 + 4 + 4 + 4 + 4 + 4 +
                                                Mprotocol.LEN_CS_PLAN + 40);
                                csdate = mprotocol.get_single_int(buf,
                                        Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                                                Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM + 4 + 4 + 4 + 4 + 4 + 4 +
                                                Mprotocol.LEN_CS_PLAN + 44);


                                System.out.println("개설교과목 수강 신청 종료 시각 : " + LocalDate.of(csyear, csmonth, csdate));

                                System.out.println("관리자 아이디(FK) : " +
                                        mprotocol.get_single_tuple(mprotocol.LEN_MAN_ID, buf,
                                                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                        Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                                                        Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM + 4 + 4 + 4 + 4 + 4 + 4 +
                                                        Mprotocol.LEN_CS_PLAN + 48
                                                ));
                                System.out.println("교수 번호(FK) : " +
                                        mprotocol.get_single_tuple(mprotocol.LEN_PRO_NUM, buf,
                                                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                        Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                                                        Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM + 4 + 4 + 4 + 4 + 4 + 4 +
                                                        Mprotocol.LEN_CS_PLAN + 48 + Mprotocol.LEN_MAN_ID
                                                ));
                                System.out.println("개설교과목 요일 : " +
                                        mprotocol.get_single_int(buf,
                                                Mprotocol.LEN_PROTOCOL_TYPE + Mprotocol.STU_LEN_PROTOCOL_TYPE + Mprotocol.LEN_CODE +
                                                        Mprotocol.LEN_CS_CODE + Mprotocol.LEN_CS_NAME + Mprotocol.LEN_CS_INFO + Mprotocol.LEN_CS_GRADE +
                                                        Mprotocol.LEN_CS_STU_MAX + Mprotocol.LEN_CS_ROOM + 4 + 4 + 4 + 4 + 4 + 4 +
                                                        Mprotocol.LEN_CS_PLAN + 48 + Mprotocol.LEN_MAN_ID + Mprotocol.LEN_PRO_NUM));
                                break;
                        }
                        break;
                }
            }
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
        }
        os.close();
        is.close();
        socket.close();

    }

}

/*/
import java.io.*;
import java.net.*;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;

public class ManagerRegistClient {
    public static int DT_LEN = 4;
    public static void main(String args[]) {
        Socket theSocket = null;
        String host;
        String theLine;

        if (args.length > 0) {
            host = args[0]; // 입력받은 호스트를 사용
        }
        else {
            host = "localhost"; // 로컬 호스트를 사용
        }

        try {
            theSocket = new Socket(host, 5000); // 서버 접속
            InputStream is = theSocket.getInputStream();
            DataInputStream dis = new DataInputStream(is);

            OutputStream os = theSocket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                System.out.print("데이터 입력 : ");
                theLine = userInput.readLine(); // 키보드로부터 데이터 입력
                if (theLine.equals("quit")) break; // 프로그램 종료

                byte[] payload = theLine.getBytes();
                byte[] sendData = new byte[payload.length + DT_LEN];

                int index = 0;
                // 데이터 길이 저장
                byte[] payloadSize = intToBytes(payload.length);
                sendData[0] = (byte)payloadSize[0];
                sendData[1] = (byte)payloadSize[1];
                sendData[2] = (byte)payloadSize[2];
                sendData[3] = (byte)payloadSize[3];
                index = 4;

                for (int i = 0; i < payload.length; i++) {
                    sendData[index] = payload[i];
                    index++;
                }

                dos.write(sendData);
                dos.flush();
            }
        }
        catch (UnknownHostException e) {
            System.err.println(args[0] + " 호스트를 찾을 수 없습니다");
        }
        catch (IOException e) {
            System.err.println(e);
        }
        finally {
            try {
                theSocket.close(); // 소켓을 닫는다.
            }
            catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    private static byte[] intToBytes(final int data) {
        return new byte[] {
                (byte)((data >> 24) & 0xff),
                (byte)((data >> 16) & 0xff),
                (byte)((data >> 8) & 0xff),
                (byte)((data >> 0) & 0xff)
        };
    }
}
//*/
