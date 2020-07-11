/**
 * 
 */
package com.alfalahsoftech.service;

import java.util.List;

import com.alfalahsoftech.inv.entity.AFMainEntity;
import com.alfalahsoftech.inv.entity.EOClient;
import com.alfalahsoftech.inv.entity.EOUser;

/**
 * @author malam
 * @date Jan 15, 2017
 */
public interface UserService {

	public List<EOUser> findAllUsers();
	public Boolean addUser(EOUser user);
	public Boolean deleteUser(int id);
	public EOUser findUserById(int id);
	public EOUser updateUser(EOUser user);
	public List<EOClient> getClientDetails();
	public void insertEntity(AFMainEntity entity);
	public List<AFMainEntity> getData(Class<?> cls);
	public List<? extends AFMainEntity> genericData(Class<?> cls);
	public Long maxPrimaryKey(Class<?> cls);
	

}
