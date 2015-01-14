package com.topsem.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Random;

public class EncryUtil {

    private static char xor(char a, char b) {
        int i = a;
        int x = i ^ b;
        char c = (char) x;
        return c;
    }


    public static String decode(String txt) {
        if (StringUtils.isBlank(txt)) {
            return "";
        }
        String encrypt_key = "fsdjk2rt";
        char[] passport = passport_key(base64_decode(txt), encrypt_key);
        char[] password = new char[passport.length / 2];

        int j = 0;
        for (int i = 0; i < passport.length; i++) {
            char md5 = passport[i];
            ++i;
            char a = passport[i];
            password[j] = xor(a, md5);
            j++;
        }
        return String.valueOf(password);
    }

    public static String encode(String txt) {
        Random r = new Random();
        String rn = String.valueOf(r.nextInt(32000));
        String encrypt_key = MD5Util.getMD5(rn);
        int ctr = 0;
        String tmp = "";
        String key = "fsdjk2rt";
        char[] a;
        char[] b;
        char[] c;
        for (int i = 0; i < txt.length(); i++) {
            ctr = ctr == encrypt_key.length() ? 0 : ctr;
            a = txt.substring(i, i + 1).toCharArray();
            b = encrypt_key.substring(ctr, ctr + 1).toCharArray();
            c = encrypt_key.substring(ctr, ctr + 1).toCharArray();
            tmp += "" + c[0] + xor(a[0], b[0]);
        }
        return base64_encode(passport_key(tmp, key));
    }

    private static char[] passport_key(String txt, String encrypt_key) {
        String encrypt_key2 = MD5Util.getMD5(encrypt_key);
        int ctr = 0;
        int len = txt.length();
        char[] passport = new char[len];
        char a[];
        char b[];
        for (int i = 0; i < len; i++) {
            ctr = ctr == encrypt_key2.length() ? 0 : ctr;
            b = encrypt_key2.substring(ctr, ctr + 1).toCharArray();
            a = txt.substring(i, i + 1).toCharArray();
            passport[i] = xor(a[0], b[0]);
            ctr++;
        }
        return passport;
    }

    /**
     * base64 解码
     *
     * @param s
     * @return
     */
    private static String base64_decode(String s) {
        byte[] b = Base64Util.decode(s);
        return new String(b);
    }

    /**
     * base64加密
     *
     * @param s
     * @return
     */
    private static String base64_encode(char[] s) {
        String ss = String.copyValueOf(s);
        String ret = "";
        try {
            ret = Base64Util.encode(ss.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return ret;
    }

//    public static void main(String[] args) {
//        String password = encode("DLSMdlsm2015");
//        System.out.println(password);
//        System.out.println(decode(password));
//    }

}
