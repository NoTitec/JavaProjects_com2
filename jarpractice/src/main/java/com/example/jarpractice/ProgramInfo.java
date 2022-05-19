package com.example.jarpractice;

import java.io.IOException;
import java.net.Socket;

public class ProgramInfo {
    public static Socket socket; //싱글톤 소켓-> ui 화면전환시 컨트롤러도 바뀌는데 static이아니면 매번 서버에 재연결요청하는문제해결

    public static boolean socketConnect=false;//소켓이 할당되어있는지 확인하는 변수

    public static void setSocket(Socket socket) {
        ProgramInfo.socket = socket;
    }

    public static void setSocketConnect(boolean socketConnect) {
        ProgramInfo.socketConnect = socketConnect;
    }

    public static void closeSocket() throws IOException {//연결종료하는 메소드
        socket.close();
    }
}
