package com.ronaldo.domain;

import java.sql.Timestamp;
import java.util.List;

public class AppDTO {
    private int appID;
    private String appKey;
	private String appName;
	private int companyID;
	private String appURL;
	private String appImageIconPath;
	private String appImageHBannerPath;
	private String appImageVBannerPath;
	private String appPackage;
	private String companyName;
	private Timestamp appDateTime;
	private boolean appEnable;
	private List<AppEventDTO> appEventList;
	
    public List<AppEventDTO> getAppEventList() {
		return appEventList;
	}
	public void setAppEventList(List<AppEventDTO> appEventList) {
		this.appEventList = appEventList;
	}
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public boolean isAppEnable() {
		return appEnable;
	}
	public void setAppEnable(boolean appEnable) {
		this.appEnable = appEnable;
	}
	public Timestamp getAppDateTime() {
		return appDateTime;
	}
	public void setAppDateTime(Timestamp appDateTime) {
		this.appDateTime = appDateTime;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public int getAppID() {
		return appID;
	}
	public String getAppURL() {
		return appURL;
	}
	public void setAppURL(String appURL) {
		this.appURL = appURL;
	}
	public String getAppImageIconPath() {
		return appImageIconPath;
	}
	public void setAppImageIconPath(String appImageIconPath) {
		this.appImageIconPath = appImageIconPath;
	}
	public String getAppImageHBannerPath() {
		return appImageHBannerPath;
	}
	public void setAppImageHBannerPath(String appImageHBannerPath) {
		this.appImageHBannerPath = appImageHBannerPath;
	}
	public String getAppImageVBannerPath() {
		return appImageVBannerPath;
	}
	public void setAppImageVBannerPath(String appImageVBannerPath) {
		this.appImageVBannerPath = appImageVBannerPath;
	}
	public String getAppPackage() {
		return appPackage;
	}
	public void setAppPackage(String appPackage) {
		this.appPackage = appPackage;
	}
	public void setAppID(int appID) {
		this.appID = appID;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public int getCompanyID() {
		return companyID;
	}
	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}
}