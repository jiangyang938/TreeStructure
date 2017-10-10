package com.example.treestructuredemo.treebean;


import java.io.Serializable;

public class FileBean implements Serializable{
	@TreeNodeId
	private String _id;
	@TreeNodePid
	private String parentId;
	@TreeNodeLabel
	private InfoBean name;
	private long length;
	private String desc;
	private boolean isCheck=false;

	public FileBean(String _id, String parentId, InfoBean name)
	{
		super();
		this._id = _id;
		this.parentId = parentId;
		this.name = name;
	}

	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean check) {
		isCheck = check;
	}

	public InfoBean getName() {
		return name;
	}

	public void setName(InfoBean name) {
		this.name = name;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}
