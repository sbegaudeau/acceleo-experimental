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
import org.eclipse.ocl.ecore.Variable
import org.eclipse.acceleo.model.mtl.MtlFactory
import org.eclipse.ocl.ecore.EcoreFactory
import org.eclipse.emf.ecore.EAnnotation

/**
 * Acceleo Let Block Parser.
 * 
 * [let myVar: myType = myExpression]body[/let]
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 0.1
 */
trait AcceleoLetBlockParser extends AcceleoParser {
	protected def letBlock: Parser[Any] = letHeader ~ letBody ~ letFooter
	protected def letHeader: Parser[Any] = letBegin ~ letVariable ~ "=" ~ letExpression ~ end
	protected def letBody: Parser[String] = "";
	protected def letBegin: Parser[String] = "[let ";
	protected def letVariable = ident ~ ":" ~ ident ^^ {
		case name ~ ":" ~ typeName =>
			val letVariable = org.eclipse.ocl.ecore.EcoreFactory.eINSTANCE.createVariable()
			letVariable.setName(name)
			
			val eAnnotation: EAnnotation = org.eclipse.emf.ecore.EcoreFactory.eINSTANCE.createEAnnotation();
			eAnnotation.setSource("MTL");
			eAnnotation.getDetails().put("type", typeName);
			
			letVariable.getEAnnotations().add(eAnnotation)
			letVariable // returns Parser[Variable]
	}
	
	protected def letExpression: Parser[String] = new Parser[String] {
		def apply(in: Input) = {
			val source = in.source
			val offset = in.offset
			var found = false
			
			val start = offset
			var i = offset
			while (i < source.length) {
				i = i + 1
				if (source.charAt(i) == ']') {
					found = true
				}
			}
			
			if (found) {
				Success(source.subSequence(start, i - 1).toString(), in.drop(i - 1 - start))
			} else {
				Failure("`]' expected but '" + source + "' found", in.drop(offset))
			}
		}
	}
	
	protected def letFooter: Parser[String] = "[/let]";
}