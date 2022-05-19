import javax.print.DocFlavor;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        //ipapi 사용?

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonStr = null;
        String line;
        try {
            URL url = new URL("http://api.ipapi.com/118.41.208.169?access_key=3c0456db3c547d2c52e65f18b51a19a2&output=json");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            System.out.println(urlConnection.getContentType());
            System.out.println(urlConnection.getResponseCode());
            System.out.println(urlConnection.getResponseMessage());

            try (InputStream in = urlConnection.getInputStream();
                 ByteArrayOutputStream out = new ByteArrayOutputStream()) {

                byte[] buf = new byte[1024 * 8];
                int length = 0;
                while ((length = in.read(buf)) != -1) {
                    out.write(buf, 0, length);
                }
                jsonStr=new String(out.toByteArray(), "UTF-8");
            }

        } catch (IOException e) {
            System.out.println(e);
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    System.out.println(e);
                }
            }
        }

        System.out.println(jsonStr);
    }
}
