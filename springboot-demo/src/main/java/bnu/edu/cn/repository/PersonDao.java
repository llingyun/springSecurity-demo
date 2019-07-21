package bnu.edu.cn.repository;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import bnu.edu.cn.entity.Person;

@Repository
public class PersonDao {

	@Inject
	StringRedisTemplate stringRedisTemplate;
	
	@Resource(name = "stringRedisTemplate")
	ValueOperations<String, String> valOpsStr;
	
	@Inject
	RedisTemplate<Object, Object> redisTemplate;
	
	@Resource(name = "redisTemplate")
	ValueOperations<Object,Object> valOps;
	
	public void stringRedisTemplateDemo() {
		valOpsStr.set("xx", "yy");
	}
	
	public void save(Person person) {
		valOps.set(person.getId(), person);
	}
	
	public String getString() {
		return valOpsStr.get("xx");
	}
	
	public Person getPerson() {
		return (Person) valOps.get("113");
	}
	
}
