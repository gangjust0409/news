package com.just.news.entiry;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/*
 * 新闻 实体类
 * @author  
 */
public class News implements Serializable {

	public News() {
	}

	public News(Integer nid, Integer ntid, String ntitle, String nauthor, Date ncreatedate, String npicpath, String ncontent, Date nmodifydate, String nsummary) {
		this.nid = nid;
		this.ntid = ntid;
		this.ntitle = ntitle;
		this.nauthor = nauthor;
		this.ncreatedate = ncreatedate;
		this.npicpath = npicpath;
		this.ncontent = ncontent;
		this.nmodifydate = nmodifydate;
		this.nsummary = nsummary;
	}

	private static final long serialVersionUID = 8456547789761324251L;
    private Integer nid;
	private Integer ntid;
	//private String ntname;
	private String ntitle;
	private String nauthor;
	private Date ncreatedate;
	private String npicpath;
	private String ncontent;
	private Date nmodifydate;
	private String nsummary;
	
	/*public String getNtname() {
		return ntname;
	}

	public void setNtname(String ntname) {
		this.ntname = ntname;
	}	*/

	public Integer getNid() {
		return nid;
	}

	public void setNid(Integer nid) {
		this.nid = nid;
	}

	public Integer getNtid() {
		return ntid;
	}

	public void setNtid(Integer ntid) {
		this.ntid = ntid;
	}

	public String getNtitle() {
		return ntitle;
	}

	public void setNtitle(String ntitle) {
		this.ntitle = ntitle;
	}

	public String getNauthor() {
		return nauthor;
	}

	public void setNauthor(String nauthor) {
		this.nauthor = nauthor;
	}

	public Date getNcreatedate() {
		return ncreatedate;
	}

	public void setNcreatedate(Date ncreatedate) {
		this.ncreatedate = ncreatedate;
	}

	public String getNpicpath() {
		return npicpath;
	}

	public void setNpicpath(String npicpath) {
		this.npicpath = npicpath;
	}

	public String getNcontent() {
		return ncontent;
	}

	public void setNcontent(String ncontent) {
		this.ncontent = ncontent;
	}

	public Date getNmodifydate() {
		return nmodifydate;
	}

	public void setNmodifydate(Date nmodifydate) {
		this.nmodifydate = nmodifydate;
	}

	public String getNsummary() {
		return nsummary;
	}

	public void setNsummary(String nsummary) {
		this.nsummary = nsummary;
	}

	/*public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}*/

	@Override
	public String toString() {
		return "News{" +
				"nid=" + nid +
				", ntid=" + ntid +
				", ntitle='" + ntitle + '\'' +
				", nauthor='" + nauthor + '\'' +
				", ncreatedate=" + ncreatedate +
				", npicpath='" + npicpath + '\'' +
				", ncontent='" + ncontent + '\'' +
				", nmodifydate=" + nmodifydate +
				", nsummary='" + nsummary + '\'' +
				'}';
	}
}
