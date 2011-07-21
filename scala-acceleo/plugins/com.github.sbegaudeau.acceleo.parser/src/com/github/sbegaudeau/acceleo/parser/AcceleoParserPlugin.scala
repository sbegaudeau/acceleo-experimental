/*******************************************************************************
 * Copyright (c) 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *     Stephane Begaudeau - conversion in Scala
 *******************************************************************************/
package com.github.sbegaudeau.acceleo.parser

import org.eclipse.core.runtime.Plugin
import org.eclipse.core.runtime.CoreException
import org.eclipse.core.runtime.IStatus
import org.eclipse.core.runtime.Status
import org.osgi.framework.BundleContext

/**
 * The Acceleo Parser Plugin activator.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 0.1
 */
object AcceleoParserPlugin {
	val PLUGIN_ID: String = "com.github.sbegaudeau.acceleo.parser";
	private var plugin: AcceleoParserPlugin = null;
	
	def getDefault(): AcceleoParserPlugin = {
		return plugin
	}
	
	def log(e: Option[Exception], isBlocker: Boolean): Unit = {
		e match {
			case Some(x) => 
				if (getDefault == null) {
					x.printStackTrace()
				} else {
					x match {
						case ex: CoreException => log(Some(ex.getStatus()))
					case ex: NullPointerException =>
						var severity = IStatus.WARNING
						if (isBlocker) {
							severity = IStatus.ERROR
						}
						val message = AcceleoParserMessages.getString("AcceleoCompilerPlugin.ElementNotFound")
						log(Some(new Status(severity, PLUGIN_ID, severity, message, ex)))
					case _ => 
						var severity = IStatus.WARNING
						if (isBlocker) {
							severity = IStatus.ERROR
						}
						log(Some(new Status(severity, PLUGIN_ID, severity, x.getMessage, x)))
					}
				}
			case None => throw new NullPointerException(AcceleoParserMessages.getString("AcceleoCompilerPlugin.LogNullException"))
		}
	}
	
	def log(status: Option[IStatus]): Unit = {
		status match {
			case Some(s) =>
				if (getDefault != null) {
					getDefault.getLog.log(s)
				} else {
					System.err.println(s.getMessage)
					s.getException.printStackTrace
				}
			case None => throw new NullPointerException(AcceleoParserMessages.getString("AcceleoCompilerPlugin.LogNullStatus"));
		}
	}
	
	def log(message: String, isBlocker: Boolean): Unit = {
		if (getDefault == null) {
			System.err.println(message)
		} else {
			var severity = IStatus.WARNING
			if (isBlocker) {
				severity = IStatus.ERROR
			}
			var errorMessage = message;
			if (errorMessage == null || "".equals(errorMessage)) {
				errorMessage = AcceleoParserMessages.getString("AcceleoCompilerPlugin.UnexpectedError")
			}
			log(Some(new Status(severity, PLUGIN_ID, errorMessage)))
		}
	}
}

/**
 * This class controls the plug-in life cycle when used inside of Eclipse.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 0.1
 */
class AcceleoParserPlugin extends Plugin {
	
	override def start(context: BundleContext) = {
		AcceleoParserPlugin.plugin = this
	}
	
	override def stop(context: BundleContext) = {
		AcceleoParserPlugin.plugin = null
	}
}