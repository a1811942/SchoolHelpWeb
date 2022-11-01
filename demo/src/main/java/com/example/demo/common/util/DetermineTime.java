package com.example.demo.common.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class DetermineTime {
    public static String showDate(Timestamp date, String pattern){

        String dateStr=format(date,pattern);

        String year = dateStr.substring(0,4);

        Long yearNum =Long.parseLong(year);

        int month = Integer.parseInt(dateStr.substring(5,7));

        int day = Integer.parseInt(dateStr.substring(8,10));

        String hour = dateStr.substring(11,13);

        String minute = dateStr.substring(14,16);

        long addtime =date.getTime();

        long today = System.currentTimeMillis();//当前时间的毫秒数

        Timestamp now = new Timestamp(today);

        String nowStr=format(now,pattern);

        int nowDay = Integer.parseInt(nowStr.substring(8,10));

        String result="";

        long l=today-addtime;//当前时间与给定时间差的毫秒数

        long days=l/(24*60*60*1000);//这个时间相差的天数整数，大于1天为前天的时间了，小于24小时则为昨天和今天的时间

        long hours=(l/(60*60*1000)-days*24);//这个时间相差的减去天数的小时数

        long min=((l/(60*1000))-days*24*60-hours*60);//

        long s=(l/1000-days*24*60*60-hours*60*60-min*60);

        if(days > 0){

            if(days>0 && days<2){

//                result ="前天 "+hour+":"+minute;

                result = yearNum+"年"+month+"月"+day+"日 "+hour+":"+minute;
            } else {

                result = yearNum+"年"+month+"月"+day+"日 "+hour+":"+minute;

            }

        }else if(hours > 0 ) {

            if(day!=nowDay){

//                result = "昨天"+hour+":"+minute;

                result = yearNum+"年"+month+"月"+day+"日 "+hour+":"+minute;
            }else{

                result=hours+"小时 前";

            }

        } else if(min > 0){

//            if(min>0 && min<15){
//
//                result="刚刚";

//            }
//                else {

                result=min+"分 前";

//            }

        }else {

            result="刚刚";

        }

        return result;

    }

    /**

     * 日期格式化

     * @param date 需要格式化的日期

     * @param pattern 时间格式，如：yyyy-MM-dd HH:mm:ss

     * @return 返回格式化后的时间字符串

     */

    public static String format(Timestamp date, String pattern){

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        return sdf.format(date);

    }

}
