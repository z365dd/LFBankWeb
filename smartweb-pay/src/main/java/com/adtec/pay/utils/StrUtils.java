package com.adtec.pay.utils;

import java.security.SecureRandom;

public class StrUtils {

    private static char[] chars = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private static String[] masks = new String[]{"", "*", "**", "***", "****", "*****", "******", "*******", "********", "*********", "**********"};

    public static String randomString(int strLength) {
        int charsLength = chars.length;
        SecureRandom random = new SecureRandom();
        StringBuffer sb = new StringBuffer(strLength);
        for (int i = 0; i < strLength; i++) {
            int index = random.nextInt(charsLength);
            sb.append(chars[index]);
        }
        return sb.toString();
    }

    public static String strAddMask(String str, int leftPlaintextLength, int rightPlaintextLength) {
        int len = str.length();
        if (len <= leftPlaintextLength + rightPlaintextLength) {
            return str;
        }
        int maskLen = len - leftPlaintextLength - rightPlaintextLength;
        StringBuffer sb = new StringBuffer(str.substring(0, leftPlaintextLength));
        if (maskLen < masks.length) {
            sb.append(masks[maskLen]);
        } else {
            while (maskLen > masks.length - 1) {
                sb.append(masks[masks.length - 1]);
                maskLen = maskLen - masks.length + 1;
            }
            sb.append(masks[maskLen]);
        }
        sb.append(str.substring(len - rightPlaintextLength));
        return sb.toString();
    }

    public static String strAddFixedMask(String str, int leftPlaintextLength, int rightPlaintextLength, int maskLen) {
        int len = str.length();
        if (len <= leftPlaintextLength + rightPlaintextLength) {
            return str;
        }
        StringBuffer sb = new StringBuffer(str.substring(0, leftPlaintextLength));
        sb.append(masks[Math.min(maskLen, masks.length - 1)]);
        sb.append(str.substring(len - rightPlaintextLength));
        return sb.toString();
    }

}
