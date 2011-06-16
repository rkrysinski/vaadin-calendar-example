package com.krycha.calendar.db;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * JPO Entity Manager Factory.
 */
public final class EMF {
	private static final String PERSISTENCE_UNIT_NAME = "calendar";
	private static final EntityManagerFactory EMF_OBJ = Persistence
			.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

	private EMF() {
	}

	public synchronized static EntityManager get() {
		return EMF_OBJ.createEntityManager();
	}

	@SuppressWarnings("unchecked")
	public synchronized static <T> List<T> findAll(Class<T> c) {
		List<T> retList = null;
		EntityManager em = get();
		em.getTransaction().begin();
		try {
			Query q = em.createQuery("select f from " + c.getSimpleName() + " f");
			retList = (List<T>) q.getResultList();
		} finally {
			em.getTransaction().rollback();
			em.close();
		}
		return retList;
	}

	@SuppressWarnings("unchecked")
	public synchronized static <T extends DbPojo> T find(Class<T> c, String query, Map<String, Object> parameters) {
		T retValue = null;
		EntityManager em = get();
		em.getTransaction().begin();
		try {
			Query q = em.createQuery(query);
			if (parameters != null) {
				for (String key : parameters.keySet()) {
					q.setParameter(key, parameters.get(key));
				}
			}
			retValue = (T) q.getSingleResult();
		} catch (NoResultException e) {
			// fall through, return null
		} finally {
			em.getTransaction().commit();
			em.close();
		}
		return retValue;
	}
	
	public synchronized static <T> T find(Class<T> c, Object id) {
		T retValue = null;
		EntityManager em = get();
		em.getTransaction().begin();
		try {
			retValue = (T) em.find(c, id);
		} catch (NoResultException e) {
			// fall through, return null
		} finally {
			em.getTransaction().commit();
			em.close();
		}
		return retValue;
	}

//	public static <T extends DbPojo> void store(T obj) {
//		EntityManager em = get();
//		em.getTransaction().begin();
//		try {
//			if (obj.getId() == null) {
//				em.persist(obj);
//			} else {
//				em.merge(obj);
//			}
//		} finally {
//			em.getTransaction().commit();
//			em.close();
//		}
//	}
	
	public synchronized static <T> void store(T obj) {
		EntityManager em = get();
		em.getTransaction().begin();
		try {
			em.merge(obj);
		} finally {
			em.getTransaction().commit();
			em.close();
		}
	}

	public synchronized static <T extends DbPojo> void store(List<T> list) {
		EntityManager em = get();
		em.getTransaction().begin();
		try {
			for (T obj : list) {
				if (obj.getId() == null) {
					em.persist(obj);
				} else {
					em.merge(obj);
				}
			}
		} finally {
			em.getTransaction().commit();
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public synchronized static <T extends DbPojo> void remove(Class<T> c, String query, Map<String, Object> parameters) {
		EntityManager em = get();
		em.getTransaction().begin();
		try {
			Query q = em.createQuery(query);
			if (parameters != null) {
				for (String key : parameters.keySet()) {
					q.setParameter(key, parameters.get(key));
				}
			}
			T toRemove = (T) q.getSingleResult();
			// System.out.println("remove: " + toRemove.toString());
			em.remove(toRemove);
		} catch (NoResultException e) {
			// fall through, return null
			// System.out.println("remove: " + e.getMessage());
		} finally {
			em.getTransaction().commit();
			em.close();
		}
	}
	
	public synchronized static <T> void remove(Class<T> c, Object id) {
		EntityManager em = get();
		em.getTransaction().begin();
		try {
			T toRemove = (T) em.find(c, id);
			// System.out.println("remove: " + toRemove.toString());
			em.remove(toRemove);
		} catch (NoResultException e) {
			// fall through, return null
			// System.out.println("remove: " + e.getMessage());
		} finally {
			em.getTransaction().commit();
			em.close();
		}
	}

}
