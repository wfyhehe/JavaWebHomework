package com.wfy.web.other;

import com.wfy.web.utils.MD5Util;
import jdk.nashorn.internal.parser.DateParser;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateParser;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/8/16.
 */
public class JavaSETest {

    @Test
    public void test() {
        System.out.println(new Date(System.currentTimeMillis()));
    }

    @Test
    public void testNull() {
        System.out.println(null == null);
    }

    public void changeRef(Long a) {
        a = new Long(5);
    }

    @Test
    public void testRef() {
        long refA = 0;
        System.out.println(refA);
        changeRef(refA);
        System.out.println(refA);
    }

    @Test
    public void testIterator() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        Iterator iterator = list.iterator();
        System.out.println(list);
        while (iterator.hasNext()) {
        }
        System.out.println(list);
    }

    @Test
    public void stringTest() {
        String name = null;
//        name = name != null ? "%" + name + "%" : "%%";
        name ="%" + name + "%" ;
        System.out.println(name);
    }

    @Test
    public void nullTest() {
        Object name = null;
        Date name2 = (Date) name;
        System.out.println(name);
        System.out.println(name2);
    }

    @Test
    public void dateTest() throws ParseException {
        String dateStr = "2017-09-05 12:44:56";
        String dateStr2 = "2017-09-05T12:44:56.270Z";
        System.out.println(DateFormatUtils.ISO_8601_EXTENDED_DATETIME_FORMAT.parse(dateStr2));
//        DateFormatUtils.format(new Date(), DateFormatUtils.ISO_8601_EXTENDED_DATETIME_FORMAT
//                .toString());
//        DateUtils.parseDate(dateStr, SimpleDateFormat.);
//        Date date = new Date();
//        System.out.println(date);
    }

    @Test
    public void iterateSet() {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            set.add(i);
        }
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            iterator.next();
        }
    }

    @Test
    public void testMD5() {
        String md5 = MD5Util.getMD5("123456");
        System.out.println(md5);
    }

    @Test
    public void testUUID() {
        for (int i = 0; i < 100; i++) {
            System.out.println(UUID.randomUUID().toString());
        }
    }

    @Test
    public void testString() {
        String str = "abcde";
        char[] buffer = str.toCharArray();
        buffer[str.length() - 1] = '-';
        String newStr = String.valueOf(buffer);
        System.out.println(newStr);
    }


}
