package com.ronaldo.config;

public class ErrorCodeConfig
{
	public enum LoginEnum
	{
		UNKNOWN,
		NOT_EXIST_APPKEY,
		USER_KEY_INVALID,
		USER_ALREADY_JOIN_APP,
		SUCCESS;
	}
	public enum AppListEnum
	{
		UNKNOWN,
		NOT_EXIST_APPKEY,
		NOT_EXIST_USERKEY,
		SUCCESS;
	}
	public enum PayloadEnum
	{
		UNKNOWN,
		NOT_EXIST_APPKEY,
		NOT_EXIST_USERKEY,
		USER_KEY_INVALID,
		SUCCESS;
	}
	public enum ExchangeEnum
	{
		UNKNOWN,
		NOT_EXIST_APPKEY,
		NOT_EXIST_USERKEY,
		SUCCESS;
	}
	public enum PurchaseEnum
	{
		UNKNOWN,
		NOT_EXIST_APPKEY,
		NOT_EXIST_USERKEY,
		NOT_EQUAL_PAYLOAD,
		INVALID_BILLING,
		SUCCESS;
	}
	public enum ExhaustEnum
	{
		UNKNOWN,
		NOT_EXIST_APPKEY,
		NOT_EXIST_USERKEY,
		NOT_EQUAL_PAYLOAD,
		NOT_ENOUGH_COIN,
		INVALID_BILLING,
		SUCCESS;
	}
	public enum EventEnum
	{
		UNKNOWN,
		NOT_EXIST_APPKEY,
		NOT_EXIST_USERKEY,
		NOT_REGIST_EVENT,
		ALREADY_EVENT_END,
		NOT_ENABLE_EVENT,
		ALREADY_SUCCESS_EVENT,
		INVALID_EVENT,
		SUCCESS;
	}
	public enum EventRewardEnum
	{
		UNKNOWN,
		NOT_EXIST_APPKEY,
		NOT_EXIST_USERKEY,
		NOT_EXIST_EVENT,
		ALREADY_EVENT_END,
		ALREADY_EVENT_LIMIT_COUNT,
		NOT_ACHIEVE_EVENT,
		NOT_ENABLE_EVENT,
		ALREADY_REWARD_EVENT,
		INVALID_USER,
		INVALID_BILLING,
		INVALID_EVENT_COUNT,
		SUCCESS;
	}
}
