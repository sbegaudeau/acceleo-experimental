/*******************************************************************************
 * Copyright (c) 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *     Stephane Begaudeau - Scala transformation
 *******************************************************************************/
package org.eclipse.acceleo.module.sample.main

import org.eclipse.acceleo.engine.service.AbstractAcceleoGenerator
import java.util.ArrayList
import java.io.File
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.acceleo.common.utils.ModelUtils
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.common.util.BasicMonitor
import java.io.IOException
import org.eclipse.emf.common.util.Monitor
import org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener
import org.eclipse.acceleo.engine.generation.strategy.IAcceleoGenerationStrategy
import org.eclipse.emf.ecore.resource.ResourceSet

/**
 * Companion object of the entry point of the generation.
 **/
object GenerateScala {
	/**
	 * The name of the module.
	 **/
	val MODULE_FILE_NAME = "/org/eclipse/acceleo/module/sample/main/generate";
	
	/**
	 * The name of the templates that are used for the generation.
	 */
	val TEMPLATE_NAMES = Array("generateElement");
	
	/**
     * This can be used to launch the generation from a standalone application.
     * 
     * @param args
     *            Arguments of the generation.
     */
	def main(args: Array[String]) = {
		try {
			if (args.length < 2) {
				Console.out.println("Arguments not valid : {model, folder}.")
			} else {
				val modelURI = URI.createFileURI(args(0))
				val folder = new File(args(1))
				val arguments = Nil
				
				val generator: GenerateScala = new GenerateScala(modelURI, folder, arguments)
				for (i <- 2 to args.length) {
                    generator.addPropertiesFile(args(i));
                }
                
                generator.doGenerate(new BasicMonitor());
			}
		} catch {
			case e: IOException => e.printStackTrace
		}
	}
}


/**
 * This is the entry point of the generation.
 * @author Stephane Begaudeau
 */
class GenerateScala extends AbstractAcceleoGenerator {
	
	/**
	 * The list of properties files.
	 */
	private val propertiesFilesList = new ArrayList[String]()
	
	/**
	 * This allows clients to instantiates a generator with all required information.
	 * 
	 * @param model
	 *            We'll iterate over the content of this element to find Objects matching the first parameter
	 *            of the template we need to call.
	 * @param targetFolder
	 *            This will be used as the output folder for this generation : it will be the base path
	 *            against which all file block URLs will be resolved.
	 * @param arguments
	 *            If the template which will be called requires more than one argument taken from the model,
	 *            pass them here.
	 * @throws IOException
	 *             This can be thrown in two scenarios : the module cannot be found, or it cannot be loaded.
	 */
	def this(model: EObject, targetFolder: File, arguments: List[Object]) {
		this()
		initialize(model, targetFolder, java.util.Arrays.asList(arguments.toArray: _*))
	}
	
	/**
     * This allows clients to instantiates a generator with all required information.
     * 
     * @param modelURI
     *            URI where the model on which this generator will be used is located.
     * @param targetFolder
     *            This will be used as the output folder for this generation : it will be the base path
     *            against which all file block URLs will be resolved.
     * @param arguments
     *            If the template which will be called requires more than one argument taken from the model,
     *            pass them here.
     * @throws IOException
     *             This can be thrown in three scenarios : the module cannot be found, it cannot be loaded, or
     *             the model cannot be loaded.
     */
	def this(modelURI: URI, targetFolder: File, arguments: List[Object]) {
		this(ModelUtils.load(modelURI, new ResourceSetImpl()), targetFolder, arguments)
	}
	
	/**
     * Launches the generation described by this instance.
     * 
     * @param monitor
     *            This will be used to display progress information to the user.
     * @throws IOException
     *             This will be thrown if any of the output files cannot be saved to disk.
     */
	override def doGenerate(monitor: Monitor) {
		super.doGenerate(monitor)
	}
	
	/**
     * If this generator needs to listen to text generation events, listeners can be returned from here.
     * 
     * @return List of listeners that are to be notified when text is generated through this launch.
     */
	override def getGenerationListeners(): java.util.List[IAcceleoTextGenerationListener] = {
		super.getGenerationListeners()
	}
	
	/**
     * If you need to change the way files are generated, this is your entry point.
     * <p>
     * The default is {@link org.eclipse.acceleo.engine.generation.strategy.DefaultStrategy}; it generates
     * files on the fly. If you only need to preview the results, return a new
     * {@link org.eclipse.acceleo.engine.generation.strategy.PreviewStrategy}. Both of these aren't aware of
     * the running Eclipse and can be used standalone.
     * </p>
     * <p>
     * If you need the file generation to be aware of the workspace (A typical example is when you wanna
     * override files that are under clear case or any other VCS that could forbid the overriding), then
     * return a new {@link org.eclipse.acceleo.engine.generation.strategy.WorkspaceAwareStrategy}.
     * <b>Note</b>, however, that this <b>cannot</b> be used standalone.
     * </p>
     * <p>
     * All three of these default strategies support merging through JMerge.
     * </p>
     * 
     * @return The generation strategy that is to be used for generations launched through this launcher.
     */
	override def getGenerationStrategy(): IAcceleoGenerationStrategy = {
		super.getGenerationStrategy
	}
	
	 /**
     * This will be called in order to find and load the module that will be launched through this launcher.
     * We expect this name not to contain file extension, and the module to be located beside the launcher.
     * 
     * @return The name of the module that is to be launched.
     */
	override def getModuleName(): String = {
		return GenerateScala.MODULE_FILE_NAME
	}
	
	/**
     * If the module(s) called by this launcher require properties files, return their qualified path from
     * here.Take note that the first added properties files will take precedence over subsequent ones if they
     * contain conflicting keys.
     * <p>
     * Properties need to be in source folders, the path that we expect to get as a result of this call are of
     * the form &lt;package>.&lt;properties file name without extension>. For example, if you have a file
     * named "messages.properties" in package "org.eclipse.acceleo.sample", the path that needs be returned by
     * a call to {@link #getProperties()} is "org.eclipse.acceleo.sample.messages".
     * </p>
     * 
     * @return The list of properties file we need to add to the generation context.
     * @see java.util.ResourceBundle#getBundle(String)
     */
	override def getProperties(): java.util.List[String] = {
		return propertiesFilesList
	}
	
	 /**
     * Adds a properties file in the list of properties files.
     * 
     * @param propertiesFile
     *            The properties file to add.
     * @since 3.1
     */
	override def addPropertiesFile(propertiesFile: String) {
		propertiesFiles.add(propertiesFile)
	}
	
	/**
     * This will be used to get the list of templates that are to be launched by this launcher.
     * 
     * @return The list of templates to call on the module {@link #getModuleName()}.
     */
	override def getTemplateNames(): Array[String] = {
		return GenerateScala.TEMPLATE_NAMES
	}
	
	/**
     * This can be used to update the resource set's package registry with all needed EPackages.
     * 
     * @param resourceSet
     *            The resource set which registry has to be updated.
     */
	override def registerPackages(resourceSet: ResourceSet) {
		super.registerPackages(resourceSet)
	}
	
	/**
     * This can be used to update the resource set's resource factory registry with all needed factories.
     * 
     * @param resourceSet
     *            The resource set which registry has to be updated.
     */
	override def registerResourceFactories(resourceSet: ResourceSet) {
		super.registerResourceFactories(resourceSet)
	}
}