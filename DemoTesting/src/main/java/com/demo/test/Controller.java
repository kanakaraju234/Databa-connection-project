package com.demo.test;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.googlecode.objectify.Key;

@org.springframework.stereotype.Controller

public class Controller {
	private Gson gson = new Gson();
	BeanUtilsBean beancopier = new BeanCopier();
	
	@RequestMapping (value="/ajaxController")
	public @ResponseBody String saveData(HttpServletRequest req, HttpServletResponse res, @RequestBody String reqBody) 
	{	
		
		Author author = gson.fromJson(reqBody, Author.class);
		
		Store.save(author);
		
//		String fullname = req.getParameter("fullname");
//		String age = req.getParameter("age");
		
		
//		Type type = new TypeToken<List<User>>(){}.getType();
//		List<User> users = gson.fromJson(fullname, type);
		
		
		
//		for (User eachUser : users) {
//			System.out.println(eachUser.getName());
//			System.out.println(eachUser.getAge());
//		}
		
//		String sname = "singamp";
		
//		User user = new User();
		
//		UsersJDO user = new UsersJDO();
//		
//		user.setName("name");
//		
//		try 
//		{
//			Store.save(user);
//		} 
//		catch (Exception e) 
//		{
//			e.printStackTrace();
//		}
		
//		String frontendjson = gson.toJson(users);
		
		String frontendjson = gson.toJson(author);
		
		return frontendjson;
	}
	
	@RequestMapping (value="/ajaxController/{authorId}", method= {RequestMethod.PUT, RequestMethod.PATCH})
	public @ResponseBody String udpateData(HttpServletRequest req, HttpServletResponse res, @RequestBody String reqBody, @PathVariable(value="authorId") Long authorId) 
	{	
		Author author = gson.fromJson(reqBody, Author.class);
		Author oldauthor = Store.get(Author.class, authorId);
		
		try 
		{
			beancopier.copyProperties(oldauthor, author);
		} 
		catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		Store.save(oldauthor);
		
		String frontendjson = gson.toJson(oldauthor);
		return frontendjson;
	}
	
	@RequestMapping (value="/ajaxController/{authorId}", method=RequestMethod.DELETE)
	public void udpateData(HttpServletRequest req, HttpServletResponse res, @PathVariable(value="authorId") Long authorId) 
	{	
		Key<Author> key = Key.create(Author.class, authorId);
		Store.delete(key);
	}
	
	@RequestMapping (value="/getData")
	public @ResponseBody String getData(HttpServletRequest req, HttpServletResponse res) 
	{	
		List<Author> allAuthors =  Store.get(Author.class).list();
		String frontendjson = gson.toJson(allAuthors);
		return frontendjson;
	}
}
