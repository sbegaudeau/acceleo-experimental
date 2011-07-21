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
import scala.util.parsing.combinator.JavaTokenParsers
import org.eclipse.acceleo.model.mtl.Comment
import org.eclipse.acceleo.model.mtl.MtlFactory

/**
 * The Acceleo Comment Parser.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 0.1
 */
trait AcceleoCommentParser extends AcceleoParser {
	protected def commentBegin: Parser[String] = "[comment ";
	protected def comment: Parser[Any] = commentBegin ~ commentBody ~ endExpression
	protected def commentBody : Parser[Comment] = new Parser[Comment] {
		def apply(in: Input) = {
			val source = in.source
			val offset = in.offset
			var countSquareBraces = 1
			var found = false
			
			val start = offset
			var i = offset
			while (i < source.length && countSquareBraces != 0) {
				i = i + 1
				if (source.charAt(i) == '[') {
					countSquareBraces = countSquareBraces + 1
				} else if (source.charAt(i) == '/' && source.length > i && source.charAt(i + 1) == ']') {
					countSquareBraces = countSquareBraces - 1
				}
				
				if (countSquareBraces == 0) {
					found = true
				}
			}
			
			if (found) {
				Console.out.println("Found comment: " + source.subSequence(start, i))
				
				val comment = MtlFactory.eINSTANCE.createComment
				val commentBody = MtlFactory.eINSTANCE.createCommentBody
				commentBody.setValue(source.subSequence(start, i).toString)
				comment.setBody(commentBody)
				
				Success(comment, in.drop(i - offset))
			} else {
				Failure("`/]' expected but '" + source + "' found", in.drop(offset))
			}
		}
	}
}