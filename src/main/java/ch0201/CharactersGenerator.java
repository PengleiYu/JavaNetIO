package ch0201;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 字符生成器协定
 * 基本的流操作
 * Created by yupenglei on 18/1/3.
 */
public class CharactersGenerator {
    private static final int firstPrintableCharacter = 33;
    private static final int numberOfPrintableCharacter = 94;
    private static final int numberOfCharactersPerLine = 72;

    private static int getPrintableChar(int position) {
        return ((position - firstPrintableCharacter) % numberOfPrintableCharacter) +
                firstPrintableCharacter;
    }

    public static void generateCharacters1(OutputStream out) throws IOException {
        int start = firstPrintableCharacter;
        while (true) {
            for (int i = start; i < start + numberOfCharactersPerLine; i++) {
                out.write(getPrintableChar(i));
            }
            out.write('\r');
            out.write('\n');
            start = getPrintableChar(start + 1);
        }
    }

    public static void generateCharacters2(OutputStream out) throws IOException {
        int start = firstPrintableCharacter;
        byte[] line = new byte[numberOfCharactersPerLine + 2];
        while (true) {
            for (int i = start; i < start + numberOfCharactersPerLine; i++) {
                line[i - start] = (byte) getPrintableChar(i);
            }
            line[numberOfCharactersPerLine] = '\r';
            line[numberOfCharactersPerLine + 1] = '\n';
            out.write(line);
            start = getPrintableChar(start + 1);
        }
    }

    public static void main(String[] args) {

    }
}
