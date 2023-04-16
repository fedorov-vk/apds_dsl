package snn.apds.projects;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.xtext.ui.XtextProjectHelper;

import com.google.common.base.Preconditions;

import snn.apds.core.natures.ApdsProjectNature;

public class ApdsProjectSupport {
	/**
	 * Для этого замечательного проекта нам нужно:
	 * <li>создать проект Eclipse, предлагаемый по умолчанию
	 * <li>добавить природу APDS проекта
	 * <li>создать структуру папок
	 * 
	 * @param projectName
	 * @param location
	 * @param natureId
	 * @return
	 */
	public static IProject createProject(String projectName, URI location) {
		Assert.isNotNull(projectName);
		Assert.isTrue(projectName.trim().length() > 0);

		IProject project = createBaseProject(projectName, location);
		try {
			addNature(project);

			String[] paths = { "прототипы", "объекты" }; //$NON-NLS-1$ //$NON-NLS-2$
			addToProjectStructure(project, paths);
		} catch (CoreException e) {
			e.printStackTrace();
			project = null;
		}

		return project;
	}

	/**
	 * Просто сделать основное: создать базовый проект, если его нет.
	 * 
	 * @param projectName имя проекта.
	 * @param location    место размещения проекта.
	 * @return созданный проект.
	 */
	private static IProject createBaseProject(String projectName, URI location) {
		// приемлемо использование класса ResourcesPlugin.
		IProject newProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

		if (newProject.exists() == false) {
			URI projectLocation = location;
			IProjectDescription desc = newProject.getWorkspace().newProjectDescription(newProject.getName());
			if (location != null && ResourcesPlugin.getWorkspace().getRoot().getLocationURI().equals(location)) {
				projectLocation = null;
			}

			desc.setLocationURI(projectLocation);
			try {
				newProject.create(desc, null);
				if (newProject.isOpen() == false) {
					newProject.open(null);
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}

		return newProject;
	}

	private static void createFolder(IFolder folder) throws CoreException {
		IContainer parent = folder.getParent();
		if (parent instanceof IFolder) {
			createFolder((IFolder) parent);
		}
		if (!folder.exists()) {
			folder.create(false, true, null);
		}
	}

	/**
	 * Создает заданную структуру каталогов в проекте.
	 * 
	 * @param project проект.
	 * @param paths   список путей каталогов, относительно корня проекта.
	 * @throws CoreException
	 */
	private static void addToProjectStructure(IProject project, String[] paths) throws CoreException {
		for (String path : paths) {
			IFolder etcFolders = project.getFolder(path);
			createFolder(etcFolders);
		}
	}

	/**
	 * Добавляет проекту: APDS природу; Xtext природу, билдер.
	 *
	 * @param project
	 * @throws CoreException
	 */
	private static void addNature(IProject project) throws CoreException {
		Preconditions.checkNotNull(project);
		boolean hasApdsNature = project.hasNature(ApdsProjectNature.NATURE_ID);
		boolean hasXtextNature = project.hasNature(XtextProjectHelper.NATURE_ID);

		if (hasApdsNature == false || hasXtextNature == false) {
			IProjectDescription description = project.getDescription();
			ArrayList<String> natures = new ArrayList<String>(Arrays.asList(description.getNatureIds()));
			if (hasApdsNature == false) {
				// Добавить APDS природу.
				natures.add(ApdsProjectNature.NATURE_ID);
			}
			if (hasXtextNature == false) {
				// Добавить Xtext природу, builder spec.
				natures.add(XtextProjectHelper.NATURE_ID);
				ICommand buildCommand = description.newCommand();
				buildCommand.setBuilderName(XtextProjectHelper.BUILDER_ID);
				description.setBuildSpec(new ICommand[] { buildCommand });
			}
			description.setNatureIds(natures.toArray(new String[natures.size()]));

			IProgressMonitor monitor = null;
			project.setDescription(description, monitor);
		}
	}

}
