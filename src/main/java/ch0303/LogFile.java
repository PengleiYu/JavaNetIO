package ch0303;

import java.io.*;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;

/**
 * 同步代码块
 * Created by yupenglei on 18/1/4.
 */
public class LogFile {
    private final Writer mOut;

    public LogFile(File file) throws IOException {
        mOut = new BufferedWriter(new FileWriter(file));
    }

    public void writeEntry(String message) throws IOException {
        synchronized (mOut) {
            mOut.write(new Date().toString());
            mOut.write("\t");
            mOut.write(message);
            mOut.write("\r\n");
        }
    }

    public void close() throws IOException {
        mOut.flush();
        mOut.close();
        Collections.synchronizedList(new LinkedList<>());
    }

}
