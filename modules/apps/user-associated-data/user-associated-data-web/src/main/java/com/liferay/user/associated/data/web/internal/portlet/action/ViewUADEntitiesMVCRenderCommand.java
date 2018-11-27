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

package com.liferay.user.associated.data.web.internal.portlet.action;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.user.associated.data.constants.UserAssociatedDataPortletKeys;
import com.liferay.user.associated.data.display.UADDisplay;
import com.liferay.user.associated.data.web.internal.constants.UADWebKeys;
import com.liferay.user.associated.data.web.internal.display.UADEntity;
import com.liferay.user.associated.data.web.internal.display.ViewUADEntitiesDisplay;
import com.liferay.user.associated.data.web.internal.registry.UADRegistry;
import com.liferay.user.associated.data.web.internal.util.SafeDisplayValueUtil;
import com.liferay.user.associated.data.web.internal.util.SelectedUserHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author William Newbury
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + UserAssociatedDataPortletKeys.USER_ASSOCIATED_DATA,
		"mvc.command.name=/view_uad_entities"
	},
	service = MVCRenderCommand.class
)
public class ViewUADEntitiesMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			User selectedUser = _selectedUserHelper.getSelectedUser(
				renderRequest);

			String applicationKey = ParamUtil.getString(
				renderRequest, "applicationKey");
			String uadRegistryKey = ParamUtil.getString(
				renderRequest, "uadRegistryKey");

			ViewUADEntitiesDisplay viewUADEntitiesDisplay =
				new ViewUADEntitiesDisplay();

			viewUADEntitiesDisplay.setApplicationKey(applicationKey);

			LiferayPortletResponse liferayPortletResponse =
				_portal.getLiferayPortletResponse(renderResponse);

			PortletURL currentURL = PortletURLUtil.getCurrent(
				renderRequest, renderResponse);

			viewUADEntitiesDisplay.setNavigationItems(
				_getNavigationItems(
					applicationKey, uadRegistryKey, currentURL,
					liferayPortletResponse));

			UADDisplay uadDisplay = _uadRegistry.getUADDisplay(uadRegistryKey);

			viewUADEntitiesDisplay.setSearchContainer(
				_getSearchContainer(
					renderRequest, currentURL, uadDisplay,
					selectedUser.getUserId(), liferayPortletResponse));
			viewUADEntitiesDisplay.setTypeName(
				uadDisplay.getTypeName(
					LocaleThreadLocal.getThemeDisplayLocale()));

			viewUADEntitiesDisplay.setUADRegistryKey(uadRegistryKey);

			renderRequest.setAttribute(
				UADWebKeys.INFO_PANEL_UAD_DISPLAY, uadDisplay);
			renderRequest.setAttribute(
				UADWebKeys.VIEW_UAD_ENTITIES_DISPLAY, viewUADEntitiesDisplay);
		}
		catch (Exception pe) {
			throw new PortletException(pe);
		}

		return "/view_uad_entities.jsp";
	}

	private <T> UADEntity<T> _constructUADEntity(
			T entity, UADDisplay<T> uadDisplay,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		UADEntity<T> uadEntity = new UADEntity(
			entity, uadDisplay.getPrimaryKey(entity),
			uadDisplay.getEditURL(
				entity, liferayPortletRequest, liferayPortletResponse));

		Map<String, Object> columnFieldValues = uadDisplay.getFieldValues(
			entity, uadDisplay.getColumnFieldNames());

		for (String columnFieldName : uadDisplay.getColumnFieldNames()) {
			uadEntity.addColumnEntry(
				columnFieldName,
				SafeDisplayValueUtil.get(
					columnFieldValues.get(columnFieldName)));
		}

		return uadEntity;
	}

	private List<NavigationItem> _getNavigationItems(
			String applicationKey, String uadRegistryKey, PortletURL currentURL,
			LiferayPortletResponse liferayPortletResponse)
		throws PortletException {

		NavigationItemList navigationItemList = new NavigationItemList();

		Locale locale = LocaleThreadLocal.getThemeDisplayLocale();
		PortletURL tabPortletURL = PortletURLUtil.clone(
			currentURL, liferayPortletResponse);

		Collection<UADDisplay> applicationUADDisplays =
			_uadRegistry.getApplicationUADDisplays(applicationKey);

		applicationUADDisplays.forEach(
			uadDisplay -> navigationItemList.add(
				navigationItem -> {
					Class<?> uadClass = uadDisplay.getTypeClass();

					navigationItem.setActive(
						uadRegistryKey.equals(uadClass.getName()));
					navigationItem.setHref(
						tabPortletURL, "uadRegistryKey", uadClass.getName());

					navigationItem.setLabel(uadDisplay.getTypeName(locale));
				}));

		return navigationItemList;
	}

	private SearchContainer<UADEntity> _getSearchContainer(
			RenderRequest renderRequest, PortletURL currentURL,
			UADDisplay uadDisplay, long selectedUserId,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		LiferayPortletRequest liferayPortletRequest =
			_portal.getLiferayPortletRequest(renderRequest);

		DisplayTerms displayTerms = new DisplayTerms(renderRequest);

		int cur = ParamUtil.getInteger(
			renderRequest, SearchContainer.DEFAULT_CUR_PARAM,
			SearchContainer.DEFAULT_CUR);

		SearchContainer<UADEntity> searchContainer = new SearchContainer<>(
			renderRequest, displayTerms, displayTerms,
			SearchContainer.DEFAULT_CUR_PARAM, cur,
			SearchContainer.DEFAULT_DELTA, currentURL, null,
			"no-entities-remain-of-this-type", null);

		searchContainer.setId("UADEntities");

		String orderByCol = ParamUtil.getString(
			renderRequest, SearchContainer.DEFAULT_ORDER_BY_COL_PARAM,
			"modifiedDate");

		searchContainer.setOrderByCol(orderByCol);

		String orderByType = ParamUtil.getString(
			renderRequest, SearchContainer.DEFAULT_ORDER_BY_TYPE_PARAM, "asc");

		searchContainer.setOrderByType(orderByType);

		Map<String, String> orderableHeaders = new LinkedHashMap<>();

		for (String orderByColumn : uadDisplay.getSortingFieldNames()) {
			orderableHeaders.put(
				TextFormatter.format(orderByColumn, TextFormatter.K),
				orderByColumn);
		}

		searchContainer.setOrderableHeaders(orderableHeaders);

		try {
			List entities = uadDisplay.search(
				selectedUserId, null, displayTerms.getKeywords(),
				searchContainer.getOrderByCol(),
				searchContainer.getOrderByType(), searchContainer.getStart(),
				searchContainer.getEnd());

			List<UADEntity> uadEntities = new ArrayList<>();

			for (Object entity : entities) {
				uadEntities.add(
					_constructUADEntity(
						entity, uadDisplay, liferayPortletRequest,
						liferayPortletResponse));
			}

			searchContainer.setResults(uadEntities);

			searchContainer.setTotal(
				(int)uadDisplay.searchCount(
					selectedUserId, null, displayTerms.getKeywords()));
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}

			searchContainer.setResults(Collections.emptyList());
			searchContainer.setTotal(0);
		}

		RowChecker rowChecker = new EmptyOnClickRowChecker(
			liferayPortletResponse);

		Class<?> uadClass = uadDisplay.getTypeClass();

		rowChecker.setRememberCheckBoxStateURLRegex(
			"uadRegistryKey=" + uadClass.getName());

		searchContainer.setRowChecker(rowChecker);

		return searchContainer;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ViewUADEntitiesMVCRenderCommand.class);

	@Reference
	private Portal _portal;

	@Reference
	private SelectedUserHelper _selectedUserHelper;

	@Reference
	private UADRegistry _uadRegistry;

}