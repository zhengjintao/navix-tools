package com.navix.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Downloader implements Runnable {
	private int startInx = 0;
	private int endInx = 0;
	private CloseableHttpClient httpclient = HttpClients.createDefault();
	
	public Downloader(int sinx, int einx){
		this.startInx = sinx;
		this.endInx = einx;
	}
	public void run() {
		downloadFile();
	}
	
	   private void downloadFile(){
		   File f = new File("/Users/kimitei/Desktop/save");
	       List<String> files = Arrays.asList(f.list());
	    	for(int index =startInx; index < endInx; index++){
	    		if(files.contains(index + ".html")){
	    			continue;
	    		}
	    		HttpGet httpGet = new HttpGet("https://www.cosme.com/products/detail.php?product_id=" + String.valueOf(index));
	        	CloseableHttpResponse response1 = null;
	    		try {
	    			response1 = httpclient.execute(httpGet);
	    			System.out.println(index);
	        	    if(response1.getStatusLine().getStatusCode() == 200){
	        	    	System.out.println(index + "  found------");
	        	    	HttpEntity entity1 = response1.getEntity();
	        	    	
	        	    	InputStream is = entity1.getContent();
	        	    	String html = ConvertStreamToString(is);
	        	    	Document doc = Jsoup.parse(html);
	        	    	
	        	    	
	        	    	
	        	    	StringBuilder sb = new StringBuilder();
	        	    	String basePath = "<base href='" + "http://www.cosme.com/" + " '/>";
	        	    	String charset = "<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />";
	        	    	sb.append("<head>");
	        	    	sb.append(charset);
	        	    			sb.append(basePath);
	        	    			sb.append("/<head>");
	        	    			
	        	    			Element ele = doc.select("div.breadcrumb-wrap").first();
	                	    	sb.append(ele.html());
	                	    	
	        	    	ele = doc.select("div.product--details").first();
	        	    	sb.append(ele.html());
	        	    	
	        	    	sb.append("<h1 class='good_detail'>商品の詳細</h1>");
	        	        ele = doc.select("dl.product-desc").first();
	        	    	sb.append(ele.html());

	        	    	sb.append("<h1 class='good_description'>商品の説明</h1>");
	        	    	Elements ele2 = doc.select("div[itemprop=description]");
	        	    	sb.append(ele2.html());
	        	    	
	        	    	StringToFile(sb.toString(), String.valueOf(index));
	        	    	EntityUtils.consume(entity1);
	        	    }
	        	    // do something useful with the response body
	        	    // and ensure it is fully consumed
	        	    
	    		} catch (ClientProtocolException e1) {
	    			System.out.println(e1.getMessage());;
	    		} catch (Exception e1) {
	    			System.out.println(e1.getMessage());;
	    		} finally {
	    			if(response1 != null){
	    				try {
	    					response1.close();
	    				} catch (IOException e) {
	    					// TODO Auto-generated catch block
	    					e.printStackTrace();
	    				}
	    			}
	        	}
	    	}
	    }
	    
	    private String ConvertStreamToString(InputStream is) { 
	    	BufferedReader reader = new BufferedReader(new InputStreamReader(is));      
	    	        StringBuilder sb = new StringBuilder();    
	    	String line = null;      
	    	        try {      
	    	            while ((line = reader.readLine()) != null) {  
	    	                sb.append(line + "\n");      
	    	            }      
	    	        } catch (IOException e) {      
	    	            System.out.println("Error=" + e.toString());      
	    	        } finally {      
	    	            try {      
	    	                is.close();      
	    	            } catch (IOException e) {      
	    	            	System.out.println("Error=" + e.toString());      
	    	            }      
	    	        }      
	    	        return sb.toString();  
	    	} 
	    
	    private void StringToFile(String content, String name){
	    	String fileBase = "/Users/kimitei/Desktop/save/";
	    	String filePath = fileBase + name + ".html";
	    	File file = new File(filePath);
	    	
	    	byte[] buff=new byte[]{};  
	        try   
	        {   if(!file.exists()){
	        	file.createNewFile();
	    		}
	            buff=content.getBytes();  
	            FileOutputStream out=new FileOutputStream(file);  
	            out.write(buff,0,buff.length);
	        }   
	        catch (FileNotFoundException e)   
	        {  
	            e.printStackTrace();  
	        }  
	        catch (IOException e)   
	        {  
	            e.printStackTrace();  
	        }finally{
	        	buff = null;
	        }
	    }
}
