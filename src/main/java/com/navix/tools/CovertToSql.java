package com.navix.tools;

import java.io.File;
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
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import com.navix.tools.pojo.GoodInfo;

public class CovertToSql implements Runnable {
	private List<GoodInfo> goodInfos = new ArrayList<GoodInfo>();

private String[] pathlist = null;
	
	public CovertToSql(String[] plist){
		pathlist = plist;
	}
	
	public void run() {
		for(String fname : pathlist){
			if(!fname.contains(".html")){
				continue;
			}
			GoodInfo info = new GoodInfo();
	    	try {
		    	File file = new File("/Users/kimitei/Desktop/save/" + fname);
				Document doc = Jsoup.parse(file, null);
				
				// 价格
				Element elePrice = doc.selectFirst(".product__price");
				if(elePrice != null){
					Node node = elePrice.childNode(0);
					info.setPrice(node.toString());
				}
				
				// 含税
				Element eleTaxFlg = doc.selectFirst(".product__price__tax");
				if(eleTaxFlg != null){
					String val = eleTaxFlg.childNode(0).toString();
					String taxFlg = val.contains("税込") ? "1" : "0";
					info.setTaxFlg(taxFlg);
				}
				
				// 含税
				Element eleDetail = doc.selectFirst(".good_detail");
				if(eleDetail != null){
					Element next = eleDetail.nextElementSibling();
					if(next != null){
						Element eleBrandDD = next.nextElementSibling();
						if(eleBrandDD != null){
							Elements elBrands = eleBrandDD.children();
							if(elBrands != null){
								Element elBrand = elBrands.first();
								info.setBrandname(elBrand.childNode(0).toString());
							}
						}
						
					}
				}
				
				
				goodInfos.add(info);
				
				
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

}
