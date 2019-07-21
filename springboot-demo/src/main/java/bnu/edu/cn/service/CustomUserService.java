package bnu.edu.cn.service;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import bnu.edu.cn.entity.User;
import bnu.edu.cn.repository.UserRepository;

@Component
public class CustomUserService implements UserDetailsService{

	@Inject
	UserRepository userRepository;
	
	/*@Inject
	private PasswordEncoder passwordEncoder;*/
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(userName);
		if (user == null) {
			throw new UsernameNotFoundException("用户名不存在!!");
		}
		return user;
		
		/* 测试自定义用户的密码加密
		 System.out.println(passwordEncoder.encode("666666"));
		return new org.springframework.security.core.userdetails.User("凌云", passwordEncoder.encode("666666"), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));*/
	}
	
	/*@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		// 测试自定义用户的密码加密
		 System.out.println(passwordEncoder.encode("666666"));
		return new org.springframework.security.core.userdetails.User("凌云", passwordEncoder.encode("666666"), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));
	}*/

}
