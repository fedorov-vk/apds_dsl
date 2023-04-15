package snn.apds.dsl.prototypes;

import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class PluginActivator implements BundleActivator {

	private static BundleContext context;

	private static PluginActivator INSTANCE;

	private ResourceDescriptionsProvider rdp;
	
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		PluginActivator.context = bundleContext;
		INSTANCE = this;
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		PluginActivator.context = null;
		INSTANCE = null;
	}
	
	public static BundleContext getContext() {
		return context;
	}

	public static PluginActivator getInstance() {
		return PluginActivator.INSTANCE;
	}
	
	
	public ResourceDescriptionsProvider getRdp() {
		return rdp;
	}

	public void setRdp(ResourceDescriptionsProvider rdp) {
		this.rdp = rdp;
	}

}
