import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class main {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("hi");
        Scanner scanner = new Scanner(new File("E:\\thrownickname.txt"));
        Scanner in = new Scanner(System.in);
        String findname= in.next();
        while (scanner.hasNext()) {
            String str = scanner.next();
            if(findname.equals(str)){
                System.out.println("fucking guy is in my text file men just throw this turn ㅋㅋ");
                break;
            }
        }


    }
}
