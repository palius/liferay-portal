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

package com.liferay.staging.taglib.servlet.taglib;

import aQute.bnd.annotation.ProviderType;

import com.liferay.petra.string.StringPool;
import com.liferay.staging.taglib.internal.servlet.ServletContextUtil;
import com.liferay.staging.taglib.servlet.taglib.base.BaseCssTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Peter Borkuti
 */
@ProviderType
public class PopoverTag extends BaseCssTag {

	@Override
	public String getTagNameForCssPath() {
		return "popover";
	}

	public void setId(String id) {
		_id = id;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setPosition(String position) {
		switch (position) {
			case "top":
			case "left":
			case "bottom":
				_position = position;
			default:
				_position = "right";
		}
	}

	public void setText(String text) {
		_text = text;
	}

	public void setTitle(String title) {
		_title = title;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_id = StringPool.BLANK;
		_text = StringPool.BLANK;
		_title = StringPool.BLANK;
		_position = StringPool.BLANK;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute("liferay-staging:popover:id", _id);
		request.setAttribute("liferay-staging:popover:text", _text);
		request.setAttribute("liferay-staging:popover:title", _title);
		request.setAttribute("liferay-staging:popover:position", _position);
	}

	private static final String _PAGE = "/popover/page.jsp";

	private String _id = StringPool.BLANK;
	private String _text = StringPool.BLANK;
	private String _title = StringPool.BLANK;
	private String _position = StringPool.BLANK;

}