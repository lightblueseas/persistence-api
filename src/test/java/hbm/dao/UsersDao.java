package hbm.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.flirt.and.date.hbm.dao.User;
import org.springframework.stereotype.Repository;

import de.alpharogroup.db.dao.jpa.JpaEntityManagerDao;

@Repository("usersDao")
public class UsersDao extends JpaEntityManagerDao<User, Long> {

	private static final long serialVersionUID = 721118353877269617L;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;		
	}

}
