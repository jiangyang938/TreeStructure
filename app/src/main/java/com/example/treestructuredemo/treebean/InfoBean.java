package com.example.treestructuredemo.treebean;

/**
 * Created by jiangyang on 2017/10/9.
 */

public class InfoBean {
    private String name;
    private String code;
    private String parentCode;
    private String isFolder;

    public InfoBean(String name,String code,String parentCode,String isFolder){
        this.name=name;
        this.code=code;
        this.parentCode=parentCode;
        this.isFolder=isFolder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getIsFolder() {
        return isFolder;
    }

    public void setIsFolder(String isFolder) {
        this.isFolder = isFolder;
    }
}
