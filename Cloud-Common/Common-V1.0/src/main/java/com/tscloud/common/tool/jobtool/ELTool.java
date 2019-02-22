package com.tscloud.common.tool.jobtool;

import com.tscloud.common.utils.StringUtils;

/**
 * Cron表达式位数对应的说明
 * 1.Seconds
 * 2.Minutes
 * 3.Hours
 * 4.Day-of-Month
 * 5.Month
 * 6.Day-of-Week
 * 7.Year (可选字段)
 * JOB需要的表达式的封装
 *
 * @author huwanshan
 * @version V1.0
 * @Company 北京光码软件有限公司
 * @date 2008-8-26 上午10:45:25
 * @Team 数据交换平台研发小组
 */
public class ELTool {
    private static ELTool eltool = null;

    public synchronized static ELTool init() {
        if (eltool == null) {
            eltool = new ELTool();
        }
        return eltool;
    }

    /**
     * 秒表达式（"0/1 * * * * ?"）
     *
     * @param datetime("0/"+datetime+" * * * * ?")
     * @return
     * @author huwanshan
     * @date 2008-9-1 下午03:55:15
     */
    public String sec(String datetime) {
        String time = "0/" + datetime + " * * * * ?";    //按秒
        return time;
    }

    /**
     * 分钟表达式（"0 0/1 * * * ?"）
     *
     * @param datetime
     * @return
     * @author huwanshan
     * @date 2008-9-1 下午03:55:15
     */
    public String cent(String datetime) {
        String cent = "0 0/" + datetime + " * * * ?";
        return cent;
    }

    /**
     * 从0点到23每隔n（小时）调度一次
     *
     * @param datetime("0-23/2" n=2)
     * @return
     * @author huwanshan
     * @date 2008-9-1 下午04:18:38
     */
    public String time(String datetime) {
        String times = "0 0 0-23/" + datetime + " * * ?";
        return times;
    }


    /**
     * 以天为单位的执行表达式
     *
     * @param h
     * @param m
     * @param s
     * @return
     */
    public String day(String h, String m, String s) {
        String days = s + " " + m + " " + h + " * * ?";
        return days;
    }


    /**
     * 星期表达式
     * 每月周三12点『天（星期）（1~7 1=SUN 或 SUN，MON，TUE，WED，THU，FRI，SAT）』
     *
     * @param weeks ("0 0 12 ? * WED,THU,SAT")
     * @return
     * @author huwanshan
     * @date 2008-9-1 下午10:42:15
     */
    public String week(String weeks, String time) {
        //String times = "0 10 1 ? * "+weeks;
        if (StringUtils.isBlank(time)) {
            String times = "0 0 0 ? * " + weeks;
            return times;
        } else {
            String[] one = time.split(":");
            String times = "0 " + Integer.valueOf(one[1]) + " " + Integer.valueOf(one[0]) + " ? * " + weeks;
            return times;
        }
    }

    /**
     * 按照月份时间生成cron表达式
     * @param months
     * @param day
     * @param time
     * @return
     */
    public String month(String months, String day, String time) {
        if (StringUtils.isBlank(time)) {
            String times = "0 0 0 " + day + " " + months + " ?";
            return times;
        } else {
            String[] one = time.split(":");
            String times = Integer.valueOf(one[2]) + " " + Integer.valueOf(one[1]) + " " + Integer.valueOf(one[0]) + " " + day + " " + months + " ?";
            return times;
        }
    }

    /**
     * 按每天什么时间调度
     *
     * @param time("0 0 10，14，16 * * ? 每天上午10点，下午2点，4点")
     * @return
     * @author huwanshan
     * @date 2008-9-1 下午10:53:08
     */
    public String today(String time) {
        //String times = "0 0 "+time+" * * ?";
        String[] one = time.split(":");
        String times = "0 " + Integer.valueOf(one[1]) + " " + Integer.valueOf(one[0]) + " * * ?";
        return times;
    }

    /**
     * 去掉汉字月
     *
     * @param str
     * @return
     * @author shm
     */
    public static String removeYue(String str) {
        String newMonth = "";
        for (int i = 0; i < str.split(",").length; i++) {
            newMonth = newMonth + str.split(",")[i].substring(0, 2) + ",";
        }
        newMonth = newMonth.substring(0, newMonth.length() - 1);
        return newMonth;
    }
}
