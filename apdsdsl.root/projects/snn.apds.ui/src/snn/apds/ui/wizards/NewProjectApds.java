package snn.apds.ui.wizards;

import java.net.URI;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

import snn.apds.projects.ApdsProjectSupport;

public class NewProjectApds extends Wizard implements INewWizard, IExecutableExtension {

	private static final String WIZARD_NAME = "New APDS Project"; //$NON-NLS-1$
	private static final String PAGE_NAME = "Custom Plug-in Project Wizard"; //$NON-NLS-1$

	private WizardNewProjectCreationPage pageOne;
	private IConfigurationElement configurationElement;

	public NewProjectApds() {
		setWindowTitle(WIZARD_NAME);
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean performFinish() {
		String name = pageOne.getProjectName();
		URI location = null;
		if (pageOne.useDefaults() == false) {
			location = pageOne.getLocationURI();
		} // else location == null

		ApdsProjectSupport.createProject(name, location);
		BasicNewProjectResourceWizard.updatePerspective(configurationElement);

		return true;
	}

	@Override
	public void addPages() {
		super.addPages();
		pageOne = new WizardNewProjectCreationPage(PAGE_NAME);
		pageOne.setTitle(NewWizardMessages.ApdsProjectNewWizard_Custom_Plugin_Project);
		pageOne.setDescription(NewWizardMessages.ApdsProjectNewWizard_Create_something_custom);

		addPage(pageOne);
	}

	@Override
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data)
			throws CoreException {
		configurationElement = config;
	}

}
