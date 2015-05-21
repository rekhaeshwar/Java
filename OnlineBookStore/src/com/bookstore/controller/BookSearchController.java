package com.bookstore.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bookstore.business.BusinessService;
import com.bookstore.data.Book;
import com.bookstore.data.User;
import com.bookstore.infra.misc.wrapper.ResponseMessageWrapper;
import com.bookstore.web.util.RESTUtil;
import com.meetup.memcached.MemcachedClient;

@Controller
public class BookSearchController {

	@Autowired
	BusinessService transactionService;
	@Autowired
	private RESTUtil restUtil;
	
	
private static final Logger LOG = LoggerFactory.getLogger(BookSearchController.class);
	
	
	
	
	@RequestMapping(value = "/booksearch", method = RequestMethod.GET)
	public String booksearch() {
		System.out.print("booksearch() called");
		return "booksearch";
	}
	
	@RequestMapping(value = "/booksearchsubmit/{title}", method = RequestMethod.GET)
	public @ResponseBody List<Book> signupsubmit(@PathVariable String title) {
		
		ResponseMessageWrapper responseMessageWrapper = new ResponseMessageWrapper();
		LOG.debug("signupsubmit() called");  
		System.out.print("searchsubmit() called");
		Book book= new Book();
		book.setTitle(title);
		List<Book> booklist=transactionService.booksearch(book);
		return booklist;
	}
	
	@RequestMapping(value = "/booksearchsubmit", method = RequestMethod.GET)
	public @ResponseBody List<Book> booksearchsubmit(@RequestParam(value = "title", required = false)final String title,@RequestParam(value = "author", required = true)final String author) {
		System.out.print("title is "+title+"   "+author);
		List<Book> booklist = new ArrayList<Book>();
		ResponseMessageWrapper responseMessageWrapper = new ResponseMessageWrapper();
		LOG.info("booksearchsubmit() called");  
		System.out.print("booksearchsubmit() called");
		Book book= new Book();
		book.setTitle(title);
		MemcachedClient mcc= transactionService.getMemcached();
		Object bookObj=mcc.get("books");
		if(bookObj!=null)
		{
			List<Book> list=(List<Book>) bookObj;
			for(int i=0;i<list.size();i++)
			{
				Book newBook = list.get(i);
				if(title!="" && author!="")
				{
					if(newBook.getTitle().toLowerCase().contains(title.toLowerCase()) && newBook.getAuthor().toLowerCase().contains(author.toLowerCase()))
					{
						booklist.add(newBook);
						LOG.debug("books found in memcache");  

					}
				}
				if(title=="" && author!="")
				{
					if(newBook.getAuthor().toLowerCase().contains(author.toLowerCase()))
					{
						booklist.add(newBook);
						LOG.debug("books found in memcache");  

					}
				}
				if(title!="" && author=="")
				{
					if(newBook.getTitle().toLowerCase().contains(title.toLowerCase()))
					{
						booklist.add(newBook);
						LOG.debug("books found in memcache");  

					}
				}
			}
			if(booklist.size()==0)
			{
				LOG.debug("books not found in memcache , hence connecting to database");  

				booklist=getFromDB(book);
			}
		}
		else{
			LOG.debug("books not found in memcache , hence connecting to database");  

			booklist=getFromDB(book);

		}
		return booklist;
	}
	@RequestMapping(value = "/searchsubmit", method = RequestMethod.GET)
	public ModelAndView  signupsubmit(@ModelAttribute(value = "book") @Valid Book book,
			BindingResult result, HttpServletRequest request, Model model) {
		
		ResponseMessageWrapper responseMessageWrapper = new ResponseMessageWrapper();
		LOG.debug("saveTownMaster() called");  
		System.out.print("searchsubmit() called");
		List<Book> booklist=transactionService.booksearch(book);
		
		//responseMessageWrapper = restUtil.putData(townMasterDTO, EFMSWebConstants.TownMaster.SAVE_TOWNMASTER_PATH);

		return new ModelAndView("booksearch",
				"responseMessageWrapper", responseMessageWrapper);	}
	
	public List<Book> getFromDB(Book book)
	{
		List<Book> booklist=transactionService.booksearch(book);
		MemcachedClient mcc= transactionService.getMemcached();

		mcc.add("books", booklist);
		return booklist;
	}
	
	 
}
