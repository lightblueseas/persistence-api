package org.flirt.and.date.hbm.service;

import hbm.dao.UsersDao;

import java.io.Serializable;
import java.util.List;

import org.flirt.and.date.hbm.dao.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("UserService")
public class UsersService {

	private UsersDao userDao;

	public Class<User> getType() {
		return userDao.getType();
	}

	public void delete(Long id) {
		userDao.delete(id);
	}


	public void delete(User persistentObject) {
		userDao.delete(persistentObject);
	}

	public boolean exists(Long id) {
		return userDao.exists(id);
	}

	public List<User> findAll() {
		return userDao.findAll();
	}

	public User get(Long id) {
		return userDao.get(id);
	}

	public void refresh(User o) {
		userDao.refresh(o);
	}

	public Serializable save(User o) {
		return userDao.save(o);
	}


	public void saveOrUpdate(User o) {
		userDao.saveOrUpdate(o);
	}

	public void update(User o) {
		userDao.update(o);
	}

	public void evict(User persistentObject) {
		userDao.evict(persistentObject);
	}
}
