/*******************************************************************************
 * Copyright (c) 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *     Stephane Begaudeau - Scala migration.
 *******************************************************************************/
package org.eclipse.acceleo.module.sample.ui.common

import org.eclipse.core.resources.IContainer
import org.eclipse.emf.common.util.URI
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.acceleo.module.sample.main.GenerateScala
import org.eclipse.acceleo.engine.utils.AcceleoLaunchingUtil
import org.eclipse.emf.common.util.BasicMonitor
import java.util.ArrayList

/**
 * Main entry point of the generation module.
 * 
 * @param modelURI
 *            is the URI of the model.
 * @param targetFolder
 *            is the output folder
 * @param arguments
 *            are the other arguments
 * @throws IOException
 *             Thrown when the output cannot be saved.
 */
class GenerateAllScala(modelURI: URI, targetFolder: IContainer, arguments: List[Object]) {
	
	/**
	 * Launches the generation.
	 *
	 * @param monitor
	 *            This will be used to display progress information to the user.
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	def doGenerate(monitor: IProgressMonitor) {
		if (!targetFolder.getLocation.toFile.exists) {
			targetFolder.getLocation.toFile.mkdirs
		}
		monitor.subTask("Loading...")
		val generator = new GenerateScala(modelURI, targetFolder.getLocation.toFile, arguments)
		monitor.worked(1)
		val generationID = AcceleoLaunchingUtil.computeUIProjectID("org.eclipse.acceleo.module.sample", "org.eclipse.acceleo.module.sample.main.Generate", modelURI.toString(), targetFolder.getFullPath().toString(), new ArrayList[String]());
		generator.setGenerationID(generationID)
		generator.doGenerate(BasicMonitor.toMonitor(monitor))
	}
}