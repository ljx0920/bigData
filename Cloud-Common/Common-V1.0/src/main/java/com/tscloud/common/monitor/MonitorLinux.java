package com.tscloud.common.monitor;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * linux 下cpu 内存 磁盘 jvm的使用监控
 * 
 * @author avery_leo
 * 
 */
public class MonitorLinux {
	/**
	 * 获取cpu使用情况
	 * 
	 * @return
	 * @throws Exception
	 */
	public double getCpuUsage() throws Exception {
		double cpuUsed = 0;

		Runtime rt = Runtime.getRuntime();
		Process p = rt.exec("top -b -n 1");// 调用系统的“top"命令

		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String str = null;
			String[] strArray = null;

			while ((str = in.readLine()) != null) {
				int m = 0;

                if (str.contains(" R ")) {// 只分析正在运行的进程，top进程本身除外 &&

					strArray = str.split(" ");
					for (String tmp : strArray) {
                        if (tmp.trim().length() == 0) {
                            continue;
                        }
						if (++m == 9) {// 第9列为CPU的使用百分比(RedHat

							cpuUsed += Double.parseDouble(tmp);

						}

					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			in.close();
		}
		return cpuUsed;
	}

	/**
	 * 内存监控
	 * 
	 * @return
	 * @throws Exception
	 */
	public double getMemUsage() throws Exception {

		double menUsed = 0;
		Runtime rt = Runtime.getRuntime();
		Process p = rt.exec("top -b -n 1");// 调用系统的“top"命令

		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String str = null;
			String[] strArray = null;

			while ((str = in.readLine()) != null) {
				int m = 0;

                if (str.contains(" R ")) {// 只分析正在运行的进程，top进程本身除外 &&
					//
					// System.out.println("------------------3-----------------");
					strArray = str.split(" ");
					for (String tmp : strArray) {
                        if (tmp.trim().length() == 0) {
                            continue;
                        }

						if (++m == 10) {
							// 9)--第10列为mem的使用百分比(RedHat 9)

							menUsed += Double.parseDouble(tmp);

						}
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			in.close();
		}
		return menUsed;
	}

	/**
	 * 获取磁盘空间大小
	 * 
	 * @return
	 * @throws Exception
	 */
	public double getDeskUsage() throws Exception {
		double totalHD = 0;
		double usedHD = 0;
		Runtime rt = Runtime.getRuntime();
		Process p = rt.exec("df -hl");// df -hl 查看硬盘空间

		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String str = null;
			String[] strArray = null;
			int flag = 0;
			while ((str = in.readLine()) != null) {
				int m = 0;
				// if (flag > 0) {
				// flag++;
				strArray = str.split(" ");
				for (String tmp : strArray) {
                    if (tmp.trim().length() == 0) {
                        continue;
                    }
					++m;
					// System.out.println("----tmp----" + tmp);
                    if (tmp.contains("G")) {
						if (m == 2) {
							// System.out.println("---G----" + tmp);
                            if (!"".equals(tmp) && !"0".equals(tmp)) {
                                totalHD += Double.parseDouble(tmp.substring(0,
                                        tmp.length() - 1)) * 1024;
                            }

						}
						if (m == 3) {
							// System.out.println("---G----" + tmp);
                            if (!"none".equals(tmp) && !"0".equals(tmp)) {
                                usedHD += Double.parseDouble(tmp.substring(0,
                                        tmp.length() - 1)) * 1024;
                            }

						}
					}
                    if (tmp.contains("M")) {
						if (m == 2) {
							// System.out.println("---M---" + tmp);
                            if (!"".equals(tmp) && !"0".equals(tmp)) {
                                totalHD += Double.parseDouble(tmp.substring(0,
                                        tmp.length() - 1));
                            }

						}
						if (m == 3) {
							// System.out.println("---M---" + tmp);
                            if (!"none".equals(tmp) && !"0".equals(tmp)) {
                                usedHD += Double.parseDouble(tmp.substring(0,
                                        tmp.length() - 1));
                            }
							// System.out.println("----3----" + usedHD);
						}
					}

				}

				// }
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			in.close();
		}
		return (usedHD / totalHD) * 100;
	}

	public static void main(String[] args) throws Exception {
		MonitorLinux cpu = new MonitorLinux();
		System.out.println("---------------cpu used:" + cpu.getCpuUsage() + "%");
		System.out.println("---------------mem used:" + cpu.getMemUsage() + "%");
		System.out.println("---------------HD used:" + cpu.getDeskUsage() + "%");
//		System.out.println("------------jvm监控----------------------");
//		Runtime lRuntime = Runtime.getRuntime();
//		System.out.println("--------------Free Momery:" + lRuntime.freeMemory()+ "K");
//		System.out.println("--------------Max Momery:" + lRuntime.maxMemory()+ "K");
//		System.out.println("--------------Total Momery:"+ lRuntime.totalMemory() + "K");
//		System.out.println("---------------Available Processors :"+ lRuntime.availableProcessors());
	}
}
