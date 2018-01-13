package com.navix.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ImageDownloader implements Runnable{
	private String[] pathlist = null;
	
	public ImageDownloader(String[] plist){
		pathlist = plist;
	}
	public void run() {
		// TODO Auto-generated method stub
		download(pathlist);
	}
	private void download(String[] pathlist){
		CloseableHttpClient httpclient = HttpClients.createDefault();
		List<String> downloaded = new ArrayList<String>();
		File f = new File("/Users/kimitei/Desktop/saveimg");
		 List<String> files = Arrays.asList(f.list());
		for(String fname : pathlist){
			
	    	try {
	    		if(!fname.contains(".html")){
					continue;
				}
		    	File file = new File("/Users/kimitei/Desktop/save/" + fname);
		    	String absname = file.getName();
		    	String fdname = absname.substring(0, absname.indexOf("."));
		    	if(files.contains(fdname)){
		    		continue;
		    	}
				Document doc = Jsoup.parse(file, null);
				Elements eles = doc.select("img");
				String basepath = "http://www.cosme.com/";
				String src = null;
				for(Element ele : eles){
					src = ele.attr("src");
					if(src == null || src.contains("PC-Present.jpg")){
						continue;
					}
					
					String imgnm = Getimagename(src);
					if(imgnm == null){
						continue;
					}
					
					if(downloaded.contains(imgnm)){
						continue;
					}
					downloaded.add(imgnm);
					String urlpath = src.startsWith("http://")? src : basepath + src;
					System.out.println(urlpath);
					
					HttpGet httpGet = new HttpGet(urlpath);
		        	CloseableHttpResponse response1 = null;
		        	
		        	response1 = httpclient.execute(httpGet);
	        	    if(response1.getStatusLine().getStatusCode() == 200){
	        	    	HttpEntity entity1 = response1.getEntity();
	        	    	
	        	    	InputStream is = entity1.getContent();
	        	    	
	        	    	InputStreamToFile(is, fdname, imgnm);
	        	    }
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}		
		public static Object[] splitAry(String[] ary, int subSize) {
	    	  int count = ary.length % subSize == 0 ? ary.length / subSize: ary.length / subSize + 1;

	    	  List<List<String>> subAryList = new ArrayList<List<String>>();

	    	  for (int i = 0; i < count; i++) {
	    	   int index = i * subSize;
	    	   
	    	   List<String> list = new ArrayList<String>();
	    	   int j = 0;
	    	   while (j < subSize && index < ary.length) {
	    	    list.add(ary[index++]);
	    	    j++;
	    	   }

	    	   subAryList.add(list);
	    	  }
	    	  
	    	  Object[] subAry = new Object[subAryList.size()];
	    	  
	    	  for(int i = 0; i < subAryList.size(); i++){
	    	   List<String> subList = subAryList.get(i);
	    	   
	    	   String[] subAryItem = new String[subList.size()];
	    	   for(int j = 0; j < subList.size(); j++){
	    	    subAryItem[j] = subList.get(j);
	    	   }
	    	   
	    	   subAry[i] = subAryItem;
	    	  }
	    	  
	    	  return subAry;
	    	 
	}
	 private static void InputStreamToFile(InputStream is, String folod, String name){
	    	String fileBase = "/Users/kimitei/Desktop/saveimg/" + folod + "/";
	    	String filePath = fileBase + name;
	    	File filebase = new File(fileBase);
	    	File file = new File(filePath);
	    	 
	        try   
	        {  
	        	if(!filebase.exists()){
	        		filebase.mkdir();
	        	}
	        	if(!file.exists()){
	        	file.createNewFile();
	    		}
	            
	            FileOutputStream out=new FileOutputStream(file);  
	            byte[] b = new byte[1024];
	            int len = 0;
	            while((len = is.read(b)) != -1){

	            out.write(b, 0, len);

	            }
	            
	            System.out.println(file);
	            is.close();
	            out.flush();
	            out.close();
	        }   
	        catch (FileNotFoundException e)   
	        {  
	            e.printStackTrace();  
	        }  
	        catch (IOException e)   
	        {  
	            e.printStackTrace();  
	        }
	    }
	    
	    private static String Getimagename(String src){
	    	String[] strs = src.split("/");
			for(String s : strs){
				if(!s.contains(".")){
					continue;
				}
				int ijpg = s.indexOf(".jpg");
				if(ijpg > 0){
					return s.substring(0, ijpg) + ".jpg";
				}
				
				ijpg = s.indexOf(".png");
				if(ijpg > 0){
					return s.substring(0, ijpg) + ".png";
				}
				
				ijpg = s.indexOf(".jepg");
				if(ijpg > 0){
					return s.substring(0, ijpg) + ".jepg";
				}

				ijpg = s.indexOf(".gif");
				if(ijpg > 0){
					return s.substring(0, ijpg) + ".gif";
				}

				ijpg = s.indexOf(".bmp");
				if(ijpg > 0){
					return s.substring(0, ijpg) + ".bmp";
				}
				
			}
			
			return null;
	    }
		
}
