package com.tscloud.common.tool.sshtool;

import com.jcraft.jsch.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author daowan.hu
 */
public class LinuxSSH2Impl implements IlinuxSSH2 {

    private static final Logger log = LogManager.getLogger(LinuxSSH2Impl.class);

    private static final String NO_SUCH_FILE = "no such file";

    @Override
    public boolean checkConn(String host, int port, String username, String password) {
        boolean flag = false;
        try {
            JSch jsch = new JSch();
            Session sshSession = jsch.getSession(username, host, port);
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "yes");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            sshSession.isConnected();
            flag = true;
            log.info("主机ip：" + host + ", ssh连接成功！");
            sshSession.disconnect();
        } catch (Exception e) {
            log.error("主机ip：" + host + ", ssh连接失败！", e);
        }
        return flag;
    }

    @Override
    public Session getJSchSession(String host, int port, String username, String password) throws Exception {
        Session session;
        JSch jsch = new JSch();
        session = jsch.getSession(username, host, port);
        session.setPassword(password);

        UserInfo ui = new BaseUserInfo() {

            @Override
            public void showMessage(String message) {
                JOptionPane.showMessageDialog(null, message);
            }

            @Override
            public boolean promptYesNo(String message) {
                Object[] options = {"yes", "no"};
                int foo = JOptionPane.showOptionDialog(null,
                        message,
                        "Warning",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null, options, options[0]);
                return foo == 0;
            }
        };

        session.setUserInfo(ui);
        session.setConfig("StrictHostKeyChecking", "no");
        session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
        session.setTimeout(60000000);
        session.connect();   // making a connection with timeout.
        log.info("主机ip：" + host + ", ssh连接成功！");
        return session;
    }

    @Override
    public boolean closeJSchSession(Session sshSession) {
        if (sshSession != null) {
            sshSession.disconnect();
        }
        return false;
    }

    @Override
    public boolean chmodShellFile(Session sshSession, String shell) {
        boolean flag = false;
        try {
            String command = "chmod +x " + shell + " -R";
            String charset = "UTF-8";
            Channel channel = sshSession.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);
            channel.connect();
            InputStream in = channel.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in, Charset.forName(charset)));
            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
            }
            log.debug("shell脚本权限设置完成！");
            flag = true;
        } catch (Exception e) {
            log.error("权限设置失败！", e.getMessage());
        }

        return flag;
    }

    @Override
    public boolean actionShell(Session sshSession, String command) {
        boolean flag = false;
        Channel channel = null;
        try {
            channel = sshSession.openChannel("exec");
            ((ChannelExec) channel).setCommand("export LD_LIBRARY_PATH=\"foo\" && echo $LD_LIBRARY_PATH");
            ((ChannelExec) channel).setCommand(command);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);

            InputStream in = channel.getInputStream();

            channel.connect();

            StringBuilder stdout = new StringBuilder();
            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) {
                        break;
                    }
                    stdout.append(new String(tmp, 0, i));
                }
                if (channel.isClosed()) {
                    if (in.available() > 0) {
                        continue;
                    }
                    flag = channel.getExitStatus() == 0;
                    log.info("exit-status: " + flag);
                    break;
                }
            }
            log.info(stdout.toString());
            System.out.println(stdout.toString());
        } catch (Exception e) {
            log.error("执行shell失败！", e);
        } finally {
            if (channel != null && channel.isConnected()) {
                channel.disconnect();
            }
        }
        return flag;
    }

    @Override
    public String actionShellStr(Session sshSession, String command) {
        StringBuilder stdout = new StringBuilder();
        Channel channel = null;
        try {
            channel = sshSession.openChannel("exec");
            ((ChannelExec) channel).setCommand("export LD_LIBRARY_PATH=\"foo\" && echo $LD_LIBRARY_PATH");
            ((ChannelExec) channel).setCommand(command);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);

            InputStream in = channel.getInputStream();

            channel.connect();

            byte[] tmp = new byte[1024];
            int window = 0;
            while (window <= 10) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) {
                        break;
                    }
                    stdout.append(new String(tmp, 0, i));
                    window++;
                }
                if (channel.isClosed()) {
                    if (in.available() > 0) {
                        continue;
                    }
                    log.info("exit-status: " + (channel.getExitStatus() == 0));
                    break;
                }
            }
            channel.disconnect();
            log.info(stdout.toString());
        } catch (Exception e) {
            log.error("执行shell失败！", e.getMessage());
        } finally {
            if (channel != null && channel.isConnected()) {
                channel.disconnect();
            }
        }
        return stdout.toString();
    }

    @Override
    public ChannelSftp getFtpConnect(Session sshSession) {
        ChannelSftp sftp = null;
        try {
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
        } catch (Exception e) {
            log.error("getFtpConnect 失败！", e);
        }
        return sftp;
    }

    @Override
    public boolean createDir(String sftpDirPath, ChannelSftp sftp) {
        boolean flag = false;
        try {
            sftp.cd("/");
            if (this.isDirOrFileExist(sftpDirPath, sftp)) {
                flag = true;
            } else {
                String[] pathArry = sftpDirPath.split("/");
                for (String path : pathArry) {
                    if ("".equals(path)) {
                        continue;
                    }
                    if (isDirOrFileExist(path, sftp)) {
                        sftp.cd(path);
                    } else {
                        //建立目录
                        sftp.mkdir(path);
                        //进入并设置为当前目录
                        sftp.cd(path);
                    }
                }
                sftp.cd("/");
                flag = true;
            }
        } catch (SftpException e) {
            e.printStackTrace();
        }

        return flag;
    }

    @Override
    public boolean uploadFile(List<Map<String, String>> list, Session sshSession) {
        boolean flag = false;
        try {
            //上传到linux
            ChannelSftp sftp = this.getFtpConnect(sshSession);
            if (sftp != null) {
                for (Map<String, String> map : list) {
                    String filePath = map.get("filePath");
                    String linuxFileDir = map.get("linuxFileDir");
                    String fileName = map.get("fileName");
                    if (this.upload(linuxFileDir, filePath, sftp, fileName)) {
                        flag = true;
                        log.info("【" + fileName + "】上传 成功！");
                    } else {
                        log.info("【" + fileName + "】文件上传 失败！");
                    }
                }
            }
        } catch (Exception e) {
            log.error("ssh连接失败！", e.getMessage());
        }
        return flag;
    }

    @Override
    public boolean upload(String directory, InputStream in, ChannelSftp sftp, String fileName) {
        boolean flag = false;
        try {
            this.createDir(directory, sftp);
            sftp.cd(directory);
            sftp.put(in, fileName);
            flag = true;
        } catch (Exception e) {
            log.error("上传文件失败", e);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                log.error("upload文件失败！", e);
            }
        }
        return flag;
    }

    @Override
    public InputStream getFile(ChannelSftp sftp, String filePath) {
        InputStream in = null;
        try {
            if (sftp != null) {
                in = sftp.get(filePath);
            }
        } catch (SftpException e) {
            e.printStackTrace();
        }
        return in;
    }

    @Override
    public boolean isDirOrFileExist(String directory, ChannelSftp sftp) {
        boolean isDirExistFlag = false;
        try {
            sftp.lstat(directory);
            isDirExistFlag = true;
        } catch (Exception e) {
            if (NO_SUCH_FILE.equals(e.getMessage().toLowerCase())) {
                isDirExistFlag = false;
            }
        }
        return isDirExistFlag;
    }

    @Override
    public boolean downloadFile(String directory, String remoteFileName, String localFullFilePath, ChannelSftp sftp) {
        boolean flag = false;
        try {
            if (this.isDirOrFileExist(directory, sftp)) {
                sftp.cd(directory);
                File file = new File(localFullFilePath);
                sftp.get(remoteFileName, new FileOutputStream(file));
                flag = true;
            } else {
                log.info("文件不存在，文件目录：" + directory);
            }
        } catch (Exception e) {
            log.error("linux远程共享文件下载失败！", e);
        }
        return flag;
    }

    public boolean upload(String directory, String uploadFile, ChannelSftp sftp, String fileName) {
        boolean flag = false;
        try {
            sftp.cd(directory);
            File file = new File(uploadFile);
            sftp.put(new FileInputStream(file), fileName);
            flag = true;
        } catch (Exception e) {
            log.error("上传文件失败", e);
        }
        return flag;
    }

    public static abstract class BaseUserInfo implements UserInfo, UIKeyboardInteractive {
        @Override
        public String getPassword() {
            return null;
        }

        @Override
        public boolean promptYesNo(String str) {
            return false;
        }

        @Override
        public String getPassphrase() {
            return null;
        }

        @Override
        public boolean promptPassphrase(String message) {
            return false;
        }

        @Override
        public boolean promptPassword(String message) {
            return false;
        }

        @Override
        public void showMessage(String message) {
        }

        @Override
        public String[] promptKeyboardInteractive(String destination,
                                                  String name,
                                                  String instruction,
                                                  String[] prompt,
                                                  boolean[] echo) {
            return null;
        }
    }
}
