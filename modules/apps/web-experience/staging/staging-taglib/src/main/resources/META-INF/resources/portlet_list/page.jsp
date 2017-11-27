<%@ page import="java.util.Locale" %>
<%@ page import="com.liferay.portal.kernel.portlet.LiferayPortletResponse" %>
<%@ page import="com.liferay.portal.kernel.json.JSON" %>
<%@ page import="com.liferay.portal.kernel.json.JSONFactory" %><%--
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

<%@ include file="/portlet_list/init.jsp" %>

<%!
	public JSONArray getControlsData(
		PortletDataHandlerControl[] exportControls,
		Boolean childControl,
		Boolean showAllPortlets,
		HttpServletRequest request,
		ResourceBundle resourceBundle,
		ManifestSummary manifestSummary,
		String portletId,
		Boolean disableInputs,
		Map<String, String[]> parameterMap,
		Locale locale,
		String action,
		LiferayPortletResponse liferayPortletResponse
	) {
		JSONArray controlsData = JSONFactoryUtil.createJSONArray();

		for (int i = 0; i < exportControls.length; ++i) {
			if (exportControls[i] instanceof PortletDataHandlerBoolean) {
				Map<String, Object> controlData = new HashMap<>();
				PortletDataHandlerBoolean control = (PortletDataHandlerBoolean)exportControls[i];
				String controlLabel = LanguageUtil.get(request, resourceBundle, control.getControlLabel());
				String className = exportControls[i].getClassName();

				if (Validator.isNotNull(className) && (manifestSummary != null)) {
					StagedModelType stagedModelType = new StagedModelType(className, exportControls[i].getReferrerClassName());
					long modelAdditionCount = manifestSummary.getModelAdditionCount(stagedModelType);

					if (modelAdditionCount != 0) {
						controlLabel += modelAdditionCount > 0 ? " (" + modelAdditionCount + ")" : StringPool.BLANK;
						controlData.put("modelAdditionCount", modelAdditionCount);
					} else if (!showAllPortlets) {
						continue;
					}
				}

				if (!childControl) {
					controlData.put("root-control-id", liferayPortletResponse.getNamespace() + PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE + portletId);
				}

				PortletDataHandlerControl[] children = control.getChildren();

				String controlName = Validator.isNotNull(control.getNamespace()) ? control.getNamespacedControlName() : (control.getControlName() + StringPool.UNDERLINE + portletId);
				String controlInputName = controlName;

				boolean disabled = exportControls[i].isDisabled() || disableInputs;

				if (disabled) {
					controlInputName += "Display";

					Boolean hiddenControlValue = MapUtil.getBoolean(parameterMap, controlName, control.getDefaultState()) || MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.PORTLET_DATA_ALL);
					controlData.put("hiddenControlValue", hiddenControlValue);
				}

				Boolean isControlChecked = MapUtil.getBoolean(parameterMap, controlName, control.getDefaultState()) || MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.PORTLET_DATA_ALL);
				String controlHelpMessage = control.getHelpMessage(locale, action);

				if (children != null) {
					JSONArray childControlsData = getControlsData(children, true, showAllPortlets, request, resourceBundle, manifestSummary, portletId, disableInputs, parameterMap, locale, action, liferayPortletResponse);
					controlData.put("childControlsData", childControlsData);
				}

				controlData.put("name", controlLabel);
				controlData.put("isControlChecked", isControlChecked);
				controlData.put("controlHelpMessage", controlHelpMessage);
				controlData.put("controlInputName", controlInputName);
				controlData.put("type", "checkbox");

				controlsData.put(controlData);
			} else if (exportControls[i] instanceof PortletDataHandlerChoice) {
				Map<String, Object> controlData = new HashMap<>();
				PortletDataHandlerChoice control = (PortletDataHandlerChoice)exportControls[i];
				String[] choices = control.getChoices();

				for (int j = 0; j < choices.length; j++) {
					String choice = choices[j];
					String controlName = LanguageUtil.get(request, resourceBundle, choice);

					controlData.put("name", controlName);
					controlData.put("isChecked", MapUtil.getBoolean(parameterMap, control.getNamespacedControlName(), control.getDefaultChoiceIndex() == j));
					controlData.put("disabled", disableInputs);
					controlData.put("helpMessage", control.getHelpMessage(locale, action));
					controlData.put("label", choice);
					controlData.put("name", control.getNamespacedControlName());
					controlData.put("value", choices[j]);
					controlData.put("type", "radio");
				}
			}
		}
		return controlsData;
	}
%>

<ul class="portlet-list">

	<%
	DateRange dateRange = null;

	for (Portlet portlet : portlets) {
		boolean isExport = type.equals(Constants.EXPORT);
		boolean isPublish = !isExport && (liveGroup != null) && liveGroup.isStagedPortlet(portlet.getRootPortletId());

		if (!isExport && !isPublish) {
			continue;
		}

		PortletDataHandler portletDataHandler = portlet.getPortletDataHandlerInstance();

		String portletDataHandlerClassName = portletDataHandler.getClass().getName();

		if (portletDataHandlerClassNames.contains(portletDataHandlerClassName)) {
			continue;
		}

		portletDataHandlerClassNames.add(portletDataHandlerClassName);

		String portletTitle = PortalUtil.getPortletTitle(portlet, application, locale);

		PortletDataHandlerControl[] exportControls = portletDataHandler.getExportControls();
		PortletDataHandlerControl[] metadataControls = portletDataHandler.getExportMetadataControls();

		if (ArrayUtil.isEmpty(exportControls) && ArrayUtil.isEmpty(metadataControls)) {
			continue;
		}

		String portletId = portlet.getPortletId();
		String rootPortletId = portlet.getRootPortletId();

		if (useRequestValues) {
			dateRange = ExportImportDateUtil.getDateRange(renderRequest, exportGroupId, privateLayout, 0, portlet.getRootPortletId(), defaultRange);
		}
		else {
			dateRange = ExportImportDateUtil.getDateRange(exportImportConfiguration, portlet.getRootPortletId());
		}

		PortletDataContext portletDataContext = PortletDataContextFactoryUtil.createPreparePortletDataContext(company.getCompanyId(), exportGroupId, dateRange.getStartDate(), dateRange.getEndDate());

		portletDataHandler.prepareManifestSummary(portletDataContext);

		ManifestSummary manifestSummary = portletDataContext.getManifestSummary();

		long exportModelCount = portletDataHandler.getExportModelCount(manifestSummary);
		String exportModelCountText = exportModelCount > 0 ? String.valueOf(exportModelCount) : StringPool.BLANK;

		long modelDeletionCount = manifestSummary.getModelDeletionCount(portletDataHandler.getDeletionSystemEventStagedModelTypes());
		String modelDeletionCountText = modelDeletionCount > 0 ? String.valueOf(modelDeletionCount) + StringPool.SPACE + LanguageUtil.get(request, "deletions") : StringPool.BLANK;

		boolean displayCounts = (exportModelCount > 0) || (modelDeletionCount > 0);

		if (!isExport) {
			UnicodeProperties liveGroupTypeSettings = liveGroup.getTypeSettingsProperties();

			displayCounts = displayCounts && GetterUtil.getBoolean(liveGroupTypeSettings.getProperty(StagingUtil.getStagedPortletId(portlet.getRootPortletId())), portletDataHandler.isPublishToLiveByDefault());
		}

		if (!displayCounts && !showAllPortlets) {
			continue;
		}

		boolean showPortletDataInput = MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE + portlet.getPortletId(), portletDataHandler.isPublishToLiveByDefault()) || MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.PORTLET_DATA_ALL);

		JSONObject portletData = JSONFactoryUtil.createJSONObject();

		portletData.put("exportModelCountText", exportModelCountText);
		portletData.put("modelDeletionCountText", modelDeletionCountText);
		portletData.put("showPortletDataInput", Boolean.toString(showPortletDataInput));
		portletData.put("disableInputs", Boolean.toString(disableInputs));
		portletData.put("portletTitle", portletTitle);
		portletData.put("mainInputName", PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE + portlet.getPortletId());
		portletData.put("portletNamespace", PortalUtil.getPortletNamespace(portlet.getPortletId()));
		portletData.put("portletId", portlet.getPortletId());
		portletData.put("rootPortletId", portlet.getRootPortletId());
		portletData.put("exportControls", exportControls);
		portletData.put("modifyLinkText", LanguageUtil.get(request, "change"));

		String action = StringPool.BLANK;

		if (exportControls != null) {
			if (isExport) {
				action = Constants.EXPORT;
				portletData.put("action", Constants.EXPORT);
			} else if (isPublish) {
				action = Constants.PUBLISH;
				portletData.put("action", Constants.PUBLISH);
			}
		}

		if (Validator.isNotNull(portletId)) {
			PortletBag portletBag = PortletBagPool.get(portletId);
			ResourceBundle portletResourceBundle =
				portletBag.getResourceBundle(locale);

			if (portletResourceBundle != null) {
				resourceBundle = new AggregateResourceBundle(resourceBundle, portletResourceBundle);
			}
		}
	%>

	<%
		JSONArray controlsData = getControlsData(exportControls, false, showAllPortlets, request, resourceBundle, manifestSummary, portletId, disableInputs, parameterMap, locale, action, liferayPortletResponse);
		portletData.put("controlsData", controlsData);

		Map<String, Object> context = new HashMap<>();
		context.put("portletData", portletData);
	%>

	<soy:template-renderer
		context="<%= context %>"
		module="staging-metal-web/js/StagedPortletDisplay/StagedPortletDisplay"
		templateNamespace="StagedPortletDisplay.render"
	/>

		<li class="tree-item">
			<liferay-util:buffer var="badgeHTML">
				<span class="badge badge-info"><%= exportModelCount > 0 ? exportModelCount : StringPool.BLANK %></span>

				<span class="badge badge-warning deletions"><%= modelDeletionCount > 0 ? (modelDeletionCount + StringPool.SPACE + LanguageUtil.get(request, "deletions")) : StringPool.BLANK %></span>
			</liferay-util:buffer>

			<aui:input checked="<%= showPortletDataInput %>" disabled="<%= disableInputs %>" label="<%= portletTitle + badgeHTML %>" name="<%= PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE + portlet.getPortletId() %>" type="checkbox" />

			<div class="<%= disableInputs && showPortletDataInput ? StringPool.BLANK : "hide " %>" id="<portlet:namespace />content_<%= portlet.getPortletId() %>">
				<ul class="lfr-tree list-unstyled">
					<li class="tree-item">
						<aui:fieldset cssClass="portlet-type-data-section" label="<%= portletTitle %>">

							<%
							if (exportControls != null) {
								if (isExport) {
									request.setAttribute("render_controls.jsp-action", Constants.EXPORT);
									request.setAttribute("render_controls.jsp-childControl", false);
									request.setAttribute("render_controls.jsp-controls", exportControls);
									request.setAttribute("render_controls.jsp-disableInputs", disableInputs);
									request.setAttribute("render_controls.jsp-manifestSummary", manifestSummary);
									request.setAttribute("render_controls.jsp-parameterMap", parameterMap);
									request.setAttribute("render_controls.jsp-portletDisabled", !portletDataHandler.isPublishToLiveByDefault());
									request.setAttribute("render_controls.jsp-portletId", portlet.getPortletId());
							%>

									<aui:field-wrapper label='<%= ArrayUtil.isNotEmpty(metadataControls) ? "content" : StringPool.BLANK %>'>
										<ul class="lfr-tree list-unstyled">
											<liferay-util:include page="/portlet_list/render_controls.jsp" servletContext="<%= application %>" />
										</ul>
									</aui:field-wrapper>

								<%
								}
								else if (isPublish) {
									request.setAttribute("render_controls.jsp-action", Constants.PUBLISH);
									request.setAttribute("render_controls.jsp-childControl", false);
									request.setAttribute("render_controls.jsp-controls", exportControls);
									request.setAttribute("render_controls.jsp-disableInputs", disableInputs);
									request.setAttribute("render_controls.jsp-manifestSummary", manifestSummary);
									request.setAttribute("render_controls.jsp-parameterMap", parameterMap);
									request.setAttribute("render_controls.jsp-portletDisabled", !portletDataHandler.isPublishToLiveByDefault());
									request.setAttribute("render_controls.jsp-portletId", portlet.getPortletId());
								%>

									<aui:field-wrapper label='<%= ArrayUtil.isNotEmpty(metadataControls) ? "content" : StringPool.BLANK %>'>
										<ul class="lfr-tree list-unstyled">
											<liferay-util:include page="/portlet_list/render_controls.jsp" servletContext="<%= application %>" />
										</ul>
									</aui:field-wrapper>

							<%
								}
							}

							if (metadataControls != null) {
								for (PortletDataHandlerControl metadataControl : metadataControls) {
									if (displayedControls.contains(metadataControl.getControlName())) {
										continue;
									}

									displayedControls.add(metadataControl.getControlName());

									PortletDataHandlerBoolean control = (PortletDataHandlerBoolean)metadataControl;

									PortletDataHandlerControl[] childrenControls = control.getChildren();

									if (ArrayUtil.isNotEmpty(childrenControls)) {
										request.setAttribute("render_controls.jsp-controls", childrenControls);
										request.setAttribute("render_controls.jsp-portletId", portlet.getPortletId());
							%>

										<aui:field-wrapper label="content-metadata">
											<ul class="lfr-tree list-unstyled">
												<liferay-util:include page="/portlet_list/render_controls.jsp" servletContext="<%= application %>" />
											</ul>
										</aui:field-wrapper>

							<%
									}
								}
							}
							%>

						</aui:fieldset>
					</li>
				</ul>
			</div>

			<%
			if (!isExport) {
				portletId = portlet.getRootPortletId();
			}
			%>

			<ul class="hide" id="<portlet:namespace />showChangeContent_<%= portlet.getPortletId() %>">
				<li>
					<span class="selected-labels" id="<portlet:namespace />selectedContent_<%= portlet.getPortletId() %>"></span>

					<%
					Map<String, Object> data = new HashMap<String, Object>();

					data.put("portletid", portletId);
					data.put("portlettitle", portletTitle);
					%>

					<span <%= !disableInputs ? StringPool.BLANK : "class=\"hide\"" %>>
						<aui:a cssClass="content-link modify-link" data="<%= data %>" href="javascript:;" id='<%= "contentLink_" + portlet.getPortletId() %>' label="change" method="get" />
					</span>
				</li>
			</ul>

			<aui:script>
				Liferay.Util.toggleBoxes('<portlet:namespace /><%= PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE + portlet.getPortletId() %>', '<portlet:namespace />showChangeContent<%= StringPool.UNDERLINE + portlet.getPortletId() %>');
			</aui:script>
		</li>

	<%
	}
	%>

</ul>

<aui:fieldset cssClass="content-options" label='<%= type.equals(Constants.EXPORT) ? "for-each-of-the-selected-content-types,-export-their" : "for-each-of-the-selected-content-types,-publish-their" %>'>
	<span class="selected-labels" id="<portlet:namespace />selectedContentOptions"></span>

	<span <%= !disableInputs ? StringPool.BLANK : "class=\"hide\"" %>>
		<aui:a cssClass="modify-link" href="javascript:;" id="contentOptionsLink" label="change" method="get" />
	</span>

	<div class="hide" id="<portlet:namespace />contentOptions">
		<ul class="lfr-tree list-unstyled">
			<li class="tree-item">
				<aui:input disabled="<%= disableInputs %>" label="comments" name="<%= PortletDataHandlerKeys.COMMENTS %>" type="checkbox" value="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.COMMENTS, true) %>" />

				<aui:input disabled="<%= disableInputs %>" label="ratings" name="<%= PortletDataHandlerKeys.RATINGS %>" type="checkbox" value="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.RATINGS, true) %>" />
			</li>
		</ul>
	</div>
</aui:fieldset>

<aui:script require="staging-metal-web/js/RestHelper">
	function calllBack(xhrResponse) {
		const response = JSON.parse(xhrResponse.response);
		console.info(response);
		if (document.querySelector(".badge .badge-info")) document.querySelector(".badge .badge-info").innerText = response.id;
	}
	
	stagingMetalWebJsRestHelper
		.getRequest("https://jsonplaceholder.typicode.com/posts/1", {opt_callback: calllBack});

	stagingMetalWebJsRestHelper
		.request("https://jsonplaceholder.typicode.com/posts/1", "get", {opt_callback: calllBack});
</aui:script>