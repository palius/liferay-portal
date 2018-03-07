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

package com.liferay.asset.display.contributor;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author Jürgen Kappler
 */
public abstract class BaseAssetDisplayContributor<T>
	implements AssetDisplayContributor {

	@Override
	public Set<AssetDisplayField> getAssetEntryFields(Locale locale) {
		Set<AssetDisplayField> assetDisplayFields = new LinkedHashSet<>();

		String[] assetEntryModelFields = getAssetEntryModelFields();

		ResourceBundle resourceBundle = resourceBundleLoader.loadResourceBundle(
			locale);

		for (String assetEntryModelField : assetEntryModelFields) {
			assetDisplayFields.add(
				new AssetDisplayField(
					assetEntryModelField,
					LanguageUtil.get(resourceBundle, assetEntryModelField)));
		}

		return assetDisplayFields;
	}

	@Override
	public Map<String, Object> getParameterMap(
			AssetEntry assetEntry, Locale locale)
		throws PortalException {

		Map<String, Object> parameterMap = _getDefaultParameterMap(
			assetEntry, locale);

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.
				getAssetRendererFactoryByClassNameId(
					assetEntry.getClassNameId());

		AssetRenderer<T> assetRenderer = assetRendererFactory.getAssetRenderer(
			assetEntry.getClassPK());

		T assetObject = assetRenderer.getAssetObject();

		String[] assetEntryModelFields = getAssetEntryModelFields();

		for (String assetEntryModelField : assetEntryModelFields) {
			parameterMap.put(
				assetEntryModelField,
				getFieldValue(assetObject, assetEntryModelField, locale));
		}

		return parameterMap;
	}

	protected abstract String[] getAssetEntryModelFields();

	protected abstract Object getFieldValue(
		T assetEntryObject, String field, Locale locale);

	protected ResourceBundleLoader resourceBundleLoader;

	private Map<String, Object> _getDefaultParameterMap(
		AssetEntry assetEntry, Locale locale) {

		Map<String, Object> parameterMap = new HashMap<>();

		parameterMap.put("description", assetEntry.getDescription(locale));
		parameterMap.put("summary", assetEntry.getSummary(locale));
		parameterMap.put("title", assetEntry.getTitle(locale));

		return parameterMap;
	}

}