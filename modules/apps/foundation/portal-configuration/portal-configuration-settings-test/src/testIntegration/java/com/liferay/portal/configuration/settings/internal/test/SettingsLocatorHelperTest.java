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

package com.liferay.portal.configuration.settings.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.metatype.util.ConfigurationScopedPidUtil;
import com.liferay.portal.configuration.settings.internal.constants.SettingsLocatorTestConstants;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsLocatorHelper;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class SettingsLocatorHelperTest extends BaseSettingsLocatorTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetCompanyScopedConfigurationSettings() throws Exception {
		long companyId = TestPropsValues.getCompanyId();

		Settings companySettings =
			_settingsLocatorHelper.getCompanyConfigurationBeanSettings(
				companyId, SettingsLocatorTestConstants.TEST_CONFIGURATION_PID,
				null);

		Assert.assertNull(companySettings);

		Settings systemSettings =
			_settingsLocatorHelper.getConfigurationBeanSettings(
				SettingsLocatorTestConstants.TEST_CONFIGURATION_PID);

		companySettings =
			_settingsLocatorHelper.getCompanyConfigurationBeanSettings(
				companyId, SettingsLocatorTestConstants.TEST_CONFIGURATION_PID,
				systemSettings);

		Assert.assertSame(systemSettings, companySettings);

		Assert.assertEquals(
			SettingsLocatorTestConstants.TEST_DEFAULT_VALUE,
			companySettings.getValue(
				SettingsLocatorTestConstants.TEST_KEY,
				SettingsLocatorTestConstants.TEST_DEFAULT_VALUE));

		String companyValue = saveScopedConfiguration(
			ExtendedObjectClassDefinition.Scope.COMPANY,
			String.valueOf(companyId));

		companySettings =
			_settingsLocatorHelper.getCompanyConfigurationBeanSettings(
				companyId, SettingsLocatorTestConstants.TEST_CONFIGURATION_PID,
				systemSettings);

		Assert.assertNotSame(systemSettings, companySettings);

		Assert.assertEquals(
			companyValue,
			companySettings.getValue(
				SettingsLocatorTestConstants.TEST_KEY,
				SettingsLocatorTestConstants.TEST_DEFAULT_VALUE));
	}

	@Test
	public void testGetGroupScopedConfigurationSettings() throws Exception {
		long groupId = TestPropsValues.getGroupId();

		Settings groupSettings =
			_settingsLocatorHelper.getGroupConfigurationBeanSettings(
				groupId, SettingsLocatorTestConstants.TEST_CONFIGURATION_PID,
				null);

		Assert.assertNull(groupSettings);

		Settings systemSettings =
			_settingsLocatorHelper.getConfigurationBeanSettings(
				SettingsLocatorTestConstants.TEST_CONFIGURATION_PID);

		groupSettings =
			_settingsLocatorHelper.getGroupConfigurationBeanSettings(
				groupId, SettingsLocatorTestConstants.TEST_CONFIGURATION_PID,
				systemSettings);

		Assert.assertSame(systemSettings, groupSettings);

		Assert.assertEquals(
			SettingsLocatorTestConstants.TEST_DEFAULT_VALUE,
			groupSettings.getValue(
				SettingsLocatorTestConstants.TEST_KEY,
				SettingsLocatorTestConstants.TEST_DEFAULT_VALUE));

		String groupValue = saveScopedConfiguration(
			ExtendedObjectClassDefinition.Scope.GROUP, String.valueOf(groupId));

		groupSettings =
			_settingsLocatorHelper.getGroupConfigurationBeanSettings(
				groupId, SettingsLocatorTestConstants.TEST_CONFIGURATION_PID,
				systemSettings);

		Assert.assertNotSame(systemSettings, groupSettings);

		Assert.assertEquals(
			groupValue,
			groupSettings.getValue(
				SettingsLocatorTestConstants.TEST_KEY,
				SettingsLocatorTestConstants.TEST_DEFAULT_VALUE));
	}

	@Test
	public void testGetPortletInstanceScopedConfigurationSettings()
		throws Exception {

		String portletId = RandomTestUtil.randomString();

		String portletInstanceKey =
			portletId + "_INSTANCE_" + RandomTestUtil.randomString();

		Settings portletInstanceSettings =
			_settingsLocatorHelper.getPortletInstanceConfigurationBeanSettings(
				portletInstanceKey,
				SettingsLocatorTestConstants.TEST_CONFIGURATION_PID, null);

		Assert.assertNull(portletInstanceSettings);

		Settings systemSettings =
			_settingsLocatorHelper.getConfigurationBeanSettings(
				SettingsLocatorTestConstants.TEST_CONFIGURATION_PID);

		portletInstanceSettings =
			_settingsLocatorHelper.getPortletInstanceConfigurationBeanSettings(
				portletInstanceKey,
				SettingsLocatorTestConstants.TEST_CONFIGURATION_PID,
				systemSettings);

		Assert.assertSame(systemSettings, portletInstanceSettings);

		Assert.assertEquals(
			SettingsLocatorTestConstants.TEST_DEFAULT_VALUE,
			portletInstanceSettings.getValue(
				SettingsLocatorTestConstants.TEST_KEY,
				SettingsLocatorTestConstants.TEST_DEFAULT_VALUE));

		String companyValue = saveScopedConfiguration(
			ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE,
			portletInstanceKey);

		portletInstanceSettings =
			_settingsLocatorHelper.getPortletInstanceConfigurationBeanSettings(
				portletInstanceKey,
				SettingsLocatorTestConstants.TEST_CONFIGURATION_PID,
				systemSettings);

		Assert.assertNotSame(systemSettings, portletInstanceSettings);

		Assert.assertEquals(
			companyValue,
			portletInstanceSettings.getValue(
				SettingsLocatorTestConstants.TEST_KEY,
				SettingsLocatorTestConstants.TEST_DEFAULT_VALUE));
	}

	@Test
	public void testGetSystemScopedConfigurationSettings() throws Exception {
		Settings settings = _settingsLocatorHelper.getConfigurationBeanSettings(
			SettingsLocatorTestConstants.TEST_CONFIGURATION_PID);

		Assert.assertEquals(
			SettingsLocatorTestConstants.TEST_DEFAULT_VALUE,
			settings.getValue(
				SettingsLocatorTestConstants.TEST_KEY,
				SettingsLocatorTestConstants.TEST_DEFAULT_VALUE));

		String systemValue = saveConfiguration();

		settings = _settingsLocatorHelper.getConfigurationBeanSettings(
			SettingsLocatorTestConstants.TEST_CONFIGURATION_PID);

		Assert.assertEquals(
			systemValue,
			settings.getValue(
				SettingsLocatorTestConstants.TEST_KEY,
				SettingsLocatorTestConstants.TEST_DEFAULT_VALUE));
	}

	protected String saveConfiguration() throws Exception {
		return saveConfiguration(
			SettingsLocatorTestConstants.TEST_CONFIGURATION_PID);
	}

	protected String saveScopedConfiguration(
			ExtendedObjectClassDefinition.Scope scope, String scopePrimKey)
		throws Exception {

		return saveConfiguration(
			ConfigurationScopedPidUtil.buildConfigurationScopedPid(
				SettingsLocatorTestConstants.TEST_CONFIGURATION_PID, scope,
				scopePrimKey));
	}

	@Inject
	private ConfigurationAdmin _configurationAdmin;

	@Inject
	private SettingsLocatorHelper _settingsLocatorHelper;

}