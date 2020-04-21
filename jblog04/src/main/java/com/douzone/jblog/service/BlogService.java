package com.douzone.jblog.service;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.jblog.repository.BlogRepository;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;
import com.douzone.jblog.vo.UserVo;
@Service
public class BlogService {

	private static final String SAVE_PATH = "/mysite-uploads";
	
	
	@Autowired
	private BlogRepository blogRepository;
	
	public UserVo getID(String id) {
		
		UserVo now=new UserVo();
		
		now.setId(id);
		now.setPassword("");
		
		UserVo vo = blogRepository.getID(now);
				
		return vo;		
		
	}
	
	public long getPostNo(long nowPostNo) {
		
		long cnt = blogRepository.getPostNo(nowPostNo);
		
		
		return cnt;
	}
	
	public long getCategoryNo(long nowCategoryNo) {
		
		long cnt = blogRepository.getCategoryNo(nowCategoryNo);
		
		return cnt;
	}

	

	public BlogVo find(String id) {
		
		BlogVo vo = blogRepository.find(id);
		
		return vo;
		
	}
	
	public boolean update(BlogVo now) {
		
		boolean result = blogRepository.update(now);
		
		return result;
	}
	
	public boolean insertCategory(CategoryVo vo) {
		
		boolean result = blogRepository.insertCategory(vo);
		
		return result;
	}


	public List<CategoryVo> findCategory(String id) {
		
		List<CategoryVo> list = blogRepository.findCategory(id);
		
		return list;
		
	}
	
	
	public boolean deleteCategory(Map<String, Object> map) {
		
		boolean result = blogRepository.deleteCategory(map);
		
		return result;
		
	}
	
	public List<PostVo> findPost(String id){
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("id",id);
		List<PostVo> list = blogRepository.findPostList(map);
		
		return list;
		
	}
	
	public boolean insertPost(PostVo vo) {
		
		boolean result = blogRepository.insertPost(vo);
		
		return result;
	}
	

	public String getFile(MultipartFile multipartFile) {
		
		String url = "";
		
		try {
			if(multipartFile.isEmpty()) {
				return url;
			}
			
			String originFilename = multipartFile.getOriginalFilename();
			
			int lastIdx = originFilename.lastIndexOf('.');
			String extName = originFilename.substring(lastIdx+1);
			
			String saveFilename = generateSaveFilename(extName);
		
			byte[] fileDate = multipartFile.getBytes();
			OutputStream os = new FileOutputStream(SAVE_PATH+"/"+saveFilename);
			os.write(fileDate);
			os.close();
			
			
			url = saveFilename;
			
		}catch(Exception e) {
			throw new RuntimeException("file upload error : "+e);
		}
		
		return url;
		
	}
	
	private String generateSaveFilename(String extName) {
		
		String fileName = "";
		
		Calendar calendar = Calendar.getInstance();
		
		fileName += calendar.get(Calendar.YEAR);
		fileName += calendar.get(Calendar.MONTH);
		fileName += calendar.get(Calendar.DATE);
		fileName += calendar.get(Calendar.HOUR);	
		fileName += calendar.get(Calendar.MINUTE);
		fileName += calendar.get(Calendar.SECOND);
		fileName += calendar.get(Calendar.MILLISECOND);
		fileName += ("."+extName);
				
				
		
		return fileName;
	}

	public Map<String,Object> getAll(String id,long categoryNo,long postNo) {
		
		BlogVo blogVo = find(id);
		PostVo nowPost;
		List<CategoryVo> categoryList=findCategory(id);
		List<PostVo> postList;
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("id",id);
		map.put("categoryNo",categoryNo);
		map.put("postNo", postNo);
		
	    postList =blogRepository.findPostList(map);
		nowPost = blogRepository.findNowPost(map);
	    
	    Map<String, Object> dataMap = new HashMap<>();
	    
	    dataMap.put("blogVo", blogVo);
	    dataMap.put("nowPost",nowPost);
	    dataMap.put("categoryList", categoryList);
	    dataMap.put("postList", postList);
	  
	    return dataMap;
	    
	}

	
	public String getUrl(long nowCategoryNo,long nowPostNo) {
		
		String url="";
		if(nowCategoryNo==0) {
			return "";
		}else {
			long categoryNo =getCategoryNo(nowCategoryNo);
			if(categoryNo!=0) {
				url="/"+nowCategoryNo;
			}
		}
		
		if(nowPostNo==0) {
			return url;
		}else {
			long postNo=getPostNo(nowPostNo);
			
			if(postNo!=0) {
				url=url+"/"+nowPostNo;
			}
		}
		
		return url;
		
	}
	

	public int findCategoryNo(String id) {
		
		
		return blogRepository.findCategoryNo(id);
	}
	

	

	
	
}
