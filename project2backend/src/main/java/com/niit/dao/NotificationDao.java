package com.niit.dao;

import java.util.List;

import com.niit.model.Notification;

public interface NotificationDao {
	//select * from notification where username='james' and viewed=0
	//List of notifications not yet viewed by james
	
	public List<Notification> getNotification(String username,int viewed);
	public Notification updateNotification(int notificationId);

}
