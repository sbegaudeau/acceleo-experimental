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
package com.github.sbegaudeau.acceleo.mylyn.notification;

import org.eclipse.mylyn.commons.ui.notifications.AbstractNotification;
import org.eclipse.swt.graphics.Image;

import com.github.sbegaudeau.acceleo.mylyn.AcceleoMylynMessages;

/**
 * The AcceleoGenerationNotification is an Acceleo specialized mylyn notification.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
public class AcceleoGenerationNotification extends AbstractNotification {

	/**
	 * The ID of the Acceleo generation category.
	 */
	public static final String ACCELEO_GENERATION_CATEGORY_ID = "com.github.sbegaudeau.acceleo.mylyn.category";
	
	/**
	 * The ID of the Acceleo generation event.
	 */
	public static final String ACCELEO_GENERATION_EVENT_ID = "com.github.sbegaudeau.acceleo.mylyn.generation.event";
	
	/**
	 * The label of the notification.
	 */
	private String notificationLabel;
	
	/**
	 * The description of the notification.
	 */
	private String notificationDescription;
	
	/**
	 * The constructor.
	 * @param label The label of the notification.
	 * @param description The description of the notification.
	 */
	public AcceleoGenerationNotification(String label, String description) {
		super(ACCELEO_GENERATION_EVENT_ID);
		this.notificationLabel = label;
		this.notificationDescription = description;
	}
	
	/**
	 * {@inheritDoc}
	 * {@link org.eclipse.mylyn.commons.ui.notifications.AbstractNotification#open()}
	 */
	@Override
	public void open() {
	}

	/**
	 * {@inheritDoc}
	 * {@link org.eclipse.mylyn.commons.ui.notifications.AbstractNotification#getDescription()}
	 */
	@Override
	public String getDescription() {
		return this.notificationDescription;
	}

	/**
	 * {@inheritDoc}
	 * {@link org.eclipse.mylyn.commons.ui.notifications.AbstractNotification#getLabel()}
	 */
	@Override
	public String getLabel() {
		return notificationLabel;
	}

	/**
	 * {@inheritDoc}
	 * {@link org.eclipse.mylyn.commons.ui.notifications.AbstractNotification#getNotificationImage()}
	 */
	@Override
	public Image getNotificationImage() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * {@link org.eclipse.mylyn.commons.ui.notifications.AbstractNotification#getNotificationKindImage()}
	 */
	@Override
	public Image getNotificationKindImage() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * {@link java.lang.Comparable#compareTo(Object)}
	 */
	public int compareTo(AbstractNotification o) {
		return 0;
	}
	
	/**
	 * {@inheritDoc}
	 * {@link org.eclipse.core.runtime.IAdaptable#getAdapter(Class)}
	 */
	public Object getAdapter(Class adapter) {
		return null;
	}

}
