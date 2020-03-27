package com.douzone.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.BlogRepository;
import com.douzone.jblog.repository.UserRepository;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.UserVo;

@Service
public class UserService {

	
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BlogRepository blogReposioty;
	
	public boolean join(UserVo vo) {
		
		boolean result = userRepository.insert(vo);
		
		if(result==true) {
			BlogVo blogVo=new BlogVo();
			
			blogVo.setId(vo.getId());
			blogVo.setTitle(vo.getId()+"님의 블로그입니다.");
			blogVo.setLogo("20202164318539.jpg");
			
			CategoryVo categoryVo = new CategoryVo();
			
			categoryVo.setName("기타");
			categoryVo.setDescription("기타");
			categoryVo.setId(vo.getId());
			
			userRepository.blogInsert(blogVo);
			blogReposioty.insertCategory(categoryVo);
		}
		
		return result;
	}
	
	public UserVo getUser(UserVo vo) {
		
		UserVo now = userRepository.getUser(vo);
				
		return now;
		
	}
}
