package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.UserInfo;
import com.example.service.UserInfoService;
import com.example.utils.JsonResult;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/api")
public class UserInfoController {
	@Autowired
	private UserInfoService userInfoService;

	/**
	 * 分页查询users
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public PageInfo<UserInfo> getAll(@RequestParam("page") Integer page,@RequestParam("rows") Integer rows) {
		List<UserInfo> userInfoList = userInfoService.getPageList(page, rows);
		PageInfo<UserInfo> pageInfo = new PageInfo<UserInfo>(userInfoList);
		//List<UserInfo> list = pageInfo.getList();
		//long total = pageInfo.getTotal();	
		// 返回类型可以进行包装.
		return pageInfo;
	}
	
	/**
	 * 分页并排序查询users,
	 * @param page
	 * @param rows
	 * @param sortField
	 * @param sortOrder
	 * @return
	 */
	//http://localhost:9090/api/users2?page=1&rows=2
	@RequestMapping(value = "/users2", method = RequestMethod.GET)
	public ResponseEntity<JsonResult> getPageList(
			@RequestParam("page") Integer page,@RequestParam("rows") Integer rows,
			@RequestParam(value = "sortField", defaultValue = "id") String sortField,
			@RequestParam(value = "sortOrder", defaultValue = "desc") String sortOrder) {
		try {
			PageInfo<UserInfo> pageInfo = userInfoService.queryPageListByWhereAndOrderBy(new UserInfo(), page, rows, sortField, sortOrder);
			if (pageInfo != null) {
				JsonResult result = JsonResult.custom(pageInfo.getTotal(), pageInfo.getList());
				return ResponseEntity.ok(result);
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	
	 /**
     * 获取单个用户详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<JsonResult> user(@PathVariable("id") Integer id) {
    	try {
			UserInfo userInfo = this.userInfoService.queryById(id);
			if (userInfo != null) {
				JsonResult result = JsonResult.custom(userInfo);
				return ResponseEntity.ok(result);
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
	
	
    /**
     * 新增用户
     * @param userInfo
     * @return
     */
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<JsonResult> addUser(@RequestBody UserInfo userInfo) {
    	try {
    		Integer num = this.userInfoService.save(userInfo);
			if (num == 1) {
				JsonResult result = JsonResult.ok();
				return ResponseEntity.ok(result);
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
	
	
    /**
     * 编辑用户
     * @param id
     * @param userInfo
     * @return
     */
	@RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
	public ResponseEntity<JsonResult> updateUser(@PathVariable("id") Integer id, @RequestBody UserInfo userInfo) {
		try {
			userInfo.setId(id);
			Integer num = this.userInfoService.updateSelective(userInfo);
			if (num == 1) {
				JsonResult result = JsonResult.ok();
				return ResponseEntity.ok(result);
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	
	/**
     * 删除用户
     * @param id
     * @return
     */
    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<JsonResult>  deleteUser(@PathVariable("id") Integer id) {
    	try {
			Integer num = this.userInfoService.deleteById(id);
			if (num == 1) {
				JsonResult result = JsonResult.ok();
				return ResponseEntity.ok(result);
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
	
}
