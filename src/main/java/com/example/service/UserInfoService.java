package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.mapper.UserInfoMapper;
import com.example.model.UserInfo;
import com.github.pagehelper.PageHelper;

@Service
public class UserInfoService extends BaseService<UserInfo>{
	@Autowired
    private UserInfoMapper userInfoMapper;

    public List<UserInfo> getPageList(Integer page,Integer rows) {
    	if (page != null && rows != null) {
    		PageHelper.startPage(page, rows);
    	}
    	return userInfoMapper.selectAll();
    }
}
