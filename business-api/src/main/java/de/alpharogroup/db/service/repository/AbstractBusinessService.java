package de.alpharogroup.db.service.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import de.alpharogroup.db.entity.BaseEntity;
import de.alpharogroup.db.repository.api.GenericRepository;
import de.alpharogroup.db.service.api.BusinessService;
import lombok.Getter;
import lombok.Setter;

/**
 * The abstract class {@link AbstractBusinessService}.
 *
 * @param <T>
 *            the type of the domain object
 * @param <PK>
 *            the type of the primary key from the domain object
 * @param <REPOSITORY>
 *            the type of the data access object.
 */
public abstract class AbstractBusinessService<T extends BaseEntity<PK>, PK extends Serializable, REPOSITORY extends GenericRepository<T, PK>>
	implements
		BusinessService<T, PK>
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	/** The repository reference. */
	@Getter
	@Setter
	private REPOSITORY repository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void delete(List<T> objects)
	{
		for (final T t : objects)
		{
			delete(t.getId());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void delete(PK id)
	{
		getRepository().delete(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void delete(T id)
	{
		getRepository().delete(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void evict(T object)
	{
		getRepository().evict(object);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean exists(PK id)
	{
		return getRepository().exists(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<T> findAll()
	{
		return getRepository().findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T get(PK id)
	{
		return getRepository().get(id);
	}

	/**
	 * Gets the {@link Query} from the given string.
	 *
	 * @param s
	 *            the query as string
	 * @return the query
	 */
	public Query getQuery(String s)
	{
		return getRepository().getQuery(s);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T load(PK id)
	{
		return getRepository().load(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public List<T> merge(List<T> objects)
	{
		return getRepository().merge(objects);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public T merge(T object)
	{
		return getRepository().merge(object);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void refresh(T object)
	{
		getRepository().refresh(object);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public List<PK> save(List<T> objects)
	{
		return getRepository().save(objects);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public PK save(T newInstance)
	{
		return getRepository().save(newInstance);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void saveOrUpdate(List<T> objects)
	{
		getRepository().saveOrUpdate(objects);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void saveOrUpdate(T object)
	{
		getRepository().saveOrUpdate(object);
	}

}
