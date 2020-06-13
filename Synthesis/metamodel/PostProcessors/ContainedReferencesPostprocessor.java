package org.ipiran.language.Synthesis.post;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.eclipse.emf.ecore.*;
import org.ipiran.language.Synthesis.*;
import org.ipiran.language.Synthesis.BuiltInTypes.CollectionDef;
import org.ipiran.language.Synthesis.BuiltInTypes.ProductElementDef;
import org.ipiran.language.Synthesis.BuiltInTypes.RangeDef;
import org.ipiran.language.Synthesis.BuiltInTypes.UnionElementDef;
import org.ipiran.language.Synthesis.Formulae.Variable;
import org.ipiran.language.Synthesis.Registration.ModelEntity;
import org.ipiran.language.Synthesis.Registration.ReductElementDef;
import org.ipiran.language.Synthesis.Registration.ReductPathElementDef;
import org.ipiran.language.Synthesis.Scripts.StateDef;
import org.ipiran.language.Synthesis.Values.CollectionValueDef;
import org.ipiran.language.Synthesis.Values.ValueDef;
import org.ipiran.language.Synthesis.resource.syn.ISynOptionProvider;
import org.ipiran.language.Synthesis.resource.syn.ISynOptions;
import org.ipiran.language.Synthesis.resource.syn.ISynResourcePostProcessor;
import org.ipiran.language.Synthesis.resource.syn.ISynResourcePostProcessorProvider;
import org.ipiran.language.Synthesis.resource.syn.mopp.SynResource;

public class ContainedReferencesPostprocessor implements ISynOptionProvider,
		ISynResourcePostProcessor, ISynResourcePostProcessorProvider {

	public Map<?, ?> getOptions() {
	    return Collections.singletonMap( 
	    		ISynOptions.RESOURCE_POSTPROCESSOR_PROVIDER, 
			    this 
			    ); 
	}

		 
	public ContainedReferencesPostprocessor getResourcePostProcessor() { 
		return this; 
	} 

	public void process(SynResource resource) {
		System.out.println("ContainedReferencesPostprocessor");
		
		for(EObject e: resource.getContents()){
			processEObject(e);
		}				
	} 
	
	private void processEObject(EObject eo){
		// DEBUG
		if(eo instanceof ElementDef) System.out.println(((ElementDef)eo).getName());
		System.out.println(eo.eClass().getName());
		
		// Recursive processing for all objects associated with eo by some structural features
		for(EStructuralFeature feature : eo.eClass().getEAllStructuralFeatures()) {
			if( (feature instanceof EAttribute) || (feature instanceof EReference && ((EReference)feature).isContainment()) ){
				Object obj = eo.eGet(feature);
											
				if (obj != null) {										
					if (obj instanceof List && !((List) obj).isEmpty()) {
						for (ListIterator iter = ((List) obj).listIterator(); iter.hasNext(); ) {
							Object elem = iter.next();
							if(elem instanceof EObject){
								EObject eobj = (EObject)elem;
								System.out.println("eobj: "+((EObject)eobj).eClass().getName());
								processEObject(eobj);		
							}
						}																	
					} else 
					if (obj instanceof EObject) {
						System.out.println("obj: "+((EObject)obj).eClass().getName());
						processEObject((EObject)obj);										
					}						
				} 				
			}
		}		
		
		// Add contained values to noncontainment references
		if(eo instanceof CollectionValueDef)
			((CollectionValueDef)eo).getContents().addAll(((CollectionValueDef)eo).getContainedContents());
		
		if(eo instanceof SlotDef)
			((SlotDef)eo).getValues().addAll(((SlotDef)eo).getContainedValues());

		if(eo instanceof ValueDef && ((ValueDef)eo).getContainedTypeOfValue() != null)
			((ValueDef)eo).setTypeOfValue(((ValueDef)eo).getContainedTypeOfValue());		
		
		if(eo instanceof Variable && ((Variable)eo).getContainedType() != null)
			((Variable)eo).setType(((Variable)eo).getContainedType());  

		if(eo instanceof ParameterDef && ((ParameterDef)eo).getContainedType() != null)
			((ParameterDef)eo).setType(((ParameterDef)eo).getContainedType());  
		
		if(eo instanceof AttributeDef && ((AttributeDef)eo).getContainedType() != null)
			((AttributeDef)eo).setType(((AttributeDef)eo).getContainedType());  
		
		if(eo instanceof ClassDef && ((ClassDef)eo).getContainedInstanceType() != null)
			((ClassDef)eo).setInstanceType(((ClassDef)eo).getContainedInstanceType());  

		if(eo instanceof MetaclassDef && ((MetaclassDef)eo).getContainedInstInstType() != null)
			((MetaclassDef)eo).setInstInstType(((MetaclassDef)eo).getContainedInstInstType());  
		
		if(eo instanceof CollectionDef && ((CollectionDef)eo).getContainedOfType() != null)
			((CollectionDef)eo).setOfType(((CollectionDef)eo).getContainedOfType());  

		if(eo instanceof RangeDef && ((RangeDef)eo).getContainedRangedType() != null)
			((RangeDef)eo).setRangedType(((RangeDef)eo).getContainedRangedType());  
		
		if(eo instanceof UnionElementDef && ((UnionElementDef)eo).getContainedType() != null)
			((UnionElementDef)eo).setType(((UnionElementDef)eo).getContainedType());  

		if(eo instanceof ProductElementDef && ((ProductElementDef)eo).getContainedType() != null)
			((ProductElementDef)eo).setType(((ProductElementDef)eo).getContainedType());
		
		if(eo instanceof ActualParameterDef && ((ActualParameterDef)eo).getContainedValue() != null)
			((ActualParameterDef)eo).setValue(((ActualParameterDef)eo).getContainedValue());  		

		if(eo instanceof StateDef && ((StateDef)eo).getContainedTokenType() != null)
			((StateDef)eo).setTokenType(((StateDef)eo).getContainedTokenType());  		

		if(eo instanceof ReductElementDef && ((ReductElementDef)eo).getContainedType() != null)
			((ReductElementDef)eo).setType(((ReductElementDef)eo).getContainedType());  		
		
		if(eo instanceof ReductPathElementDef && ((ReductPathElementDef)eo).getContainedType() != null)
			((ReductPathElementDef)eo).setType(((ReductPathElementDef)eo).getContainedType());  		
		
	}


	@Override
	public void terminate() {
		// TODO Auto-generated method stub
		
	}
		
}
