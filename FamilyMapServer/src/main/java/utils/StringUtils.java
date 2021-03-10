package utils;

import java.io.*;

public class StringUtils {
    public static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    public static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

    public static int countSlashes(String str) {
        int count = 0;

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '/') {
                count++;
            }
        }
        return count;
    }

    public static String urlToParamStr(String str) {
        int slashesAlreadySeen = 0;
        int newStartIndex = 0;

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '/') {
                slashesAlreadySeen++;
            }

            if (slashesAlreadySeen == 2) {
                if (i == str.length() - 1) {
                    return "";
                } else {
                    newStartIndex = i + 1;
                }
                break;
            }
        }

        StringBuilder sb = new StringBuilder(str);
        return sb.substring(newStartIndex);
    }
}
