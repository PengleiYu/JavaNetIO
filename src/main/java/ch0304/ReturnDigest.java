package ch0304;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by yupenglei on 18/1/4.
 */
public class ReturnDigest extends Thread  {
    private final String mFilename;
    private byte[] mDigest;

    public ReturnDigest(String filename) {mFilename = filename;}

    @Override
    public void run() {
        try {
            FileInputStream fileInputStream = new FileInputStream(mFilename);
            MessageDigest sha = MessageDigest.getInstance("sha-256");
            DigestInputStream in = new DigestInputStream(fileInputStream, sha);
            while (in.read() != -1) ;
            in.close();
            mDigest = sha.digest();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] getDigest() { return mDigest; }
}
