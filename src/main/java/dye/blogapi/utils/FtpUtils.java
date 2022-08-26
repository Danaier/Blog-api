package dye.blogapi.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static dye.blogapi.common.CONSTANT.FTP_HOST;

@Slf4j
public class FtpUtils {

    private static String host = FTP_HOST;
    private static String username = "ftpuser";
    private static String password = "ftpuser";
    private static int port = 21;


    /**
     * 登陆FTP并获取FTPClient对象
     * @param host     FTP主机地址
     * @param port     FTP端口
     * @param userName 登录用户名
     * @param password 登录密码
     * @return
     */
    public static FTPClient loginFTP(String host, int port, String userName, String password) {
        FTPClient ftpClient = null;
        try {
            ftpClient = new FTPClient();
            ftpClient.setConnectTimeout(1000*30);//设置连接超时时间
            ftpClient.connect(host, port);// 连接FTP服务器
            ftpClient.login(userName, password);// 登陆FTP服务器
            ftpClient.setControlEncoding("UTF-8");// 中文支持
            // 设置文件类型为二进制（如果从FTP下载或上传的文件是压缩文件的时候，不进行该设置可能会导致获取的压缩文件解压失败）
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();//开启被动模式，否则文件上传不成功，也不报错
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                log.info("连接FTP失败，用户名或密码错误。");
                ftpClient.disconnect();
            } else {
                log.info("FTP连接成功!");
            }
        } catch (Exception e) {
            log.info("登陆FTP失败，请检查FTP相关配置信息是否正确！"+ e);
            return null;
        }
        return ftpClient;
    }

    /**
     * 从FTP下载文件到本地
     * @param ftpClient     已经登陆成功的FTPClient
     * @param fileName   FTP上的目标文件路径+文件名称
     * @param localFilePath 下载到本地的文件路径
     * @param servicePath  服务器的上面文件的上层路径
     */
    public static String downFile(FTPClient ftpClient,String servicePath, String fileName, String localFilePath) {
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            ftpClient.enterLocalPassiveMode();
            is = ftpClient.retrieveFileStream(servicePath+fileName);// 获取ftp上的文件
            fos = new FileOutputStream(new File(localFilePath+fileName));
            // 文件读取方式一
            int i;
            byte[] bytes = new byte[1024];
            while ((i = is.read(bytes)) != -1) {
                fos.write(bytes, 0, i);
            }
            // 文件读取方式二
            //ftpClient.retrieveFile(ftpFilePath, new FileOutputStream(new File(localFilePath)));
            ftpClient.completePendingCommand();
            log.info("FTP文件下载成功！");
        } catch (Exception e) {
            log.error("FTP文件下载失败！" + e);
        } finally {
            try {
                if (fos != null) fos.close();
                if (is != null) is.close();
            } catch (IOException e) {
                log.error("下载流关闭失败"+e);
                return null;
            }
        }
        return localFilePath+fileName;
    }

    //判断ftp服务器文件是否存在
    private static boolean existFile(FTPClient ftpClient,String path) throws IOException {
        boolean flag = false;
        FTPFile[] ftpFileArr = ftpClient.listFiles(path);
        if (ftpFileArr.length > 0) {
            flag = true;
        }
        return flag;
    }

    public static List<String> getFileNameList(FTPClient ftpClient, String ftpDirPath) {
        List<String> list = new ArrayList();
        try {
            if (ftpDirPath.startsWith("/") && ftpDirPath.endsWith("/")) {
                // 通过提供的文件路径获取FTPFile对象列表
                FTPFile[] files = ftpClient.listFiles(ftpDirPath);
                // 遍历文件列表，打印出文件名称
                for (int i = 0; i < files.length; i++) {
                    FTPFile ftpFile = files[i];
                    // 此处只打印文件，未遍历子目录（如果需要遍历，加上递归逻辑即可）
                    if (ftpFile.isFile()) {
//                        log.info(ftpDirPath + ftpFile.getName());
                        list.add(ftpFile.getName());
                    }
                }
                log.info("当前FTP路径可用");
            } else {
                log.info("当前FTP路径不可用");
            }
        } catch (IOException e) {
            log.error("错误"+e);
        }
        return list;
    }




}
