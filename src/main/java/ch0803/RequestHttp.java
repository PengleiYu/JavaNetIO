package ch0803;

import java.io.*;
import java.net.Socket;

/**
 * 半关闭socket
 * Created by yupenglei on 18/1/2.
 */
public class RequestHttp {
    public static void main(String[] args) {
        try (Socket socket = new Socket("www.baidu.com", 80)) {
            socket.setSoTimeout(15000);
            Writer out = new OutputStreamWriter(socket.getOutputStream(), "8859_1");
            out.write("GET / HTTP/1.0\r\n\r\n");
            out.flush();
            socket.shutdownOutput();

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            for (String line = in.readLine(); line != null; line = in.readLine()) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
