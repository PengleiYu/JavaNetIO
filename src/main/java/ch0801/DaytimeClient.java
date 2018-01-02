package ch0801;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * socket从服务器读取
 * Created by yupenglei on 18/1/2.
 */
public class DaytimeClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("time.nist.gov", 13)) {
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
