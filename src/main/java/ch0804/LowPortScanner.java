package ch0804;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 查看主机前1024个端口哪个安装有tcp服务器
 * 连接网络服务器不能正常运行，不知为何
 * Created by yupenglei on 18/1/2.
 */
public class LowPortScanner {
    public static void main(String[] args) {
        String host = "localhost";
        for (int i = 0; i < 1024; i++) {
            try {
                Socket socket = new Socket(host, i);
                System.out.println("There is a server on port " + i + " of " + host);
                socket.close();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                System.err.println("There is not a server on port " + i + " of " + host);
            }
        }
    }
}
