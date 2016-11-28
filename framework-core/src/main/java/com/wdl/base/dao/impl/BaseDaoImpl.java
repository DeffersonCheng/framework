package com.wdl.base.dao.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.wdl.base.dao.BaseDao;
import com.wdl.base.pojo.PageInfo;

/**
 * @author bin
 *
 */
@Repository("baseDao")
public class BaseDaoImpl implements BaseDao {

	@Resource
	private SessionFactory sessionFactory;

	public Session getCurrentSession() {
		return this.sessionFactory.getCurrentSession();
	}
	public Session openSession() {
		return this.sessionFactory.openSession();
	}

	public <T> Serializable save(T obj) {
		return this.getCurrentSession().save(obj);
	}

	public <T> void delete(T obj) {
		this.getCurrentSession().delete(obj);
	}

	public <T> void update(T obj) {
		this.getCurrentSession().merge(obj);
	}

	public <T> void saveOrUpdate(T obj) {
		this.getCurrentSession().saveOrUpdate(obj);
	}

	public <T> void batchSave(List<T> entityList) {
		Session session = getCurrentSession();
		for (int i = 0; i < entityList.size(); i++) {
			T entity = entityList.get(i);
			session.save(entity);
			if (i % 20 == 0) {
				session.flush();
				session.clear();
			}
		}
	}

	public <T> void batchUpdate(List<T> entityList) {
		Session session = getCurrentSession();
		for (int i = 0; i < entityList.size(); i++) {
			T entity = entityList.get(i);
			session.update(entity);
			if (i % 20 == 0) {
				session.flush();
				session.clear();
			}
		}
	}

	public <T> void batchDelete(List<T> entityList) {
		Session session = getCurrentSession();
		for (int i = 0; i < entityList.size(); i++) {
			T entity = entityList.get(i);
			session.delete(entity);
			if (i % 20 == 0) {
				session.flush();
				session.clear();
			}
		}
	}

	public <T> void batchSaveOrUpdate(List<T> entityList) {
		Session session = getCurrentSession();
		for (int i = 0; i < entityList.size(); i++) {
			T entity = entityList.get(i);
			session.saveOrUpdate(entity);
			if (i % 20 == 0) {
				session.flush();
				session.clear();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> list(Class<T> clazz) {
		Criteria ct = this.getCurrentSession().createCriteria(clazz);
		return ct.list();
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> clazz, Serializable id) {
		return (T) this.getCurrentSession().get(clazz, id);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String hql) {
		Query query = this.getCurrentSession().createQuery(hql);
		List<T> ls = query.list();
		if (ls != null && ls.size() > 0) {
			return ls.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String hql, Map<String, Object> params) {
		Query query = this.getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
		}
		List<T> ls = query.list();
		if (ls != null && ls.size() > 0) {
			return ls.get(0);
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public <T> T get(String hql,Object... params){
		Query query = this.getCurrentSession().createQuery(hql);
		query.setMaxResults(1);
		if (params != null && params.length>0) {
			int index=0;
			for (Object param:params) {
				query.setParameter(index, param);
				index++;
			}
		}
		List<T> ls = query.list();
		if (ls != null && ls.size() > 0) {
			return ls.get(0);
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public <T> List<T> find(String hql) {
		Query query = this.getCurrentSession().createQuery(hql);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> find(String hql, Map<String, Object> params) {
		Query query = this.getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
		}
		return query.list();
	}
	@SuppressWarnings("unchecked")
	public <T> List<T> find(String hql, Object... params){
		Query query = this.getCurrentSession().createQuery(hql);
		int index=0;
		for (Object param : params) {
			query.setParameter(index,param);
			index++;
		}
		return query.list();
	}
	@SuppressWarnings("unchecked")
	public <T> List<T> find(String hql, int page, int rows) {
		Query query = this.getCurrentSession().createQuery(hql);
		return query.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> find(String hql, Map<String, Object> params, int page, int rows) {
		Query query = this.getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
		}
		return query.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	public Long count(String hql) {
		Query query = this.getCurrentSession().createQuery(hql);
		return (Long) query.uniqueResult();
	}

	public Long count(String hql, Map<String, Object> params) {
		Query query = this.getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
		}
		return (Long) query.uniqueResult();
	}
	public Long count(String hql,Object... params){
		Query query = this.getCurrentSession().createQuery(hql);
		int index=0;
		for (Object object : params) {
			query.setParameter(index, object);
			index++;
		}
		return (Long) query.uniqueResult();
	}
	public int executeHql(String hql,Object... params) {
		Query query = this.getCurrentSession().createQuery(hql);
		for(int i=0;i<params.length;i++){
			query.setParameter(i, params[i]);
		}
		return query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public <T> T getBySql(String sql) {
		SQLQuery sqlQuery = this.getCurrentSession().createSQLQuery(sql);
		List<T> ls = sqlQuery.list();
		if (ls != null && ls.size() > 0) {
			return ls.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public <T> T getBySql(String sql, Map<String, Object> params) {
		SQLQuery sqlQuery = this.getCurrentSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				sqlQuery.setParameter(key, params.get(key));
			}
		}
		List<T> ls = sqlQuery.list();
		if (ls != null && ls.size() > 0) {
			return ls.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findBySql(String sql, Class<T> clazz) {
		SQLQuery sqlQuery = this.getCurrentSession().createSQLQuery(sql);
		sqlQuery.addEntity(clazz);
		return sqlQuery.list();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findBySql(String sql, Map<String, Object> params, Class<T> clazz) {
		SQLQuery sqlQuery = this.getCurrentSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				sqlQuery.setParameter(key, params.get(key));
			}
		}
		sqlQuery.addEntity(clazz);
		return sqlQuery.list();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findBySql(String sql, int page, int rows, Class<T> clazz) {
		SQLQuery sqlQuery = this.getCurrentSession().createSQLQuery(sql);
		sqlQuery.addEntity(clazz);
		return sqlQuery.list();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findBySql(String sql, Map<String, Object> params, int page, int rows, Class<T> clazz) {
		SQLQuery sqlQuery = this.getCurrentSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				sqlQuery.setParameter(key, params.get(key));
			}
		}
		sqlQuery.addEntity(clazz);
		return sqlQuery.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	public BigInteger countBySql(String sql) {
		SQLQuery sqlQuery = this.getCurrentSession().createSQLQuery(sql);
		return (BigInteger) sqlQuery.uniqueResult();
	}

	public Long countBySql(String sql, Map<String, Object> params) {
		SQLQuery sqlQuery = this.getCurrentSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				sqlQuery.setParameter(key, params.get(key));
			}
		}
		return (Long) sqlQuery.uniqueResult();
	}

	public int executeSql(String sql) {
		SQLQuery sqlQuery = this.getCurrentSession().createSQLQuery(sql);
		return sqlQuery.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> findMapBySql(String sql) {
		SQLQuery sqlQuery = this.getCurrentSession().createSQLQuery(sql);
		return sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> findMapBySql(String sql, Map<String, Object> params) {
		SQLQuery sqlQuery = this.getCurrentSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				sqlQuery.setParameter(key, params.get(key));
			}
		}
		return sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> findMapBySql(String sql, int page, int rows) {
		SQLQuery sqlQuery = this.getCurrentSession().createSQLQuery(sql);
		return sqlQuery.setFirstResult((page - 1) * rows).setMaxResults(rows)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> findMapBySql(String sql, Map<String, Object> params, int page, int rows) {
		SQLQuery sqlQuery = this.getCurrentSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				sqlQuery.setParameter(key, params.get(key));
			}
		}
		return sqlQuery.setFirstResult((page - 1) * rows).setMaxResults(rows)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	public List<Map> findMap(String hql,Object... params) {
		Query query = this.getCurrentSession().createQuery(hql);
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		int index=0;
		for (Object param : params) {
			query.setParameter(index,param);
			index++;
		}
		return query.list();
	}

	public List<?> getListByCriteria(DetachedCriteria criteria, PageInfo page) {
		if (page == null) {
			return criteria.getExecutableCriteria(getCurrentSession()).setProjection(null)
					.setResultTransformer(CriteriaSpecification.ROOT_ENTITY).list();
		} else {
			return criteria.getExecutableCriteria(getCurrentSession()).setProjection(null)
					.setResultTransformer(CriteriaSpecification.ROOT_ENTITY)
					.setFirstResult((page.getPageNum() - 1) * page.getPageSize()).setMaxResults(page.getPageSize())
					.list();
		}
	}

	public List<?> getListByCriteria(DetachedCriteria criteria, Integer startPage, Integer pageSize) {
		if (startPage != null && pageSize != null) {
			return criteria.getExecutableCriteria(getCurrentSession()).setProjection(null)
					.setResultTransformer(CriteriaSpecification.ROOT_ENTITY).setFirstResult(startPage)
					.setMaxResults(pageSize).list();
		} else {
			return criteria.getExecutableCriteria(getCurrentSession()).setProjection(null)
					.setResultTransformer(CriteriaSpecification.ROOT_ENTITY).list();
		}
	}

	public int getCountByCriteria(DetachedCriteria criteria) {
		return ((Long) criteria.getExecutableCriteria(getCurrentSession()).setProjection(Projections.rowCount())
				.uniqueResult()).intValue();
	}

	public List findByExample(Object example) {
		return this.getCurrentSession().createCriteria(example.getClass()).add(Example.create(example)).list();
	}

	@Override
	public List getMapByHql(String hql) {
		Query query = getCurrentSession().createQuery(hql);
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}
	public List getPageMapByHql(String hql,PageInfo page) {
		Query query = getCurrentSession().createQuery(hql);
		if(page!=null){
			query.setFirstResult((page.getPageNum() - 1) * page.getPageSize()).setMaxResults(page.getPageSize());
		}
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}
	@Override
	public List getPageMapByHql(String hql,Map<String, Object> params, int page, int rows) {
		Query query = this.getCurrentSession().createQuery(hql);
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
		}
		return query.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}
	@Override
	public List getPageMapByHql(String hql, Map<String, Object> params) {
		Query query = this.getCurrentSession().createQuery(hql);
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
		}
		return query.list();
	}
}
