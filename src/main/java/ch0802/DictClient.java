package ch0802;

import java.io.*;
import java.net.Socket;

/**
 * socket写入服务器
 * Created by yupenglei on 18/1/2.
 */
public class DictClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("dict.org", 2628)) {
            socket.setSoTimeout(15000);
            Writer writer = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
            writer.write("define fd-eng-lat gold\r\n");
            writer.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket
                    .getInputStream(), "UTF-8"));
            for (String line = reader.readLine(); !line.equals("."); line = reader.readLine()) {
                System.out.println(line);
            }
            writer.write("quit\r\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
