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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.JSPDropdownItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Péter Alius
 */
public abstract class ExportImportToolbarDisplayContext {

	public ExportImportToolbarDisplayContext(
		HttpServletRequest request, PageContext pageContext,
		LiferayPortletResponse portletResponse) {

		_request = request;

		_pageContext = pageContext;

		_portletResponse = portletResponse;

		Portlet portlet = portletResponse.getPortlet();

		_portletNamespace = PortalUtil.getPortletNamespace(
			portlet.getRootPortletId());
	}

	public abstract JSPDropdownItemList getActionItems();

	public DropdownGroupItem getFilterByList() {
		HttpServletRequest request = getRequest();

		DropdownGroupItem filterByGroup = new DropdownGroupItem();

		filterByGroup.setLabel(LanguageUtil.get(request, "filter"));

		DropdownItemList filterByList = new DropdownItemList();

		DropdownItem allItems = new DropdownItem();

		allItems.setHref(
			getRenderURL(), "groupId",
			String.valueOf(ParamUtil.getLong(request, "groupId")),
			"privateLayout",
			String.valueOf(ParamUtil.getBoolean(request, "privateLayout")),
			"displayStyle",
			ParamUtil.getString(request, "displayStyle", "descriptive"),
			"orderByCol", ParamUtil.getString(request, "orderByCol"),
			"orderByType", ParamUtil.getString(request, "orderByType"),
			"navigation", "all", "searchContainerId",
			ParamUtil.getString(request, "searchContainerId"));

		allItems.setLabel(LanguageUtil.get(request, "all"));

		DropdownItem completedItems = new DropdownItem();

		completedItems.setHref(
			getRenderURL(), "groupId",
			String.valueOf(ParamUtil.getLong(request, "groupId")),
			"privateLayout",
			String.valueOf(ParamUtil.getBoolean(request, "privateLayout")),
			"displayStyle",
			ParamUtil.getString(request, "displayStyle", "descriptive"),
			"orderByCol", ParamUtil.getString(request, "orderByCol"),
			"orderByType", ParamUtil.getString(request, "orderByType"),
			"navigation", "completed", "searchContainerId",
			ParamUtil.getString(request, "searchContainerId"));

		completedItems.setLabel(LanguageUtil.get(request, "completed"));

		DropdownItem inProgressItems = new DropdownItem();

		inProgressItems.setHref(
			getRenderURL(), "groupId",
			String.valueOf(ParamUtil.getLong(request, "groupId")),
			"privateLayout",
			String.valueOf(ParamUtil.getBoolean(request, "privateLayout")),
			"displayStyle",
			ParamUtil.getString(request, "displayStyle", "descriptive"),
			"orderByCol", ParamUtil.getString(request, "orderByCol"),
			"orderByType", ParamUtil.getString(request, "orderByType"),
			"navigation", "in-progress", "searchContainerId",
			ParamUtil.getString(request, "searchContainerId"));

		inProgressItems.setLabel(LanguageUtil.get(request, "in-progress"));

		filterByList.add(allItems);
		filterByList.add(completedItems);
		filterByList.add(inProgressItems);

		filterByGroup.setSeparator(true);
		filterByGroup.setDropdownItems(filterByList);

		return filterByGroup;
	}

	public DropdownItemList getFilterItems() {
		return new DropdownItemList() {
			{
				add(getFilterByList());
				add(getOrderByList());
			}
		};
	}

	public DropdownGroupItem getOrderByList() {
		HttpServletRequest request = getRequest();

		DropdownGroupItem orderByGroup = new DropdownGroupItem();

		orderByGroup.setLabel(LanguageUtil.get(request, "order-by"));

		DropdownItemList orderByList = new DropdownItemList();

		DropdownItem orderByName = new DropdownItem();

		orderByName.setHref(
			getRenderURL(), "groupId",
			String.valueOf(ParamUtil.getLong(request, "groupId")),
			"privateLayout",
			String.valueOf(ParamUtil.getBoolean(request, "privateLayout")),
			"displayStyle",
			ParamUtil.getString(request, "displayStyle", "descriptive"),
			"orderByCol", "name", "orderByType",
			ParamUtil.getString(request, "orderByType"), "navigation",
			ParamUtil.getString(request, "navigation", "all"),
			"searchContainerId",
			ParamUtil.getString(request, "searchContainerId"));

		orderByName.setLabel(LanguageUtil.get(request, "name"));

		DropdownItem orderByCreateDate = new DropdownItem();

		orderByCreateDate.setHref(
			getRenderURL(), "groupId",
			String.valueOf(ParamUtil.getLong(request, "groupId")),
			"privateLayout",
			String.valueOf(ParamUtil.getBoolean(request, "privateLayout")),
			"displayStyle",
			ParamUtil.getString(request, "displayStyle", "descriptive"),
			"orderByCol", "create-date", "orderByType",
			ParamUtil.getString(request, "orderByType"), "navigation",
			ParamUtil.getString(request, "navigation", "all"),
			"searchContainerId",
			ParamUtil.getString(request, "searchContainerId"));

		orderByCreateDate.setLabel(LanguageUtil.get(request, "create-date"));

		DropdownItem orderByCompletionDate = new DropdownItem();

		orderByCreateDate.setHref(
			getRenderURL(), "groupId",
			String.valueOf(ParamUtil.getLong(request, "groupId")),
			"privateLayout",
			String.valueOf(ParamUtil.getBoolean(request, "privateLayout")),
			"displayStyle",
			ParamUtil.getString(request, "displayStyle", "descriptive"),
			"orderByCol", "completion-date", "orderByType",
			ParamUtil.getString(request, "orderByType"), "navigation",
			ParamUtil.getString(request, "navigation", "all"),
			"searchContainerId",
			ParamUtil.getString(request, "searchContainerId"));

		orderByCompletionDate.setLabel(
			LanguageUtil.get(request, "completion-date"));

		orderByList.add(orderByName);
		orderByList.add(orderByCreateDate);
		orderByList.add(orderByCompletionDate);

		orderByGroup.setDropdownItems(orderByList);

		return orderByGroup;
	}

	public abstract String getSortingURL();

	protected String getDisplayStyle() {
		return ParamUtil.getString(_request, "displayStyle", "list");
	}

	protected DropdownItem getDropdownItem(String label, String href) {
		return _getDropdownItem(label, href, false, false, StringPool.BLANK);
	}

	protected DropdownItem getDropdownItem(
		String label, String href, boolean separator) {

		return _getDropdownItem(
			label, href, separator, false, StringPool.BLANK);
	}

	protected DropdownItem getDropdownItem(
		String label, String href, boolean separator, boolean quickAction,
		String icon) {

		return _getDropdownItem(label, href, separator, quickAction, icon);
	}

	protected DropdownItem getDropdownItem(
		String label, String href, boolean quickAction, String icon) {

		return _getDropdownItem(label, href, false, quickAction, icon);
	}

	protected PageContext getPageContext() {
		return _pageContext;
	}

	protected String getPortletNamespace() {
		return _portletNamespace;
	}

	protected PortletURL getRenderURL() {
		return _portletResponse.createRenderURL();
	}

	protected HttpServletRequest getRequest() {
		return _request;
	}

	private DropdownItem _getDropdownItem(
		String label, String href, boolean separator, boolean quickAction,
		String icon) {

		DropdownItem item = new DropdownItem();

		item.setLabel(label);
		item.setHref(href);

		item.setSeparator(separator);

		item.setQuickAction(quickAction);
		item.setIcon(icon);

		return item;
	}

	private final PageContext _pageContext;
	private final String _portletNamespace;
	private final LiferayPortletResponse _portletResponse;
	private final HttpServletRequest _request;

}