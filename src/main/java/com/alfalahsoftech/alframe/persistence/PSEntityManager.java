package com.alfalahsoftech.alframe.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.Query;
import javax.persistence.metamodel.Metamodel;

import org.hibernate.jpa.internal.EntityManagerImpl;

import com.alfalahsoftech.alframe.AFArrayList;
import com.alfalahsoftech.inv.entity.AFMainEntity;
import com.alfalahsoftech.web.AFObject;

public class PSEntityManager extends AFObject {
	private static final long serialVersionUID = 1L;

	private final transient EntityManager entityManager;
	/*private IdentityMapAccessor internalAccessor;
		private UnitOfWorkImpl unitOfWork;*/
	private transient EntityManagerImpl manager;
	private final transient Metamodel metamodel;
	private final PSSchema schema;

	public PSEntityManager(EntityManager em, PSSchema psSchema) {
		this.entityManager = em;

		this.metamodel = this.entityManager.getMetamodel();
		this.schema = psSchema;
		this.init();
	}

	private void init() {
		this.manager = (EntityManagerImpl) this.entityManager;
		System.out.println("Mappppppppp " + manager.getProperties());
		this.manager.setFlushMode(FlushModeType.COMMIT);
		//this.internalAccessor = (IdentityMapAccessor) (this.manager).getActiveSession().getIdentityMapAccessor();
		//this.unitOfWork = (UnitOfWorkImpl) this.manager.getUnitOfWork();
	}
	public PSSchema getSchema() {
		return this.schema;
	}

	public boolean isOpen() {
		return this.entityManager.isOpen();
	}

	public void detach(AFMainEntity entity) {
		this.entityManager.detach(entity);
	}

	public void refresh(AFMainEntity AFMainEntity) {
		this.entityManager.refresh(AFMainEntity);
	}

	public AFMainEntity merge(AFMainEntity AFMainEntity) {
		return this.entityManager.merge(AFMainEntity);
	}

	public EntityTransaction getTransaction() {
		return this.entityManager.getTransaction();
	}

	public void persist(AFMainEntity e) {
		System.out.println("Persisting object " + e);
		this.entityManager.persist(e);
	}

	public void flush() {
		this.entityManager.flush();
	}

	public void remove(AFMainEntity AFMainEntity) {
		this.entityManager.remove(AFMainEntity);
	}

	public void close() {
		this.clear();
		this.entityManager.close();
	}

	public void clear() {
		this.entityManager.clear();
	}

	public boolean contains(Object object) {
		return this.entityManager.contains(object);
	}

	public Metamodel getMetaModel() {
		return this.metamodel;
	}

	public Query createQuery(String sqlString) {
		return this.entityManager.createQuery(sqlString);
	}

	public Query createQuery(String sqlString,Class<?> cls) {
		return this.entityManager.createQuery(sqlString,cls);
	}
	public EntityManager getEntityManager() {
		return entityManager;
	}

	@SuppressWarnings("unchecked")
	public <T> T getUniqueResult(Class<T> t,String query) {
		AFArrayList<Object> list = new AFArrayList<>();
		this.createQuery(query, t).getResultList().forEach(obj->{
			list.add(obj);
		});
		if(list.size()>1) {
			print("Class=> PSEntityManager | Method:getUniqueResult | Message: Size of elements is greater than 1");
			return null;
		}
		if(list.size()==0) {
			print("Class=> PSEntityManager | Method:getUniqueResult | Message: Size of elements is greater is 0 ");
			return null;
		}
		return (T)list.get(0);
	}

	public AFMainEntity getObject() {
		return null;
	}



}