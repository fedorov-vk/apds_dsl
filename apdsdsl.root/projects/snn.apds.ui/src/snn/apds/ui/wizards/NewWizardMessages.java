package snn.apds.ui.wizards;

import org.eclipse.osgi.util.NLS;

public class NewWizardMessages extends NLS {

	private static final String BUNDLE_NAME = "snn.apds.ui.wizards.messages"; //$NON-NLS-1$
	public static String CustomProjectNewWizard_Create_something_custom;
	public static String CustomProjectNewWizard_Custom_Plugin_Project;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, NewWizardMessages.class);
	}

	private NewWizardMessages() {
		// Hide me!
	}

}
