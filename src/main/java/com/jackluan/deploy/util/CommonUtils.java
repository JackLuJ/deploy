package com.jackluan.deploy.util;

public class CommonUtils {

    public static String upperCaseFirst(String s) {
        char[] ch = s.toCharArray();
        if (ch[0] > 'a' && ch[0] < 'z') {
            ch[0] = (char) (ch[0] - 32);
            return new String(ch);
        }
        return s;
    }
}
