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

import snn.apds.projects.CustomProjectSupport;

public class NewProjectApds extends Wizard implements INewWizard, IExecutableExtension {

	private static final String PAGE_NAME = "Custom Plug-in Project Wizard"; //$NON-NLS-1$
	private WizardNewProjectCreationPage _pageOne;
	private IConfigurationElement _configurationElement;

	public NewProjectApds() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean performFinish() {
		String name = _pageOne.getProjectName();
		URI location = null;
		if (!_pageOne.useDefaults()) {
			location = _pageOne.getLocationURI();
		} // else location == null

		CustomProjectSupport.createProject(name, location);
		BasicNewProjectResourceWizard.updatePerspective(_configurationElement);

		return true;
	}

	@Override
	public void addPages() {
		super.addPages();

		_pageOne = new WizardNewProjectCreationPage(PAGE_NAME);
		_pageOne.setTitle(NewWizardMessages.CustomProjectNewWizard_Custom_Plugin_Project);
		_pageOne.setDescription(NewWizardMessages.CustomProjectNewWizard_Create_something_custom);

		addPage(_pageOne);
	}

	@Override
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data)
			throws CoreException {
		_configurationElement = config;
	}

}
