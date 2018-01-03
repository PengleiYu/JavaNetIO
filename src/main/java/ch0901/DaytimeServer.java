package ch0901;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * 使用{@link ServerSocket}模拟时间服务器
 * Created by yupenglei on 18/1/2.
 */
public class DaytimeServer {
    private static final int PORT = 8888;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    System.out.println("a connecting is coming.\n" + socket);
//                    writeTimeSeconds(socket);
                    writeTimeString(socket);
                    socket.close();
                } catch (IOException e) {
                    System.out.println("a connecting has error ");
                }
            }
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void writeTimeSeconds(Socket socket) throws IOException {
        long seconds = new Date().getTime() / 1000L;
        System.out.println("seconds: " + seconds);
        byte[] time = new byte[4];
        time[0] = (byte) ((seconds & 0xFF000000L) >> 24);
        time[1] = (byte) ((seconds & 0x00FF0000L) >> 16);
        time[2] = (byte) ((seconds & 0x0000FF00L) >> 8);
        time[3] = (byte) (seconds & 0x000000FFL);
        OutputStream out = socket.getOutputStream();
        out.write(time);
        out.flush();
    }

    private static void writeTimeString(Socket socket) throws IOException {
        Writer out = new OutputStreamWriter(socket.getOutputStream());
        //网络中换行必须使用\r\n
        String time = new Date() + "\r\n";
        out.write(time);
        out.flush();
    }
}
