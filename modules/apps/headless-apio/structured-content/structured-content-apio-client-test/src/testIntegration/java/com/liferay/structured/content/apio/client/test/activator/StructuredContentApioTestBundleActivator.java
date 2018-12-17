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

package com.liferay.structured.content.apio.client.test.activator;

import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerTracker;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestHelper;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.apio.test.util.AuthConfigurationTestUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.InputStream;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author Ruben Pulido
 */
public class StructuredContentApioTestBundleActivator
	implements BundleActivator {

	public static final String SITE_NAME =
		StructuredContentApioTestBundleActivator.class.getSimpleName() + "Site";

	public static final String TITLE_1_LOCALE_ES =
		StructuredContentApioTestBundleActivator.class.getSimpleName() +
			"Title1_es";

	public static final String TITLE_2_LOCALE_ES =
		StructuredContentApioTestBundleActivator.class.getSimpleName() +
			"Title2_es";

	public static final String TITLE_2_LOCALE_US =
		StructuredContentApioTestBundleActivator.class.getSimpleName() +
			"Title2_us";

	@Override
	public void start(BundleContext bundleContext) {
		_ddmFormDeserializerTrackerServiceReference =
			bundleContext.getServiceReference(DDMFormDeserializerTracker.class);

		_ddmFormDeserializerTracker = bundleContext.getService(
			_ddmFormDeserializerTrackerServiceReference);

		_groupLocalServiceServiceReference = bundleContext.getServiceReference(
			GroupLocalService.class);

		_groupLocalService = bundleContext.getService(
			_groupLocalServiceServiceReference);

		_journalArticleLocalServiceServiceReference =
			bundleContext.getServiceReference(JournalArticleLocalService.class);

		_journalArticleLocalService = bundleContext.getService(
			_journalArticleLocalServiceServiceReference);

		try {
			AuthConfigurationTestUtil.deployOAuthConfiguration(bundleContext);

			_prepareTest();
		}
		catch (Exception e) {
			_cleanUp();

			_log.error("Error found during startup ", e);
		}
	}

	@Override
	public void stop(BundleContext bundleContext) {
		_cleanUp();

		bundleContext.ungetService(_ddmFormDeserializerTrackerServiceReference);
		bundleContext.ungetService(_groupLocalServiceServiceReference);
		bundleContext.ungetService(_journalArticleLocalServiceServiceReference);
	}

	protected DDMForm deserialize(String content) {
		DDMFormDeserializer ddmFormDeserializer =
			_ddmFormDeserializerTracker.getDDMFormDeserializer("json");

		DDMFormDeserializerDeserializeRequest.Builder builder =
			DDMFormDeserializerDeserializeRequest.Builder.newBuilder(content);

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				ddmFormDeserializer.deserialize(builder.build());

		return ddmFormDeserializerDeserializeResponse.getDDMForm();
	}

	private void _cleanUp() {
		try {
			_groupLocalService.deleteGroup(_group);
		}
		catch (Exception e) {
			_log.error("Error found during cleanup ", e);
		}
	}

	private DDMStructure _getDDMStructure(Group group, String fileName)
		throws Exception {

		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				PortalUtil.getClassNameId(JournalArticle.class), group);

		return ddmStructureTestHelper.addStructure(
			PortalUtil.getClassNameId(JournalArticle.class), null,
			StructuredContentApioTestBundleActivator.SITE_NAME,
			deserialize(_read(fileName)), StorageType.JSON.getValue(),
			DDMStructureConstants.TYPE_DEFAULT);
	}

	private void _prepareTest() throws Exception {
		User user = UserTestUtil.getAdminUser(TestPropsValues.getCompanyId());
		Map<Locale, String> nameMap = Collections.singletonMap(
			LocaleUtil.getDefault(), SITE_NAME);

		_group = _groupLocalService.addGroup(
			user.getUserId(), GroupConstants.DEFAULT_PARENT_GROUP_ID, null, 0,
			GroupConstants.DEFAULT_LIVE_GROUP_ID, nameMap, nameMap,
			GroupConstants.TYPE_SITE_OPEN, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,
			StringPool.SLASH + FriendlyURLNormalizerUtil.normalize(SITE_NAME),
			true, true, ServiceContextTestUtil.getServiceContext());

		DDMStructure ddmStructure = _getDDMStructure(
			_group, "test-journal-all-fields-structure.json");

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class),
			TemplateConstants.LANG_TYPE_VM,
			_read("test-journal-all-fields-template.xsl"), LocaleUtil.US);

		Map<Locale, String> titleMap1 = new HashMap<Locale, String>() {
			{
				put(LocaleUtil.SPAIN, TITLE_1_LOCALE_ES);
			}
		};

		_journalArticleLocalService.addArticle(
			user.getUserId(), _group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, titleMap1, null,
			_read("test-journal-all-fields-content-1.xml"),
			ddmStructure.getStructureKey(), ddmTemplate.getTemplateKey(),
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), user.getUserId()));

		Map<Locale, String> titleMap2 = new HashMap<Locale, String>() {
			{
				put(LocaleUtil.getDefault(), TITLE_2_LOCALE_US);
				put(LocaleUtil.SPAIN, TITLE_2_LOCALE_ES);
			}
		};

		_journalArticleLocalService.addArticle(
			user.getUserId(), _group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, titleMap2, null,
			_read("test-journal-all-fields-content-2.xml"),
			ddmStructure.getStructureKey(), ddmTemplate.getTemplateKey(),
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), user.getUserId()));
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(
			"/com/liferay/structured/content/apio/client/test/activator/" +
				fileName);

		return StringUtil.read(inputStream);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StructuredContentApioTestBundleActivator.class);

	private DDMFormDeserializerTracker _ddmFormDeserializerTracker;
	private ServiceReference<DDMFormDeserializerTracker>
		_ddmFormDeserializerTrackerServiceReference;
	private Group _group;
	private GroupLocalService _groupLocalService;
	private ServiceReference<GroupLocalService>
		_groupLocalServiceServiceReference;
	private JournalArticleLocalService _journalArticleLocalService;
	private ServiceReference<JournalArticleLocalService>
		_journalArticleLocalServiceServiceReference;

}