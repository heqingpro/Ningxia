package cn.ipanel.ningxia.utils;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.ipanel.ningxia.config.SysConfig;
import cn.ipanel.ningxia.utils.ImageDownloader.DownloadThread;

public class ImageDownloadUtil {
	protected static final Logger log = LoggerFactory.getLogger(ImageDownloadUtil.class);  
	public static void changeImage(Element contentElement,String url,String topicName) throws Exception {
		//topicName为专题的名称，以[]包起来,提取其中字符
		if(topicName.contains("[")){
			topicName = topicName.substring(topicName.lastIndexOf("[") + 1 , topicName.length()-1);			
		}
		Elements imgElements = contentElement.getElementsByTag("img");
		List<Element> remoreList = new ArrayList<Element>();
		for(Element e : imgElements ){
			String src = e.attr("src");
			//file:///C:/Users/bgt077/AppData/Local/Temp/msohtmlclip1/01/clip_image001.gif
			//对于上面的格式，直接去掉
			if(src != null && src.startsWith("file")){
				remoreList.add(e);
				continue;
			}
			String filename = src.substring(src.lastIndexOf("/")+1, src.length());
			src = URIUtil.getAbsoluteUrl(src, url);
			String savePath ;
			//如果上传到FTP	
			if(SysConfig.IS_UPLOAD_FTP){
				savePath = SysConfig.FTP_UPLOAD_PATH + "/"+topicName;
				String accessPath = SysConfig.FTP_ACCESS_PATH + "/" +savePath+ "/" + filename;
				e.attr("src", accessPath);			
				log.info("上传FTP路径为:--->"+savePath);
				ImageDownloader.downloadImage(new DownloadThread(src, savePath,filename));
			}else{
				savePath = SysConfig.IMAGE_UPLOAD_PATH + "/"+topicName+"/"+filename;
				String accessPath = SysConfig.IMAGE_ACCESS_PATH + "/"+topicName+"/"+filename;
				e.attr("src", accessPath);			
				ImageDownloader.downloadImage(new DownloadThread(src, savePath));
			}
		}
		if(!remoreList.isEmpty()){
			for (Element element : remoreList) {
				element.remove();
			}
		}
	}
	
	/** 下载图片
	 * @param src
	 * @return 下载图片后图片的访问路径
	 * @throws Exception 
	 */
	public static String downloadImage(String url,String src) throws Exception{
		String filename = src.substring(src.lastIndexOf("/")+1, src.length());
		String imagePath = "/"+"download"+"/"+filename;
		if(url != null ){
			src = URIUtil.getAbsoluteUrl(src, url);			
		}
		String accessPath ;
		if(SysConfig.IS_UPLOAD_FTP){
			accessPath = "";
		}else{
			String savePath = SysConfig.IMAGE_UPLOAD_PATH + imagePath;
			ImageDownloader.downloadImage(new DownloadThread(src, savePath));
			accessPath = SysConfig.IMAGE_ACCESS_PATH + imagePath;			
		}
		return accessPath;
	}
}
