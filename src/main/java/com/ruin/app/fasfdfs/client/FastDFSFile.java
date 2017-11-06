package com.ruin.app.fasfdfs.client;

/**
 * 
 * @ClassName:FastDFSFile
 * @author zhangjr(作者)
 * @Version 1.0(版本号)
 * @email 807213853@qq.com
 * @Create Date:2017年7月18日
 */
public class FastDFSFile implements FastDFSFileManagerConfig {
	private static final long serialVersionUID = 1L;

	private String name;

	private byte[] content;

	private String ext;

	private String length;

	private String height = FILE_DEFAULT_HEIGHT;

	private String width = FILE_DEFAULT_WIDTH;

	private String author = FILE_DEFAULT_AUTHOR;

	public FastDFSFile(byte[] content, String ext) {
		this.content = content;
		this.ext = ext;
	}

	public FastDFSFile(String name, byte[] content, String ext) {
		super();
		this.name = name;
		this.content = content;
		this.ext = ext;
	}

	public FastDFSFile(byte[] content, String name, String ext, String length, String author) {
		this.content = content;
		this.name = name;
		this.ext = ext;
		this.length = length;
		this.author = author;
	}

	public FastDFSFile(String name, byte[] content, String ext, String length, String height, String width,
			String author) {
		super();
		this.name = name;
		this.content = content;
		this.ext = ext;
		this.length = length;
		this.height = height;
		this.width = width;
		this.author = author;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

}
