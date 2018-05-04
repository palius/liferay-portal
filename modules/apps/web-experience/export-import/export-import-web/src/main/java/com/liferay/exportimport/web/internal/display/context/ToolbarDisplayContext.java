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

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.JSPDropdownItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Péter Alius
 */
public abstract class ToolbarDisplayContext {

	public ToolbarDisplayContext(
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

	protected String getDisplayStyle() {
		return ParamUtil.getString(_request, "displayStyle");
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

	protected LiferayPortletResponse getPortletResponse() {
		return _portletResponse;
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