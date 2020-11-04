package com.zlutil.tools.toolpackage.JavaBasic;

import java.math.BigInteger;
import java.security.MessageDigest;

public class SHA {
    public static void main(String[] args) {
        String inputStr = "abcdefg";
        BigInteger sha = null;
        System.out.println("=======加密前的数据:" + inputStr);
        byte[] inputData = inputStr.getBytes();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA");
            messageDigest.update(inputData);
            sha = new BigInteger(messageDigest.digest());
            System.out.println("SHA加密后:" + sha.toString(32));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //return sha.toString(32);
        System.out.println(sha.toString(32).equals("5uqu2d0pvi4i8q35suhi9t3mthh4t1q0"));
    }
}
