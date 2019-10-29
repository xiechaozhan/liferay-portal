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

package com.liferay.layout.service.base;

import com.liferay.layout.model.LayoutClassedModelUsage;
import com.liferay.layout.service.LayoutClassedModelUsageLocalService;
import com.liferay.layout.service.persistence.LayoutClassedModelUsagePersistence;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Reference;

/**
 * Provides the base implementation for the layout classed model usage local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.layout.service.impl.LayoutClassedModelUsageLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.layout.service.impl.LayoutClassedModelUsageLocalServiceImpl
 * @generated
 */
public abstract class LayoutClassedModelUsageLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements AopService, IdentifiableOSGiService,
			   LayoutClassedModelUsageLocalService {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>LayoutClassedModelUsageLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>com.liferay.layout.service.LayoutClassedModelUsageLocalServiceUtil</code>.
	 */

	/**
	 * Adds the layout classed model usage to the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutClassedModelUsage the layout classed model usage
	 * @return the layout classed model usage that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public LayoutClassedModelUsage addLayoutClassedModelUsage(
		LayoutClassedModelUsage layoutClassedModelUsage) {

		layoutClassedModelUsage.setNew(true);

		return layoutClassedModelUsagePersistence.update(
			layoutClassedModelUsage);
	}

	/**
	 * Creates a new layout classed model usage with the primary key. Does not add the layout classed model usage to the database.
	 *
	 * @param layoutClassedModelUsageId the primary key for the new layout classed model usage
	 * @return the new layout classed model usage
	 */
	@Override
	@Transactional(enabled = false)
	public LayoutClassedModelUsage createLayoutClassedModelUsage(
		long layoutClassedModelUsageId) {

		return layoutClassedModelUsagePersistence.create(
			layoutClassedModelUsageId);
	}

	/**
	 * Deletes the layout classed model usage with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutClassedModelUsageId the primary key of the layout classed model usage
	 * @return the layout classed model usage that was removed
	 * @throws PortalException if a layout classed model usage with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public LayoutClassedModelUsage deleteLayoutClassedModelUsage(
			long layoutClassedModelUsageId)
		throws PortalException {

		return layoutClassedModelUsagePersistence.remove(
			layoutClassedModelUsageId);
	}

	/**
	 * Deletes the layout classed model usage from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutClassedModelUsage the layout classed model usage
	 * @return the layout classed model usage that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public LayoutClassedModelUsage deleteLayoutClassedModelUsage(
		LayoutClassedModelUsage layoutClassedModelUsage) {

		return layoutClassedModelUsagePersistence.remove(
			layoutClassedModelUsage);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(
			LayoutClassedModelUsage.class, clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return layoutClassedModelUsagePersistence.findWithDynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.layout.model.impl.LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return layoutClassedModelUsagePersistence.findWithDynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.layout.model.impl.LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return layoutClassedModelUsagePersistence.findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return layoutClassedModelUsagePersistence.countWithDynamicQuery(
			dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection) {

		return layoutClassedModelUsagePersistence.countWithDynamicQuery(
			dynamicQuery, projection);
	}

	@Override
	public LayoutClassedModelUsage fetchLayoutClassedModelUsage(
		long layoutClassedModelUsageId) {

		return layoutClassedModelUsagePersistence.fetchByPrimaryKey(
			layoutClassedModelUsageId);
	}

	/**
	 * Returns the layout classed model usage matching the UUID and group.
	 *
	 * @param uuid the layout classed model usage's UUID
	 * @param groupId the primary key of the group
	 * @return the matching layout classed model usage, or <code>null</code> if a matching layout classed model usage could not be found
	 */
	@Override
	public LayoutClassedModelUsage fetchLayoutClassedModelUsageByUuidAndGroupId(
		String uuid, long groupId) {

		return layoutClassedModelUsagePersistence.fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the layout classed model usage with the primary key.
	 *
	 * @param layoutClassedModelUsageId the primary key of the layout classed model usage
	 * @return the layout classed model usage
	 * @throws PortalException if a layout classed model usage with the primary key could not be found
	 */
	@Override
	public LayoutClassedModelUsage getLayoutClassedModelUsage(
			long layoutClassedModelUsageId)
		throws PortalException {

		return layoutClassedModelUsagePersistence.findByPrimaryKey(
			layoutClassedModelUsageId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(
			layoutClassedModelUsageLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(LayoutClassedModelUsage.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"layoutClassedModelUsageId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(
			layoutClassedModelUsageLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(
			LayoutClassedModelUsage.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"layoutClassedModelUsageId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {

		actionableDynamicQuery.setBaseLocalService(
			layoutClassedModelUsageLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(LayoutClassedModelUsage.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"layoutClassedModelUsageId");
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		return layoutClassedModelUsageLocalService.
			deleteLayoutClassedModelUsage(
				(LayoutClassedModelUsage)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return layoutClassedModelUsagePersistence.findByPrimaryKey(
			primaryKeyObj);
	}

	/**
	 * Returns the layout classed model usage matching the UUID and group.
	 *
	 * @param uuid the layout classed model usage's UUID
	 * @param groupId the primary key of the group
	 * @return the matching layout classed model usage
	 * @throws PortalException if a matching layout classed model usage could not be found
	 */
	@Override
	public LayoutClassedModelUsage getLayoutClassedModelUsageByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		return layoutClassedModelUsagePersistence.findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns a range of all the layout classed model usages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.layout.model.impl.LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @return the range of layout classed model usages
	 */
	@Override
	public List<LayoutClassedModelUsage> getLayoutClassedModelUsages(
		int start, int end) {

		return layoutClassedModelUsagePersistence.findAll(start, end);
	}

	/**
	 * Returns the number of layout classed model usages.
	 *
	 * @return the number of layout classed model usages
	 */
	@Override
	public int getLayoutClassedModelUsagesCount() {
		return layoutClassedModelUsagePersistence.countAll();
	}

	/**
	 * Updates the layout classed model usage in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param layoutClassedModelUsage the layout classed model usage
	 * @return the layout classed model usage that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public LayoutClassedModelUsage updateLayoutClassedModelUsage(
		LayoutClassedModelUsage layoutClassedModelUsage) {

		return layoutClassedModelUsagePersistence.update(
			layoutClassedModelUsage);
	}

	@Override
	public Class<?>[] getAopInterfaces() {
		return new Class<?>[] {
			LayoutClassedModelUsageLocalService.class,
			IdentifiableOSGiService.class, PersistedModelLocalService.class
		};
	}

	@Override
	public void setAopProxy(Object aopProxy) {
		layoutClassedModelUsageLocalService =
			(LayoutClassedModelUsageLocalService)aopProxy;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return LayoutClassedModelUsageLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return LayoutClassedModelUsage.class;
	}

	protected String getModelClassName() {
		return LayoutClassedModelUsage.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource =
				layoutClassedModelUsagePersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(
				dataSource, sql);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	protected LayoutClassedModelUsageLocalService
		layoutClassedModelUsageLocalService;

	@Reference
	protected LayoutClassedModelUsagePersistence
		layoutClassedModelUsagePersistence;

	@Reference
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

}