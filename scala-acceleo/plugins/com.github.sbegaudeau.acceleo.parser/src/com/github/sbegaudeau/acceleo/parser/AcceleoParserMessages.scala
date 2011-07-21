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

import java.text.MessageFormat
import java.util.ResourceBundle
import java.util.MissingResourceException

object AcceleoParserMessages {
	val BUNDLE_NAME = "com.github.sbegaudeau.acceleo.parser.acceleoparsermessages";
	val RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	
	def getString(key: String): String = {
		return MessageFormat.format(internalGetString(key))
	}
	
	def getString(key: String, arguments: String): String = {
		if (arguments == null) {
			return internalGetString(key)
		}
		return MessageFormat.format(internalGetString(key), arguments)
	}
	
	private def internalGetString(key: String): String = {
		try {
			return RESOURCE_BUNDLE.getString(key)
		} catch {
			case e: MissingResourceException => return '!' + key + '!'
		}
	}
}