/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.layout.page.template.service.base;

import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.layout.page.template.service.persistence.LayoutPageTemplateCollectionPersistence;
import com.liferay.layout.page.template.service.persistence.LayoutPageTemplateEntryPersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.service.BaseServiceImpl;
import com.liferay.portal.kernel.service.persistence.ClassNamePersistence;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the layout page template entry remote service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.layout.page.template.service.impl.LayoutPageTemplateEntryServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.layout.page.template.service.impl.LayoutPageTemplateEntryServiceImpl
 * @see com.liferay.layout.page.template.service.LayoutPageTemplateEntryServiceUtil
 * @generated
 */
public abstract class LayoutPageTemplateEntryServiceBaseImpl
	extends BaseServiceImpl implements LayoutPageTemplateEntryService,
		IdentifiableOSGiService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.layout.page.template.service.LayoutPageTemplateEntryServiceUtil} to access the layout page template entry remote service.
	 */

	/**
	 * Returns the layout page template entry local service.
	 *
	 * @return the layout page template entry local service
	 */
	public com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService getLayoutPageTemplateEntryLocalService() {
		return layoutPageTemplateEntryLocalService;
	}

	/**
	 * Sets the layout page template entry local service.
	 *
	 * @param layoutPageTemplateEntryLocalService the layout page template entry local service
	 */
	public void setLayoutPageTemplateEntryLocalService(
		com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService layoutPageTemplateEntryLocalService) {
		this.layoutPageTemplateEntryLocalService = layoutPageTemplateEntryLocalService;
	}

	/**
	 * Returns the layout page template entry remote service.
	 *
	 * @return the layout page template entry remote service
	 */
	public LayoutPageTemplateEntryService getLayoutPageTemplateEntryService() {
		return layoutPageTemplateEntryService;
	}

	/**
	 * Sets the layout page template entry remote service.
	 *
	 * @param layoutPageTemplateEntryService the layout page template entry remote service
	 */
	public void setLayoutPageTemplateEntryService(
		LayoutPageTemplateEntryService layoutPageTemplateEntryService) {
		this.layoutPageTemplateEntryService = layoutPageTemplateEntryService;
	}

	/**
	 * Returns the layout page template entry persistence.
	 *
	 * @return the layout page template entry persistence
	 */
	public LayoutPageTemplateEntryPersistence getLayoutPageTemplateEntryPersistence() {
		return layoutPageTemplateEntryPersistence;
	}

	/**
	 * Sets the layout page template entry persistence.
	 *
	 * @param layoutPageTemplateEntryPersistence the layout page template entry persistence
	 */
	public void setLayoutPageTemplateEntryPersistence(
		LayoutPageTemplateEntryPersistence layoutPageTemplateEntryPersistence) {
		this.layoutPageTemplateEntryPersistence = layoutPageTemplateEntryPersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.kernel.service.CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.kernel.service.CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the layout page template collection local service.
	 *
	 * @return the layout page template collection local service
	 */
	public com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService getLayoutPageTemplateCollectionLocalService() {
		return layoutPageTemplateCollectionLocalService;
	}

	/**
	 * Sets the layout page template collection local service.
	 *
	 * @param layoutPageTemplateCollectionLocalService the layout page template collection local service
	 */
	public void setLayoutPageTemplateCollectionLocalService(
		com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService layoutPageTemplateCollectionLocalService) {
		this.layoutPageTemplateCollectionLocalService = layoutPageTemplateCollectionLocalService;
	}

	/**
	 * Returns the layout page template collection remote service.
	 *
	 * @return the layout page template collection remote service
	 */
	public com.liferay.layout.page.template.service.LayoutPageTemplateCollectionService getLayoutPageTemplateCollectionService() {
		return layoutPageTemplateCollectionService;
	}

	/**
	 * Sets the layout page template collection remote service.
	 *
	 * @param layoutPageTemplateCollectionService the layout page template collection remote service
	 */
	public void setLayoutPageTemplateCollectionService(
		com.liferay.layout.page.template.service.LayoutPageTemplateCollectionService layoutPageTemplateCollectionService) {
		this.layoutPageTemplateCollectionService = layoutPageTemplateCollectionService;
	}

	/**
	 * Returns the layout page template collection persistence.
	 *
	 * @return the layout page template collection persistence
	 */
	public LayoutPageTemplateCollectionPersistence getLayoutPageTemplateCollectionPersistence() {
		return layoutPageTemplateCollectionPersistence;
	}

	/**
	 * Sets the layout page template collection persistence.
	 *
	 * @param layoutPageTemplateCollectionPersistence the layout page template collection persistence
	 */
	public void setLayoutPageTemplateCollectionPersistence(
		LayoutPageTemplateCollectionPersistence layoutPageTemplateCollectionPersistence) {
		this.layoutPageTemplateCollectionPersistence = layoutPageTemplateCollectionPersistence;
	}

	/**
	 * Returns the class name local service.
	 *
	 * @return the class name local service
	 */
	public com.liferay.portal.kernel.service.ClassNameLocalService getClassNameLocalService() {
		return classNameLocalService;
	}

	/**
	 * Sets the class name local service.
	 *
	 * @param classNameLocalService the class name local service
	 */
	public void setClassNameLocalService(
		com.liferay.portal.kernel.service.ClassNameLocalService classNameLocalService) {
		this.classNameLocalService = classNameLocalService;
	}

	/**
	 * Returns the class name remote service.
	 *
	 * @return the class name remote service
	 */
	public com.liferay.portal.kernel.service.ClassNameService getClassNameService() {
		return classNameService;
	}

	/**
	 * Sets the class name remote service.
	 *
	 * @param classNameService the class name remote service
	 */
	public void setClassNameService(
		com.liferay.portal.kernel.service.ClassNameService classNameService) {
		this.classNameService = classNameService;
	}

	/**
	 * Returns the class name persistence.
	 *
	 * @return the class name persistence
	 */
	public ClassNamePersistence getClassNamePersistence() {
		return classNamePersistence;
	}

	/**
	 * Sets the class name persistence.
	 *
	 * @param classNamePersistence the class name persistence
	 */
	public void setClassNamePersistence(
		ClassNamePersistence classNamePersistence) {
		this.classNamePersistence = classNamePersistence;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.kernel.service.ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.kernel.service.UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.kernel.service.UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user remote service.
	 *
	 * @return the user remote service
	 */
	public com.liferay.portal.kernel.service.UserService getUserService() {
		return userService;
	}

	/**
	 * Sets the user remote service.
	 *
	 * @param userService the user remote service
	 */
	public void setUserService(
		com.liferay.portal.kernel.service.UserService userService) {
		this.userService = userService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public void afterPropertiesSet() {
	}

	public void destroy() {
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return LayoutPageTemplateEntryService.class.getName();
	}

	protected Class<?> getModelClass() {
		return LayoutPageTemplateEntry.class;
	}

	protected String getModelClassName() {
		return LayoutPageTemplateEntry.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = layoutPageTemplateEntryPersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService.class)
	protected com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService layoutPageTemplateEntryLocalService;
	@BeanReference(type = LayoutPageTemplateEntryService.class)
	protected LayoutPageTemplateEntryService layoutPageTemplateEntryService;
	@BeanReference(type = LayoutPageTemplateEntryPersistence.class)
	protected LayoutPageTemplateEntryPersistence layoutPageTemplateEntryPersistence;
	@ServiceReference(type = com.liferay.counter.kernel.service.CounterLocalService.class)
	protected com.liferay.counter.kernel.service.CounterLocalService counterLocalService;
	@BeanReference(type = com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService.class)
	protected com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService layoutPageTemplateCollectionLocalService;
	@BeanReference(type = com.liferay.layout.page.template.service.LayoutPageTemplateCollectionService.class)
	protected com.liferay.layout.page.template.service.LayoutPageTemplateCollectionService layoutPageTemplateCollectionService;
	@BeanReference(type = LayoutPageTemplateCollectionPersistence.class)
	protected LayoutPageTemplateCollectionPersistence layoutPageTemplateCollectionPersistence;
	@ServiceReference(type = com.liferay.portal.kernel.service.ClassNameLocalService.class)
	protected com.liferay.portal.kernel.service.ClassNameLocalService classNameLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.ClassNameService.class)
	protected com.liferay.portal.kernel.service.ClassNameService classNameService;
	@ServiceReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@ServiceReference(type = com.liferay.portal.kernel.service.ResourceLocalService.class)
	protected com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.UserLocalService.class)
	protected com.liferay.portal.kernel.service.UserLocalService userLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.UserService.class)
	protected com.liferay.portal.kernel.service.UserService userService;
	@ServiceReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
}