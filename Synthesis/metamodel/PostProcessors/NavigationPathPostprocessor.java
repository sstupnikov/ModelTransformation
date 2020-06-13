package org.ipiran.language.Synthesis.post;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.ipiran.language.Synthesis.ActualParameterDef;
import org.ipiran.language.Synthesis.AttributeDef;
import org.ipiran.language.Synthesis.ClassDef;
import org.ipiran.language.Synthesis.ElementDef;
import org.ipiran.language.Synthesis.MetaclassDef;
import org.ipiran.language.Synthesis.ParameterDef;
import org.ipiran.language.Synthesis.SlotDef;
import org.ipiran.language.Synthesis.BuiltInTypes.CollectionDef;
import org.ipiran.language.Synthesis.BuiltInTypes.ProductElementDef;
import org.ipiran.language.Synthesis.BuiltInTypes.RangeDef;
import org.ipiran.language.Synthesis.BuiltInTypes.UnionElementDef;
import org.ipiran.language.Synthesis.Formulae.NavigationPath;
import org.ipiran.language.Synthesis.Formulae.ReferableToPoststate;
import org.ipiran.language.Synthesis.Formulae.Variable;
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

public class NavigationPathPostprocessor implements ISynOptionProvider,
ISynResourcePostProcessor, ISynResourcePostProcessorProvider {

	public Map<?, ?> getOptions() {
	return Collections.singletonMap( 
			ISynOptions.RESOURCE_POSTPROCESSOR_PROVIDER, 
		    this 
		    ); 
	}
	
	public NavigationPathPostprocessor getResourcePostProcessor() { 
		return this; 
	} 
	
	public void process(SynResource resource) {
		System.out.println("NavigationPathPostprocessor");
		
		for(EObject e: resource.getContents()){
			processEObject(e);
		}				
	} 
	
	private void processEObject(EObject eo){
		// DEBUG
		if(eo instanceof ElementDef) System.out.println(((ElementDef)eo).getName());
		System.out.println(eo.eClass().getName());

		// Merging path
		//
		// Initially by EMFText.Synthesis navigation path v.a1.a2.a3~ is represented as
		// NavigationPath(NavigationPath(NavigationPath(a3~, a2), a1), v)
		// The following code transforms it into
		// NavigationPath(v, a1, a2, a3)~
		if(eo instanceof NavigationPath){
			NavigationPath p = (NavigationPath)eo;
			List<ValueDef> terms = p.getTerms();
			ValueDef first;						 
			
			if(terms.size() > 1){
				first = terms.get(0);

				terms.remove(first);
				
				while(first instanceof NavigationPath){
					terms = ((NavigationPath)first).getTerms();					 
					if(terms.size() > 1){
						first = terms.get(0); 
						p.getTerms().add(terms.get(1));												
					}					
				}
				
				p.getTerms().add(first);
				
				if(first instanceof ReferableToPoststate){
					if(((ReferableToPoststate)first).isRefersToPoststate()){
						((ReferableToPoststate)first).setRefersToPoststate(false);
						p.setRefersToPoststate(true);
					}
				}
			}
			
			
		} 
		// Recursive processing for all objects associated with eo by some structural features
		else{ 					
			for(EStructuralFeature feature : eo.eClass().getEAllStructuralFeatures()) {
				if( feature instanceof EReference && ((EReference)feature).isContainment() ){
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
		}						
	}
	
	
	@Override
	public void terminate() {
		// TODO Auto-generated method stub
	
	}

}

