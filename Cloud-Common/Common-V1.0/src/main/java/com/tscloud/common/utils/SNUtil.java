package com.tscloud.common.utils;

import java.util.Scanner;

public class SNUtil {

    public static void main(String[] arg){
        try{
            String endDate = "";
            System.out.println("开始生成SN.......");
            System.out.print("请输入公司名称：");
            Scanner scan = new Scanner(System.in);
            String company  = scan.nextLine();

            System.out.print("请输入许可证内容(0-试用，1-正式)：");
            String type = scan.nextLine();

            if("0".equals(type.trim())){
                String dataStr = DateUtil.getDateTimeInSecond();
                endDate = DateUtil.getDateTimeInSecond(DateUtil.addDay(dataStr,90));
            }else if("1".equals(type.trim())){
                endDate = "00";
            }else{
                endDate = "999";
            }

            System.out.print("请输入机器码：");
            String macStr = scan.nextLine();

            StringBuffer sb = new StringBuffer();
            sb.append("Company：").append(company).append("& \n")
                    .append("Type:").append(type).append("&\n")
                    .append("Time：").append(endDate).append("&\n")
                    .append("SN:").append(macStr).append("\n");

            System.out.println(EncodeUtils.strEncode(sb.toString()));
            System.out.println(EncodeUtils.strDecode(EncodeUtils.strEncode(sb.toString())));

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
