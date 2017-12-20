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

package com.liferay.project.templates.soy.portlet.internal;

import com.liferay.project.templates.ProjectTemplateCustomizer;
import com.liferay.project.templates.ProjectTemplatesArgs;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.maven.archetype.ArchetypeGenerationRequest;
import org.apache.maven.archetype.ArchetypeGenerationResult;

/**
 * @author Gregory Amerson
 */
public class SoyPortletProjectTemplateCustomizer
	implements ProjectTemplateCustomizer {

	@Override
	public void onAfterGenerateProject(
		File destinationDir,
		ArchetypeGenerationResult archetypeGenerationResult) {

		if ((archetypeGenerationResult.getCause() == null) &&
			_projectTemplateArgs.isGradle()) {

			Path destinationDirPath = destinationDir.toPath();

			Path gulpfileJsPath = destinationDirPath.resolve("gulpfile.js");

			try {
				Files.deleteIfExists(gulpfileJsPath);
			}
			catch (IOException ioe) {
				archetypeGenerationResult.setCause(ioe);
			}
		}
	}

	@Override
	public void onBeforeGenerateProject(
		ProjectTemplatesArgs projectTemplatesArgs,
		ArchetypeGenerationRequest archetypeGenerationRequest) {

		_projectTemplateArgs = projectTemplatesArgs;
	}

	private ProjectTemplatesArgs _projectTemplateArgs;

}