package bnu.edu.cn.service.data;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import bnu.edu.cn.entity.User;
import bnu.edu.cn.repository.UserRepository;
import bnu.edu.cn.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Inject
	UserRepository userRepository;
	
	public User findOne(Long id) {
		User user = userRepository.findOne(id);
		return user;
	}
}
