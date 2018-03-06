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

package com.liferay.wiki.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.wiki.engine.WikiEngine;
import com.liferay.wiki.engine.impl.WikiEngineRenderer;
import com.liferay.wiki.exception.PageContentException;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.model.WikiPageDisplay;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;

import java.io.Serializable;

import java.util.Collections;
import java.util.Map;
import java.util.function.Supplier;

import javax.portlet.PortletURL;

import org.apache.commons.lang.time.StopWatch;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = WikiCacheHelper.class)
public class WikiCacheHelper {

	public void clearCache() {
		_portalCache.removeAll();
	}

	/**
	 * @deprecated As of 1.7.0, replaced by {@link #clearCache()}
	 */
	@Deprecated
	public void clearCache(long nodeId) {
		_portalCache.removeAll();
	}

	/**
	 * @deprecated As of 1.7.0, replaced by {@link #clearCache()}
	 */
	@Deprecated
	public void clearCache(long nodeId, String title) {
		_portalCache.removeAll();
	}

	public WikiPageDisplay getDisplay(
			long nodeId, String title, PortletURL viewPageURL,
			Supplier<PortletURL> editPageURLSupplier,
			String attachmentURLPrefix)
		throws PortalException {

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		String key = _encodeKey(nodeId, title, viewPageURL.toString());

		WikiPageDisplay pageDisplay = (WikiPageDisplay)_portalCache.get(key);

		if (pageDisplay == null) {
			pageDisplay = WikiPageLocalServiceUtil.getPageDisplay(
				nodeId, title, viewPageURL, editPageURLSupplier.get(),
				attachmentURLPrefix);

			_portalCache.put(key, pageDisplay);
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"getDisplay for {", String.valueOf(nodeId), ", ", title,
					", ", String.valueOf(viewPageURL), ", ",
					String.valueOf(editPageURLSupplier.get()), "} takes ",
					String.valueOf(stopWatch.getTime()), " ms"));
		}

		return pageDisplay;
	}

	public Map<String, Boolean> getOutgoingLinks(
			WikiPage page, WikiEngineRenderer wikiEngineRenderer)
		throws PageContentException {

		String key = _encodeKey(
			page.getNodeId(), page.getTitle(), _OUTGOING_LINKS);

		Map<String, Boolean> links = (Map<String, Boolean>)_portalCache.get(
			key);

		if (links == null) {
			WikiEngine wikiEngine = wikiEngineRenderer.fetchWikiEngine(
				page.getFormat());

			if (wikiEngine != null) {
				links = wikiEngine.getOutgoingLinks(page);
			}
			else {
				links = Collections.emptyMap();
			}

			_portalCache.put(key, (Serializable)links);
		}

		return links;
	}

	@Activate
	protected void activate() {
		_portalCache =
			(PortalCache<String, Serializable>)_multiVMPool.getPortalCache(
				_CACHE_NAME);

		_portalCache.removeAll();
	}

	@Deactivate
	protected void deactivate() {
		_portalCache.removeAll();

		_multiVMPool.removePortalCache(_CACHE_NAME);
	}

	@Reference(unbind = "-")
	protected void setMultiVMPool(MultiVMPool multiVMPool) {
		_multiVMPool = multiVMPool;
	}

	private String _encodeKey(long nodeId, String title, String postfix) {
		StringBundler sb = new StringBundler(5);

		sb.append(StringUtil.toHexString(nodeId));
		sb.append(StringPool.POUND);
		sb.append(title);

		if (postfix != null) {
			sb.append(StringPool.POUND);
			sb.append(postfix);
		}

		return sb.toString();
	}

	private static final String _CACHE_NAME = WikiPageDisplay.class.getName();

	private static final String _OUTGOING_LINKS = "OUTGOING_LINKS";

	private static final Log _log = LogFactoryUtil.getLog(
		WikiCacheHelper.class);

	private MultiVMPool _multiVMPool;
	private PortalCache<String, Serializable> _portalCache;

}