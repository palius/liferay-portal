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

package com.liferay.exportimport.web.internal.display.context;

import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.JSPCreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.JSPDropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portlet.layoutsadmin.display.context.GroupDisplayContextHelper;

import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Péter Alius
 */
public class ImportExportImportToolbarDisplayContext
	extends ExportImportToolbarDisplayContext {

	public ImportExportImportToolbarDisplayContext(
		HttpServletRequest request, PageContext pageContext,
		LiferayPortletResponse portletResponse) {

		super(request, pageContext, portletResponse);
	}

	public JSPDropdownItemList getActionItems() {
		JSPDropdownItemList itemList = new JSPDropdownItemList(
			getPageContext());

		itemList.add(
			getDropdownItem(
				LanguageUtil.get(getRequest(), "delete"),
				"javascript:" + getPortletNamespace() + "deleteEntries();"));

		return itemList;
	}

	public JSPCreationMenu getCreationMenu() {
		HttpServletRequest request = getRequest();

		JSPCreationMenu creationMenu = new JSPCreationMenu(getPageContext());

		GroupDisplayContextHelper groupDisplayContextHelper =
			new GroupDisplayContextHelper(request);

		creationMenu.addPrimaryDropdownItem(
			mainAddButton -> {
				mainAddButton.setHref(
					getRenderURL(), "mvcPath",
					"/import/new_import/import_layouts.jsp", Constants.CMD,
					Constants.IMPORT, "groupId",
					String.valueOf(ParamUtil.getLong(request, "groupId")),
					"privateLayout",
					String.valueOf(ParamUtil.getBoolean(request, "privateLayout")),
					"validate", true);

				mainAddButton.setLabel(LanguageUtil.get(request, "import"));
			});

		List<ExportImportConfiguration> exportImportConfigurations =
			ExportImportConfigurationLocalServiceUtil.getExportImportConfigurations(
				groupDisplayContextHelper.getLiveGroupId(),
				ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT);

		return creationMenu;
	}

	public String getSearchContainerId() {
		return ParamUtil.getString(getRequest(), "searchContainerId");
	}

	public String getSortingOrder() {
		return ParamUtil.getString(getRequest(), "orderByType", "asc");
	}

	public String getSortingURL() {
		HttpServletRequest request = getRequest();

		PortletURL sortingURL = getRenderURL();

		sortingURL.setParameter(
			"groupId", String.valueOf(ParamUtil.getLong(request, "groupId")));
		sortingURL.setParameter(
			"privateLayout",
			String.valueOf(ParamUtil.getBoolean(request, "privateLayout")));
		sortingURL.setParameter(
			"displayStyle",
			ParamUtil.getString(request, "displayStyle", "descriptive"));
		sortingURL.setParameter(
			"orderByCol", ParamUtil.getString(request, "orderByCol"));
		sortingURL.setParameter(
			"orderByType",
			Objects.equals(
				ParamUtil.getString(request, "orderByType"),
				"asc") ? "desc" : "asc");
		sortingURL.setParameter(
			"navigation", ParamUtil.getString(request, "navigation", "all"));
		sortingURL.setParameter(
			"searchContainerId",
			ParamUtil.getString(request, "searchContainerId"));

		return sortingURL.toString();
	}

	public List<ViewTypeItem> getViewTypes() {
		return new ViewTypeItemList(getRenderURL(), getDisplayStyle()) {
			{
				addListViewTypeItem();
				addTableViewTypeItem();
			}
		};
	}

}