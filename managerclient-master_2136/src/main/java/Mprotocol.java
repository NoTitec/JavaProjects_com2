
import java.nio.ByteBuffer;

public class Mprotocol {
    //포맷정보
    public static final int PT_UNDEFINED = -1;
    public static final int PT_EXIT = 0;
    public static final int LEN_PROTOCOL_TYPE = 1;//프로토콜 타입 길이
    public static final int MAN_LEN_PROTOCOL_TYPE = 1;  // 관리자 프로토콜 길이
    public static final int STU_LEN_PROTOCOL_TYPE = 1;//학생 프로토콜 타입 길이
    public static final int LEN_CODE = 1;//프로토콜 코드 길이

    public static final int LEN_ID = 100;   // ID 길이 (모든 종류)
    public static final int LEN_PW = 200;   // PW 길이 (모든 종류)
    public static final int LEN_NUM = 100;  // 코드, 번호 길이 (모든 종류)
    public static final int LEN_INT = 4;    // INT 길이
    public static final int LEN_MAN_ID = 20;
    public static final int LEN_SUB_CODE = 10;
    public static final int LEN_SUB_NAME = 20;
    public static final int LEN_SUB_INFO = 10000;
    public static final int LEN_SUB_GRADE = LEN_INT;
    public static final int LEN_PRO_NUM = 10;
    public static final int LEN_PRO_ID = 20;
    public static final int LEN_PRO_PASS = 30;
    public static final int LEN_PRO_NAME = 20;
    public static final int LEN_PRO_AGE = LEN_INT;
    public static final int LEN_PRO_GENDER = 10;
    public static final int LEN_PRO_PHONE = 20;
    public static final int LEN_STU_NUM = LEN_INT;
    public static final int LEN_STU_ID = 20;
    public static final int LEN_STU_PASS = 30;
    public static final int LEN_STU_NAME = 20;
    public static final int LEN_STU_AGE = LEN_INT;
    public static final int LEN_STU_GENDER = 10;
    public static final int LEN_STU_GRADE = LEN_INT;
    public static final int LEN_YEAR = LEN_INT;
    public static final int LEN_MONTH = LEN_INT;
    public static final int LEN_DAY = LEN_INT;
    public static final int LEN_HOUR = LEN_INT;
    public static final int LEN_MINUTE = LEN_INT;
    public static final int LEN_SECOND = LEN_INT;
    public static final int LEN_CS_CODE = 10;
    public static final int LEN_CS_NAME = 20;
    public static final int LEN_CS_INFO = 10000;
    public static final int LEN_CS_GRADE = LEN_INT;
    public static final int LEN_CS_STU_MAX = LEN_INT;
    public static final int LEN_CS_ROOM = 7;
    public static final int LEN_CS_START_CLASS = LEN_HOUR + LEN_MINUTE + LEN_SECOND;
    public static final int LEN_CS_END_CLASS = LEN_HOUR + LEN_MINUTE + LEN_SECOND;
    public static final int LEN_CS_PLAN = 1000;
    public static final int LEN_CS_START_PLAN = LEN_YEAR + LEN_MONTH + LEN_DAY;
    public static final int LEN_CS_END_PLAN = LEN_YEAR + LEN_MONTH + LEN_DAY;
    public static final int LEN_CS_START_APPLY = LEN_YEAR + LEN_MONTH + LEN_DAY;
    public static final int LEN_CS_END_APPLY = LEN_YEAR + LEN_MONTH + LEN_DAY;
    public static final int LEN_CS_DAY = LEN_INT;


    public static final int LEN_SUBJECT_COUNT = 4;//교과목 개수 길이
    public static final int LEN_MAX = 100000;//최대 데이터 길이
    //---------------------------------------------------------------------------------
    //TYPE정보
    public static final int ACCOUNT_INFO_REQ = 1;//계정 정보 요청
    public static final int ACCOUNT_INFO_ANS = 2;//계정 정보 응답
    public static final int MAN_SUBJECT_SET_REQ = 3;   // 교과목 생성/수정/삭제 요청
    public static final int MAN_SUBJECT_SET_ANS = 4;   // 교과목 생성/수정/삭제 응답
    public static final int MAN_TERM_SET_REQ = 5;   // 기간 설정 요청
    public static final int MAN_TERM_SET_ANS = 6;   // 기간 설정 응답
    public static final int MAN_INFO_REQ = 7;   // 관리자 시스템 내 정보 조회 요청
    public static final int MAN_INFO_ANS = 8;   // 관리자 시스템 내 정보 조회 응답
    public static final int MAN_INFO_RESULT = 9;   // 관리자 시스템 내 정보 조회 결과 전송\
    public static final int MAN_MENU_ANS = 10;

    // 나중에 삭제
    public static final int REGIST_REQ = 30;//수강 요청
    public static final int CREATE_SUBJECT_INFO_ANS = 31;//개설 교과목 정보 응답
    public static final int SEL_SUBJECT_ANS = 32;//선택 교과목 응답
    public static final int MY_REGIST_ANS = 33;//본인 수강목록 응답
    public static final int REGIST_RESULT = 34;//수강 요청 결과
    public static final int REGIST_CANSEL_RESULT = 35;//수강 취소 결과
    public static final int STU_INFO_UPDATE_REQ = 36;//학생 정보 수정 요청
    public static final int STU_INFO_UPDATE_ANS = 37;//학생 정보 수정 응답
    public static final int STU_INFO_UPDATE_RESULT = 38;//학생 정보 수정 결과
    public static final int CREATE_SUBJECT_INFO_REQ = 39;//개설 교과목 정보 요청
    public static final int SUJECT_PLAN_RESULT = 40;//강의 계획서 결과
    public static final int MY_TIMETABLE_REQ = 41;//시간표 요청
    public static final int ACCOUNT_INFO_RESULT = 99;//로그인 결과
    //------------------------------------------------------------------------------------
    //CODE정보
    //TYPE01
    public static final int M_MAN_ID_REQ = 1; // 관리자 아이디 요청
    public static final int M_MAN_PW_REQ = 2; // 관리자 비밀번호 요청
    public static final int M_PRO_ID_REQ = 3; // 교수 아이디 요청
    public static final int M_PRO_PW_REQ = 4; // 교수 비밀번호 요청
    public static final int M_PRO_NUM_REQ = 5; // 교수 번호 요청
    public static final int M_STU_ID_REQ = 6; // 학생 아이디 요청
    public static final int M_STU_PW_REQ = 7; // 학생 비밀번호 요청
    public static final int M_STU_NUM_REQ = 8; // 학생 번호 요청

    // 나중에 삭제
    public static final int STU_ID_PWD_REQ_CODE = 9;//학생 ID,PW요청
    public static final int WHO_INFO_REQ = 11;

    //TYPE02
    public static final int M_MAN_ID_ANS = 1; // 관리자 아이디 응답
    public static final int M_MAN_PW_ANS = 2; // 관리자 비밀번호 응답
    public static final int M_PRO_ID_ANS = 3; // 교수 아이디 응답
    public static final int M_PRO_PW_ANS = 4; // 교수 비밀번호 응답
    public static final int M_PRO_NUM_ANS = 5; // 교수 번호 응답
    public static final int M_STU_ID_ANS = 6; // 학생 아이디 응답
    public static final int M_STU_PW_ANS = 7; // 학생 비밀번호 응답
    public static final int M_STU_NUM_ANS = 8; // 학생 번호 응답

    // 나중에 삭제
    public static final int STU_ID_PWD_ANS_CODE = 9;//학생id,pwd응답
    public static final int WHO_INFO_ANS = 11;

    //TYPE03
    public static final int M_SUB_INS_REQ = 1;    // 교과목 생성 요청
    public static final int M_SUB_CODE_REQ = 2;   // 수정할 튜플의 코드 요청
    public static final int M_SUB_REC_REQ = 3;    // 수정할 레코드 값 요청
    public static final int M_SUB_DEL_REQ = 4;    // 삭제할 튜플 코드 요청

    //TYPE04
    public static final int M_SUB_INS_ANS = 1;    // 교과목 생성 응답
    public static final int M_SUB_CODE_ANS = 2;   // 수정할 튜플의 코드 응답
    public static final int M_SUB_REC_ANS = 3;    // 수정할 레코드 값 응답
    public static final int M_SUB_DEL_ANS = 4;    // 삭제할 튜플 코드 응답

    //TYPE05
    public static final int M_PLAN_TIME_REQ = 1;  // 강의 계획서 수정 시간 요청
    public static final int M_APPLY_TIME_REQ = 2; // 수강 신청 기간 요청

    //TYPE06
    public static final int M_PLAN_TIME_ANS = 1;  // 강의 계획서 수정 시간 응답
    public static final int M_APPLY_TIME_ANS = 2; // 수강 신청 기간 응답

    //TYPE 07
    public static final int M_PRO_CALL_NUM_REQ = 1;   // 호출할 교수의 교수 번호 요청
    public static final int M_STU_CALL_NUM_REQ = 2;   // 호출할 학생의 학생 번호 요청
    public static final int M_CS_CALL_NUM_REQ = 3;   // 호출할 개설교과목의 개설교과목 번호 요청

    //TYPE 08
    public static final int M_PRO_CALL_NUM_ANS = 1;   // 호출할 교수의 교수 번호 응답
    public static final int M_STU_CALL_NUM_ANS = 2;   // 호출할 학생의 학생 번호 응답
    public static final int M_CS_CALL_NUM_ANS = 3;   // 호출할 개설교과목의 개설교과목 번호 응답

    //TYPE 09
    public static final int M_PRO_CALL_REC_REQ = 1;   // 호출한 교수 레코드 응답
    public static final int M_STU_CALL_REC_REQ = 2;   // 호출한 학생 레코드 응답
    public static final int M_CS_CALL_REC_REQ = 3;   // 호출할 개설교과목 레코드 응답

    //TYPE 10
    public static final int M_MENU_PRO_CREATE = 1; // 교수 계정 생성
    public static final int M_MENU_STU_CREATE = 2; // 학생 계정 생성
    public static final int M_MENU_SUB_CREATE = 3; // 교과목 생성
    public static final int M_MENU_SUB_UPDATE = 4; // 교과목 수정
    public static final int M_MENU_SUB_DELETE = 5; // 교과목 삭제
    public static final int M_MENU_PLAN_UPDATE = 6; // 강의 계획서 입력 기간 변경
    public static final int M_MENU_APPLY_UPDATE = 7; // 학년별 수강 신청 기간 변경
    public static final int M_MENU_PRO_INFO = 8; // 교수 정보 조회
    public static final int M_MENU_STU_INFO = 9; // 학생 정보 조회
    public static final int M_MENU_CS_INFO = 10; // 개설교과목 정보 조회

    //TYPE99
    public static final int SUCCESS = 1;    // 성공
    public static final int FAIL = 2;   // 실패

    public static final int MAN_LOGIN_SUCCESS_CODE  = 13;
    public static final int MAN_LOGIN_FAIL_CODE = 14;

//    // 나중에 삭제
//    public static final int STU_LOGIN_SUCCESS_CODE = 3;//학생로그인 성공
//    public static final int STU_LOGIN_FAIL_CODE = 4;//학생로그인 실패

    //STUTYPE30
    //STUTYPE31
    //STUTYPE32
    //STUTYPE33
    //STUTYPE34
    //STUTYPE35
    //STUTYPE36
    //STUTYPE37
    //STUTYPE38
    //STUTYPE39
    //STUTYPE40
    //STUTYPE41

    protected int protocolType;
    protected int stuprotocolType;
    protected int protocolCode;
    private byte[] packet;

    public Mprotocol() {
        this(PT_UNDEFINED, PT_UNDEFINED,  PT_UNDEFINED);
    }

    public Mprotocol(int protocolType, int stuprotocolType, int protocolCode) {
        this.protocolType = protocolType;
        this.stuprotocolType = stuprotocolType;
        this.protocolCode = protocolCode;

        getPacket(protocolType, stuprotocolType, protocolCode);
    }

    public byte[] getPacket() {
        return packet;
    }

    //프로토콜 타입,코드따라서 길이 다르게 생성
    public byte[] getPacket(int protocolType, int stuprotocolType, int protocolCode) {//사용안하는필드는 -1으로넣음
        if (packet == null) {
            switch (protocolType) {
                case PT_UNDEFINED: // -1
                    packet = new byte[LEN_MAX];
                    break;
                case PT_EXIT: // 0
                    packet = new byte[LEN_PROTOCOL_TYPE + STU_LEN_PROTOCOL_TYPE + LEN_CODE];
                    break;
                case ACCOUNT_INFO_RESULT:    // 99
                    switch (protocolCode) {
                        case SUCCESS:
                        case FAIL:
                        case MAN_LOGIN_SUCCESS_CODE:
                        case MAN_LOGIN_FAIL_CODE:
                            packet = new byte[LEN_PROTOCOL_TYPE + STU_LEN_PROTOCOL_TYPE + LEN_CODE];
                            break;
                    }
                    break;
                case ACCOUNT_INFO_REQ: // 계정 정보 요청
                    switch (protocolCode) {
                        case M_MAN_ID_REQ:
                        case M_MAN_PW_REQ:
                        case M_PRO_ID_REQ:
                        case M_PRO_PW_REQ:
                        case M_PRO_NUM_REQ:
                        case M_STU_ID_REQ:
                        case M_STU_PW_REQ:
                        case M_STU_NUM_REQ:
                        case WHO_INFO_REQ:
                            packet = new byte[LEN_PROTOCOL_TYPE + STU_LEN_PROTOCOL_TYPE + LEN_CODE];
                            break;
                    }
                    break;

                case ACCOUNT_INFO_ANS: // 계정 정보 응답
                    switch (protocolCode) {
                        case M_MAN_ID_ANS: // 관리자 ID
                        case M_PRO_ID_ANS: // 교수 ID
                        case M_STU_ID_ANS: // 학생 ID
                            packet = new byte[LEN_PROTOCOL_TYPE + STU_LEN_PROTOCOL_TYPE + LEN_CODE + LEN_ID];
                            break;
                        case M_MAN_PW_ANS: // 관리자 PW
                        case M_PRO_PW_ANS: // 교수 PW
                        case M_STU_PW_ANS: // 학생 PW
                            packet = new byte[LEN_PROTOCOL_TYPE + STU_LEN_PROTOCOL_TYPE + LEN_CODE + LEN_PW];
                            break;
                        case M_PRO_NUM_ANS: // 교수 NUM
                            packet = new byte[LEN_PROTOCOL_TYPE + STU_LEN_PROTOCOL_TYPE + LEN_CODE + LEN_NUM];
                        case M_STU_NUM_ANS: // 학생 NUM
                            packet = new byte[LEN_PROTOCOL_TYPE + STU_LEN_PROTOCOL_TYPE + LEN_CODE + LEN_INT];
                            break;
                    }
                    break;

                case MAN_SUBJECT_SET_REQ:
                    switch (protocolCode) {
                        case M_SUB_INS_REQ:
                        case M_SUB_CODE_REQ:
                        case M_SUB_REC_REQ:
                        case M_SUB_DEL_REQ:
                            packet = new byte[LEN_PROTOCOL_TYPE + STU_LEN_PROTOCOL_TYPE + LEN_CODE];
                            break;

                    }
                    break;

                case MAN_SUBJECT_SET_ANS:
                    switch (protocolCode) {
                        case M_SUB_INS_ANS:
                            packet = new byte[LEN_PROTOCOL_TYPE + STU_LEN_PROTOCOL_TYPE + LEN_CODE +
                                    LEN_SUB_CODE + LEN_SUB_NAME + LEN_SUB_INFO + LEN_SUB_GRADE + LEN_ID];
                            break;
                        case M_SUB_REC_ANS:
                            packet = new byte[LEN_PROTOCOL_TYPE + STU_LEN_PROTOCOL_TYPE + LEN_CODE +
                                    LEN_SUB_NAME + LEN_SUB_INFO + LEN_SUB_GRADE + LEN_ID];  // 코드를 먼저 받기 때문에 뺌
                            break;
                        case M_SUB_DEL_ANS:
                        case M_SUB_CODE_ANS:
                            packet = new byte[LEN_PROTOCOL_TYPE + STU_LEN_PROTOCOL_TYPE + LEN_CODE +
                                    LEN_SUB_CODE];
                            break;
                    }
                    break;

                case MAN_TERM_SET_REQ:
                    switch (protocolCode) {
                        case M_PLAN_TIME_REQ:
                        case M_APPLY_TIME_REQ:
                            packet = new byte[LEN_PROTOCOL_TYPE + STU_LEN_PROTOCOL_TYPE + LEN_CODE];
                            break;
                    }
                    break;

                case MAN_TERM_SET_ANS:
                    switch (protocolCode) {
                        case M_PLAN_TIME_ANS:
                            packet = new byte[LEN_PROTOCOL_TYPE + STU_LEN_PROTOCOL_TYPE + LEN_CODE +
                                    LEN_YEAR + LEN_MONTH + LEN_DAY + LEN_YEAR + LEN_MONTH + LEN_DAY];
                            break;
                        case M_APPLY_TIME_ANS:
                            packet = new byte[LEN_PROTOCOL_TYPE + STU_LEN_PROTOCOL_TYPE + LEN_CODE +
                                    LEN_CS_GRADE + LEN_YEAR + LEN_MONTH + LEN_DAY + LEN_YEAR + LEN_MONTH + LEN_DAY];
                            break;
                    }
                    break;

                case MAN_INFO_REQ:
                    switch (protocolCode) {
                        case M_PRO_CALL_NUM_REQ:
                        case M_STU_CALL_NUM_REQ:
                        case M_CS_CALL_NUM_REQ:
                            packet = new byte[LEN_PROTOCOL_TYPE + STU_LEN_PROTOCOL_TYPE + LEN_CODE];
                            break;
                    }
                    break;

                case MAN_INFO_ANS:
                    switch (protocolCode) {
                        case M_PRO_CALL_NUM_ANS:
                            packet = new byte[LEN_PROTOCOL_TYPE + STU_LEN_PROTOCOL_TYPE + LEN_CODE +
                                    LEN_SUB_CODE];
                            break;
                        case M_STU_CALL_NUM_ANS:
                            packet = new byte[LEN_PROTOCOL_TYPE + STU_LEN_PROTOCOL_TYPE + LEN_CODE +
                                    LEN_STU_NUM];
                            break;
                        case M_CS_CALL_NUM_ANS:
                            packet = new byte[LEN_PROTOCOL_TYPE + STU_LEN_PROTOCOL_TYPE + LEN_CODE +
                                    LEN_CS_CODE];
                            break;
                    }
                    break;

                case MAN_INFO_RESULT:
                    switch (protocolCode) {
                        case M_PRO_CALL_REC_REQ:
                            packet = new byte[LEN_PROTOCOL_TYPE + STU_LEN_PROTOCOL_TYPE + LEN_CODE +
                                    LEN_SUB_CODE + LEN_PRO_ID + LEN_PRO_PASS + LEN_PRO_NAME + LEN_PRO_AGE + LEN_MAN_ID +
                                    LEN_PRO_GENDER + LEN_PRO_PHONE];
                            break;
                        case M_STU_CALL_REC_REQ:
                            packet = new byte[LEN_PROTOCOL_TYPE + STU_LEN_PROTOCOL_TYPE + LEN_CODE +
                                    LEN_STU_NUM + LEN_STU_ID + LEN_STU_PASS + LEN_STU_NAME + LEN_STU_AGE + LEN_MAN_ID +
                                    LEN_STU_GENDER + LEN_STU_GRADE];
                            break;
                        case M_CS_CALL_REC_REQ:
                            packet = new byte[LEN_PROTOCOL_TYPE + STU_LEN_PROTOCOL_TYPE + LEN_CODE +
                                    LEN_CS_CODE + LEN_CS_NAME + LEN_CS_INFO + LEN_CS_GRADE + LEN_CS_STU_MAX
                                    + LEN_CS_ROOM + LEN_CS_START_CLASS + LEN_CS_END_CLASS + LEN_CS_PLAN
                                    + LEN_CS_START_PLAN + LEN_CS_END_PLAN + LEN_CS_START_APPLY + LEN_CS_END_APPLY
                                    + LEN_MAN_ID + LEN_PRO_NUM + LEN_CS_DAY];
                            break;
                    }
                    break;

                case MAN_MENU_ANS:
                    switch (protocolCode) {
                        case M_MENU_PRO_CREATE:
                        case M_MENU_STU_CREATE:
                        case M_MENU_SUB_CREATE:
                        case M_MENU_SUB_UPDATE:
                        case M_MENU_SUB_DELETE:
                        case M_MENU_PLAN_UPDATE:
                        case M_MENU_APPLY_UPDATE:
                        case M_MENU_PRO_INFO:
                        case M_MENU_STU_INFO:
                        case M_MENU_CS_INFO:
                            packet = new byte[LEN_PROTOCOL_TYPE + STU_LEN_PROTOCOL_TYPE + LEN_CODE];
                            break;
                    }
                    break;
                default:
                    break;
                /*/
                case PT_UNDEFINED:
                    packet = new byte[LEN_PROTOCOL_TYPE + STU_LEN_PROTOCOL_TYPE + LEN_CODE + LEN_MAX];
                    break;
                case ACCOUNT_INFO_REQ:
                    switch (protocolCode) {
                        case PT_UNDEFINED:
                        case STU_ID_PWD_REQ_CODE://id,pwd요청(서버)
                        case WHO_INFO_REQ://직책정보요청(서버)
                            packet = new byte[LEN_PROTOCOL_TYPE + STU_LEN_PROTOCOL_TYPE + LEN_CODE + LEN_MAX];
                            break;
                    }
                    break;
                case ACCOUNT_INFO_ANS:
                    switch (protocolCode) {
                        case PT_UNDEFINED:
                        case WHO_INFO_ANS://직책정보응답(클라)
                        case STU_ID_PWD_ANS_CODE://id,pwd 입력값(클라)
                            packet = new byte[LEN_PROTOCOL_TYPE + STU_LEN_PROTOCOL_TYPE + LEN_CODE + LEN_MAX];
                            break;
                    }
                    break;
                case ACCOUNT_INFO_RESULT:
                    switch (protocolCode) {
                        case PT_UNDEFINED:
                        case STU_LOGIN_SUCCESS_CODE://로그인성공결과(서버)
                        case STU_LOGIN_FAIL_CODE://로그인실패결과(서버)
                            packet = new byte[LEN_PROTOCOL_TYPE + STU_LEN_PROTOCOL_TYPE + LEN_CODE];
                            break;
                    }

                 //*/
            }
        }


        packet[0] = (byte) protocolType;
        packet[1] = (byte) stuprotocolType;
        packet[2] = (byte) protocolCode;
        return packet;
    }

    //write할 buf 바이트배열 타입,코드에맞게 생성한 packet으로 복사해줌
    public void setPacket(int pt, int spt, int code, byte[] buf) {
        packet = null;
        packet = getPacket(pt, spt, code);
        System.arraycopy(buf, 0, packet, 3, buf.length);
        packet[3+buf.length-1]='\0';
    }

    public byte[] intto4byte(int intvalue) {//int값을 4byte byte배열로 전환후 반환
        byte[] byteArray = ByteBuffer.allocate(4).putInt(intvalue).array();

        return byteArray;
    }

    public int byteArrayToInt(byte bytes[]) {//4byte 배열을 int 값으로 전환해주는 메소드
        return ((((int) bytes[0] & 0xff) << 24) |
                (((int) bytes[1] & 0xff) << 16) |
                (((int) bytes[2] & 0xff) << 8) |
                (((int) bytes[3] & 0xff)));
    }

    public byte[] IntToBytes(int data) {
        return new byte[]{
                (byte) ((data >> 24) & 0xff),
                (byte) ((data >> 16) & 0xff),
                (byte) ((data >> 8) & 0xff),
                (byte) ((data >> 0) & 0xff),
        };
    }
    public void setPacket_type_and_code(int type,int stutype,int code){
        packet[0]=(byte)type; packet[1]=(byte)stutype; packet[2]=(byte)code;

    }

    // 관리자 관련 프로토콜 함수들
    public String get_single_tuple(int tupletype, byte[] buf)
    {
        return new String(buf, 3, tupletype).trim();
    }

    public String get_single_tuple(int tupletype, byte[] buf, int prevsize) {
        return new String(buf, prevsize, tupletype).trim();
    }
    public int get_single_int(byte[] buf) {
        byte[] bytes = {0x00, 0x00, 0x00, 0x00};
        System.arraycopy(buf, 3, bytes, 0,bytes.length);
        return byteArrayToInt(bytes);
    }

    public int get_single_int(byte[] buf, int prevsize) {
        byte[] bytes = {0x00, 0x00, 0x00, 0x00};
        System.arraycopy(buf, prevsize, bytes, 0, bytes.length);
        return byteArrayToInt(bytes);
    }

    public void set_single_tuple(String tuple) {
        System.arraycopy(tuple.trim().getBytes(), 0, packet, 3, tuple.trim().getBytes().length);
        packet[3 + tuple.trim().getBytes().length] = '\0';
    }

    public void set_single_tuple(String tuple, int prevsize) {
        System.arraycopy(tuple.trim().getBytes(), 0, packet, prevsize, tuple.trim().getBytes().length);
        packet[prevsize + tuple.trim().getBytes().length] = '\0';
    }

    public void set_single_int(int num) {
        byte[] data = intto4byte(num);
        System.arraycopy(data, 0, packet, 3, data.length);
    }

    public void set_single_int(int num, int prevsize) {
        byte[] data = intto4byte(num);
        System.arraycopy(data, 0, packet, prevsize, data.length);
    }

    public void m_setPacket(int pt, int spt, int code, byte[] buf) {
        packet = null;
        packet = getPacket(pt, spt, code);
        System.arraycopy(buf, 0, packet, 0, packet.length);
    }

}