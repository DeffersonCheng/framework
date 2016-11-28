package com.wdl.base.service.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;

import com.wdl.base.dao.BaseDao;
import com.wdl.base.pojo.PageInfo;
import com.wdl.base.service.BaseService;

/**
 * @author bin
 *
 */
@Service("baseService")
public class BaseServiceImpl implements BaseService {

	@Resource
	private BaseDao baseDao;

	public <T> Serializable save(T obj) {
		return baseDao.save(obj);
	}

	public <T> void delete(T obj) {
		baseDao.delete(obj);
	}

	public <T> void update(T obj) {
		baseDao.update(obj);
	}

	public <T> void saveOrUpdate(T obj) {
		baseDao.saveOrUpdate(obj);
	}

	public <T> void batchSave(List<T> entityList) {
		baseDao.batchSave(entityList);
	}

	public <T> void batchUpdate(List<T> entityList) {
		baseDao.batchUpdate(entityList);
	}

	public <T> void batchDelete(List<T> entityList) {
		baseDao.batchDelete(entityList);
	}
	
	public <T> void batchSaveOrUpdate(List<T> entityList) {
		baseDao.batchSaveOrUpdate(entityList);
	}
	
	public <T> List<T> list(Class<T> clazz) {
		return baseDao.list(clazz);
	}

	public <T> T get(Class<T> clazz, Serializable id) {
		return baseDao.get(clazz, id);
	}

	public <T> T get(String hql) {
		return baseDao.get(hql);
	}

	public <T> T get(String hql, Map<String, Object> params) {
		return baseDao.get(hql, params);
	}
	@Override
	public <T> T get(String hql,Object... params){
		return baseDao.get(hql, params);
	}

	public <T> List<T> find(String hql) {
		return baseDao.find(hql);
	}

	public <T> List<T> find(String hql, Map<String, Object> params) {
		return baseDao.find(hql, params);
	}

	public <T> List<T> find(String hql, int page, int rows) {
		return baseDao.find(hql, page, rows);
	}

	public <T> List<T> find(String hql, Map<String, Object> params, int page, int rows) {
		return baseDao.find(hql, params, page, rows);
	}

	public Long count(String hql) {
		return baseDao.count(hql);
	}

	public Long count(String hql, Map<String, Object> params) {
		return baseDao.count(hql, params);
	}
	public Long count(String hql,Object... params){
		return baseDao.count(hql, params);
	}

	public int executeHql(String hql,Object...params) {
		return baseDao.executeHql(hql,params);
	}

	public <T> T getBySql(String sql) {
		return baseDao.getBySql(sql);
	}

	public <T> T getBySql(String sql, Map<String, Object> params) {
		return baseDao.getBySql(sql, params);
	}

	public <T> List<T> findBySql(String sql, Class<T> clazz) {
		return baseDao.findBySql(sql, clazz);
	}

	public <T> List<T> findBySql(String sql, Map<String, Object> params, Class<T> clazz) {
		return baseDao.findBySql(sql, params, clazz);
	}

	public <T> List<T> findBySql(String sql, int page, int rows, Class<T> clazz) {
		return baseDao.findBySql(sql, page, rows, clazz);
	}

	public <T> List<T> findBySql(String sql, Map<String, Object> params, int page, int rows, Class<T> clazz) {
		return baseDao.findBySql(sql, params, page, rows, clazz);
	}

	public List<Map<String, Object>> findMapBySql(String sql) {
		return baseDao.findMapBySql(sql);
	}

	public List<Map<String, Object>> findMapBySql(String sql, Map<String, Object> params) {
		return baseDao.findMapBySql(sql, params);
	}

	public List<Map<String, Object>> findMapBySql(String sql, int page, int rows) {
		return baseDao.findMapBySql(sql, page, rows);
	}

	public List<Map<String, Object>> findMapBySql(String sql, Map<String, Object> params, int page, int rows) {
		return baseDao.findMapBySql(sql, params, page, rows);
	}

	public BigInteger countBySql(String sql) {
		return baseDao.countBySql(sql);
	}

	public Long countBySql(String sql, Map<String, Object> params) {
		return baseDao.countBySql(sql, params);
	}

	public int executeSql(String sql) {
		return baseDao.executeSql(sql);
	}

	public List<?> getListByCriteria(DetachedCriteria criteria, PageInfo page) {
		return baseDao.getListByCriteria(criteria, page);
	}

	public List<?> getListByCriteria(DetachedCriteria criteria, Integer startPage, Integer pageSize) {
		return baseDao.getListByCriteria(criteria, startPage,pageSize);
	}

	public int getCountByCriteria(DetachedCriteria criteria) {
		return baseDao.getCountByCriteria(criteria);
	}
	public List findByExample(Object example){
		return baseDao.findByExample(example);
	}

	@Override
	public List getMapByHql(String hql) {
		return baseDao.getMapByHql(hql);
	}
 
	@Override
	public List getPageMapByHql(String hql,Map<String, Object> params, int page, int rows){
		return baseDao.getPageMapByHql(hql, params, page, rows);
	}

	@Override
	public List getPageMapByHql(String hql, Map<String, Object> params) {
		return baseDao.getPageMapByHql(hql, params);
	}

	@Override
	public <T> List<T> find(String hql, Object... params) {
		return baseDao.find(hql, params);
	}

	@Override
	public List<Map> findMap(String hql, Object... params) {
		return baseDao.findMap(hql, params);
	}

}
