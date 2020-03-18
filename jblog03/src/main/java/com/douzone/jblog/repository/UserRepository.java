package com.douzone.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.UserVo;

@Repository
public class UserRepository {

	@Autowired
	SqlSession sqlSession;
	
	public boolean insert(UserVo vo) {

		int count = sqlSession.insert("user.insert",vo);
		
		
		return count ==1;
	
	}
	
	public boolean blogInsert(BlogVo vo) {
		
		
		int count = sqlSession.insert("user.bloginsert",vo);
		
		
		return count==1;
	}
	
	
	public UserVo getUser(UserVo vo) {
		
		UserVo now = sqlSession.selectOne("user.getUser",vo);
		
		return now;
	}
}
