package ch0902;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * 与{@link ch0804.LowPortScanner}的区别是不再试图连接端口上的服务器
 * Created by yupenglei on 18/1/3.
 */
public class LocalPortScanner {
    public static void main(String[] args) {
        for (int port = 1; port <= 65535; port++) {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
//                System.out.println("Here is not a server on port " + port);
            } catch (IOException e) {
                System.err.println("There is a server on port " + port + ".");
            }
        }
    }
}
