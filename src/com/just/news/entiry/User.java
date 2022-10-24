package com.just.news.entiry;

import java.io.Serializable;

/*
 * 用户 实体类
 * @author
 */
public class User implements Serializable {
	
    private static final long serialVersionUID = 435894070589975762L;
    private Integer uid;
	private String uname;
	private String upwd;

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public void setUpwd(String upwd) {
		this.upwd = upwd;
	}

	public Integer getUid() {
		return uid;
	}

	public String getUname() {
		return uname;
	}

	public String getUpwd() {
		return upwd;
	}
}
