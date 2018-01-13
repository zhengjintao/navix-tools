package com.navix.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	Converttosql();
    }
    
    private static void Converttosql(){
    	File f = new File("/Users/kimitei/Desktop/save");
	      String[] files = f.list();
	      
	      int loop = 1;
	       int splitSize = files.length / loop;
	         
	        Object[] subAry = CovertToSql.splitAry(files, splitSize);
	        
	       for(Object obj: subAry){
	        
	    	   String[] aryItem = (String[]) obj;
	    	   CovertToSql dl = new CovertToSql(aryItem);
	    		Thread tr = new Thread(dl);
	    		tr.start();
	       }
    }
    
    private static void downloadImg(){
    	File f = new File("/Users/kimitei/Desktop/save");
	      String[] files = f.list();
	      
	      int loop = 17;
	       int splitSize = files.length / loop;
	         
	        Object[] subAry = ImageDownloader.splitAry(files, splitSize);
	        
	       for(Object obj: subAry){
	        
	    	   String[] aryItem = (String[]) obj;
	    	   ImageDownloader dl = new ImageDownloader(aryItem);
	    		Thread tr = new Thread(dl);
	    		tr.start();
	       }
    }
    
    private static void htmlDownload(){
    	int count = 10;
    	int sinx = 0;
		int einx = 120000;
    	for(int i = 0; i < 20; i++){
    		sinx = einx;
    		einx = 500 + einx;
    		
    		Downloader dl = new Downloader(sinx, einx);
    		Thread tr = new Thread(dl);
    		tr.start();
    	}
    }
    
 
}


