package com.iamazy.springcloud.audit.annotation;

import org.apache.commons.lang3.StringUtils;

/**
 * @author iamazy
 * @date 2019/1/11
 * @descrition
 **/
public class DeIdentifyUtils {

    private DeIdentifyUtils(){}

    private static String deIdentifyLeft(String str, int size) {
        int repeat;
        if (size > str.length()) {
            repeat = str.length();
        } else {
            repeat = size;
        }
        return StringUtils.overlay(str, StringUtils.repeat('*', repeat), 0, size);
    }

    private static String deIdentifyRight(String str, int size) {
        int end = str.length();
        int repeat;
        if (size > str.length()) {
            repeat = str.length();
        } else {
            repeat = size;
        }
        return StringUtils.overlay(str, StringUtils.repeat('*', repeat), end - size, end);
    }

    private static String deIdentifyFromLeft(String str, int size) {
        int end = str.length();
        int repeat;
        if (size > str.length()) {
            repeat = 0;
        } else {
            repeat = str.length() - size;
        }
        return StringUtils.overlay(str, StringUtils.repeat('*', repeat), size, end);
    }

    public static String deIdentifyFromRight(String str, int size) {
        int end = str.length();
        int repeat;
        if (size > str.length()) {
            repeat = str.length();
        } else {
            repeat = end - size;
        }
        return StringUtils.overlay(str, StringUtils.repeat('*', repeat), 0, end - size);
    }

    private static String deIdentifyMiddle(String str, int start, int end) {

        int repeat;
        if (end - start > str.length()) {
            repeat = str.length();
        } else {
            repeat = (str.length()- end) - start;
        }
        return StringUtils.overlay(str, StringUtils.repeat('*', repeat), start, str.length()-end);
    }

    private static String deIdentifyEdge(String str, int start, int end) {
        return deIdentifyLeft(deIdentifyRight(str, end), start);
    }

    public static String deIdentify(String text, int left, int right, int fromLeft, int fromRight) {
        if (left == 0 && right == 0 && fromLeft == 0 && fromRight == 0) {
            return StringUtils.repeat('*', text.length());
        } else if (left > 0 && right == 0 && fromLeft == 0 && fromRight == 0) {
            return deIdentifyLeft(text, left);
        } else if (left == 0 && right > 0 && fromLeft == 0 && fromRight == 0) {
            return deIdentifyRight(text, right);
        }else if (left > 0 && right > 0 && fromLeft == 0 && fromRight == 0) {
            return deIdentifyEdge(text, left, right);
        }else if (left == 0 && right == 0 && fromLeft > 0 && fromRight == 0) {
            return deIdentifyFromLeft(text, fromLeft);
        }else if (left == 0 && right == 0 && fromLeft == 0 && fromRight > 0) {
            return deIdentifyFromRight(text, fromRight);
        }else if (left == 0 && right == 0 && fromLeft > 0 && fromRight > 0) {
            return deIdentifyMiddle(text, fromLeft, fromRight);
        }
        return text;
    }
}
