package ch0301;

import javax.xml.bind.DatatypeConverter;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 获取文件摘要
 * 开启子线程
 * Created by yupenglei on 18/1/4.
 */
public class DigestThread extends Thread {
    private String mFilename;

    private DigestThread(String filename) {mFilename = filename;}

    @Override
    public void run() {
        try {
            FileInputStream fileInputStream = new FileInputStream(mFilename);
            MessageDigest sha = MessageDigest.getInstance("sha-256");
            DigestInputStream digestInputStream = new DigestInputStream(fileInputStream, sha);
            //noinspection StatementWithEmptyBody
            while (digestInputStream.read() != -1) ;
            digestInputStream.close();
            byte[] digest = sha.digest();

            StringBuilder builder = new StringBuilder(mFilename);
            builder.append(":");
            builder.append(DatatypeConverter.printHexBinary(digest));
            System.out.println(builder);
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new DigestThread("README.md").start();
    }
}
