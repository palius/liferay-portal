<%--
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
--%>

<%@ include file="/init.jsp" %>

<%@ page import="com.liferay.exportimport.changeset.constants.ChangesetPortletKeys" %><%@
page import="com.liferay.portal.kernel.model.ClassName" %><%@
page import="com.liferay.portal.kernel.portlet.PortletURLFactoryUtil" %><%@
page import="com.liferay.portal.kernel.service.ClassNameLocalServiceUtil" %>

<%
String className = GetterUtil.getString(request.getAttribute("liferay-export-import-changeset:publish-entity:className"));
long classNameId = GetterUtil.getLong(request.getAttribute("liferay-export-import-changeset:publish-entity:classNameId"));

ClassName classNameModel = null;

if (Validator.isNotNull(classNameId)) {
	classNameModel = ClassNameLocalServiceUtil.getClassName(classNameId);
}
else if (Validator.isNotNull(className)) {
	classNameModel = ClassNameLocalServiceUtil.getClassName(className);
}

if (classNameModel != null) {
	className = classNameModel.getClassName();
	classNameId = classNameModel.getClassNameId();
}

long exportEntityGroupId = GetterUtil.getLong(request.getAttribute("liferay-export-import-changeset:publish-entity:groupId"));
String uuid = GetterUtil.getString(request.getAttribute("liferay-export-import-changeset:publish-entity:uuid"));

boolean showMenuItem = false;

try {
	if ((group.isStagingGroup() || group.isStagedRemotely()) && group.isStagedPortlet(portletDisplay.getId())) {
		showMenuItem = true;
	}
}
catch (Exception e) {
}
%>