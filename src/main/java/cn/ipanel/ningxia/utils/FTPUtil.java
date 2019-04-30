package cn.ipanel.ningxia.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.ipanel.ningxia.config.SysConfig;

public class FTPUtil {

	protected static final Logger log = LoggerFactory.getLogger(FTPUtil.class);  
    private static String host = SysConfig.FTP_HOST;  
    private static int port = Integer.valueOf(SysConfig.FTP_PORT);  
    private static String username = SysConfig.FTP_USER;
    
    private static String password = SysConfig.FTP_PASS;  
  
    private static final boolean BINARYTRANSFER = true;  
    private static final boolean PASSIVE_MODE = true;  
    private static final String ENCODING = "UTF-8";  
    private static final int CLIENT_TIMEOUT = 1000 * 30;  
    
    private static FTPClient getFTPClient() {  
       
        FTPClient ftpClient = new FTPClient(); //构造一个FtpClient实例  
        ftpClient.setControlEncoding(ENCODING); //设置字符集  
        connect(ftpClient); //连接到ftp服务器  
        //设置为passive模式  为被动模式
        if (PASSIVE_MODE) {  
            ftpClient.enterLocalPassiveMode();  
        } 
        try { 
            if (BINARYTRANSFER) {  //设置文件传输类型  
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);  
            } else {  
                ftpClient.setFileType(FTPClient.ASCII_FILE_TYPE);  
            }  
            ftpClient.setSoTimeout(CLIENT_TIMEOUT);  
        } catch (SocketException e) {  
        	log.error("FTPClient Set timeout error.",e);
        } catch (IOException e) {
        	log.error("FTPClient Set FileType error.",e);
		}  
        return ftpClient;  
         
    } 
    
    private static boolean connect(FTPClient ftpClient){  
        try {  
            ftpClient.connect(host, port);  
            // 连接后检测返回码来校验连接是否成功  
            int reply = ftpClient.getReplyCode();  
  
            if (FTPReply.isPositiveCompletion(reply)) {  
                //登陆到ftp服务器  
                if (ftpClient.login(username, password)) {  
                    return true;  
                }  
            } else {  
                ftpClient.disconnect();  
            }  
        } catch (IOException e) {  
            if (ftpClient.isConnected()) {  
                try {  
                    ftpClient.disconnect(); //断开连接  
                } catch (IOException e1) {  
                	log.error("FTPClient could not disconnect from server.",e);
                }  
            }  
        	log.error("FTPClient could not connect to server",e);
        }  
        return false;  
    } 
    public static boolean uploadFile(InputStream inStream, String remoteFileName,
			String remoteDir) {
    	FTPClient client = getFTPClient();
		boolean success = false;

		try {
			if (changeOrCreateDirecroty(client,remoteDir)) {
				success = client.storeFile(remoteFileName, inStream);
				return success;
			}
		} catch (FileNotFoundException e) {
        	log.error("FTPClient upload fail.",e);
			return success;
		} catch (IOException e1) {
        	log.error("FTPClient upload fail.",e1);
			return success;
		} finally {
			//回到根目录
			try {
				client.changeWorkingDirectory("/");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
    
    
    public static boolean changeOrCreateDirecroty(FTPClient ftpClient , String remote) {
		try {
			boolean flag = true;
			String[] dirs = remote.split("/");
			for(String dir : dirs){
				if(StringUtils.isBlank(dir)){
					continue;
				}
				boolean isChangeSuccess = ftpClient.changeWorkingDirectory(dir);
				if(!isChangeSuccess){
					boolean isMakeSuccess = ftpClient.makeDirectory(dir);
					if(isMakeSuccess){
						ftpClient.changeWorkingDirectory(dir);
					}else{						
						return false;
					}
				}
			}
			return flag;
		} catch (Exception e) {
			return false;
		}
	}
    
    public static void main(String[] args) {
		/*FTPClient ftpClient = getFTPClient();
		try {
			boolean b1 = ftpClient.changeWorkingDirectory("hello/helloworld");
			b1 = changeOrCreateDirecroty(ftpClient, "hello/helloworld");
			System.out.println(b1);
			boolean b2 = ftpClient.changeWorkingDirectory("/ywdt");
			System.out.println(b2);
			FTPFile[] listFiles = ftpClient.listFiles();
			for (FTPFile ftpFile : listFiles) {
				String name = ftpFile.getName();
				System.out.println(name);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}
}
