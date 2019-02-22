package com.tscloud.common.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Scanner;

import static com.tscloud.common.utils.DataConvert.bytesToHexString;

/**
 * 获取CPU核数
 *
 * @author huwanshan
 */
public class CpuNum {

    private static final String WINDOWS = "windows";

    /**
     * 获取cpu核数
     *
     * @return
     */
    public static int getCpuNum() {
        int num = 1;
        // 操作系统
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().startsWith(WINDOWS)) {
            num = Runtime.getRuntime().availableProcessors();
        } else {
            String cmdCpuCount = " cat /proc/cpuinfo |grep processor|sort -u|wc -l ";
            String cpuCount = execLinuxCmd(cmdCpuCount);
            if (cpuCount != null) {
                String[] arr = cpuCount.split(":");
                if (arr.length > 0) {
                    num = Integer.valueOf(arr[arr.length - 1]);
                }
                if (num <= 0) {
                    num = 1;
                }
            }
        }
        return num;
    }

    /**
     * 执行linux命令
     *
     * @param cmd
     * @return
     */
    private static String execLinuxCmd(String cmd) {
        String result = "";
        InputStream in;
        try {
            String[] cmdA = {"/bin/sh", "-c", cmd};
            Process pro = Runtime.getRuntime().exec(cmdA);
            pro.waitFor();
            in = pro.getInputStream();
            BufferedReader read = new BufferedReader(new InputStreamReader(in));
            result = read.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Object exec(String cmd) {
        try {
            String[] cmdA = {"/bin/sh", "-c", cmd};
            Process process = Runtime.getRuntime().exec(cmdA);
            LineNumberReader br = new LineNumberReader(new InputStreamReader(
                    process.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取机器码
     *
     * @return
     */
    public static String getCpuSerialNo() {
        String serial = null;
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"wmic", "cpu", "get", "ProcessorId"});
            process.getOutputStream().close();
            Scanner sc = new Scanner(process.getInputStream());
            String property = sc.next();
            serial = sc.next();
            System.out.println(property + ": " + serial);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serial;
    }

    /**
     * 获取网卡地址
     * @return
     */
    public static String getMac() {
        String sn = "";
        StringBuffer sb  = new StringBuffer();
        try {
            Enumeration<NetworkInterface> el = NetworkInterface.getNetworkInterfaces();
            while (el.hasMoreElements()) {
                byte[] mac = el.nextElement().getHardwareAddress();
                if (mac == null)
                    continue;
                String hexstr = bytesToHexString(mac);
                if(hexstr.length()<16){
                    String macStr = getSplitString(hexstr, "-", 2).toUpperCase();
                    sb.append(macStr).append("#");
                }
            }
            sn = EncodeUtils.strEncode(sb.toString());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return sn;
    }

    private static String getSplitString(String str, String split, int length) {
        int len = str.length();
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < len; i++) {
            if (i % length == 0 && i > 0) {
                temp.append(split);
            }
            temp.append(str.charAt(i));
        }
        String[] attrs = temp.toString().split(split);
        StringBuilder finalMachineCode = new StringBuilder();
        for (String attr : attrs) {
            if (attr.length() == length) {
                finalMachineCode.append(attr).append(split);
            }
        }
        String result = finalMachineCode.toString().substring(0,
                finalMachineCode.toString().length() - 1);
        return result;
    }

    public static void main(String[] args) {
        System.out.print(CpuNum.getMac() + "\n");
//        System.out.println(CpuNum.getCpuSerialNo() + "\n");
    }
}
