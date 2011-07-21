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
package com.github.sbegaudeau.acceleo.parser.messages

/**
 * Acceleo Parser Message.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 0.1
 */
abstract sealed class ParserMessage {
}

/**
 * Acceleo Parser Error.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 0.1
 */
case class AcceleoParserError(message: String, line: Int, posBegin: Int, posEnd: Int) extends ParserMessage {
}

/**
 * Acceleo Parser Warning.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 0.1
 */
case class AcceleoParserWarning(message: String, line: Int, posBegin: Int, posEnd: Int) extends ParserMessage {
}

/**
 * Acceleo Parser Infos.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 0.1
 */
case class AcceleoParserInfo(message: String, line: Int, posBegin: Int, posEnd: Int) extends ParserMessage {
}