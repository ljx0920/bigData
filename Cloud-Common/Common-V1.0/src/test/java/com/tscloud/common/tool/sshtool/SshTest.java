package com.tscloud.common.tool.sshtool;

import com.google.common.collect.Lists;
import com.jcraft.jsch.Session;
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Sigar;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by jicheng.cui on 2018/1/22.
 */
public class SshTest {

    private String ip = "10.10.0.186";
    private int port = 22;
    private String user = "root";
    private String password = "hirainlinux";
    private static Sigar sigar;

    public void getSsh() throws Exception {
        LinuxSSH2Impl linuxSSH2 = new LinuxSSH2Impl();
        Session session = linuxSSH2.getJSchSession(ip, 222, user, password);
        if (session != null) {
            System.out.println("yes");
        }
    }

    public static Sigar getInstance() {
        if (null == sigar) {
            sigar = new Sigar();
        }
        return sigar;
    }

    @Test
    public void getShellRet() {
        //try {

        this.getCpu();
          /*  Sigar sigar = new Sigar();

            CpuInfo infos[] = sigar.getCpuInfoList();
            CpuPerc cpuList[] = sigar.getCpuPercList();

            JSONObject jsonObject = new JSONObject();

            JSONArray jsonArray = new JSONArray();
            for (int i = 0, len = infos.length; i < len; i++) {// 不管是单块CPU还是多CPU都适用

                CpuInfo info = infos[i];

                JSONObject jso = new JSONObject();

                jso.put("mhz", info.getMhz()); //CPU的总量MHz
                jso.put("company", info.getVendor()); //CPU的厂商
                jso.put("model", info.getModel()); //CPU型号类别
                jso.put("cache.size", info.getCacheSize()); // 缓冲缓存数量
                CpuPerc cpu = cpuList[i];
                jso.put("freq.user", CpuPerc.format(cpu.getUser())); //CPU的用户使用率
                jso.put("freq.sys", CpuPerc.format(cpu.getSys())); //CPU的系统使用率
                jso.put("freq.wait", CpuPerc.format(cpu.getWait())); //CPU的当前等待率
                jso.put("freq.nice", CpuPerc.format(cpu.getNice())); //CPU的当前错误率
                jso.put("freq.idle", CpuPerc.format(cpu.getIdle())); //CPU的当前空闲率
                jso.put("freq.combined", CpuPerc.format(cpu.getCombined())); //CPU总的使用率
                jsonArray.add(jso);
            }
            System.out.println(jsonArray);
        } catch (Exception e) {
            System.out.println(e);
        }*/
    }

    public void getCpu() {
        try {
            List<Map> cpuInfoList = Lists.newArrayList();

            CpuInfo[] cpuInfos = getInstance().getCpuInfoList();
            CpuPerc[] cpuPercs = getInstance().getCpuPercList();

            for (int i = 0; i < cpuInfos.length; i++) {
                CpuInfo cpuInfo = cpuInfos[i];
                /*cpuInfoList.add(new SigarInfoEntity(String.valueOf(i), "第" + i
                        + "个CPU信息"));*/
                System.out.println("第" + i + "个CPU信息=" + String.valueOf(i));
                /*cpuInfoList.add(new SigarInfoEntity(
                        String.valueOf(cpuInfo.getMhz()), "CPU的总量MHz" + i));*/
                System.out.println("CPU的总量MHz" + cpuInfo.getMhz());
                /*cpuInfoList.add(new SigarInfoEntity(cpuInfo.getVendor(),
                        "获得CPU的供应商" + i));*/
                System.out.println("获得CPU的供应商" + i + "  " + cpuInfo.getVendor());
                /*cpuInfoList.add(new SigarInfoEntity(cpuInfo.getModel(), "获得CPU的类别"
                        + i));*/
                System.out.println("获得CPU的类别" + cpuInfo.getModel());
                /*cpuInfoList.add(new SigarInfoEntity(String.valueOf(cpuInfo
                        .getCacheSize()), "缓冲存储器数量" + i));*/
                System.out.println("缓冲存储器数量" + i + "  " + cpuInfo.getCacheSize());
            }

            for (int i = 0; i < cpuPercs.length; i++) {
                CpuPerc cpuPerc = cpuPercs[i];
/*                cpuInfoList.add(new SigarInfoEntity(String.valueOf(i), "第" + i
                        + "个CPU片段"));
                cpuInfoList.add(new SigarInfoEntity(String.valueOf(cpuPerc
                        .getUser()), "CPU用户使用率" + i));*/
                System.out.println("CPU用户使用率" + i + "  " + cpuPerc.getUser());
              /*  cpuInfoList.add(new SigarInfoEntity(
                        String.valueOf(cpuPerc.getSys()), "CPU系统使用率" + i));
                cpuInfoList.add(new SigarInfoEntity(String.valueOf(cpuPerc
                        .getWait()), "CPU当前等待率" + i));
                cpuInfoList.add(new SigarInfoEntity(String.valueOf(cpuPerc
                        .getNice()), "CPU当前错误率" + i));
                cpuInfoList.add(new SigarInfoEntity(String.valueOf(cpuPerc
                        .getIdle()), "CPU当前空闲率" + i));
                cpuInfoList.add(new SigarInfoEntity(String.valueOf(cpuPerc
                        .getCombined()), "CPU总的使用率" + i));*/

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
