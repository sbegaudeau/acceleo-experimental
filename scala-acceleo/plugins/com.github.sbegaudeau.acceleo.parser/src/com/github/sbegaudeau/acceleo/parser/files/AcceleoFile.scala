/*******************************************************************************
 * Copyright (c) 2011 Stephane Begaudeau.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Stephane Begaudeau - initial API and implementation
 *******************************************************************************/
package com.github.sbegaudeau.acceleo.parser.files

import java.io.File
import java.util.StringTokenizer

/**
 * An utility class to manipulate an Acceleo module and its qualified name.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
case class AcceleoFile(file: File, qualifiedName: String) {
	
	def javaPackageToFullModuleName(javaPackageName: String, moduleName: String): String = {
		if (javaPackageName != null && javaPackageName.length > 0) {
			return javaPackageName.replaceAll("\\.", "::") + "::" + moduleName
		}
		return moduleName
	}
	
	def simpleModuleName(file: File): String = {
		var fileName = file.getName();
		if (fileName.endsWith(".mtl")) {
			fileName = fileName.substring(0, fileName.length - ".mtl".length)
		}
		return fileName
	}
	
	def relativePathtoFullModuleName(relativePath: String): String =  {
		val fullModuleName = new StringBuilder()
		var counter = 0
		
		if (relativePath.contains("/")) {
			val tokenizer = new StringTokenizer(relativePath, "/")
			while (tokenizer.hasMoreElements()) {
				val elem = tokenizer.nextElement()
				if (counter != 0) {
					fullModuleName.append("::")
				}
				fullModuleName.append(elem)
				counter = counter + 1
			}
		} else if (relativePath.contains("\\")) {
			val tokenizer = new StringTokenizer(relativePath, "\\")
			while (tokenizer.hasMoreElements()) {
				val elem = tokenizer.nextElement()
				if (counter != 0) {
					fullModuleName.append("::")
				}
				fullModuleName.append(elem)
				counter = counter + 1
			}
		}
		return fullModuleName.toString
	}
}