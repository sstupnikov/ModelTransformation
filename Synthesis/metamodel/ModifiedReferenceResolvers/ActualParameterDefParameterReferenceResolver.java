/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.ipiran.language.Synthesis.resource.syn.analysis;

import org.ipiran.language.Synthesis.FrameDef;
import org.ipiran.language.Synthesis.ParameterDef;

public class ActualParameterDefParameterReferenceResolver implements org.ipiran.language.Synthesis.resource.syn.ISynReferenceResolver<org.ipiran.language.Synthesis.ActualParameterDef, org.ipiran.language.Synthesis.ParameterDef> {
	
	private org.ipiran.language.Synthesis.resource.syn.analysis.SynDefaultResolverDelegate<org.ipiran.language.Synthesis.ActualParameterDef, org.ipiran.language.Synthesis.ParameterDef> delegate = new org.ipiran.language.Synthesis.resource.syn.analysis.SynDefaultResolverDelegate<org.ipiran.language.Synthesis.ActualParameterDef, org.ipiran.language.Synthesis.ParameterDef>();
	
	public void resolve(String identifier, org.ipiran.language.Synthesis.ActualParameterDef container, org.eclipse.emf.ecore.EReference reference, int position, boolean resolveFuzzy, final org.ipiran.language.Synthesis.resource.syn.ISynReferenceResolveResult<org.ipiran.language.Synthesis.ParameterDef> result) {
		// OLD CODE
		//delegate.resolve(identifier, container, reference, position, resolveFuzzy, result);
		
		// NEW CODE
		ParameterDef parameter = null;
		FrameDef frame = container.getActualParameterOf().getConcretizationOf();
		
		if(frame != null)
			for(ParameterDef p: frame.getParameters())
				if(p.getName().compareTo(identifier) == 0) 
					parameter = p;
		
		if(parameter != null)
			result.addMapping(identifier, parameter);

	}
	
	public String deResolve(org.ipiran.language.Synthesis.ParameterDef element, org.ipiran.language.Synthesis.ActualParameterDef container, org.eclipse.emf.ecore.EReference reference) {
		return delegate.deResolve(element, container, reference);
	}
	
	public void setOptions(java.util.Map<?,?> options) {
		// save options in a field or leave method empty if this resolver does not depend
		// on any option
	}
	
}
