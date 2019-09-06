package file.majing.community.service;

import file.majing.community.mapper.UserMapper;
import file.majing.community.model.User;
import file.majing.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by hechuan on 2019/9/3;
 */
@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;
	@Transactional
	public void createOrUpdate(User user) {
		UserExample userExample = new UserExample();
		userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
		List<User> users=userMapper.selectByExample(userExample);
		if(users.size() ==0){
			user.setGmtCreate(System.currentTimeMillis());
			user.setGmtModified(user.getGmtCreate());
			userMapper.insert(user);
		}else{
			User updateUser = new User();
			updateUser.setName(user.getName());
			updateUser.setToken(user.getToken());
			updateUser.setAvatarUrl(user.getAvatarUrl());
			updateUser.setGmtModified(System.currentTimeMillis());
			userExample.createCriteria().andIdEqualTo(users.get(0).getId());
			userMapper.updateByExampleSelective(updateUser,userExample);
		}
	}
}
