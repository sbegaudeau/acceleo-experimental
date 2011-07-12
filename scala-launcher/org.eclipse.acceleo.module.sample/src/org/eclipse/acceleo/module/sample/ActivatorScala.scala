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
package org.eclipse.acceleo.module.sample

import org.eclipse.core.runtime.Plugin
import org.osgi.framework.BundleContext

/**
 * The companion object of the activator.
 */
object ActivatorScala {
	/**
	 * The plug-in ID.
	 */
	val PLUGIN_ID = "org.eclipse.acceleo.module.sample";
	
	/**
	 * The shared instance.
	 */
	private var plugin: ActivatorScala = null
	
	/**
     * Returns the shared instance.
     *
     * @return the shared instance
     */
	def getDefault(): ActivatorScala = plugin
}

/**
 * The activator class controls the plug-in lifecycle.
 **/
class ActivatorScala extends Plugin {
	
	/**
     * {@inheritDoc}
     *
     * @see org.eclipse.core.runtime.Plugin#start(org.osgi.framework.BundleContext)
     */
	override def start(context: BundleContext) {
		super.start(context)
		ActivatorScala.plugin = this
	}
	
	/**
     * {@inheritDoc}
     *
     * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
     */
	override def stop(context: BundleContext) {
		ActivatorScala.plugin = null
		super.stop(context)
	}
}