package com.douzone.jblog.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.mysite.dto.JsonResult;

@Controller("BlogApiController")
@RequestMapping("/apiblog")
public class BlogController {

	
	@Autowired
	private BlogService blogService;
	
	@GetMapping("/category-list/{id}")
	@ResponseBody
	public JsonResult list(@PathVariable("id") String id) {
		
		List<CategoryVo> list =blogService.findCategory(id);
		
		return JsonResult.success(list);
	}
	
	@PostMapping("/category-add")
	@ResponseBody
	public JsonResult add(@RequestBody CategoryVo vo) {
		System.out.println(vo.toString());
		
		blogService.insertCategory(vo);
		vo.setNo(blogService.findCategoryNo(vo.getId()));
		return JsonResult.success(vo);
	}
	
	@DeleteMapping("/delete/{no}/{id}")
	@ResponseBody
	public JsonResult delete(@PathVariable("no") long no,@PathVariable("id") String id) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("id", id);
		map.put("no", no);
		
		System.out.println("ID :"+id+",NO:"+no);
		blogService.deleteCategory(map);
		
		return JsonResult.success(null);
	}
	
	
}
