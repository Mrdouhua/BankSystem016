package cn.edu.cqupt.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.registry.infomodel.User;

import org.springframework.stereotype.Service;

import cn.edu.cqupt.core.JobsConstants;
import cn.edu.cqupt.dao.LoginuserMapper;
import cn.edu.cqupt.dao.UserinfoMapper;
import cn.edu.cqupt.dao.InteresttableMapper;
import cn.edu.cqupt.dao.LoginUserInfoMapper;
import cn.edu.cqupt.model.Interesttable;
import cn.edu.cqupt.model.LoginUserInfo;
import cn.edu.cqupt.model.Loginuser;
import cn.edu.cqupt.model.Userinfo;

@Service("homeService")
public class HomeService implements cn.edu.cqupt.service.HomeService {
	
	@Resource
	private LoginUserInfoMapper LoginUserInfoMapper;
	
	@Resource
	private LoginuserMapper loginuserMapper;
	@Resource
	private UserinfoMapper userinfoMapper;
	@Resource
	private InteresttableMapper interesttableMapper;
	
	public LoginUserInfoMapper getLoginUserInfoMapper() {
		return LoginUserInfoMapper;
	}


	public void setLoginUserInfoMapper(LoginUserInfoMapper loginUserInfoMapper) {
		LoginUserInfoMapper = loginUserInfoMapper;
	}


	public InteresttableMapper getInteresttableMapper() {
		return interesttableMapper;
	}


	public void setInteresttableMapper(InteresttableMapper interesttableMapper) {
		this.interesttableMapper = interesttableMapper;
	}


	public LoginuserMapper getLoginuserMapper() {
		return loginuserMapper;
	}


	public void setLoginuserMapper(LoginuserMapper loginuserMapper) {
		this.loginuserMapper = loginuserMapper;
	}


	public UserinfoMapper getUserinfoMapper() {
		return userinfoMapper;
	}


	public void setUserinfoMapper(UserinfoMapper userinfoMapper) {
		this.userinfoMapper = userinfoMapper;
	}


	
	

	public HashMap<String, Object> selectLoginuser(String username, String password) throws Exception {
		Loginuser user=loginuserMapper.isHaveUser(username);
		if (user==null){
			throw new Exception("该用户不存在");
		}
		
		String status=user.getStatus().toLowerCase();
		if (!JobsConstants.LOGIN_USER_STATUS_NORMAL.equals(status)) {
			switch (status) {
			case JobsConstants.LOGIN_USER_STATUS_FREEZE:
				throw new Exception("该用户账号已被冻结");
			default:
				throw new Exception("该用户账号存在异常");
			}
		}
		//检查密码是否匹配
		if (!password.trim().equals(user.getPassword().trim())){
			throw new Exception("账号或密码不正确");
		}
		Userinfo info=userinfoMapper.selectUserinfoByuserid(user.getUserid());
		HashMap<String, Object> userinfo=new HashMap<>();
		userinfo.put("loginuser",user);
		userinfo.put("userinfo", info);
		return userinfo;
	}


	@Override
	public LoginUserInfo selectuserinfo(String string) {
		System.out.println("用户id为"+string);
		return LoginUserInfoMapper.selectuserinfo(string);
	}


	@Override
	public int saveuser(Loginuser user, Userinfo userinfo) {
		int res=0;
		try {
			loginuserMapper.insertSelective(user);
			userinfoMapper.insert(userinfo);
			res=1;
		} catch (Exception e) {
			throw new RuntimeException("保存用户信息异常");
		}
		return res;
	}


	@Override
	public int updateInterests(List<Interesttable> interests) {
		int res=0;
		
		try {
			for (Interesttable p:interests){
				interesttableMapper.updatedDepositInterest(p);
			}
			res=1;
		} catch (Exception e) {
			throw new RuntimeException("更新利率失败"+e.getMessage());
		}
		return res;
	}


	@Override
	public int updateLoanInterests(List<Interesttable> interests) {
		int res=0;
		try {
			for (Interesttable p:interests){
				interesttableMapper.updatedDepositInterest(p);
			}
			res=1;
		} catch (Exception e) {
			throw new RuntimeException("更新贷款利率失败"+e.getMessage());
		}
		return res;
	}

}
