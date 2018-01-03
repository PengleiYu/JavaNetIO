package ch0901;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 多线程服务器
 * Created by yupenglei on 18/1/3.
 */
public class MultiThreadDaytimeServer {
    private static final int PORT = 1313;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
//                    new DaytimeThread(socket).start();
                    Callable<Void> callable = new DaytimeTask(socket);
                    executorService.submit(callable);
                } catch (IOException e) { }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final class DaytimeTask implements Callable<Void> {
        private Socket mSocket;

        private DaytimeTask(Socket socket) {mSocket = socket;}

        @Override
        public Void call() throws Exception {
            try {
                Writer out = new OutputStreamWriter(mSocket.getOutputStream());
                out.write(new Date() + "\r\n");
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    mSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    private static final class DaytimeThread extends Thread {
        private Socket mSocket;

        private DaytimeThread(Socket socket) {mSocket = socket;}

        @Override
        public void run() {
            try {
                Writer out = new OutputStreamWriter(mSocket.getOutputStream());
                out.write(new Date() + "\r\n");
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    mSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
