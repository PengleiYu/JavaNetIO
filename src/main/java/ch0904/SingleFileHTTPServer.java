package ch0904;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by yupenglei on 18/1/3.
 */
public class SingleFileHTTPServer {
    private static final Logger sLogger = Logger.getLogger("SingleFileHTTPServer");
    private final byte[] mContent;
    private final byte[] mHeader;
    private final int mPort;
    private final String mEncoding;

    public SingleFileHTTPServer(String data, String encoding, String mimeType, int port) throws
            UnsupportedEncodingException {
        this(data.getBytes(encoding), encoding, mimeType, port);

    }

    private SingleFileHTTPServer(byte[] data, String encoding, String mimeType, int port) {
        mContent = data;
        mPort = port;
        mEncoding = encoding;
        String header = "HTTP/1.0 200 OK\r\n" +
                "Server: OneFile 2.0\r\n" +
                "Content-length: " + mContent.length + "\r\n" +
                "Content-type: " + mimeType + "; charset=" + encoding + "\r\n\r\n";
        mHeader = header.getBytes(Charset.forName("US-ASCII"));
    }

    private void start() {
        ExecutorService pool = Executors.newFixedThreadPool(100);
        try (ServerSocket server = new ServerSocket(mPort)) {
            sLogger.info("Accepting connections on port " + server.getLocalPort());
            sLogger.info("Data to be sent: ");
            sLogger.info(new String(mContent, mEncoding));
            while (true) {
                Socket connection = server.accept();
                pool.submit(new HTTPHandler(connection));
            }
        } catch (IOException e) {
            sLogger.log(Level.WARNING, "Exception accepting connection", e);
        } catch (RuntimeException e) {
            sLogger.log(Level.SEVERE, "Unexpected error", e);
        }
    }

    private class HTTPHandler implements Callable<Void> {
        private final Socket mConnection;

        private HTTPHandler(Socket connection) {mConnection = connection;}


        @Override
        public Void call() throws IOException {
            try {
                OutputStream out = new BufferedOutputStream(mConnection.getOutputStream());
                InputStream in = new BufferedInputStream(mConnection.getInputStream());

                StringBuilder request = new StringBuilder();
                while (true) {
                    int c = in.read();
                    if (c == '\r' || c == '\n' || c == -1) break;
                    request.append((char) c);
                }
                if (request.indexOf("HTTP/") != -1) out.write(mHeader);
                out.write(mContent);
                out.flush();
            } catch (IOException e) {
                sLogger.log(Level.WARNING, "Error writing to client", e);
            } finally {
                mConnection.close();
            }
            return null;
        }
    }

    public static void main(String[] args) {
        int port = 8080;
        String encoding = "utf-8";
        String fileName =
//                ".gitignore";
                "README.md";
        try {
            byte[] data = Files.readAllBytes(Paths.get(fileName));
            String contentType = URLConnection.getFileNameMap().getContentTypeFor(fileName);
            SingleFileHTTPServer server =
                    new SingleFileHTTPServer(data, encoding, contentType, port);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


