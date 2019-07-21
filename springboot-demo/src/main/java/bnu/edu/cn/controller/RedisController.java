package bnu.edu.cn.controller;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bnu.edu.cn.entity.Person;
import bnu.edu.cn.repository.PersonDao;

@RestController
public class RedisController {

	@Inject
	PersonDao personDao;
	
	@RequestMapping("/set")
	public void set() {
		Person person = new Person("113", "xiaoming", 15);
		personDao.save(person);
		personDao.stringRedisTemplateDemo();
	}
	
	@GetMapping("/getStr") 
	public String get() {
		String d = personDao.getString();
		return d;
	}
	
	@GetMapping("/getPerson") 
	public Person getPerson() {
		return personDao.getPerson();
	}
	
}
