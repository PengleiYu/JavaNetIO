package ch0304;

import javax.xml.bind.DatatypeConverter;

/**
 * Created by yupenglei on 18/1/4.
 */
public class JoinDigestUserInterface {
    public static void main(String[] args) {
        String[] filenames = {"README.md", ".gitignore", "build.gradle"};
        ReturnDigest[] digests = new ReturnDigest[filenames.length];
        for (int i = 0; i < digests.length; i++) {
            digests[i] = new ReturnDigest(filenames[i]);
            digests[i].start();
        }

        for (int i = 0; i < digests.length; i++) {
            try {
                digests[i].join();
                StringBuilder result = new StringBuilder(filenames[i]);
                result.append(":");
                byte[] digest = digests[i].getDigest();
                result.append(DatatypeConverter.printHexBinary(digest));
                System.out.println(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
