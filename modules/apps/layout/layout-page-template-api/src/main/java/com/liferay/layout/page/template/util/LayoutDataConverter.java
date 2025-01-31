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

package com.liferay.layout.page.template.util;

import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.layout.util.structure.ColumnLayoutStructureItem;
import com.liferay.layout.util.structure.ContainerStyledLayoutStructureItem;
import com.liferay.layout.util.structure.DropZoneLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.layout.util.structure.RowStyledLayoutStructureItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Rubén Pulido
 */
public class LayoutDataConverter {

	public static final int LATEST_VERSION = 1;

	public static String convert(String data) {
		if (Validator.isNull(data)) {
			return StringPool.BLANK;
		}

		JSONObject inputDataJSONObject = null;

		try {
			inputDataJSONObject = JSONFactoryUtil.createJSONObject(data);
		}
		catch (JSONException jsonException) {
			throw new RuntimeException(jsonException);
		}

		if (isLatestVersion(inputDataJSONObject)) {
			return data;
		}

		JSONArray structureJSONArray = inputDataJSONObject.getJSONArray(
			"structure");

		if (structureJSONArray == null) {
			return data;
		}

		LayoutStructure layoutStructure = new LayoutStructure();

		LayoutStructureItem rootLayoutStructureItem =
			layoutStructure.addRootLayoutStructureItem();

		for (int i = 0; i < structureJSONArray.length(); i++) {
			JSONObject inputRowJSONObject = structureJSONArray.getJSONObject(i);

			JSONArray columnsJSONArray = inputRowJSONObject.getJSONArray(
				"columns");

			if (inputRowJSONObject.getInt("type") ==
					FragmentConstants.TYPE_COMPONENT) {

				ContainerStyledLayoutStructureItem
					containerStyledLayoutStructureItem =
						(ContainerStyledLayoutStructureItem)
							layoutStructure.addContainerLayoutStructureItem(
								rootLayoutStructureItem.getItemId(), i);

				JSONObject inputRowConfigJSONObject =
					inputRowJSONObject.getJSONObject("config");

				if (inputRowConfigJSONObject != null) {
					containerStyledLayoutStructureItem.
						setBackgroundColorCssClass(
							inputRowConfigJSONObject.getString(
								"backgroundColorCssClass"));
					containerStyledLayoutStructureItem.
						setBackgroundImageJSONObject(
							_getBackgroundImageJSONObject(
								inputRowConfigJSONObject));
					containerStyledLayoutStructureItem.setPaddingBottom(
						inputRowConfigJSONObject.getInt("paddingVertical", 0));
					containerStyledLayoutStructureItem.setPaddingLeft(
						inputRowConfigJSONObject.getInt(
							"paddingHorizontal", 0));
					containerStyledLayoutStructureItem.setPaddingRight(
						inputRowConfigJSONObject.getInt(
							"paddingHorizontal", 0));
					containerStyledLayoutStructureItem.setPaddingTop(
						inputRowConfigJSONObject.getInt("paddingVertical", 0));
					containerStyledLayoutStructureItem.setWidthType(
						inputRowConfigJSONObject.getString(
							"widthType", "fluid"));
				}

				RowStyledLayoutStructureItem rowStyledLayoutStructureItem =
					(RowStyledLayoutStructureItem)
						layoutStructure.addRowLayoutStructureItem(
							containerStyledLayoutStructureItem.getItemId(), 0,
							columnsJSONArray.length());

				if (inputRowConfigJSONObject != null) {
					boolean columnSpacing = inputRowConfigJSONObject.getBoolean(
						"columnSpacing", true);

					rowStyledLayoutStructureItem.setGutters(columnSpacing);
				}

				for (int j = 0; j < columnsJSONArray.length(); j++) {
					JSONObject inputColumnJSONObject =
						columnsJSONArray.getJSONObject(j);

					ColumnLayoutStructureItem columnLayoutStructureItem =
						(ColumnLayoutStructureItem)
							layoutStructure.addColumnLayoutStructureItem(
								rowStyledLayoutStructureItem.getItemId(), j);

					columnLayoutStructureItem.setSize(
						inputColumnJSONObject.getInt("size"));

					JSONArray fragmentEntryLinksJSONArray =
						inputColumnJSONObject.getJSONArray(
							"fragmentEntryLinkIds");

					for (int k = 0; k < fragmentEntryLinksJSONArray.length();
						 k++) {

						_addFragmentEntryLink(
							fragmentEntryLinksJSONArray.getString(k),
							inputDataJSONObject, layoutStructure,
							columnLayoutStructureItem.getItemId(), k);
					}
				}
			}
			else {
				JSONObject columnJSONObject = columnsJSONArray.getJSONObject(0);

				JSONArray fragmentEntryLinkIdsJSONArray =
					columnJSONObject.getJSONArray("fragmentEntryLinkIds");

				_addFragmentEntryLink(
					fragmentEntryLinkIdsJSONArray.getString(0),
					inputDataJSONObject, layoutStructure,
					rootLayoutStructureItem.getItemId(), i);
			}
		}

		JSONObject layoutStructureJSONObject = layoutStructure.toJSONObject();

		return layoutStructureJSONObject.toString();
	}

	public static boolean isLatestVersion(JSONObject dataJSONObject) {
		int version = dataJSONObject.getInt("version");

		if (version == LATEST_VERSION) {
			return true;
		}

		return false;
	}

	private static void _addFragmentEntryLink(
		String fragmentEntryLinkId, JSONObject inputDataJSONObject,
		LayoutStructure layoutStructure, String parentItemId, int position) {

		if (fragmentEntryLinkId.equals(
				LayoutDataItemTypeConstants.TYPE_DROP_ZONE)) {

			DropZoneLayoutStructureItem dropZoneLayoutStructureItem =
				(DropZoneLayoutStructureItem)
					layoutStructure.addDropZoneLayoutStructureItem(
						parentItemId, position);

			dropZoneLayoutStructureItem.setAllowNewFragmentEntries(
				inputDataJSONObject.getBoolean(
					"allowNewFragmentEntries", true));

			JSONArray fragmentEntryKeysJSONArray =
				inputDataJSONObject.getJSONArray("fragmentEntryKeys");

			dropZoneLayoutStructureItem.setFragmentEntryKeys(
				JSONUtil.toStringList(fragmentEntryKeysJSONArray));

			return;
		}

		layoutStructure.addFragmentLayoutStructureItem(
			GetterUtil.getLong(fragmentEntryLinkId), parentItemId, position);
	}

	private static JSONObject _getBackgroundImageJSONObject(
		JSONObject inputRowConfigJSONObject) {

		if (inputRowConfigJSONObject.isNull("backgroundImage")) {
			return JSONFactoryUtil.createJSONObject();
		}

		Object backgroundImage = inputRowConfigJSONObject.get(
			"backgroundImage");

		if (backgroundImage instanceof JSONObject) {
			return (JSONObject)backgroundImage;
		}

		return JSONUtil.put("url", backgroundImage);
	}

}