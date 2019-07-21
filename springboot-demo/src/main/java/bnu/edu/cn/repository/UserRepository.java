package bnu.edu.cn.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import bnu.edu.cn.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	/**
	 * 通过用户名来查找用户
	 * @param userName 用户名
	 * @return
	 */
	User findByUserName(String userName);
}
