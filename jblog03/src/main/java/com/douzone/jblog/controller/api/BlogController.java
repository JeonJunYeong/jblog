package com.douzone.jblog.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
		
		blogService.insertCategory(vo);
		vo.setNo(blogService.findCategoryNo(vo.getId()));
		return JsonResult.success(vo);
	}
	
	
}
