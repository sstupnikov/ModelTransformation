/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.ipiran.language.Synthesis.resource.syn.analysis;

import org.ipiran.language.Synthesis.ParameterDef;
import org.ipiran.language.Synthesis.TypeDef;
import org.ipiran.language.Synthesis.Scripts.StateDef;
import org.ipiran.language.Synthesis.Scripts.TransitionDef;

public class StateBindingDefStateReferenceResolver implements org.ipiran.language.Synthesis.resource.syn.ISynReferenceResolver<org.ipiran.language.Synthesis.Scripts.StateBindingDef, org.ipiran.language.Synthesis.Scripts.StateDef> {
	
	private org.ipiran.language.Synthesis.resource.syn.analysis.SynDefaultResolverDelegate<org.ipiran.language.Synthesis.Scripts.StateBindingDef, org.ipiran.language.Synthesis.Scripts.StateDef> delegate = new org.ipiran.language.Synthesis.resource.syn.analysis.SynDefaultResolverDelegate<org.ipiran.language.Synthesis.Scripts.StateBindingDef, org.ipiran.language.Synthesis.Scripts.StateDef>();
	
	public void resolve(String identifier, org.ipiran.language.Synthesis.Scripts.StateBindingDef container, org.eclipse.emf.ecore.EReference reference, int position, boolean resolveFuzzy, final org.ipiran.language.Synthesis.resource.syn.ISynReferenceResolveResult<org.ipiran.language.Synthesis.Scripts.StateDef> result) {
		// OLD CODE
		//delegate.resolve(identifier, container, reference, position, resolveFuzzy, result);
		
		// NEW CODE
		StateDef state = null;
		
		if(container.getBindingFromOf() != null){
			for(StateDef s: container.getBindingFromOf().getFrom())
				if(s.getName().compareTo(identifier) == 0) 
					state = s;							
		} else
		if(container.getBindingToOf() != null){
			for(StateDef s: container.getBindingToOf().getTo())
				if(s.getName().compareTo(identifier) == 0) 
					state = s;							
		} 
					
		if(state != null)
			result.addMapping(identifier, state);	
	}
	
	public String deResolve(org.ipiran.language.Synthesis.Scripts.StateDef element, org.ipiran.language.Synthesis.Scripts.StateBindingDef container, org.eclipse.emf.ecore.EReference reference) {
		return delegate.deResolve(element, container, reference);
	}
	
	public void setOptions(java.util.Map<?,?> options) {
		// save options in a field or leave method empty if this resolver does not depend
		// on any option
	}
	
}
