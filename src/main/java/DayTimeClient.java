import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by yupenglei on 18/1/2.
 */
public class DayTimeClient {

    public static void main(String[] args) {
        String hostname = args.length > 0 ? args[0] : "time.nist.gov";
        //创建时就会建立网络连接
        try (Socket socket = new Socket(hostname, 13)) {
            socket.setSoTimeout(15000);
            StringBuilder time = new StringBuilder();
            InputStreamReader reader = new InputStreamReader(socket.getInputStream(), "ASCII");
            for (int c = reader.read(); c != -1; c = reader.read()) {
                time.append((char) c);
            }
            System.out.println(time);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
