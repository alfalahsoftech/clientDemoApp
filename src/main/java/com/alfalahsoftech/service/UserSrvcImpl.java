package com.alfalahsoftech.service;

import java.util.ArrayList;
import java.util.List;

import com.alfalahsoftech.controller.AFBaseController;
import com.alfalahsoftech.entity.User;
import com.alfalahsoftech.inv.entity.AFMainEntity;
import com.alfalahsoftech.inv.entity.EOClient;
import com.alfalahsoftech.inv.entity.EOUser;
import com.alfalahsoftech.medi.entity.EOMedicine;
import com.alfalahsoftech.web.AFObject;

public class UserSrvcImpl extends AFObject implements UserService {
	static public  List<User> userList = new ArrayList<User>();

	static{
		userList.add(new User(1, "Sam", "Soft. Dev"));
		userList.add(new User(2, "Arshad", "Soft. Dev"));
		userList.add(new User(3, "Zabi", "Soft. Dev"));	
	}

	public List<EOUser> findAllUsers(){
		List<EOUser> users	=reqRespObject().reqEM().createQuery("select e from EOUser e").getResultList();
		print(users.toString());
		return users;
	}

	public List<EOClient> getClientDetails(){
		List<EOClient> clientList = reqRespObject().reqEM().createQuery("SELECT e FROM EOClient e").getResultList();
		return clientList;
	}
	public  List<AFMainEntity> getData(Class<?> cls){
		List<AFMainEntity> clientList = reqRespObject().reqEM().createQuery("SELECT e FROM "+cls.getSimpleName()+" e").getResultList();
		return clientList;
	}
	public void insertEntity(AFMainEntity af){
		print("insertEntity method Called:");
		this.reqRespObject().startTransaction();
		this.reqRespObject().reqEM().persist(af);
		this.reqRespObject().endTransaction();
	}


	public Boolean addUser(EOUser user){
		this.insertEntity(user);
		return true;
	}

	public Boolean deleteUser(int id){
		for(User user : userList){
			if(user.getId() == id)
				return userList.remove(user);
		}
		return false;
	}

	public EOUser findUserById(int id){
		findAllUsers();
		EOUser user =(EOUser)this.reqRespObject().reqEM().createQuery("select e from EOUser e where primaryKey="+id).getSingleResult();
		return user;
	}
	private static List<EOMedicine> meidlist  = new ArrayList<EOMedicine>();
	public   List<EOMedicine> allEOMedicineData(){
		if(UserSrvcImpl.meidlist.size() == 0) {
			meidlist = this.reqRespObject().reqEM().createQuery("SELECT e FROM EOMedicine e").getResultList();
			print("All medicne loaded##############");
		}
		return meidlist;
	}


	public EOUser updateUser(EOUser userToUpd){
		//		int i=0;
		//		for(User user : userList){
		//			
		//			if(user.getId() == userToUpd.getId()){
		//				userList.add(i,userToUpd);
		//				return user;
		//			}
		//			i++;
		//		}
		return null;
	}

	@Override
	public List<? extends AFMainEntity> genericData(Class<?> cls) {
		List<? extends AFMainEntity> list = reqRespObject().reqEM().createQuery("SELECT e FROM "+cls.getSimpleName()+" e").getResultList();
		return list;
	}
	@Override
	public Long maxPrimaryKey(Class<?> cls) {
		return(Long) reqRespObject().reqEM().createQuery("SELECT max(primaryKey) FROM "+cls.getSimpleName()+" e").getSingleResult();
	}
}
