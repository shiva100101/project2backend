package com.niit.dao;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.model.BlogPost;
import com.niit.model.BlogPostLikes;
import com.niit.model.User;

@Repository
@Transactional
public class BlogPostLikesDaoImpl implements BlogPostLikesDao {

	@Autowired
	private SessionFactory sessionFactory;
	public BlogPostLikes userLikes(BlogPost blogPost, User user) {
		Session session=sessionFactory.getCurrentSession();
		//select * from blogpostlikes where blogpost_id=? and user_username=?
		Query query=session.createQuery("from BlogPostLikes where blogPost.id=? and user.username=?");
		query.setInteger(0, blogPost.getId());
		query.setString(1, user.getUsername());
		//blogpostlikes=0 / 1 object
		BlogPostLikes blogPostLikes=(BlogPostLikes)query.uniqueResult();
		return blogPostLikes;
	}

	public BlogPost updateLikes(BlogPost blogPost, User user) {
		Session session=sessionFactory.getCurrentSession();
		BlogPostLikes blogPostLikes=userLikes(blogPost,user);
		//insert and increment / delete and decrement
		//likes
		if(blogPostLikes==null) { // insert into blogpost likes,increment blogpost.likes
			BlogPostLikes insertLikes=new BlogPostLikes();
			insertLikes.setBlogPost(blogPost);// FK blogpost_id
			insertLikes.setUser(user);// FK user_username
			session.save(insertLikes);// insert into blogpostlikes
			blogPost.setLikes(blogPost.getLikes() + 1);//increment the number of likes
			session.update(blogPost);// update blogpost likes=.. where id=?
		}
		else { // unlike
			session.delete(blogPostLikes); // delete from blogpost likes
			blogPost.setLikes(blogPost.getLikes() - 1);// decrement the number of blog post likes
			session.merge(blogPost);// update blogpost set likes ...
		}
		return blogPost;
	}

}
