package com.just.news.entiry;

import java.io.Serializable;

/*
 * 主题 实体类
 * @author
 */
public class Topic implements Serializable {
    private static final long serialVersionUID = 2786883833042050721L;
    private Integer tid;
	private String tname;

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

}
