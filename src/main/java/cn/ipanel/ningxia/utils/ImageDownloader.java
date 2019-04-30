package cn.ipanel.ningxia.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;

import org.apache.log4j.Logger;

import cn.ipanel.ningxia.config.SysConfig;

public class ImageDownloader {

	private static ExecutorService threadPool;
	
	public static boolean isInit = false;
	
	private static int IMAGE_WIDTH ;
	
	private static int IMAGE_HEIGHT ;
	
	private static double IMAGE_MAX_LENGTH;
	
	static {
		IMAGE_WIDTH = Integer.parseInt(PropertiesUtil
						.getValue("config.properties", "IMAGE_WIDTH", "1280"));
	
		IMAGE_HEIGHT = Integer.parseInt(PropertiesUtil
				.getValue("config.properties", "IMAGE_HEIGHT", "720"));
		IMAGE_MAX_LENGTH = Double.parseDouble(PropertiesUtil
				.getValue("config.properties", "IMAGE_MAX_LENGTH", "150"));
	}	
	
	public static void init(){
		threadPool = Executors.newFixedThreadPool(8);
		isInit = true;
	}
	
	public static void shutdown(){
		threadPool.shutdown();
		isInit = false;
	}
	
	public static void downloadImage(DownloadThread thread){
		if(!isInit){
			init();
		}
		threadPool.execute(thread);
	}
	
	public static class DownloadThread implements Runnable{

		private Logger log = Logger.getLogger(getClass());
		
		private String url;
		private String savePath;
		private String fileName;
		
		public DownloadThread(String url, String savePath) {
			this.url = url;
			this.savePath = savePath;
		}
		public DownloadThread(String url, String savePath, String fileName) {
			this.url = url;
			this.savePath = savePath;
			this.fileName = fileName;
		}

		@Override
		public void run() {
			URL imageUrl;
			ByteArrayOutputStream bos = null;
			boolean flag = true;
			int count = 0 ;
			try {
				//三次机会,加上直接获取流。总共6次
				while(flag && count < 3){
					try {
						
						imageUrl = new URL(url);
						URLConnection connection = imageUrl.openConnection();
						int length = connection.getContentLength();
						//计算压缩比例
						double quality = IMAGE_MAX_LENGTH * 1024 / length ;
						Builder<? extends InputStream> builder = Thumbnails.of(connection.getInputStream());
						if(quality < 1.0 && quality > 0){
							builder.scale(quality);
							builder.outputQuality(quality);
						}else{
							builder.size(IMAGE_WIDTH, IMAGE_HEIGHT);
						}
						if(SysConfig.IS_UPLOAD_FTP){//上传到FTP
							bos = new ByteArrayOutputStream();
							builder.toOutputStream(bos);
							ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
							FTPUtil.uploadFile(bis, fileName, savePath);
						}else{//不是ftp则存到服务器本地
							File file = new File(savePath);
							if(!file.getParentFile().exists()){
								File parent = file.getParentFile();
								parent.mkdirs();
								//忽略，现在控制
								/*if ("linux".equals(System
										.getProperty("os.name").toLowerCase())) {
									Runtime.getRuntime().exec(
											"chmod 755 " + parent.getAbsolutePath());
								}*/
							}
							builder.toFile(file);
							file.setExecutable(true,false);//设置可执行权限  
							file.setReadable(true,false);//设置可读权限  
							file.setWritable(true,false);//设置可写权限
							/*if ("linux".equals(System
									.getProperty("os.name").toLowerCase())) {
								log.info("修改文件权限：");
								log.info("modify permission");
								Runtime.getRuntime().exec(
										"chmod 777 " + savePath);
							}*/
						}
						flag = false;
					}catch (ArrayIndexOutOfBoundsException e) {//imageio bug ，无法裁剪的图片忽略
						log.error("出现无法处理的图片，url为：" + url , e);
						flag = false;
					}catch (Exception e) {
						log.error("获取图片错误，路径为：" + url + "获取了" + (count+1) + "次", e);
						count++;
					}		
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				close(bos);
			}
		}
	
		public void close(Closeable...closeables){
			try {
				for(Closeable c : closeables){
					if(c != null ){
						c.close();						
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
