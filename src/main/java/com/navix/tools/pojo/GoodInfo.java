package com.navix.tools.pojo;

import java.util.ArrayList;
import java.util.List;

public class GoodInfo {
	private String cosmeDetailId;
	private String seqid;
	private String goodid;
	private String goodkbn;
	private String goodName;
	private String compotion;
	private String size;
	private String goodImg;
	private String delflg;
	private String price;
	private String content;
	private String brandname;
	private String taxflg;
	private String createtime;
	private String createuser;
	private String updtime;
	private String upduser;
	
	public String getCosmeDetailId() {
		return cosmeDetailId;
	}

	public void setCosmeDetailId(String cosmeDetailId) {
		this.cosmeDetailId = cosmeDetailId;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	public String getTaxFlg() {
		return taxflg;
	}

	public void setTaxFlg(String taxflg) {
		this.taxflg = taxflg;
	}
	
	public String getSeqid() {
		return seqid;
	}

	public void setSeqid(String seqid) {
		this.seqid = seqid;
	}

	public String getGoodid() {
		return goodid;
	}

	public void setGoodid(String goodid) {
		this.goodid = goodid;
	}

	public String getGoodkbn() {
		return goodkbn;
	}

	public void setGoodkbn(String goodkbn) {
		this.goodkbn = goodkbn;
	}

	public String getGoodImg() {
		return goodImg;
	}

	public void setGoodImg(String goodImg) {
		this.goodImg = goodImg;
	}

	public String getDelflg() {
		return delflg;
	}

	public void setDelflg(String delflg) {
		this.delflg = delflg;
	}

	public String getTaxflg() {
		return taxflg;
	}

	public void setTaxflg(String taxflg) {
		this.taxflg = taxflg;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getCreateuser() {
		return createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}

	public String getUpdtime() {
		return updtime;
	}

	public void setUpdtime(String updtime) {
		this.updtime = updtime;
	}

	public String getUpduser() {
		return upduser;
	}

	public void setUpduser(String upduser) {
		this.upduser = upduser;
	}

	public String toString(){
		return "品牌：" + this.brandname +
				" 价格：" + this.price +
				" 含税：" + this.taxflg +
				" 内容：" + this.content;
	}
	
	public String toSqlString(){
		List<String> list = new ArrayList<String>();
		list.add(this.seqid);
		list.add(this.brandname);
		list.add(this.price);
		list.add(this.taxflg);
		list.add(this.content);
		String strSql = "'";
		return strSql + String.join("','", list);
	}
	
}
