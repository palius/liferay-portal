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

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownGroupItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.JSPCreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.JSPDropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Péter Alius
 */
public class ExportImportToolbarDisplayContext extends ToolbarDisplayContext {

	public ExportImportToolbarDisplayContext(
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
		return new JSPCreationMenu(getPageContext()) {
			{
				addPrimaryDropdownItem(
					dropdownItem -> {
						dropdownItem.setHref(
							getPortletResponse().createRenderURL(), "mvcPath",
							"/export/add_button.jsp");
						dropdownItem.setLabel("add-tag");
					});
			}
		};
	}

	public DropdownItemList getFilterItems() {
		DropdownItemList ddil = new DropdownItemList();

		DropdownGroupItem ddgi = getOrderByList();

		ddil.add(ddgi);

		return ddil;
	}

	public DropdownGroupItem getOrderByList() {
		DropdownGroupItem ddgi = new DropdownGroupItem();

		ddgi.setLabel("order-by");

		DropdownItemList ddil2 = new DropdownItemList();

		DropdownItem ddi = new DropdownItem();

		ddi.setHref(getPortletResponse().createRenderURL(),
			"orderByCol", "name", "orderByType",
			ParamUtil.getString(getRequest(), "orderByType", "asc"));
		ddi.setLabel("name");

		DropdownItem ddi2 = new DropdownItem();

		ddi2.setHref(getPortletResponse().createRenderURL(),
			"orderByCol", "create-date", "orderByType",
			ParamUtil.getString(getRequest(), "orderByType", "asc"));
		ddi2.setLabel("create-date");

		DropdownItem ddi3 = new DropdownItem();

		ddi3.setHref(getPortletResponse().createRenderURL(),
			"orderByCol", "completion-date", "orderByType",
			ParamUtil.getString(getRequest(), "orderByType", "asc"));
		ddi3.setLabel("completion-date");

		ddil2.add(ddi);
		ddil2.add(ddi2);
		ddil2.add(ddi3);

		ddgi.setDropdownItems(ddil2);

		return ddgi;
	}

	public String getSearchContainerId() {
		return ParamUtil.getString(getRequest(), "searchContainerId");
	}

	public String getSortingOrder() {
		return ParamUtil.getString(getRequest(), "orderByType", "asc");
	}

	public String getSortingURL() {
		PortletURL sortingURL = getPortletResponse().createRenderURL();

		String mvcRenderCommandName = ParamUtil.getString(
			getRequest(), "mvcRenderCommandName");

		long groupId = ParamUtil.getLong(getRequest(), "groupId");
		boolean privateLayout = ParamUtil.getBoolean(
			getRequest(), "privateLayout");
		String displayStyle = ParamUtil.getString(
			getRequest(), "displayStyle", "descriptive");
		String orderByCol = ParamUtil.getString(getRequest(), "orderByCol");
		String orderByType = ParamUtil.getString(getRequest(), "orderByType");
		String navigation = ParamUtil.getString(
			getRequest(), "navigation", "all");
		String searchContainerId = ParamUtil.getString(
			getRequest(), "searchContainerId");

		sortingURL.setParameter("mvcRenderCommandName", mvcRenderCommandName);
		sortingURL.setParameter("groupId", String.valueOf(groupId));
		sortingURL.setParameter("privateLayout", String.valueOf(privateLayout));
		sortingURL.setParameter("displayStyle", displayStyle);
		sortingURL.setParameter("navigation", navigation);
		sortingURL.setParameter("orderByCol", orderByCol);
		sortingURL.setParameter(
			"searchContainerId", String.valueOf(searchContainerId));

		sortingURL.setParameter(
			"orderByType", Objects.equals(orderByType, "asc") ? "desc" : "asc");

		return sortingURL.toString();
	}

	public List<ViewTypeItem> getViewTypes() {
		PortletURL renderURL = getPortletResponse().createRenderURL();

		return new ViewTypeItemList(renderURL, getDisplayStyle()) {
			{
				addListViewTypeItem(); addTableViewTypeItem();
			}
		};
	}

}