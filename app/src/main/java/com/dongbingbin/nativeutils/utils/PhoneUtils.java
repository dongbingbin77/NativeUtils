package com.dongbingbin.nativeutils.utils;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kotlin.text.MatchResult;
import kotlin.text.Regex;

/**
 * 手机号码处理工具类
 * Created by Hilox on 2018/12/29 0029.
 */
public class PhoneUtils {

    private PhoneUtils() {}

    /**
     * 手机号格式校验正则
     */
    public static final String PHONE_REGEX = "^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}$";

    /**
     * 手机号脱敏筛选正则
     */
    public static final String PHONE_BLUR_REGEX = "(\\d{3})\\d{4}(\\d{4})";

    /**
     * 手机号脱敏替换正则
     */
    public static final String PHONE_BLUR_REPLACE_REGEX = "$1****$2";

    public static final String PHONE_REGEX_CONTAINS = "1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}$*";

    /**
     * 手机号格式校验
     * @param phone
     * @return
     */
    public static final boolean checkPhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return false;
        }
        return phone.matches(PHONE_REGEX);
    }

    /**
     * 手机号脱敏处理
     * @param phone
     * @return
     */
    public static final String blurPhone(String phone) {

        boolean result = containsPhone(phone);

        boolean checkFlag = checkPhone(phone);
        if (!checkFlag) {
            throw new IllegalArgumentException("手机号格式不正确!");
        }
        return phone.replaceAll(PHONE_BLUR_REGEX, PHONE_BLUR_REPLACE_REGEX);
    }

    public static final boolean containsPhone(String phone){

        Matcher ma = Pattern.compile(PHONE_REGEX).matcher(phone);
        return false;
    }

    public static List<String> getTelnum(String sParam) {
        if (sParam == null || sParam.length() <= 0)
            return null;
        ArrayList<String> list = new ArrayList<String>();
        Pattern pattern = Pattern.compile(PHONE_REGEX_CONTAINS);
        Matcher matcher = pattern.matcher(sParam);
        while (matcher.find()) {
            String phone = matcher.group();
            if(!list.contains(phone)) {
                list.add(phone);
            }
        }
        return list;
    }
}
