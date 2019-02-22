package com.tscloud.common.tool.sshtool;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * ssh 无秘钥打通
 * <p>
 * Created by jiancai.wang on 2016/10/19.
 */
public class SSHAuthTool {

    private static Logger log = LogManager.getLogger(SSH2Utils.class);
    private SSH2Utils ssh2Utils = SSH2Utils.getInstance();
    private static SSHAuthTool sshAuthTool;

    private String rasPath = "/root/.ssh/id_rsa";
    private String rasPubPath = "/root/.ssh/id_rsa.pub";
    private String authKeysPath = "/root/.ssh/authorized_keys";

    public static SSHAuthTool getInstance() {
        if (sshAuthTool == null) {
            synchronized (SSHAuthTool.class) {
                if (sshAuthTool == null) {
                    sshAuthTool = new SSHAuthTool();
                }
            }
        }
        return sshAuthTool;
    }

    private SSHAuthTool() {

    }

    /**
     * 删除文件
     *
     * @param session
     * @param path
     */
    private void removeFile(Session session, String path) {
        String rm = "rm -f " + path;
        ssh2Utils.actionShell(session, rm);
        log.info("删除文件>>>" + path);
    }

    /**
     * 添加host TODO:如果存在则不追加
     *
     * @param session
     * @param host
     * @param ip
     * @return
     */
    public void addHosts(Session session, String host, String ip) {
        ChannelSftp sftp = ssh2Utils.getFtpConnect(session);
        try {
            String echoCmd = "echo " + ip + " " + host + " >> /etc/hosts";
            ssh2Utils.actionShell(session, echoCmd);
            log.info("添加host映射<<<" + ip + " - " + host);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("添加host映射失败!" + e.getMessage());
        }
    }

    /**
     * 初始化秘钥打通环境（删除已有的秘钥文件）
     *
     * @param session
     * @param hostMap
     */
    private boolean cleaning(Session session, Map<String, String> hostMap) {
        boolean result = false;
        try {
            removeFile(session, rasPath);
            removeFile(session, rasPubPath);
            removeFile(session, authKeysPath);
            hostMap.forEach((host, ip) -> {
                String newPath = rasPubPath.replace(".pub", "_" + host + ".pub");
                removeFile(session, newPath);
            });
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除秘钥失败!" + e.getMessage());
        }
        return result;
    }

    /**
     * 生成秘钥
     *
     * @param session
     * @return
     */
    public byte[] sshKeyGen(Session session) {
        byte[] result = null;
        String sshKeyGenCmd = "ssh-keygen -t rsa -f /root/.ssh/id_rsa -P ''";
        ChannelSftp sftp = ssh2Utils.getFtpConnect(session);
        try {
            if (ssh2Utils.actionShell(session, sshKeyGenCmd)) {
                result = IOUtils.toByteArray(ssh2Utils.getFile(sftp, rasPubPath));
                log.info("秘钥生成<<<" + result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("秘钥生成失败!");
        } finally {
            sftp.disconnect();
        }
        return result;
    }

    /**
     * 传输秘钥给其它主机
     *
     * @param session
     * @param host
     * @param keyStream
     */
    private boolean deliveryKey(Session session, String host, byte[] keyStream) {

        // /root/.ssh
        boolean result = false;
        // "/root/.ssh/id_rsa.pub"; ->> /root/.ssh/id_rsa_master.pub
        String newPath = rasPubPath.replace(".pub", "_" + host + ".pub");
        ChannelSftp sftp = ssh2Utils.getFtpConnect(session);
        try {
            InputStream stream = new ByteArrayInputStream(keyStream);
            sftp.put(stream, newPath);
            result = true;
            log.info("秘钥传输成功!" + newPath);
        } catch (SftpException e) {
            e.printStackTrace();
            log.error("秘钥传输失败!" + e.getMessage());
        } finally {
            sftp.disconnect();
        }
        return result;
    }

    /**
     * 将秘钥添加到加鉴权秘钥中
     *
     * @param subSession
     * @param host
     */
    private void catKeyToAuthorizedKeys(Session subSession, String host) {
        // cat id_rsa.pub | ssh root@xiaojiali 'cat >> /root/.ssh/authorized_keys'
        ChannelSftp sftp = ssh2Utils.getFtpConnect(subSession);
        String newPath = rasPubPath.replace(".pub", "_" + host + ".pub");
        try {
            if (ssh2Utils.isDirOrFileExist(newPath, sftp)) {
                String catCmd = "cat " + newPath + " >> /root/.ssh/authorized_keys";
                if (ssh2Utils.actionShell(subSession, catCmd)) {
                    log.info("秘钥添加成功!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("秘钥添加失败!" + e.getMessage());
        } finally {
            sftp.disconnect();
        }
    }

    private void restart(Session session) {
        String chmodCmd = "chmod 600 /root/.ssh/authorized_keys";
        ssh2Utils.actionShell(session, chmodCmd);
        String restartCmd = "service sshd restart";
        ssh2Utils.actionShell(session, restartCmd);
    }

    /**
     * 设置ssh无密码登陆
     * <p>
     * 步骤：
     * 1. 设置host(遍历配置文件，添加新的host到各个机器的/etc/hosts)
     * 2. 初始化免秘钥的环境（确保机器已经是独立的，将主机上面的秘钥文件"rsa_key"、"rsa_key.pub"等文件删除）
     * 3. 重新生成秘钥文件（此步骤必须确保上步骤成功，所有的文件都被删除的情况，否则新秘钥不会被生成）
     * 4. 将.pub秘钥文件传输到其他主机上（由于.pub命名一致，防止秘钥覆盖，需要对.pub文件重命名，再被发送到其他主机上）
     * 5. 将推送后的所有秘钥文件追加到/authorized_keys
     * 6. 重启ssh（service sshd restart）
     *
     * @param conf {"host": []}
     * @return
     */
    public boolean setUpSshAutoAuth(JSONObject conf) {
        boolean result = false;

        // host : session
        Map<String, Session> sessionMap = new HashedMap();
        // host : ip
        Map<String, String> hostMap = new HashedMap();
        // host : inputStream
        Map<String, byte[]> keyIdMap = new HashedMap();
        try {
            List<JSONObject> hostsConf = (List<JSONObject>) conf.get("hosts");
            if (hostsConf.size() > 0) {
                for (JSONObject confJson : hostsConf) {
                    String host = confJson.getString("host");
                    String ip = confJson.getString("ip");
                    String user = confJson.getString("user");
                    String pwd = confJson.getString("pwd");
                    Session session = ssh2Utils.getJSchSession(ip, 22, user, pwd);
                    // init session
                    sessionMap.put(host, session);
                    // inti hosts
                    hostMap.put(host, ip);
                }
            }
            /**
             *  1. cover hosts /etc/hosts
             *  **当多个ip对应一个机器名，或是多个机器名对应一个ip
             *  方式一：追加方式 (缺省)
             *  方式二：覆盖
             */
            // echo ip user >> /etc/hosts

            if (sessionMap.isEmpty()) {
                return false;
            }

            sessionMap.forEach((host, session) -> {
                // 将Hosts添加到所有机器上
                hostMap.forEach((subHost, ip) -> {
                    addHosts(session, subHost, ip);
                });
                // 初始化秘钥打通环境
                cleaning(session, hostMap);
                // 生成秘钥文件 (秘钥文件/rsa_key.pub)
                keyIdMap.put(host, sshKeyGen(session));
            });

            sessionMap.forEach((host, session) -> {
                // 分派秘钥（将个人的秘钥发给其它成员）
                keyIdMap.forEach((subHost, keyByte) -> {
                    if (deliveryKey(session, subHost, keyByte)) {
                        // 追加秘钥
                        catKeyToAuthorizedKeys(session, subHost);
                    }
                });
                // 重启
                restart(session);
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sessionMap.forEach((host, session) -> {
                if (session.isConnected()) {
                    session.disconnect();
                }
            });
        }
        return result;
    }

    public static void main(String[] args) {

        /**
         * {
         * "hosts": [
         * {
         * "host": "master",
         * "ip": "10.10.0.131",
         * "user":"root",
         * "pwd":123456
         * },
         * {
         * "host": "xiaojiali",
         * "ip": "10.10.0.129",
         * "user":"root",
         * "pwd":123456
         * },
         * {
         * "host": "liuyang",
         * "ip": "10.10.0.128",
         * "user":"root",
         * "pwd":123456
         * }
         * ]
         * }
         */
        JSONObject host1 = new JSONObject();
        JSONObject host2 = new JSONObject();
        JSONObject host3 = new JSONObject();
        host1.put("host", "master");
        host1.put("ip", "10.10.0.131");
        host1.put("user", "root");
        host1.put("pwd", "123456");
        host2.put("host", "xiaojiali");
        host2.put("ip", "10.10.0.129");
        host2.put("user", "root");
        host2.put("pwd", "123456");
        host3.put("host", "liuyang");
        host3.put("ip", "10.10.0.128");
        host3.put("user", "root");
        host3.put("pwd", "123456");
        List<JSONObject> hostList = Lists.newArrayList();
        hostList.add(host1);
        hostList.add(host2);
        hostList.add(host3);
        JSONObject conf = new JSONObject();
        conf.put("hosts", hostList);
        // SSHAuthTool.getInstance().setUpSshAutoAuth(conf);
        Map<String, String> map = System.getenv();
        System.out.println(map);
    }
}
