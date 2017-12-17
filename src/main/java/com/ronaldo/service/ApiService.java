package com.ronaldo.service;

import java.util.List;

import com.ronaldo.domain.AppEventDTO;
import com.ronaldo.domain.AppDTO;
import com.ronaldo.domain.BillingDTO;
import com.ronaldo.domain.CompanyDTO;
import com.ronaldo.domain.ExchangeDTO;
import com.ronaldo.domain.UserEventDTO;
import com.ronaldo.domain.UserInAppDTO;
import com.ronaldo.vo.ReceiveAppEventVO;
import com.ronaldo.vo.ReceiveAppListVO;
import com.ronaldo.vo.ReceiveAppVO;
import com.ronaldo.vo.ReceiveCompanyVO;
import com.ronaldo.vo.ReceiveEventRewardVO;
import com.ronaldo.vo.ReceiveEventVO;
import com.ronaldo.vo.ReceiveExchangeAPIVO;
import com.ronaldo.vo.ReceiveExchangeVO;
import com.ronaldo.vo.ReceiveExhaustVO;
import com.ronaldo.vo.ReceivePayloadVO;
import com.ronaldo.vo.ReceivePurchaseVO;
import com.ronaldo.vo.ReceiveUserVO;
import com.ronaldo.vo.ReturnAppListVO;
import com.ronaldo.vo.ReturnEventRewardVO;
import com.ronaldo.vo.ReturnEventVO;
import com.ronaldo.vo.ReturnExchangeListVO;
import com.ronaldo.vo.ReturnExhaustVO;
import com.ronaldo.vo.ReturnPayloadVO;
import com.ronaldo.vo.ReturnPurchaseVO;
import com.ronaldo.vo.ReturnUserVO;
import com.ronaldo.domain.UserDTO;

public interface ApiService
{
	public boolean registApp(ReceiveAppVO receiveAppVO, String appImageIconPath, String appImageBannerPath);
	public List<AppDTO> getAppList();
	public List<AppDTO> getEnableAppList(boolean appEnable);
	public AppDTO getApp(int appID);
	public boolean modifyApp(ReceiveAppVO receiveAppVO,String appImageIconPath,String appImageBannerPath);
	public AppDTO getApp(String appKey);
	
	public String setUserPayload(String appKey, String userKey);
	public String getUserPayload(String userKey);
	
	public boolean registCompany(ReceiveCompanyVO receiveCompanyVO);
	public List<CompanyDTO> getCompanyList();
	public CompanyDTO getCompany(int companyID);
	public boolean modifyCompany(ReceiveCompanyVO receiveCompanyVOe);
	
	public List<ExchangeDTO> getExchangeList();
	boolean modifyExchange(ReceiveExchangeVO receiveExchangeVO, String exchangeImagePath);
	public ExchangeDTO getExchange(int exchangeID);
	boolean registExchange(ReceiveExchangeVO receiveExchangeVO, String exchangeImagePath);
	public List<ExchangeDTO> getEnableExchangeList(boolean exchangeEnable);
	
	public boolean registUser(String userKey);
	public boolean registUserInApp(int userID,int appID);
	public List<UserDTO> getUserList();
	public UserDTO getUser(String userKey);
	public UserDTO getUser(int userID);
	public List<UserInAppDTO> getUserInAppByUserID(int userID);
	public UserInAppDTO getUserInApp(int userID,int appID);
	
	public List<BillingDTO> getBillingList();
	public List<BillingDTO> getUserBillingList(String userKey);
	public List<BillingDTO> getAppBillingList(String appID);
	public boolean addBilling(UserDTO userDTO, int appID, int billingCoin, int billingMoney, String billingType);
	public boolean minusBilling(UserDTO userDTO, int appID,int billingCoin,String billingType);
	
	public List<AppEventDTO> getAppEventList(int appID);
	public List<AppEventDTO> getAppEventEnableList(int appID);
	public AppEventDTO getAppEvent(int appEventID);
	public AppEventDTO getAppEvent(int appID,String appEventKey);
	public boolean registAppEvent(ReceiveAppEventVO receiveAppEventVO);
	boolean modifyAppEvent(ReceiveAppEventVO receiveAppEventVO);	
	boolean disableAppEvent(int appEventID);
	
	public boolean registUserEvent(int userID, int appEventID);
	public UserEventDTO getUserEvent(int userID, int appEventID);
	public List<UserEventDTO> getUserEventList(int userID);
	public boolean modifyUserEvent(int userEventID,int userID, int appEventID);
	
	public void login(ReceiveUserVO receiveUserVO , ReturnUserVO returnUserVO);
	public void appList(ReceiveAppListVO receiveAppListVO , ReturnAppListVO returnAppListVO);
	public void payload(ReceivePayloadVO receivePayloadVO, ReturnPayloadVO returnPayloadVO);
	public void exchange(ReceiveExchangeAPIVO receiveExchangeAPIVO, ReturnExchangeListVO returnExchangeListVO);
	public void purchase(ReceivePurchaseVO receivePurchaseVO, ReturnPurchaseVO returnPurchaseVO);
	public void exhaust(ReceiveExhaustVO receiveExhaustVO, ReturnExhaustVO returnExhaustVO);
	public void event(ReceiveEventVO receiveEventVO, ReturnEventVO returnEventVO);
	public void eventReward(ReceiveEventRewardVO receiveEventRewardVO, ReturnEventRewardVO returnEventRewardVO);
}
