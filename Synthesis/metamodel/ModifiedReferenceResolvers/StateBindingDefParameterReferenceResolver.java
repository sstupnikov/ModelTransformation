/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.ipiran.language.Synthesis.resource.syn.analysis;

import org.ipiran.language.Synthesis.FunctionDef;
import org.ipiran.language.Synthesis.ParameterDef;
import org.ipiran.language.Synthesis.TypeDef;

public class StateBindingDefParameterReferenceResolver implements org.ipiran.language.Synthesis.resource.syn.ISynReferenceResolver<org.ipiran.language.Synthesis.Scripts.StateBindingDef, org.ipiran.language.Synthesis.ParameterDef> {
	
	private org.ipiran.language.Synthesis.resource.syn.analysis.SynDefaultResolverDelegate<org.ipiran.language.Synthesis.Scripts.StateBindingDef, org.ipiran.language.Synthesis.ParameterDef> delegate = new org.ipiran.language.Synthesis.resource.syn.analysis.SynDefaultResolverDelegate<org.ipiran.language.Synthesis.Scripts.StateBindingDef, org.ipiran.language.Synthesis.ParameterDef>();
	
	public void resolve(String identifier, org.ipiran.language.Synthesis.Scripts.StateBindingDef container, org.eclipse.emf.ecore.EReference reference, int position, boolean resolveFuzzy, final org.ipiran.language.Synthesis.resource.syn.ISynReferenceResolveResult<org.ipiran.language.Synthesis.ParameterDef> result) {
		// OLD CODE
		//delegate.resolve(identifier, container, reference, position, resolveFuzzy, result);
		
		// NEW CODE
		ParameterDef parameter = null;
		
		if(container.getBindingFromOf() != null){
			for(ParameterDef p: container.getBindingFromOf().getAction().getParameters())
				if(p.getName().compareTo(identifier) == 0) 
					parameter = p;		
		} else 
		if(container.getBindingToOf() != null){
			for(ParameterDef p: container.getBindingToOf().getAction().getParameters())
				if(p.getName().compareTo(identifier) == 0) 
					parameter = p;					
		}
				
		if(parameter != null)
			result.addMapping(identifier, parameter);	
	}
	
	public String deResolve(org.ipiran.language.Synthesis.ParameterDef element, org.ipiran.language.Synthesis.Scripts.StateBindingDef container, org.eclipse.emf.ecore.EReference reference) {
		return delegate.deResolve(element, container, reference);
	}
	
	public void setOptions(java.util.Map<?,?> options) {
		// save options in a field or leave method empty if this resolver does not depend
		// on any option
	}
	
}
