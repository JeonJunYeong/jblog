package com.douzone.jblog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;
import com.douzone.jblog.vo.UserVo;


@Controller
@RequestMapping("/{id:(?!assets|images).*}")
public class BlogController {

	@Autowired
	private BlogService blogService;
	
	@RequestMapping({"","/{categoryNo}","/{categoryNo}/{postNo}"})
	public String index(@PathVariable("id") String id,
						@PathVariable("categoryNo") Optional<Long> categoryNo ,
						@PathVariable("postNo") Optional<Long>  postNo,Model model) {
		
		UserVo regUser = blogService.getID(id);
		
		if(regUser == null) {
			return "error/404";
		}
		
		long nowCategoryNo = 0L;
		long nowPostNo = 0L;
		
		
		
		if(postNo.isPresent()) {
			nowCategoryNo =categoryNo.get();
			nowPostNo=postNo.get();
			
		}else if(categoryNo.isPresent()) {
			nowCategoryNo = categoryNo.get();
		}
		Map<String, Object> data = blogService.getAll(id, nowCategoryNo, nowPostNo);
		
		
		
		model.addAllAttributes(data);
		
		return "blog/blog-main";
	}
	
	@RequestMapping("/admin-basic")
	public String adminBasic(@PathVariable("id") String id,Model model) {
	
		BlogVo blogVo = blogService.find(id);
		model.addAttribute("blogVo",blogVo);
		
		return "blog/blog-admin-basic";
	}
	
	@RequestMapping("/admin/basic")
	public String basic(@PathVariable("id") String id,
						@RequestParam("title") String title,
						@RequestParam(value="logo-file")MultipartFile multipartFile,Model model) {
	
		String fileName = blogService.getFile(multipartFile);
		
		BlogVo now = new BlogVo();
		now.setId(id);
		now.setTitle(title);
		now.setLogo(fileName);
		
		
		blogService.update(now);
		
		return "redirect:/"+id;
	}

	@RequestMapping("/admin-category")
	public String adminCategory(@PathVariable("id") String id,Model model) {
	
		List<CategoryVo> list = blogService.findCategory(id);
		model.addAttribute("list",list);
		int min = list.get(0).getNo();
		for (CategoryVo categoryVo : list) {
			
			if(min>categoryVo.getNo())
				min=categoryVo.getNo();
			
		}
		model.addAttribute("min",min);
		BlogVo blogVo = blogService.find(id);
		model.addAttribute("blogVo",blogVo);
		
		
		
		return "blog/blog-admin-category";
	}
	
	@RequestMapping("/admin/category")
	public String  category(@PathVariable("id") String id,CategoryVo vo) {
		
		vo.setId(id);
		
		
		blogService.insertCategory(vo);
		
		
		return "redirect:/"+id+"/admin-category";
	}
	
	@RequestMapping("/admin-write")
	public String adminWrite(@PathVariable("id") String id,Model model) {
		
		
		List<CategoryVo> list = blogService.findCategory(id);
		model.addAttribute("list",list);
		BlogVo blogVo = blogService.find(id);
		model.addAttribute("blogVo",blogVo);
		
		return "blog/blog-admin-write";
		
	}
	
	@RequestMapping("/admin/write")
	public String write(@PathVariable("id") String id,PostVo vo,Model model) {
		
		
		
		blogService.insertPost(vo);
		
		
		return "redirect:/"+id;
	}
	
	@RequestMapping("/admin/delete/{idx}")
	public String delete(@PathVariable("id") String id,
						@PathVariable("idx") long no ) {
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("id", id);
		map.put("no", no);
		
		blogService.deleteCategory(map);
		
		return "redirect:/"+id+"/admin-category";
	}
}
