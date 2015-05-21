package com.bookstore.businessImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.business.BusinessService;
import com.bookstore.dao.BookDAO;
import com.bookstore.dao.UserDAO;
import com.bookstore.dao.UserInfoDAO;
import com.bookstore.data.Book;
import com.bookstore.data.User;
import com.bookstore.data.UserInfo;
import com.meetup.memcached.MemcachedClient;
import com.meetup.memcached.SockIOPool;
@Transactional

public class BusinessServiceImpl implements BusinessService {

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private UserInfoDAO userInfoDAO;
	
	@Autowired
	private BookDAO bookDAO;
	@Override
	public String validate() {
		
	//	User user=userDAO.findById(1);
	//	System.out.println(user.getName());
		userDAO.getUserList();
		return null;
	}
	
	
	@Override
	public boolean authenticate(User user) {
		// TODO Auto-generated method stub
		boolean auth=userDAO.authenticate(user);
		
		return auth;
	}


	@Override
	public boolean signup(UserInfo user) {
		try{
			userInfoDAO.persist(user);
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public List<Book> booksearch(Book book) {
		List<Book> booklist=null;
		if(book.getAuthor()!= null || book.getTitle() != null)
		{
			 booklist=bookDAO.bookSearch(book);
		}
		return booklist;
	}
	
	@Override
	public boolean userSignUp(User user) {
		try{
			userDAO.persist(user);
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public MemcachedClient startMemcached() {
		// TODO Auto-generated method stub
		 String[] servers = {"localhost:11211"};
	        SockIOPool pool = SockIOPool.getInstance("bookstore");
	        pool.setServers( servers );
	        pool.setFailover( true );
	        pool.setInitConn( 10 );
	        pool.setMinConn( 5 );
	        pool.setMaxConn( 250 );
	        pool.setMaintSleep( 30 );
	        pool.setNagle( false );
	        pool.setSocketTO( 3000 );
	        pool.setAliveCheck( true );
	        pool.initialize();
	        //Get the Memcached Client from SockIOPool named Test1
	        MemcachedClient mcc = new MemcachedClient("bookstore");
	        //add some value in cache
	        System.out.println("add status:"+mcc.add("1", "Original"));
	        boolean set=true;
	        mcc.add("memset", set);

	        return mcc;
	}


	@Override
	public MemcachedClient getMemcached() {
        MemcachedClient mcc = new MemcachedClient("bookstore");
        Object set= mcc.get("memset");
       if(set != null  )
       {
    	   boolean isset=(boolean) set;
    	   if(isset)
    	   {
               return mcc;

    	   }

       }
       else
    	   return startMemcached();
	return null;
	}

}
