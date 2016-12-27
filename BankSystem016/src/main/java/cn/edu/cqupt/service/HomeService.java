package cn.edu.cqupt.service;

import java.util.HashMap;
import java.util.List;

import cn.edu.cqupt.model.Interesttable;
import cn.edu.cqupt.model.LoginUserInfo;
import cn.edu.cqupt.model.Loginuser;
import cn.edu.cqupt.model.Userinfo;

public interface HomeService {

	HashMap<String, Object> selectLoginuser(String username, String password) throws Exception;

	LoginUserInfo selectuserinfo(String string);

	int saveuser(Loginuser user, Userinfo userinfo);

	int updateInterests(List<Interesttable> interests);

	int updateLoanInterests(List<Interesttable> interests);

}
