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

package com.liferay.fragment.entry.processor.portlet;

import com.liferay.fragment.processor.PortletRegistry;
import com.liferay.fragment.processor.WhitelistEntry;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = WhitelistEntry.class)
public class PortletWhitelistEntry implements WhitelistEntry {

	@Override
	public String[] getAttributes() {
		return new String[0];
	}

	@Override
	public List<String> getHTMLTagNames() {
		List<String> htmlTagNames = new ArrayList<>();

		for (String alias : _portletRegistry.getPortletAliases()) {
			htmlTagNames.add("lfr-app-" + alias);
		}

		return htmlTagNames;
	}

	@Reference
	private PortletRegistry _portletRegistry;

}