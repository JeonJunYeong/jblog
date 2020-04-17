package com.douzone.jblog.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;
import com.douzone.jblog.vo.UserVo;


@Repository
public class BlogRepository {

	@Autowired
	SqlSession sqlSession;
	
	public UserVo getID(UserVo vo) {

		UserVo now = sqlSession.selectOne("user.getUser",vo);
		
		return now;
	}
	
	public long getPostNo(long nowPostNo) {
		
		long cnt = sqlSession.selectOne("blog.findPostNo",nowPostNo);
		
		return cnt;
		
	}
	
	public long getCategoryNo(long nowCategoryNo) {
		
		long cnt = sqlSession.selectOne("blog.findCategoryNo",nowCategoryNo);
		
		return cnt;
	}

	
	public BlogVo find(String id) {
		
		
		BlogVo vo = sqlSession.selectOne("blog.find",id);
		
		return vo;
	}

	public boolean update(BlogVo now) {
		
		int count = sqlSession.update("blog.update",now);
		
		return count ==1;
		
	}
	
	
	public boolean insertCategory(CategoryVo vo) {
		
		int count = sqlSession.insert("blog.insertCategory",vo);
		
		return count ==1;
	}

	public List<CategoryVo> findCategory(String id) {
		
		List<CategoryVo> list = sqlSession.selectList("blog.findCategory",id);
		
		return list;
		
	}

	public boolean insertPost(PostVo vo) {
		
		int count = sqlSession.insert("blog.insertPost",vo);
		
		return count ==1;
	}
	public List<PostVo> findPostList(Map<String, Object> map) {
		
		List<PostVo> list = sqlSession.selectList("blog.findPostList",map);
		
		return list;
	}

	public PostVo findNowPost(Map<String,Object> map) {
		

		PostVo vo = sqlSession.selectOne("blog.findNowPost",map);
		
		
		return vo;
	}

	public boolean deleteCategory(Map<String, Object> map) {
		
		int count = sqlSession.delete("blog.deleteCategory",map);
		
		
		return count ==1;
		
	}

	public int findCategoryNo(String id) {
		
		return sqlSession.selectOne("blog.findLastCategoryNo",id);
	}

	
	

	
}
