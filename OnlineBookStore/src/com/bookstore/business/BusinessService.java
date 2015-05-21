package com.bookstore.business;

import java.util.List;

import com.bookstore.data.Book;
import com.bookstore.data.User;
import com.bookstore.data.UserInfo;
import com.meetup.memcached.MemcachedClient;

public interface BusinessService {

	public String validate();
	public boolean authenticate(User user);
	public boolean signup(UserInfo userInfo);
	public List<Book> booksearch(Book book);
	boolean userSignUp(User user);
	public MemcachedClient startMemcached();
	public MemcachedClient getMemcached();
}
