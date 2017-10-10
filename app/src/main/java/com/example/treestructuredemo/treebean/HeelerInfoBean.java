package com.example.treestructuredemo.treebean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunyafei on 16/11/25.
 * 组织架构的第三层bean
 */
public class HeelerInfoBean implements Serializable {

    private String englishName;
    private String userId;
    private String name;
    private String avatar;
    private String deptName;
    private String jobName;
    private String job2;
    private List<HeelerInfoBean> heelerInfo;
    private String levelName;



    public HeelerInfoBean(String name, String avatar, String englishName, String deptName, String jobName, String job2, String levelName) {
        this.name = name;
        this.avatar = avatar;
        this.englishName=englishName;
        this.deptName=deptName;
        this.jobName=jobName;
        this.job2=job2;
        this.levelName=levelName;
    }
    public HeelerInfoBean(String name, String avatar) {
        this.name = name;
        this.avatar = avatar;

    }

    public HeelerInfoBean(String englishName, String userId, String name, String avatar, String deptName, String jobName, List<HeelerInfoBean> heelerInfo) {
        this.englishName = englishName;
        this.userId = userId;
        this.name = name;
        this.avatar = avatar;
        this.deptName = deptName;
        this.jobName = jobName;
        this.heelerInfo = heelerInfo;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public List<HeelerInfoBean> getHeelerInfo() {
        return heelerInfo;
    }

    public void setHeelerInfo(List<HeelerInfoBean> heelerInfo) {
        this.heelerInfo = heelerInfo;
    }

    public String getJob2() {
        return job2;
    }

    public void setJob2(String job2) {
        this.job2 = job2;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }
}
