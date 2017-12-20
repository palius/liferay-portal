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

package com.liferay.project.templates.service.builder.internal;

import com.liferay.project.templates.ProjectTemplateCustomizer;
import com.liferay.project.templates.ProjectTemplatesArgs;
import com.liferay.project.templates.WorkspaceUtil;

import java.io.File;
import java.io.IOException;

import java.nio.file.Path;

import java.util.Properties;

import org.apache.maven.archetype.ArchetypeGenerationRequest;
import org.apache.maven.archetype.ArchetypeGenerationResult;

/**
 * @author Gregory Amerson
 */
public class ServiceBuilderProjectTemplateCustomizer
	implements ProjectTemplateCustomizer {

	@Override
	public void onAfterGenerateProject(
		File destinationDir,
		ArchetypeGenerationResult archetypeGenerationResult) {
	}

	@Override
	public void onBeforeGenerateProject(
		ProjectTemplatesArgs projectTemplatesArgs,
		ArchetypeGenerationRequest archetypeGenerationRequest) {

		String artifactId = archetypeGenerationRequest.getArtifactId();

		String apiPath = ":" + artifactId + "-api";

		File destinationDir = new File(
			archetypeGenerationRequest.getOutputDirectory());

		try {
			File workspaceDir = WorkspaceUtil.getWorkspaceDir(destinationDir);

			if (workspaceDir != null) {
				Path destinationDirPath = destinationDir.toPath();
				Path workspaceDirPath = workspaceDir.toPath();

				destinationDirPath = destinationDirPath.toAbsolutePath();
				workspaceDirPath = workspaceDirPath.toAbsolutePath();

				String relativePath = String.valueOf(
					workspaceDirPath.relativize(destinationDirPath));

				relativePath = relativePath.replace(File.separatorChar, ':');

				apiPath = ":" + relativePath + ":" + artifactId + apiPath;
			}
		}
		catch (IOException ioe) {
		}

		Properties properties = archetypeGenerationRequest.getProperties();

		properties.put("apiPath", apiPath);
	}

}