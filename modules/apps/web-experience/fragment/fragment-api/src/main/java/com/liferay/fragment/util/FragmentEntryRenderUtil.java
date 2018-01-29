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

package com.liferay.fragment.util;

import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringBundler;

import java.util.Optional;

/**
 * @author Pablo Molina
 */
public class FragmentEntryRenderUtil {

	public static String renderFragmentEntry(FragmentEntry fragmentEntry) {
		return renderFragmentEntry(
			fragmentEntry.getFragmentEntryId(), 0, fragmentEntry.getCss(),
			fragmentEntry.getHtml(), fragmentEntry.getJs());
	}

	public static String renderFragmentEntry(
		long fragmentEntryId, long fragmentEntryInstanceId) {

		FragmentEntry fragmentEntry =
			FragmentEntryLocalServiceUtil.fetchFragmentEntry(fragmentEntryId);

		return renderFragmentEntry(
			fragmentEntryId, fragmentEntryInstanceId, fragmentEntry.getCss(),
			fragmentEntry.getHtml(), fragmentEntry.getJs());
	}

	public static String renderFragmentEntry(
		long fragmentEntryId, long fragmentEntryInstanceId, String css,
		String html, String js) {

		try {
			StringBundler sb = new StringBundler(14);

			sb.append("<div class=\"fragment-");
			sb.append(fragmentEntryId);
			sb.append("\" id=\"fragment-");
			sb.append(fragmentEntryId);
			sb.append("-");
			sb.append(fragmentEntryInstanceId);
			sb.append("\">");
			sb.append("<style>");
			sb.append(css);
			sb.append("</style>");

			Optional<ServiceContext> serviceContextOptional =
				Optional.ofNullable(
					ServiceContextThreadLocal.getServiceContext());

			ServiceContext serviceContext = serviceContextOptional.orElse(
				new ServiceContext());

			String sanitizedHTML = SanitizerUtil.sanitize(
				serviceContext.getCompanyId(), serviceContext.getScopeGroupId(),
				serviceContext.getUserId(), FragmentEntry.class.getName(),
				fragmentEntryId, ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL,
				html, null);

			sb.append(sanitizedHTML);

			sb.append("<script>(function(){");
			sb.append(js);
			sb.append(";}());</script></div>");

			return sb.toString();
		}
		catch (SanitizerException se) {
			throw new SystemException(se);
		}
	}

	public static String renderFragmentEntry(
		long fragmentEntryId, String css, String html, String js) {

		return renderFragmentEntry(fragmentEntryId, 0, css, html, js);
	}

}