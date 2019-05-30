package com.cloud.backend.utils;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class CallLinuxShTools {

   /***
    * @author: yuansq
    * @date: 2018-08-14 17:32
    * @param: [cmd, ip, userName, userPwd] 执行的命令  主机ip 用户名 用户密码
    * @desc:  登录linux服务器 执行ssh 命令 成功返回0 失败返回非0
    * @return: int
    */
    public static String exeLinuxCommand(String cmd,String ip,String userName,String userPwd)
    {
        String charset = Charset.defaultCharset().toString();
        String resJson="";
        Map<String, Object> resMap=new HashMap<String,Object>();
        boolean flag =false;
        InputStream stdOut = null;
        InputStream stdErr = null;
        String outStr="";
        String outErr = "";
        int ret = -1;
        Connection   conn = new Connection(ip);
        try {
            conn.connect();
            flag=conn.authenticateWithPassword(userName,userPwd);
            if(flag)
            {
                System.out.println("认证成功");

                //在connection中打开一个新的会话
                Session session = conn.openSession();
                //在远程服务器上执行linux指令
                session.execCommand(cmd);
                //指令执行结束后的输出
                stdOut = new StreamGobbler(session.getStdout());

                outStr = processStream(stdOut, charset);
                System.out.print("-----outStr-----"+outStr);
                //指令执行结束后的错误
                stdErr = new StreamGobbler(session.getStderr());
                outErr = processStream(stdErr, charset);
                System.out.print("-----outErr-----"+outErr);
                //等待指令执行结束
                session.waitForCondition(ChannelCondition.EXIT_STATUS, 60000);
                //取得指令执行结束后的状态
                ret = session.getExitStatus();
                System.out.println("---ExitCode:--- " + ret);
                resMap.put("code",ret);
                resMap.put("result",ret==0?outStr.replaceAll("\u0000",""):outErr.replaceAll("\u0000",""));
                resJson =JsonUtil.mapToJson(resMap);
                conn.close();
                session.close();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return resJson;
    }

    /**
     * @param in
     * @param charset
     * @return  读取执行命令后返回的结果
     * @throws IOException
     */
    private static String processStream(InputStream in, String charset) throws IOException {
        byte[] buf = new byte[1024];
        StringBuilder sb = new StringBuilder();
        while (in.read(buf) != -1) {
            sb.append(new String(buf, charset));
        }
        return sb.toString();
    }

    /**
     * 执行系统命令, 返回执行结果 当前系统的命令
     * 命令 bin/kafka-topics.sh --zookeeper 10.97.65.31:2181 --create --topic hermestopic2 --partitions 3  --replication-factor 3
     * @param cmd 需要执行的命令
     * @param dir 执行命令的子进程的工作目录, null 表示和当前主进程工作目录相同
     * @author: yuansq
     */
    public static Map excLinuxCommand(String[] cmd, File dir) throws Exception {
        StringBuilder result = new StringBuilder();
        Process process = null;
        BufferedReader bufrIn = null;
        BufferedReader bufrError = null;
        Map<String, Object> resMap=new HashMap<String,Object>();
        try {
            // 执行命令, 返回一个子进程对象（命令在子进程中执行）
            process = Runtime.getRuntime().exec(cmd, null, dir);
            process.waitFor();
            // 获取命令执行结果, 有两个结果: 正常的输出 和 错误的输出（PS: 子进程的输出就是主进程的输入）
            bufrIn = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
            bufrError = new BufferedReader(new InputStreamReader(process.getErrorStream(), "UTF-8"));
            String line = null;
            while ((line = bufrIn.readLine()) != null) {
                result.append(line).append('\n');
            }
            while ((line = bufrError.readLine()) != null) {
                result.append(line).append('\n');
            }
            resMap.put("result",result.toString());
        } finally {
            closeStream(bufrIn);
            closeStream(bufrError);
            if (process != null) {
                process.destroy();
            }
        }
        return resMap;
    }
    private static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

