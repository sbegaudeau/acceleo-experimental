/*******************************************************************************
 * Copyright (c) 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *     Stephane Begaudeau - Scala migration
 *******************************************************************************/
package org.eclipse.acceleo.module.sample.ui.popupMenus

import org.eclipse.ui.actions.ActionDelegate
import org.eclipse.jface.viewers.ISelection
import org.eclipse.jface.action.IAction
import org.eclipse.core.resources.IFile
import org.eclipse.jface.viewers.IStructuredSelection
import org.eclipse.jface.operation.IRunnableWithProgress
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.CoreException
import org.eclipse.core.runtime.Status
import org.eclipse.core.runtime.IStatus
import org.eclipse.acceleo.module.sample.ui.ActivatorScala
import org.eclipse.emf.common.util.URI
import java.io.IOException
import java.lang.reflect.InvocationTargetException
import org.eclipse.ui.PlatformUI
import java.util.ArrayList
import org.eclipse.acceleo.module.sample.ui.common.GenerateAllScala
import org.eclipse.core.resources.IResource
import org.eclipse.core.runtime.NullProgressMonitor

/**
 * Scala Generate code generation.
 */
class AcceleoGenerateScalaAction extends ActionDelegate {
	/**
	 * Selected model files.
	 */
	protected var files = List[IFile]()
	
	/**{@inheritDoc}
	 *
	 * @see org.eclipse.ui.actions.ActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	override def selectionChanged(action: IAction, selection: ISelection): Unit = {
		selection match {			
			case structuredSelection: IStructuredSelection =>
				for(obj <- structuredSelection.toArray) {
					obj match {
						case iFile: IFile => files = iFile :: files 
					}
				}
		}
	}
	
	/**{@inheritDoc}
	 *
	 * @see org.eclipse.ui.actions.ActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	override def run(action: IAction) {
		if (files != null) {
			val operation = new IRunnableWithProgress() {
				override def run(progressMonitor: IProgressMonitor) {
					try {
						for(model: IFile <-files) {
							val modelURI: URI = URI.createPlatformResourceURI(model.getFullPath().toString(), true);
							val target = model.getProject.getFolder("src-gen")
							val generator = new GenerateAllScala(modelURI, target, getArguments)
							generator.doGenerate(progressMonitor)
						}
					} catch {
						case e: CoreException => 
							val status: IStatus = new Status(IStatus.ERROR, ActivatorScala.PLUGIN_ID, e.getMessage, e)
							ActivatorScala.getDefault.getLog.log(status)
						case e : IOException =>
							val status: IStatus = new Status(IStatus.ERROR, ActivatorScala.PLUGIN_ID, e.getMessage, e)
							ActivatorScala.getDefault.getLog.log(status)
					}
				}
			}

			try {
				PlatformUI.getWorkbench.getProgressService.run(false, true, operation)
				for(model: IFile <-files) {
					model.getProject.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor)
				}
			} catch {
				case e: InvocationTargetException =>
					val status: IStatus = new Status(IStatus.ERROR, ActivatorScala.PLUGIN_ID, e.getMessage, e)
					ActivatorScala.getDefault.getLog.log(status)
				case e: InterruptedException =>
					val status: IStatus = new Status(IStatus.ERROR, ActivatorScala.PLUGIN_ID, e.getMessage, e)
					ActivatorScala.getDefault.getLog.log(status)
			}
		}
	}
	
	/**
	 * Computes the arguments of the generator.
	 * 
	 * @return the arguments
	 */
	protected def getArguments: List[Object] = {
		List[Object]()
	}
}