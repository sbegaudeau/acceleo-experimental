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
package com.github.sbegaudeau.acceleo.mylyn.generation.listener;

import java.util.Collections;

import org.eclipse.acceleo.engine.event.AbstractAcceleoTextGenerationListener;
import org.eclipse.acceleo.engine.event.AcceleoTextGenerationEvent;
import org.eclipse.acceleo.traceability.GeneratedFile;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.mylyn.commons.ui.notifications.Notifications;

import com.github.sbegaudeau.acceleo.mylyn.AcceleoMylynMessages;
import com.github.sbegaudeau.acceleo.mylyn.notification.AcceleoGenerationNotification;

/**
 * The MylynTextGenerationListener listens to Acceleo generations and uses Acceleo
 * events to create mylyn notifications.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
public class MylynTextGenerationListener extends AbstractAcceleoTextGenerationListener {

	/**
	 * The number of file generated.
	 */
	private int fileGenerated;
	
	/**
	 * The constructor.
	 */
	public MylynTextGenerationListener() {
		fileGenerated = 0;
	}
	
	/**
	 * {@inheritDoc}
	 * {@link org.eclipse.acceleo.engine.event.AbstractAcceleoTextGenerationListener#fileGenerated(AcceleoTextGenerationEvent)}
	 */
	@Override
	public void fileGenerated(AcceleoTextGenerationEvent event) {
		super.fileGenerated(event);
		
		EObject traceabilityInformation = event.getTraceabilityInformation();
		if (traceabilityInformation instanceof GeneratedFile) {
			GeneratedFile generatedFile = (GeneratedFile) traceabilityInformation;
			String file = AcceleoMylynMessages.getString("AcceleoGenerationNotification.fileGeneratedLabel", generatedFile.getName());
			
			int length = generatedFile.getLength();
			String description = AcceleoMylynMessages.getString("AcceleoGenerationNotification.fileGeneratedDescription", length);
			
			Notifications.getService().notify(Collections.singletonList(new AcceleoGenerationNotification(file, description)));
		}
	}
	
	/**
	 * {@inheritDoc}
	 * {@link org.eclipse.acceleo.engine.event.AbstractAcceleoTextGenerationListener#generationEnd(AcceleoTextGenerationEvent)}
	 */
	@Override
	public void generationEnd(AcceleoTextGenerationEvent event) {
		super.generationEnd(event);
		String label = AcceleoMylynMessages.getString("AcceleoGenerationNotification.generationEndLabel");
		String description = AcceleoMylynMessages.getString("AcceleoGenerationNotification.generationEndDescription", fileGenerated);
		Notifications.getService().notify(Collections.singletonList(new AcceleoGenerationNotification(label, description)));
	}
	
	/**
	 * {@inheritDoc}
	 * {@link org.eclipse.acceleo.engine.event.AbstractAcceleoTextGenerationListener#listensToGenerationEnd()}
	 */
	@Override
	public boolean listensToGenerationEnd() {
		return true;
	}
}
