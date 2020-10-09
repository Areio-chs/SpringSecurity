package com.atguigu.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class GongfuController {

	/*数据库给zhangsan学徒这个角色，并且给学徒罗汉拳和武当长拳的权限*/
	/*level1下的1资源只有学徒并且又罗汉拳权限的才能访问*/
	@PreAuthorize("hasRole('学徒') AND hasAuthority('putong:luohanquan')")
	@GetMapping("/level1/1")
	public String level1Page1(){
		return "/level1/1";
	}
	@PreAuthorize("hasRole('学徒') AND hasAuthority('putong:wudangchangquan')")
	@GetMapping("/level1/2")
	public String level1Page2(){
		return "/level1/2";
	}
	@PreAuthorize("hasRole('学徒') AND hasAuthority('putong:quanzhenjianfa')")
	@GetMapping("/level1/3")
	public String level1Page3(){
		return "/level1/3";
	}
	@GetMapping("/level2/{path}")
	public String leve2Page(@PathVariable("path")String path){
		return "/level2/"+path;
	}
	
	@GetMapping("/level3/{path}")
	public String leve3Page(@PathVariable("path")String path){
		return "/level3/"+path;
	}

}
