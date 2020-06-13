/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.ipiran.language.Synthesis.resource.syn.analysis;

import org.ipiran.language.Synthesis.ADTDef;
import org.ipiran.language.Synthesis.AttributeDef;
import org.ipiran.language.Synthesis.TypeDef;
import org.ipiran.language.Synthesis.Registration.ReductElementDef;

public class ReductPathElementDefAttributeReferenceResolver implements org.ipiran.language.Synthesis.resource.syn.ISynReferenceResolver<org.ipiran.language.Synthesis.Registration.ReductPathElementDef, org.ipiran.language.Synthesis.AttributeDef> {
	
	private org.ipiran.language.Synthesis.resource.syn.analysis.SynDefaultResolverDelegate<org.ipiran.language.Synthesis.Registration.ReductPathElementDef, org.ipiran.language.Synthesis.AttributeDef> delegate = new org.ipiran.language.Synthesis.resource.syn.analysis.SynDefaultResolverDelegate<org.ipiran.language.Synthesis.Registration.ReductPathElementDef, org.ipiran.language.Synthesis.AttributeDef>();
	
	public void resolve(String identifier, org.ipiran.language.Synthesis.Registration.ReductPathElementDef container, org.eclipse.emf.ecore.EReference reference, int position, boolean resolveFuzzy, final org.ipiran.language.Synthesis.resource.syn.ISynReferenceResolveResult<org.ipiran.language.Synthesis.AttributeDef> result) {
		// OLD CODE
		//delegate.resolve(identifier, container, reference, position, resolveFuzzy, result);
		
		// NEW CODE
		AttributeDef attribute = null;
		TypeDef type = null;
		ReductElementDef elm = container.getPathElementOf();
		int index = elm.getPath().indexOf(container);
		
		// container is the first elm of path  
		if(index == 0){
			type = elm.getElementOf().getReducedType();
		}
		// container is not the first elm of path
		else {
			AttributeDef previousAttr = elm.getPath().get(index - 1).getAttribute();
			
			if(previousAttr.getType() != null)				
				type = previousAttr.getType();
			else
				type = previousAttr.getContainedType();
		}
		
		if(type != null && type instanceof ADTDef)
			for(AttributeDef a: ((ADTDef)type).getAttributes())
				if(a.getName().compareTo(identifier) == 0) 
					attribute = a;
		
		if(attribute != null)
			result.addMapping(identifier, attribute);
	}
	
	public String deResolve(org.ipiran.language.Synthesis.AttributeDef element, org.ipiran.language.Synthesis.Registration.ReductPathElementDef container, org.eclipse.emf.ecore.EReference reference) {
		return delegate.deResolve(element, container, reference);
	}
	
	public void setOptions(java.util.Map<?,?> options) {
		// save options in a field or leave method empty if this resolver does not depend
		// on any option
	}
	
}
