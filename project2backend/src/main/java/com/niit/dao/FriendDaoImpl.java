package com.niit.dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.model.Friend;
import com.niit.model.User;

@Repository
@Transactional
public class FriendDaoImpl implements FriendDao {
	@Autowired
	private SessionFactory sessionFactory;

	public List<User> suggestedUserList(String username) {
		Session session = sessionFactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery("select * from user_s180133 where username in "
				+ " (select username from user_s180133 where username!=? " 
				+ " minus "
				+ "(select fromId from friend_s180133 where toId=? "
				+ " union select toId from friend_s180133 where fromId=? ))" );
		query.setString(0, username);
		query.setString(1, username);
		query.setString(2, username);
		query.addEntity(User.class);
		List<User> suggestedUsers = query.list();
		return suggestedUsers;
	}

	public void friendRequest(Friend friend) {
		Session session = sessionFactory.getCurrentSession();
		session.save(friend);// insert into friend

	}

	public List<Friend> pendingRequests(String username) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Friend where toId=? and status=? ");
		query.setString(0, username);
		query.setCharacter(1,'P');
		List<Friend> pendingRequests = query.list();
		return pendingRequests;
	}

	public void updatePendingRequest(Friend friend) {
		Session session = sessionFactory.getCurrentSession();
		if (friend.getStatus() == 'A')
			session.update(friend);// update the status from P to A
		else
			session.delete(friend);// delete the record

	}

	public List<User> listOfFriends(String username) {
		Session session = sessionFactory.getCurrentSession();
		SQLQuery query1=session.createSQLQuery("select * from user_s180133 where username in "
				+ "(select toId from friend_s180133 where fromId=? and status='A' "
				+ " union "
				+ "select fromId from friend_s180133 where toId=? and status='A')");
		
		query1.setString(0, username);
		query1.setString(1, username);
		query1.addEntity(User.class);
		List<User> list1=query1.list();
			return list1;
	}

	public List<User> listofMutualFriends(String loginId, String sugguestedUsername) {
		List<User> list1=listOfFriends(loginId);
		List<User> list2=listOfFriends(sugguestedUsername);
		List<User> mutualFriends=new ArrayList<User>();
		for(User user1:list1)
		{
			for(User user2:list2){
				if(user1.getUsername().equals(user2.getUsername()))
				mutualFriends.add(user1);
			}
	}
	return mutualFriends;
	}
	

	
}