package ch0903;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * 获取{@link ServerSocket}的信息
 * port设为0则为随机端口
 * Created by yupenglei on 18/1/3.
 */
public class RandomPort {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(0)) {
            System.out.println("This server runs on port " + serverSocket.getLocalPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
