package com.wdl.service.impl.rbac;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.wdl.base.service.impl.BaseServiceImpl;
import com.wdl.util.EncoderHandler;
import com.wdl.dao.rbac.RoleDao;
import com.wdl.dao.rbac.UserDao;
import com.wdl.dao.rbac.UserRoleDao;
import com.wdl.entity.rbac.RoleEntity;
import com.wdl.entity.rbac.UserEntity;
import com.wdl.entity.rbac.UserRoleEntity;
import com.wdl.service.rbac.UserService;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl implements UserService {
	@Resource
	private UserDao userDao;
	@Resource
	private RoleDao roleDao;
	@Resource
	private UserRoleDao userRoleDao;

	@SuppressWarnings("rawtypes")
	@Override
	public Set<String> getRoleNameSetByUserId(Long userId) {
		String hql = "select r.name as roleName from RoleEntity r, UserRoleEntity ur where r.id=ur.roleId and ur.userId ="
				+ userId;
		List<Map> list = userRoleDao.findMap(hql);
		Set<String> roleNames = new HashSet<String>();
		for (Map map : list) {
			roleNames.add((String) map.get("roleName"));
		}
		return roleNames;
	}

	public List<RoleEntity> findUserRoleChacks(Long userId) {
		List<RoleEntity> rlist = new ArrayList<RoleEntity>();
		rlist = roleDao.findRoleIsDel();
		List<UserRoleEntity> uList = new ArrayList<UserRoleEntity>();
		uList = userRoleDao.findUserRoleByUserId(userId);
		for (UserRoleEntity userRoleEntity : uList) {
			for (RoleEntity roleEntity : rlist) {
				if (userRoleEntity.getRoleId().equals(roleEntity.getId())) {
					roleEntity.setIsChack(1); // 选中
				} else {
					if (roleEntity.getIsChack() != 1) {
						roleEntity.setIsChack(0); // 未选中
					}
				}
			}
		}
		return rlist;
	}

	public String saveOrUpdateUser(UserEntity userEntity) {
		if (!checkExist(userEntity.getId(), userEntity.getLoginName())) {
			UserEntity entity = null;
			if (userEntity.getId() != null) {
				entity = userDao.get(UserEntity.class, userEntity.getId());
			} else {
				entity = new UserEntity();
				entity.setCountMistake(0);
				entity.setPassWord(EncoderHandler.encode("SHA1", "Experian123."));
			}
			BeanUtils.copyProperties(userEntity, entity, new String[] { "version", "createDateTime", "countMistake",
					"countMistakeTime", "validataCode", "outDate", "passWord" });
			entity.setUpdateDateTime(new Date());
			saveOrUpdate(entity);
			return "01";
		} else {
			return "02";// 用户重复
		}
	}

	private boolean checkExist(Long id, String loginName) {
		Map<String, Object> param = new HashMap<String, Object>();

		StringBuffer hql = new StringBuffer("select count(1) from UserEntity u where 1=1 ");
		if (loginName != null) {
			hql.append(" and u.loginName=:loginName");
			param.put("loginName", loginName);
		}
		if (id != null) {
			hql.append(" and u.id!=:id");
			param.put("id", id);
		}
		Long count = count(hql.toString(), param);
		if (count != null && count > 0) {
			return true;
		}
		return false;
	}

	@Override
	public UserEntity findUserByloginName(String loginName) {

		return userDao.findUserByloginName(loginName);
	}

	@Override
	public UserEntity chackEmain(String validataCode, String id) {
		return userDao.chackEmain(validataCode, id);
	}

	@Override
	public List<UserEntity> findByIsDel(Integer del) {
		return userDao.findByIsDel(del);
	}

	@Override
	public UserEntity findUserLoginAndPasword(String LoginName, String password) {
		return userDao.findUserLoginAndPasword(LoginName, password);
	}
}