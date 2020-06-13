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

public class AttributeDefInverseAttributeReferenceResolver implements org.ipiran.language.Synthesis.resource.syn.ISynReferenceResolver<org.ipiran.language.Synthesis.AttributeDef, org.ipiran.language.Synthesis.AttributeDef> {
	
	private org.ipiran.language.Synthesis.resource.syn.analysis.SynDefaultResolverDelegate<org.ipiran.language.Synthesis.AttributeDef, org.ipiran.language.Synthesis.AttributeDef> delegate = new org.ipiran.language.Synthesis.resource.syn.analysis.SynDefaultResolverDelegate<org.ipiran.language.Synthesis.AttributeDef, org.ipiran.language.Synthesis.AttributeDef>();
	
	public void resolve(String identifier, org.ipiran.language.Synthesis.AttributeDef container, org.eclipse.emf.ecore.EReference reference, int position, boolean resolveFuzzy, final org.ipiran.language.Synthesis.resource.syn.ISynReferenceResolveResult<org.ipiran.language.Synthesis.AttributeDef> result) {
		// OLD CODE
		//delegate.resolve(identifier, container, reference, position, resolveFuzzy, result);
		
		// NEW CODE
		AttributeDef attribute = null;
		TypeDef attributeType = null;

		if(container.getType() != null)
			attributeType = container.getType();
		else
			attributeType = container.getContainedType();
		
		if(attributeType != null && attributeType instanceof ADTDef)
			for(AttributeDef a: ((ADTDef)attributeType).getAttributes())
				if(a.getName().compareTo(identifier) == 0) 
					attribute = a;
		
		if(attribute != null)
			result.addMapping(identifier, attribute);
	}
	
	public String deResolve(org.ipiran.language.Synthesis.AttributeDef element, org.ipiran.language.Synthesis.AttributeDef container, org.eclipse.emf.ecore.EReference reference) {
		return delegate.deResolve(element, container, reference);
	}
	
	public void setOptions(java.util.Map<?,?> options) {
		// save options in a field or leave method empty if this resolver does not depend
		// on any option
	}
	
}
