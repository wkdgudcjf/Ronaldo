package com.ronaldo.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.ronaldo.config.ErrorCodeConfig.AppListEnum;
import com.ronaldo.config.ErrorCodeConfig.EventAwardEnum;
import com.ronaldo.config.ErrorCodeConfig.EventEnum;
import com.ronaldo.config.ErrorCodeConfig.ExchangeEnum;
import com.ronaldo.config.ErrorCodeConfig.ExhaustEnum;
import com.ronaldo.config.ErrorCodeConfig.LoginEnum;
import com.ronaldo.config.ErrorCodeConfig.PayloadEnum;
import com.ronaldo.config.ErrorCodeConfig.PurchaseEnum;

import com.ronaldo.domain.AppEventDTO;
import com.ronaldo.domain.AppDTO;
import com.ronaldo.domain.BillingDTO;
import com.ronaldo.domain.CompanyDTO;
import com.ronaldo.domain.UserInAppDTO;
import com.ronaldo.domain.UserDTO;
import com.ronaldo.domain.ExchangeDTO;
import com.ronaldo.domain.UserEventDTO;

import com.ronaldo.mapper.BillingMapper;
import com.ronaldo.mapper.AppEventMapper;
import com.ronaldo.mapper.AppMapper;
import com.ronaldo.mapper.CompanyMapper;
import com.ronaldo.mapper.ExchangeMapper;
import com.ronaldo.mapper.UserEventMapper;
import com.ronaldo.mapper.UserInAppMapper;
import com.ronaldo.mapper.UserMapper;

import com.ronaldo.vo.ReceiveAppEventVO;
import com.ronaldo.vo.ReceiveAppListVO;
import com.ronaldo.vo.ReceiveAppVO;
import com.ronaldo.vo.ReceiveCompanyVO;
import com.ronaldo.vo.ReceiveEventAwardVO;
import com.ronaldo.vo.ReceiveEventVO;
import com.ronaldo.vo.ReceiveExchangeAPIVO;
import com.ronaldo.vo.ReceiveExchangeVO;
import com.ronaldo.vo.ReceiveExhaustVO;
import com.ronaldo.vo.ReceivePayloadVO;
import com.ronaldo.vo.ReceivePurchaseVO;
import com.ronaldo.vo.ReceiveUserVO;

import com.ronaldo.vo.ReturnAppEventVO;
import com.ronaldo.vo.ReturnAppListVO;
import com.ronaldo.vo.ReturnAppVO;
import com.ronaldo.vo.ReturnEventAwardVO;
import com.ronaldo.vo.ReturnEventVO;
import com.ronaldo.vo.ReturnExchangeListVO;
import com.ronaldo.vo.ReturnExchangeVO;
import com.ronaldo.vo.ReturnExhaustVO;
import com.ronaldo.vo.ReturnPayloadVO;
import com.ronaldo.vo.ReturnPurchaseVO;
import com.ronaldo.vo.ReturnUserVO;

@Service
public class ApiServiceImpl implements ApiService
{ 
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private CompanyMapper companyMapper;
	
	@Autowired
	private AppMapper appMapper;
	
	@Autowired
	private ExchangeMapper exchangeMapper;
	
	@Autowired
	private BillingMapper billingMapper;
	
	@Autowired
	private AppEventMapper appEventMapper;
	
	@Autowired
	private UserInAppMapper userInAppMapper;

	@Autowired
	private UserEventMapper userEventMapper;

	@Autowired
	private DataSourceTransactionManager dataSourceTransactionManager;
	
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	private static final Logger LOG = LoggerFactory.getLogger(ApiServiceImpl.class);
	 
	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	@Override
	public boolean registApp(ReceiveAppVO receiveAppVO, String appImageIconPath,String appImageBannerPath) {
		DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
		defaultTransactionDefinition.setName("registapp");
		defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(defaultTransactionDefinition);
		AppDTO appDTO = new AppDTO();
		appDTO.setAppName(receiveAppVO.getAppName());
		appDTO.setCompanyID(receiveAppVO.getCompanyID());
		appDTO.setAppImageIconPath(appImageIconPath);
		appDTO.setAppImageBannerPath(appImageBannerPath);
		appDTO.setAppURL(receiveAppVO.getAppURL());
		appDTO.setAppPackage(receiveAppVO.getAppPackage());
		try
		{
			appMapper.registApp(appDTO);
			appDTO.setAppKey(passwordEncoder.encode(String.valueOf(appDTO.getAppID())));
			appMapper.modifyAppKey(appDTO);
			dataSourceTransactionManager.commit(transactionStatus);
			return true;
		}
		catch(Exception e)
		{
			dataSourceTransactionManager.rollback(transactionStatus);
			LOG.info(e.getMessage());
			return false;
		}
	}
	@Override
	public boolean modifyApp(ReceiveAppVO receiveAppVO,String appImageIconPath,String appImageBannerPath)
	{
		AppDTO appDTO = new AppDTO();
		appDTO.setAppID(receiveAppVO.getAppID());
		appDTO.setAppName(receiveAppVO.getAppName());
		appDTO.setCompanyID(receiveAppVO.getCompanyID());
		appDTO.setAppImageIconPath(appImageIconPath);
		appDTO.setAppImageBannerPath(appImageBannerPath);
		appDTO.setAppURL(receiveAppVO.getAppURL());
		appDTO.setAppPackage(receiveAppVO.getAppPackage());
		appDTO.setAppEnable(receiveAppVO.isAppEnable());
		try
		{
			appMapper.updateApp(appDTO);
			return true;
		}
		catch(Exception e)
		{
			LOG.info(e.getMessage());
			return false;
		}
	}
	@Override
	public AppDTO getApp(int appID) {
		return appMapper.getAppByID(appID);
	}
	@Override
	public AppDTO getApp(String appKey) {
		return appMapper.getAppByKey(appKey);
	}
	
	@Override
	public List<AppDTO> getAppList() {
		return appMapper.getAppList();
	}
	@Override
	public List<AppDTO> getEnableAppList(boolean appEnable) {
		return appMapper.getEnableAppList(appEnable);
	}
	@Override
	public boolean registCompany(ReceiveCompanyVO receiveCompanyVO) {
		CompanyDTO companyDTO = new CompanyDTO();
		companyDTO.setCompanyName(receiveCompanyVO.getCompanyName());
		try
		{
			companyMapper.registCompany(companyDTO);
			return true;
		}
		catch(Exception e)
		{
			LOG.info(e.getMessage());
			return false;
		}
	}
	@Override
	public boolean modifyCompany(ReceiveCompanyVO receiveCompanyVO)
	{
		CompanyDTO companyDTO = new CompanyDTO();
		companyDTO.setCompanyID(receiveCompanyVO.getCompanyID());
		companyDTO.setCompanyName(receiveCompanyVO.getCompanyName());
		companyDTO.setCompanyEnable(receiveCompanyVO.isCompanyEnable());
		try
		{
			companyMapper.updateCompany(companyDTO);
			return true;
		}
		catch(Exception e)
		{
			LOG.info(e.getMessage());
			return false;
		}
	}
	@Override
	public CompanyDTO getCompany(int companyID) {
		return companyMapper.getCompany(companyID);
	}
	@Override
	public List<CompanyDTO> getCompanyList() {
		return companyMapper.getCompanyList();
	}

	@Override
	public boolean registUser(String userKey) {
		UserDTO userDTO = new UserDTO();
		userDTO.setUserKey(userKey);
		userDTO.setUserEmail("none");
		userDTO.setUserPassword("none");
		userDTO.setUserMoney(0);
		userDTO.setUserCoin(0);
		try
		{
			userMapper.registUser(userDTO); // 회원가입.
			return true;
		}
		catch(Exception e)
		{
			LOG.info(e.getMessage());
			return false;
		}
	}
	@Override
	public boolean registUserInApp(int userID,int appID) {
		try
		{
			UserInAppDTO userInAppDTO = new UserInAppDTO();
			userInAppDTO.setUserID(userID);
			userInAppDTO.setAppID(appID);
			//여기서 그 유저가 이 앱에 로그인이 되어 있다면?
			userInAppMapper.registUserInApp(userInAppDTO);
			return true;
		}
		catch(Exception e)
		{
			LOG.info(e.getMessage());
			return false;
		}
	}
	@Override
	public UserInAppDTO getUserInApp(int userID,int appID) {
		UserInAppDTO userInAppDTO = new UserInAppDTO();
		userInAppDTO.setUserID(userID);
		userInAppDTO.setAppID(appID);
		return userInAppMapper.getUserInApp(userInAppDTO);
	}
	@Override
	public List<UserInAppDTO> getUserInAppByUserID(int userID) {
		return userInAppMapper.getUserInAppByUserID(userID);
	}
	@Override
	public List<UserDTO> getUserList() {
		return userMapper.getUserList();
	}
	@Override
	public UserDTO getUser(String userKey) {
		return userMapper.getUser(userKey);
	}
	@Override
	public UserDTO getUser(int userID) {
		return userMapper.getUserByUserID(userID);
	}
	@Override
	public List<BillingDTO> getBillingList() {
		return billingMapper.getBillingList();
	}
	@Override
	public boolean addBilling(UserDTO userDTO, int appID, int billingCoin, int billingMoney,String billingType) {
		DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
		defaultTransactionDefinition.setName("addBilling");
		defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(defaultTransactionDefinition);
		BillingDTO billingDTO = new BillingDTO();
		billingDTO.setUserID(userDTO.getUserID());
		billingDTO.setAppID(appID);
		billingDTO.setBillingCoin(billingCoin);
		billingDTO.setBillingMoney(billingMoney);
		billingDTO.setBillingType(billingType);
		try
		{
			billingMapper.registBilling(billingDTO);
			userDTO.setUserCoin(userDTO.getUserCoin() + billingCoin);
			userDTO.setUserMoney(userDTO.getUserMoney() + billingMoney);
			userDTO.setUserPayload("");
			userMapper.updateUser(userDTO);
			dataSourceTransactionManager.commit(transactionStatus);
			return true;
		}
		catch(Exception e)
		{
			LOG.info(e.getMessage());
			dataSourceTransactionManager.rollback(transactionStatus);
			return false;
		}
	}
	@Override
	public boolean minusBilling(UserDTO userDTO, int appID, int billingCoin,String billingType) {
		DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
		defaultTransactionDefinition.setName("minusBilling");
		defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(defaultTransactionDefinition);
		BillingDTO billingDTO = new BillingDTO();
		billingDTO.setUserID(userDTO.getUserID());
		billingDTO.setAppID(appID);
		billingDTO.setBillingCoin(billingCoin);
		billingDTO.setBillingType(billingType);
		try
		{
			billingMapper.registBilling(billingDTO);
			userDTO.setUserCoin(userDTO.getUserCoin() - billingCoin);
			userMapper.updateUser(userDTO);
			dataSourceTransactionManager.commit(transactionStatus);
			return true;
		}
		catch(Exception e)
		{
			LOG.info(e.getMessage());
			dataSourceTransactionManager.rollback(transactionStatus);
			return false;
		}
	}
	@Override
	public List<BillingDTO> getUserBillingList(String userKey) {
		return billingMapper.getUserBillingList(userMapper.getUser(userKey).getUserID());
	}
	@Override
	public List<BillingDTO> getAppBillingList(String appKey) {
		return billingMapper.getAppBillingList(appMapper.getAppByKey(appKey).getAppID());
	}
	@Override
	public List<AppEventDTO> getAppEventList(int appID) {
		return appEventMapper.getAppEventByAppID(appID);
	}
	@Override
	public boolean registAppEvent(ReceiveAppEventVO receiveAppEventVO) {
		AppEventDTO appEventDTO = new AppEventDTO();
		appEventDTO.setAppID(receiveAppEventVO.getAppID());
		appEventDTO.setAppEventContent(receiveAppEventVO.getAppEventContent());
		appEventDTO.setAppEventCoin(receiveAppEventVO.getAppEventCoin());
		appEventDTO.setAppEventStartTime(Timestamp.valueOf(receiveAppEventVO.getAppEventStartTime().replace("T"," ")));
		appEventDTO.setAppEventEndTime(Timestamp.valueOf(receiveAppEventVO.getAppEventEndTime().replace("T"," ")));
		appEventDTO.setAppEventKey(receiveAppEventVO.getAppEventKey());
		appEventDTO.setAppEventLimit(receiveAppEventVO.getAppEventLimit());
		try
		{
			appEventMapper.registAppEvent(appEventDTO);
			return true;
		}
		catch(Exception e)
		{
			LOG.info(e.getMessage());
			return false;
		}
	}
	@Override
	public AppEventDTO getAppEvent(int appEventID) {
		return appEventMapper.getAppEventByEventID(appEventID);
	}
	@Override
	public AppEventDTO getAppEvent(int appID,String appEventKey) {
		AppEventDTO appEventDTO = new AppEventDTO();
		appEventDTO.setAppID(appID);
		appEventDTO.setAppEventKey(appEventKey);
		return appEventMapper.getAppEventByAppIDByEventKey(appEventDTO);
	}
	@Override
	public boolean modifyAppEvent(ReceiveAppEventVO receiveAppEventVO) {
		AppEventDTO appEventDTO = new AppEventDTO();
		appEventDTO.setAppEventID(receiveAppEventVO.getAppEventID());
		appEventDTO.setAppEventContent(receiveAppEventVO.getAppEventContent());
		appEventDTO.setAppEventCoin(receiveAppEventVO.getAppEventCoin());
		appEventDTO.setAppEventEnable(receiveAppEventVO.isAppEventEnable());
		appEventDTO.setAppEventStartTime(Timestamp.valueOf(receiveAppEventVO.getAppEventStartTime().replace("T"," ")));
		appEventDTO.setAppEventEndTime(Timestamp.valueOf(receiveAppEventVO.getAppEventEndTime().replace("T"," ")));
		appEventDTO.setAppEventKey(receiveAppEventVO.getAppEventKey());
		appEventDTO.setAppEventLimit(receiveAppEventVO.getAppEventLimit());
		try
		{
			appEventMapper.updateAppEvent(appEventDTO);
			return true;
		}
		catch(Exception e)
		{
			LOG.info(e.getMessage());
			return false;
		}
	}
	@Override
	public String setUserPayload(String appKey, String userKey) {
		Date date = new Date();
		String encode = appKey+userKey+date.toString();
		String payload = passwordEncoder.encode(encode);
		UserDTO userDTO = new UserDTO();
		userDTO.setUserKey(userKey);
		userDTO.setUserPayload(payload);
		try {
			userMapper.updateUserPayload(userDTO);
		} catch (Exception e) {
			payload = "fail";
			LOG.info(e.getMessage());
		}
		return payload;
	}
	@Override
	public String getUserPayload(String userKey) {
		return userMapper.getUser(userKey).getUserPayload();
	}
	@Override
	public List<ExchangeDTO> getExchangeList() {
		return exchangeMapper.getExchangeList();
	}
	@Override
	public List<ExchangeDTO> getEnableExchangeList(boolean exchangeEnable) {
		return exchangeMapper.getEnableExchangeList(exchangeEnable);
	}
	@Override
	public boolean modifyExchange(ReceiveExchangeVO receiveExchangeVO, String exchangeImagePath) {
		ExchangeDTO exchangeDTO = new ExchangeDTO();
		exchangeDTO.setExchangeID(receiveExchangeVO.getExchangeID());
		exchangeDTO.setExchangeMoney(receiveExchangeVO.getExchangeMoney());
		exchangeDTO.setExchangeCoin(receiveExchangeVO.getExchangeCoin());
		exchangeDTO.setExchangeEnable(receiveExchangeVO.isExchangeEnable());
		exchangeDTO.setExchangeName(receiveExchangeVO.getExchangeName());
		exchangeDTO.setExchangeImagePath(exchangeImagePath);
		exchangeDTO.setExchangeKey(receiveExchangeVO.getExchangeKey());
		try
		{
			exchangeMapper.updateExchange(exchangeDTO);
			return true;
		}
		catch(Exception e)
		{
			LOG.info(e.getMessage());
			return false;
		}
	}
	@Override
	public ExchangeDTO getExchange(int exchangeID) {
		return exchangeMapper.getExchange(exchangeID);
	}
	@Override
	public boolean registExchange(ReceiveExchangeVO receiveExchangeVO, String exchangeImagePath) {
		ExchangeDTO exchangeDTO = new ExchangeDTO();
		exchangeDTO.setExchangeMoney(receiveExchangeVO.getExchangeMoney());
		exchangeDTO.setExchangeCoin(receiveExchangeVO.getExchangeCoin());
		exchangeDTO.setExchangeName(receiveExchangeVO.getExchangeName());
		exchangeDTO.setExchangeImagePath(exchangeImagePath);
		exchangeDTO.setExchangeKey(receiveExchangeVO.getExchangeKey());
		try
		{
			exchangeMapper.registExchange(exchangeDTO);
			return true;
		}
		catch(Exception e)
		{
			LOG.info(e.getMessage());
			return false;
		}
	}

	@Override
	public UserEventDTO getUserEvent(int userID, int appEventID) {
		UserEventDTO userEventDTO = new UserEventDTO();
		userEventDTO.setUserID(userID);
		userEventDTO.setAppEventID(appEventID);
		return userEventMapper.getUserEvent(userEventDTO);
	}
	@Override
	public boolean registUserEvent(int userID, int appEventID) {
		UserEventDTO userEventDTO = new UserEventDTO();
		userEventDTO.setUserID(userID);
		userEventDTO.setAppEventID(appEventID);
		try
		{
			userEventMapper.registUserEvent(userEventDTO);
			return true;
		}
		catch(Exception e)
		{
			LOG.info(e.getMessage());
			return false;
		}
	}
	@Override
	public boolean modifyUserEvent(int userEventID,int userID, int appEventID) {
		UserEventDTO userEventDTO = new UserEventDTO();
		userEventDTO.setUserEventID(userEventID);
		userEventDTO.setUserID(userID);
		userEventDTO.setAppEventID(appEventID);
		userEventDTO.setUserEventEnable(true);
		try
		{
			userEventMapper.updateUserEvent(userEventDTO);
			return true;
		}
		catch(Exception e)
		{
			LOG.info(e.getMessage());
			return false;
		}
	}
	@Override
	public List<UserEventDTO> getUserEventList(int userID) {
		return userEventMapper.getUserEventList(userID);
	}
	@Override
	public void login(ReceiveUserVO receiveUserVO, ReturnUserVO returnUserVO) 
	{
		DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
		defaultTransactionDefinition.setName("login");
		defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(defaultTransactionDefinition);
		String appKey = receiveUserVO.getAppKey();
		String userKey = receiveUserVO.getUserKey();
		AppDTO appDTO = getApp(appKey);
		if (appDTO == null)
		{
			returnUserVO.setState(LoginEnum.NOT_EXIST_APPKEY);
			LOG.info("login - appKey not exist - AppKey : " + appKey+" / UserKey : "+userKey);
			return;
		}
		
		UserDTO userDTO = getUser(userKey);
		if (userDTO == null) // 모든 앱에서 처음 가입이면.
		{
			if (!registUser(userKey)) // 일단 유저 등록시키고.
			{
				returnUserVO.setState(LoginEnum.USER_KEY_INVALID);
				LOG.info("login(registUser) - AppKey : " + appKey+" / UserKey : "+userKey);
				return;
			}
			userDTO = getUser(userKey);
		} 
		if (getUserInApp(userDTO.getUserID() , appDTO.getAppID()) == null) // 앱 처음이면 앱 등록시킨다.
		{
			if(!registUserInApp(userDTO.getUserID() , appDTO.getAppID()))
			{
				returnUserVO.setState(LoginEnum.USER_ALREADY_JOIN_APP);
				LOG.info("login(registUserInApp) - AppKey : " + appKey+" / UserKey : "+userKey);
				dataSourceTransactionManager.rollback(transactionStatus);
				return;
			}
		}
		dataSourceTransactionManager.commit(transactionStatus);
		returnUserVO.setUserCoin(userDTO.getUserCoin());
		returnUserVO.setUserEmail(userDTO.getUserEmail());
		returnUserVO.setUserMoney(userDTO.getUserMoney());
		returnUserVO.setState(LoginEnum.SUCCESS);
		return;
	}
	@Override
	public void appList(ReceiveAppListVO receiveAppListVO, ReturnAppListVO returnAppListVO)
	{
		String appKey = receiveAppListVO.getAppKey();
		String userKey = receiveAppListVO.getUserKey();
		AppDTO appDTO = getApp(appKey);
		if (appDTO == null)
		{
			returnAppListVO.setState(AppListEnum.NOT_EXIST_APPKEY);
			LOG.info("appList - appKey not exist - AppKey : " + appKey+" / UserKey : "+userKey);
			return;
		}
		List<ReturnAppVO> returnAppVoList = new ArrayList<ReturnAppVO>();
		List<AppDTO> appDTOList = getEnableAppList(true);
		UserDTO userDTO = getUser(userKey);
		List<UserInAppDTO> userInAppList = getUserInAppByUserID(userDTO.getUserID());
		List<UserEventDTO> userEventList = getUserEventList(userDTO.getUserID());
		
		for (int i = 0; i < appDTOList.size(); i++) 
		{
			ReturnAppVO returnAppVO = new ReturnAppVO();
			returnAppVO.setAppKey(appDTOList.get(i).getAppKey());
			returnAppVO.setAppImageIconPath(appDTOList.get(i).getAppImageIconPath());
			returnAppVO.setAppImageBannerPath(appDTOList.get(i).getAppImageBannerPath());
			returnAppVO.setAppName(appDTOList.get(i).getAppName());
			returnAppVO.setAppPackage(appDTOList.get(i).getAppPackage());
			returnAppVO.setAppURL(appDTOList.get(i).getAppURL());
			returnAppVO.setAppInstall(false);
			// event 확인하기 (userKey로)
			List<AppEventDTO> appEventList = getAppEventList(appDTOList.get(i).getAppID());
			
			List<ReturnAppEventVO> returnEventList = new ArrayList<ReturnAppEventVO>();
			for(int j=0;j<appEventList.size();j++)
			{
				ReturnAppEventVO appEventDAO = new ReturnAppEventVO();
				appEventDAO.setAppEventKey(appEventList.get(j).getAppEventKey());
				appEventDAO.setAppEventContent(appEventList.get(j).getAppEventContent());
				appEventDAO.setAppEventCoin(appEventList.get(j).getAppEventCoin());
				appEventDAO.setAppEventRewardEnable(false);
				appEventDAO.setAppEventSuccessEnable(false);
				for(int k=0;k<userEventList.size();k++)
				{
					if(userEventList.get(k).getAppEventID()==appEventList.get(j).getAppEventID())
					{
						appEventDAO.setAppEventSuccessEnable(true);
						if(userEventList.get(k).isUserEventEnable())
						{
							appEventDAO.setAppEventRewardEnable(true);
						}
						break;
					}
				}
				returnEventList.add(appEventDAO);
			}
			returnAppVO.setAppEventList(returnEventList); // event 여부.
			for(int j=0;j<userInAppList.size();j++)
			{
				if(userInAppList.get(j).getAppID()==appDTOList.get(i).getAppID())
				{
					returnAppVO.setAppInstall(true); // 첫고객 판단
					// 설치 여부는 패키지로 판단.
				}
			}
			returnAppVoList.add(returnAppVO);
		}
		returnAppListVO.setState(AppListEnum.SUCCESS);
		returnAppListVO.setAppList(returnAppVoList);
		return;
	}
	@Override
	public void payload(ReceivePayloadVO receivePayloadVO, ReturnPayloadVO returnPayloadVO)
	{
		String appKey = receivePayloadVO.getAppKey();
		String userKey = receivePayloadVO.getUserKey();
		AppDTO appDTO = getApp(appKey);
		if (appDTO == null)
		{
			returnPayloadVO.setState(PayloadEnum.NOT_EXIST_APPKEY);
			LOG.info("payload - appKey not exist - AppKey : " + appKey+" / UserKey : "+userKey);
			return;
		}
		String payload = setUserPayload(appKey, userKey);
		if(payload.equals("fail"))
		{
			returnPayloadVO.setState(PayloadEnum.USER_KEY_INVALID);
			LOG.info("payload(setUserPayload) - AppKey : " + appKey+" / UserKey : "+userKey);
		}
		else
		{
			returnPayloadVO.setState(PayloadEnum.SUCCESS);
		}
		returnPayloadVO.setUserPayload(payload);
		return;
	}
	@Override
	public void exchange(ReceiveExchangeAPIVO receiveExchangeAPIVO, ReturnExchangeListVO exchangeListDAO)
	{
		String appKey = receiveExchangeAPIVO.getAppKey();
		String userKey = receiveExchangeAPIVO.getUserKey();
		List<ExchangeDTO> exchangeList = null;
		List<ReturnExchangeVO> returnExchangeVOList = null;
			
		AppDTO appDTO = getApp(appKey);
		if (appDTO == null)
		{
			exchangeListDAO.setState(ExchangeEnum.NOT_EXIST_APPKEY);
			LOG.info("exchange - appKey not exist - AppKey : " + appKey+" / UserKey : "+userKey);
			return;
		}
			
		exchangeList = getEnableExchangeList(true);
		returnExchangeVOList = new ArrayList<ReturnExchangeVO>();
			
		for (int i = 0; i < exchangeList.size(); i++) {
			ReturnExchangeVO returnExchangeVO = new ReturnExchangeVO();
			returnExchangeVO.setExchangeCoin(exchangeList.get(i).getExchangeCoin());
			returnExchangeVO.setExchangeMoney(exchangeList.get(i).getExchangeMoney());
			returnExchangeVO.setExchangeName(exchangeList.get(i).getExchangeName());
			returnExchangeVO.setExchangeImagePath(exchangeList.get(i).getExchangeImagePath());
			returnExchangeVO.setExchangeKey(exchangeList.get(i).getExchangeKey());
			returnExchangeVOList.add(returnExchangeVO);
		}
		
		exchangeListDAO.setExchangeList(returnExchangeVOList);
		exchangeListDAO.setState(ExchangeEnum.SUCCESS);
		return;
	}
	@Override
	public void purchase(ReceivePurchaseVO receivePurchaseVO, ReturnPurchaseVO returnPurchaseVO) 
	{
		String appKey = receivePurchaseVO.getAppKey();
		String userKey = receivePurchaseVO.getUserKey();
		String payload = receivePurchaseVO.getPayload();
		int coin = receivePurchaseVO.getCoin();
		int price = receivePurchaseVO.getPrice();
		
		String userpayload = getUserPayload(userKey);
		
		if (!(userpayload.equals(payload))) {
			returnPurchaseVO.setState(PurchaseEnum.NOT_EQUAL_PAYLOAD);
			LOG.info("purchase(getUserPayload) - AppKey : " + appKey+" / UserKey : "+userKey+" /rec : "+payload+" /saved : "+userpayload);
			return;
		}
		AppDTO appDTO = getApp(appKey);
		if (appDTO == null) {
			returnPurchaseVO.setState(PurchaseEnum.NOT_EXIST_APPKEY);
			LOG.info("purchase - appKey not exist - " + appKey+"/"+userKey);
			return;
		}
	/*	String emailAddress = "gran-server-service@granmonster-185912.iam.gserviceaccount.com";

		JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
		HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		GoogleCredential credential = new GoogleCredential.Builder().setTransport(httpTransport)
				.setJsonFactory(JSON_FACTORY).setServiceAccountId(emailAddress)
				.setServiceAccountPrivateKeyFromP12File(new File(context.getRealPath("p12/granmonster-160fa837dc4c.p12")))
				.setServiceAccountScopes(Collections.singleton("https://www.googleapis.com/auth/androidpublisher"))
				.build();

		String packageName = appVo.getAppPackage();
		
		AndroidPublisher publisher = new AndroidPublisher.Builder(httpTransport, JSON_FACTORY, credential)
				.setApplicationName(packageName).build();
		
		AndroidPublisher.Purchases.Products.Get get = publisher.purchases().products().get(packageName, productId,
				purchaseToken);
		ProductPurchase productPurchase = get.execute();
		System.out.println(productPurchase.toPrettyString());

		// 인앱 상품의 소비 상태. 0 아직 소비 안됨(Yet to be consumed) / 1 소비됨(Consumed)
		Integer consumptionState = productPurchase.getConsumptionState();
		System.out.println(consumptionState);
		// 개발자가 지정한 임의 문자열 정보
		String developerPayload = productPurchase.getDeveloperPayload();
		System.out.println(developerPayload);
		// 구매 상태. 0 구매완료 / 1 취소됨
		Integer purchaseState = productPurchase.getPurchaseState();
		System.out.println(purchaseState);
		// 상품이 구매된 시각. 타임스탬프 형태
		Long purchaseTimeMillis = productPurchase.getPurchaseTimeMillis();
		System.out.println(purchaseTimeMillis);*/
		UserDTO userDTO = getUser(userKey);
		if(!addBilling(userDTO, appDTO.getAppID(), coin, price, "purchase"))
		{
			returnPurchaseVO.setState(PurchaseEnum.INVALID_BILLING);
			LOG.info("purchase(addBilling) - AppKey : " + appKey+" / UserKey : "+userKey);
		}
		returnPurchaseVO.setState(PurchaseEnum.SUCCESS);
		returnPurchaseVO.setCoin(userDTO.getUserCoin()+coin);
		return;
	}
	
	
	@Override
	public void exhaust(ReceiveExhaustVO receiveExhaustVO, ReturnExhaustVO returnExhaustVO) {
		String appKey = receiveExhaustVO.getAppKey();
		String userKey = receiveExhaustVO.getUserKey();
		String payload = receiveExhaustVO.getPayload();
		int coin = receiveExhaustVO.getCoin();
		
		String userpayload = getUserPayload(userKey);
		
		if (!(userpayload.equals(payload))) {
			returnExhaustVO.setState(ExhaustEnum.NOT_EQUAL_PAYLOAD);
			LOG.info("exhaust(getUserPayload) - AppKey : " + appKey+" / UserKey : "+userKey+" /rec : "+payload+" /saved : "+userpayload);
			return;
		}
		AppDTO appDTO = getApp(appKey);
		if (appDTO == null) {
			returnExhaustVO.setState(ExhaustEnum.NOT_EXIST_APPKEY);
			LOG.info("exhaust - appKey not exist - " + appKey+"/"+userKey);
			return;
		}
		
		UserDTO userDTO = getUser(userKey);
		if(userDTO.getUserCoin() < coin) // 코인이 더 적으면 false 리턴
		{
			returnExhaustVO.setState(ExhaustEnum.NOT_ENOUGH_COIN);
			LOG.info("exhaust(getUserCoin) - AppKey : " + appKey+" / UserKey : "+userKey+" /rec : "+coin+" /saved : "+userDTO.getUserCoin());
			return;
		}
		
		if(!minusBilling(userDTO, appDTO.getAppID(), coin, "exhaust"))
		{
			returnExhaustVO.setState(ExhaustEnum.INVALID_BILLING);
			LOG.info("exhaust(minusBilling) - AppKey : " + appKey+" / UserKey : "+userKey);
			return;
		}
		returnExhaustVO.setState(ExhaustEnum.SUCCESS);
		returnExhaustVO.setCoin(userDTO.getUserCoin()-coin);
		return;
	}
	
	@Override
	public void event(ReceiveEventVO receiveEventVO, ReturnEventVO returnEventVO) {
		String appKey = receiveEventVO.getAppKey();
		String userKey = receiveEventVO.getUserKey();
		String appEventKey = receiveEventVO.getEventKey();
		
		AppDTO appDTO = getApp(appKey);
		if (appDTO == null) {
			returnEventVO.setState(EventEnum.NOT_EXIST_APPKEY);
			LOG.info("event - appKey not exist - AppKey : " + appKey+" / UserKey : "+userKey);
			return;
		}
		AppEventDTO appEventDTO  = getAppEvent(appDTO.getAppID(),appEventKey);
		if (appEventDTO == null) {
			returnEventVO.setState(EventEnum.NOT_REGIST_EVENT); // 이벤트가 없을때.
			LOG.info("event(getAppEvent) - AppKey : " + appKey+" / UserKey : "+userKey +" /eventKey : "+appEventKey);
			return;
		}
		
		if(!appEventDTO.isAppEventEnable())
		{
			returnEventVO.setState(EventEnum.NOT_ENABLE_EVENT); // 이벤트가 활성화되지 않았을때
			LOG.info("event(isAppEventEnable) - AppKey : " + appKey+" / UserKey : "+userKey +" /eventKey : "+appEventKey);
			return;
		}
		
		int userID = getUser(userKey).getUserID();
		
		UserEventDTO userEventDTO = getUserEvent(userID,appEventDTO.getAppEventID());
		if(userEventDTO!=null)
		{
			returnEventVO.setState(EventEnum.ALREADY_SUCCESS_EVENT); //  이미 이벤트 진행
			LOG.info("event(getUserEvent) AppKey : " + appKey+" / UserKey : "+userKey +" /eventKey : "+appEventKey);
			return;
		}
		if(!registUserEvent(userID,appEventDTO.getAppEventID())) // userevent 등록
		{
			returnEventVO.setState(EventEnum.INVALID_EVENT); //등록실패
			LOG.info("event(registUserEvent) AppKey : " + appKey+" / UserKey : "+userKey +" /eventKey : "+appEventKey);
			return;
		}
		returnEventVO.setState(EventEnum.SUCCESS); //성공
		return;
	}
	
	@Override
	public void eventAward(ReceiveEventAwardVO receiveEventAwardVO, ReturnEventAwardVO returnEventAwardVO) {
		String appKey = receiveEventAwardVO.getAppKey();
		String userKey = receiveEventAwardVO.getUserKey();
		String appEventKey = receiveEventAwardVO.getEventKey();
		
		AppDTO appDTO = getApp(appKey);
		if (appDTO == null) {
			returnEventAwardVO.setState(EventAwardEnum.NOT_EXIST_APPKEY);
			LOG.info("eventAward - appKey not exist - AppKey : " + appKey+" / UserKey : "+userKey);
			return;
		}
			
		AppEventDTO appEventDTO  = getAppEvent(appDTO.getAppID(),appEventKey);
		if (appEventDTO == null) {
			returnEventAwardVO.setState(EventAwardEnum.NOT_EXIST_EVENT); // 이벤트가 없을때.
			LOG.info("eventAward(getAppEvent) - AppKey : " + appKey+" / UserKey : "+userKey +" /eventKey : "+appEventKey);
			return;
		}
			
		if(!appEventDTO.isAppEventEnable())
		{
			returnEventAwardVO.setState(EventAwardEnum.NOT_ENABLE_EVENT); // 이벤트가 활성화되지 않았을때
			LOG.info("eventAward(isAppEventEnable) - AppKey : " + appKey+" / UserKey : "+userKey +" /eventKey : "+appEventKey);
			return;
		}
		
		UserDTO userDTO = getUser(userKey);

		UserEventDTO userEventDTO = getUserEvent(userDTO.getUserID(),appEventDTO.getAppEventID());
		if(userEventDTO==null)
		{
			returnEventAwardVO.setState(EventAwardEnum.NOT_ACHIEVE_EVENT); // 이벤트가 등록되지 않았을때
			LOG.info("eventAward(getUserEvent) - AppKey : " + appKey+" / UserKey : "+userKey +" /eventKey : "+appEventKey);
			return;
		}
		else if(userEventDTO.isUserEventEnable())
		{
			returnEventAwardVO.setState(EventAwardEnum.ALREADY_REWARD_EVENT); // 이미 이벤트 보상을 받았을때
			LOG.info("eventAward(getUserEvent) - AppKey : " + appKey+" / UserKey : "+userKey +" /eventKey : "+appEventKey);
			return;
		}
		DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
		defaultTransactionDefinition.setName("eventAward");
		defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(defaultTransactionDefinition);
		if(!modifyUserEvent(userEventDTO.getUserEventID(),userDTO.getUserID(),appEventDTO.getAppEventID()))
		{
			returnEventAwardVO.setState(EventAwardEnum.INVALID_USER); // 이미 이벤트 보상을 받았을때
			LOG.info("eventAward(modifyUserEvent) - AppKey : " + appKey+" / UserKey : "+userKey +" /eventKey : "+appEventKey);
			return;
		}
		if(!addBilling(userDTO, appDTO.getAppID(), appEventDTO.getAppEventCoin(), 0, appEventDTO.getAppEventContent()))
		{
			dataSourceTransactionManager.rollback(transactionStatus);
			returnEventAwardVO.setState(EventAwardEnum.INVALID_BILLING); // 이미 이벤트 보상을 받았을때
			LOG.info("eventAward(addBilling) - AppKey : " + appKey+" / UserKey : "+userKey +" /eventKey : "+appEventKey);
			return;
		}
		dataSourceTransactionManager.commit(transactionStatus);
		
		returnEventAwardVO.setCoin(userDTO.getUserCoin() + appEventDTO.getAppEventCoin());
		returnEventAwardVO.setState(EventAwardEnum.SUCCESS); // ok
		return;
	}
}
