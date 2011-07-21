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
package com.github.sbegaudeau.acceleo.parser.internal

import java.io.File
import com.github.sbegaudeau.acceleo.parser.files.AcceleoFile
import scala.util.parsing.combinator.JavaTokenParsers

/**
 * The root trait of all the Acceleo parsers.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
trait AcceleoParser extends JavaTokenParsers {
	protected def begin: Parser[String] = "[";
	protected def end: Parser[String] = "]";
	protected def slash: Parser[String] = "/";
	protected def endExpression: Parser[String] = "/]"
}