package com.ronaldo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ronaldo.domain.UserVo;

@Mapper
public interface  UserMapper{
	 List<UserVo> getUserList();
	 String joinUser(UserVo userVo);
}