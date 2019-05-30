package com.wynlink.park_platform.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wynlink.park_platform.entity.ResultData;
import com.wynlink.park_platform.entity.SysUser;
import com.wynlink.park_platform.entity.SysUserGroup;
import com.wynlink.park_platform.service.SysUserGroupService;
import com.wynlink.park_platform.service.SysUserService;

/**
 * <p>
 * 系统用户 前端控制器
 * </p>
 *
 * @author Vincent
 * @since 2019-03-19
 */
@RestController
@RequestMapping("/sysUser")
public class SysUserController {

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserGroupService sysUserGroupService;

	@RequestMapping("/register")
	public ResultData register(@RequestBody SysUser sysUser) {

		String loginAccount = sysUser.getLoginAccount();

		int count = sysUserService.selectCount(new EntityWrapper<SysUser>().eq("login_account", loginAccount));
		if (count > 0) {
			return ResultData.createError("用户名已存在！");
		}
		/**
		 * 先创建用户组，用来管理该车场管理员下的所有用户
		 */
		SysUserGroup group = new SysUserGroup();
		group.setGroupName(loginAccount);
		try {
			sysUserGroupService.insert(group);
		} catch (Exception e1) {
			e1.printStackTrace();
			return ResultData.createError("注册失败，请重新注册！");
		}
		
		Integer groupId = group.getId();//用户组id
		
		
		String password = new Md5Hash(sysUser.getLoginPassword(),sysUser.getLoginAccount(),2).toString();
		sysUser.setLoginPassword(password);
		sysUser.setGroupId(groupId);
		sysUser.setCreateTime(new Date());
		sysUser.setUpdateTime(new Date());
		try {
			sysUserService.insert(sysUser);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createError("注册失败，请重新注册！");
		}
		
		/**
		 * 注册车场管理员成功后，根据登录账户创建一个用户组，
		 */
		
		
		return ResultData.createSuccess("注册成功！");
	}

	@RequestMapping(value="/login")
	public ResultData login(@RequestBody SysUser sysUser,HttpSession httpSession) {
		/*
		 * String loginAccount = sysUser.getLoginAccount(); String loginPassword =
		 * sysUser.getLoginPassword(); SysUser user = sysUserService.selectOne( new
		 * EntityWrapper<SysUser>().eq("login_account",
		 * loginAccount).eq("login_password", loginPassword)); if (user == null) {
		 * return ResultData.createError("用户名或密码错误，请重试！"); }
		 * session.setAttribute("user", user);
		 */

		Map<String,Object> map = new HashMap<String, Object>();
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(sysUser.getLoginAccount(), sysUser.getLoginPassword());
		try {
			subject.login(token);
			
			map.put("token", subject.getSession().getId());
			SysUser user = (SysUser)subject.getPrincipal();
			map.put("userName",user.getUserName());
			
			httpSession.setAttribute("user", user);
			return ResultData.createSuccess(map, "登录成功");
		} catch (IncorrectCredentialsException e) {
			return ResultData.createError("密码错误");
		} catch (LockedAccountException e) {
			return ResultData.createError("登录失败，该用户已被冻结");
		} catch (AuthenticationException e) {
			return ResultData.createError("该用户不存在");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResultData.createSuccess(sysUser, "登录成功！");
	}
	
	/**
     * 未登录，shiro应重定向到登录界面，此处返回未登录状态信息由前端控制跳转页面
     * @return
     */
    @RequestMapping(value = "/unauth")
    public ResultData unauth() {
    	
    	ResultData resultData = ResultData.createError("未登录,请跳转到登录页面！");
    	resultData.setStatus(1001);
        return resultData;
    }
	/*
	 * @RequestMapping(value="/logout") public ResultData logout(HttpSession
	 * httpSession){ //httpSession.invalidate(); //使用权限管理工具进行用户的退出，跳出登录，给出提示信息
	 * SecurityUtils.getSubject().logout();
	 * 
	 * return ResultData.createSuccess("您已安全退出"); }
	 */
    
    
    /**
	 * 给当前车场下添加用户
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping("/add")
	public ResultData add(@RequestBody SysUser sysUser) {
		
		SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
		Integer id = user.getId();//用户id
		Integer groupId = user.getGroupId();//用户组id
		
		/**
		 * 查询添加的用户是否已存在
		 */
		int count = sysUserService.selectCount(new EntityWrapper<SysUser>()
				.eq("login_account", sysUser.getLoginAccount())
				.eq("group_id", groupId));
		if (count > 0) {
			return ResultData.createError("用户名已存在！");
		}
		
		
		
		/**
		 * 给密码加密
		 */
		String password = new Md5Hash(sysUser.getLoginPassword(),sysUser.getLoginAccount(),2).toString();
		sysUser.setLoginPassword(password);
		sysUser.setGroupId(groupId);
		sysUser.setCreateTime(new Date());
		sysUser.setUpdateTime(new Date());
		try {
			sysUserService.insert(sysUser);
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createError("添加用户失败！");
		}
		
		
		return ResultData.createSuccess("添加用户成功！");
	}

	/**
	 * 用户列表
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping("/list")
	public ResultData list(@RequestBody Map<String, Object> params) {
		Integer currentPage = 1;
		if (!StringUtils.isEmpty(params.get("currentPage"))) {
			currentPage = Integer.parseInt(params.get("currentPage").toString());
		}
		
		SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
		Integer userId = user.getId();
		Integer groupId = user.getGroupId();
		Integer userType = user.getUserType();

		Integer pageSize = 10;
		if (!StringUtils.isEmpty(params.get("pageSize"))) {
			pageSize = Integer.parseInt(params.get("pageSize").toString());
		}

		EntityWrapper<SysUser> wrapper = new EntityWrapper<SysUser>();
		if (!StringUtils.isEmpty(params.get("loginAccount"))) {
			wrapper.like("login_account", params.get("loginAccount").toString());
		}

		if (!StringUtils.isEmpty(params.get("userName"))) {
			wrapper.like("user_name", params.get("userName").toString());
		}
		
		if(userType != 2) {
			wrapper.eq("group_id", groupId);
		}
		
		wrapper.orderBy("create_time", false);

		Page<SysUser> page = sysUserService.selectPage(new Page<SysUser>(currentPage, pageSize), wrapper);
		return ResultData.createSuccess(page, "查询成功！");

	}

	/**
	 * 用户编辑/详情
	 * 
	 * @param sysUser
	 * @return
	 */
	@RequestMapping("/edit")
	public ResultData edit(Integer id) {
		if (StringUtils.isEmpty(id)) {
			return ResultData.createError("用户id不能为空！");
		}
		SysUser sysUser = sysUserService.selectById(id);
		
		
		return ResultData.createSuccess(sysUser, "查询成功！");
	}
	
	/**
	 * 修改用户
	 * @param sysUser
	 * @return
	 */
	@RequestMapping("/update")
	public ResultData update(@RequestBody SysUser sysUser) {
		String loginAccount = sysUser.getLoginAccount();
		if (StringUtils.isEmpty(loginAccount)) {
			return ResultData.createError("登录账户不能为空！");
		}
		Integer id = sysUser.getId();
		if (StringUtils.isEmpty(id)) {
			return ResultData.createError("id不能为空！");
		}

		int count = sysUserService
				.selectCount(new EntityWrapper<SysUser>()
						.eq("login_account", loginAccount)
						.eq("pid", id)
						.ne("id", id));
		if (count > 0) {
			return ResultData.createError("登录账户已存在！");
		}
		sysUser.setUpdateTime(new Date());
		try {
			sysUserService.updateById(sysUser);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createError("更新失败，请重试！");
		}
		return ResultData.createSuccess("更新成功！");
	}

	/**
	 * 账户冻结
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/blocking")
	public ResultData blocking(Integer id) {
		if (StringUtils.isEmpty(id)) {
			return ResultData.createError("id不能为空！");
		}

		SysUser sysUser = new SysUser();
		sysUser.setUserStatus(0);
		try {
			sysUserService.updateById(sysUser);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createError("冻结失败，请重试！");
		}

		return ResultData.createSuccess("冻结成功！");
	}
	
	/**
	 * 账户解冻
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/unBlocking")
	public ResultData unBlocking(Integer id) {
		if (StringUtils.isEmpty(id)) {
			return ResultData.createError("id不能为空！");
		}

		SysUser sysUser = new SysUser();
		sysUser.setUserStatus(1);
		try {
			sysUserService.updateById(sysUser);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createError("解冻失败，请重试！");
		}

		return ResultData.createSuccess("解冻成功！");
	}

	/**
	 * 账户删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	public ResultData delete(Integer id) {
		if (StringUtils.isEmpty(id)) {
			return ResultData.createError("id不能为空！");
		}
		try {
			sysUserService.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.createError("删除失败，请重试！");
		}

		return ResultData.createSuccess("删除成功！");
	}
	
	public static void main(String[] args) {
		String password = new Md5Hash(
				"123456","huanghe",2).toString();
		System.out.println(password);
	}
}
