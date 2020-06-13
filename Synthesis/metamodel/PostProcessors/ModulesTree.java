package org.ipi.synthesiseditor.utils;


import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Stack;

import metamodel.ClassDef;
import metamodel.ElementDef;
import metamodel.FrameDef;
import metamodel.FunctionDef;
import metamodel.MetamodelFactory;
import metamodel.MetamodelPackage;
import metamodel.ModelEntity;
import metamodel.ModuleDef;
import metamodel.TypeDef;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.ipi.synthesiseditor.save.RealModuleFromModuleDefStubLoader;
import org.ipi.synthesiseditor.save.SaverUtils;

public class ModulesTree {
	
	private static final boolean debug = false;
	private static final Logger log = Logger.getLogger(ModulesTree.class);
	
	public Node root;
	private IProject project;

	private ModulesTree() {
		
	}
	
	public ModulesTree(ModuleDef rootModule, Resource moduleResource, IFile moduleFile) {

		root = new Node();
		root.module = rootModule;
		root.uidmap = SaverUtils.loadModuleUidMap(moduleResource, rootModule, false);
//		root.moduleFile = moduleFile;
		
		this.project = moduleFile.getProject();

	}

	public class Node {
		Node parent = null;
		List<Node> children = new LinkedList<Node>();
		
		public ModuleDef module;
		public Map<Integer, ModelEntity> uidmap;
//		public IFile moduleFile;
	}
	
	public class ModulesIterator implements Iterator<ModuleDef> {

		Stack<Node> stack = new Stack<Node>();		
		
		ModulesIterator() {
			stack.push(root);
		}
		
		public boolean hasNext() {
			return !stack.empty();
		}

		public ModuleDef next() {
			Node curNode = stack.pop();
			stack.addAll(curNode.children);			
			return curNode.module;
		}

		public void remove() {
			// TODO Auto-generated method stub
		}
						
	}
	
	public class NodesIterator implements Iterator<Node> {

		Stack<Node> stack = new Stack<Node>();		
		
		NodesIterator() {
			stack.push(root);
		}
		
		public boolean hasNext() {
			return !stack.empty();
		}

		public Node next() {
			Node curNode = stack.pop();
			stack.addAll(curNode.children);			
			return curNode;
		}

		public void remove() {
			// TODO Auto-generated method stub
		}
						
	}
	
	public Iterator<ModuleDef> modulesIterator() {
		return new ModulesIterator();
	}
	
	public Iterator<Node> nodesIterator() {
		return new NodesIterator();
	}
	
	public void constructModulesTree() {			
		constructModuleTree(root, project);
	}	
	
	public ModulesTree getSubtreeForModule(ModuleDef module) {
		ModulesTree tree = new ModulesTree();
		for (Iterator<Node> it = nodesIterator(); it.hasNext(); ) {
			Node curNode = it.next();
			if (curNode.module == module) {
				tree.root = curNode;
				return tree;
			}
		}
		
		return tree;
	}
	
	/**
	 * Appends module tree with given stub module definition to root
	 * It finds real module for stub module, and if successfull,
	 * adds module to tree to root node, and appends root module
	 * imports with this module
	 * 
	 * @param stubModuleDef
	 */
	public void appendTreeToRoot(ModuleDef stubModuleDef) {
		root.module.getImports().add(stubModuleDef);		
		constructModuleTree(root, project);
	}
	
	public void removeFromTree(String moduleName) {
		for (Iterator<Node> it = nodesIterator(); it.hasNext(); ) {
			Node n = it.next();
			if (n.module.getName().equals(moduleName)) {
				n.parent.children.remove(n);
				break;
			}
		}
	}
	
	private boolean ifAlreadyInChildren(Node parentNode, ModuleDef childModule) {
		for (Node n : parentNode.children) {
			if (n.module == childModule) return true;
		}
			
		return false;
	}
	
	private void constructModuleTree(Node parentNode, IProject project) {
	
		for (Iterator<FrameDef> it = parentNode.module.getImports().iterator(); it.hasNext(); ) {
			FrameDef frame = it.next();
			if (frame instanceof ModuleDef && !ifAlreadyInChildren(parentNode, (ModuleDef) frame)) {
				ModuleDef moduleToImport = (ModuleDef) frame;
				
				RealModuleFromModuleDefStubLoader loader = new RealModuleFromModuleDefStubLoader(project, moduleToImport);
				ModuleDef realModule = loader.findModule();
				
				if (realModule != null) {
					Node childNode = new Node();
					parentNode.children.add(childNode);
					childNode.parent = parentNode;
					
					parentNode.module.getImports().set(
							parentNode.module.getImports().lastIndexOf(moduleToImport),
							realModule);
					childNode.module = realModule;
					childNode.uidmap = SaverUtils.loadModuleUidMap(loader.getResLoad(), realModule, false);						
					constructModuleTree(childNode, project);				
				} else {
					log.warn(" Real module for stubModule (name=" + moduleToImport.getName() + ") not found");
				}
			}
		}
		
	}
	
	public void restoreModuleImports(ModuleDef module) {
		for (ListIterator<FrameDef> it = module.getImports().listIterator(); it.hasNext(); ) {
			FrameDef f = it.next();
			if (f instanceof ModuleDef) {
				for (Iterator<Node> nit = nodesIterator(); nodesIterator().hasNext(); ) {
					Node node = nit.next();
					if (node.module.getName().equals(f.getName())) {
						it.set(node.module);
						break;
					}
				}
				
			}
		}
		
	}
	
	
	
	
	public String ifInOtherModule(String moduleName, ModelEntity vd) {
		if (vd instanceof FunctionDef 
				&& ((FunctionDef) vd).getFunctionInModule() != null 
				&&  !moduleName.equals( ((FunctionDef) vd).getFunctionInModule().getName()) 
			)  {
						return ((FunctionDef) vd).getFunctionInModule().getName();
		} else if (vd instanceof TypeDef 
				&& ((TypeDef) vd).getTypeInModule() != null 
				&&  !moduleName.equals( ((TypeDef) vd).getTypeInModule().getName()) 
			)  {
						return ((TypeDef) vd).getTypeInModule().getName();
		} else if (vd instanceof ClassDef 
				&& ((ClassDef) vd).getClassInModule() != null 
				&&  !moduleName.equals( ((ClassDef) vd).getClassInModule().getName()) 
			)  {
						return ((ClassDef) vd).getClassInModule().getName();
		} 
		//TODO is it needed to support frames?
				
		return null;
	}
	
	public ModelEntity findModelEntityInModule(String moduleName, Integer uid) {
		
		for (Iterator<Node> it = this.nodesIterator(); it.hasNext(); ) {
			Node curNode = it.next();
			if (curNode.module.getName().equals(moduleName)) {  				
				return curNode.uidmap.get(uid);
			}
		}
		
		return null;
	}
	
	public ModuleDef findModuleForModelEntity(ModelEntity entity) {
		if (entity == null || entity.getUid() == null)
			throw new IllegalArgumentException();
		
		for (Iterator<Node> it = this.nodesIterator(); it.hasNext(); ) {
			Node curNode = it.next();
			if (entity == curNode.uidmap.get(entity.getUid())) {  				
				return curNode.module;
			}
		}
		
		return null;
	}
	
	
	private FrameDef getStubObject(ModuleDef module, ModelEntity vd, String moduleOfVd) {
		
		Integer uid = vd.getUid();
		
		for (Iterator<FrameDef> it = module.getImports().iterator(); it.hasNext(); ) {
			FrameDef frame = it.next();			
			
			String modName = frame.getInImportedModule();
			Integer modUid = frame.getImportedUid();
			if ( modUid != null && modUid.equals(uid)
					&& modName != null && modName.equals(moduleOfVd)
					&& vd.eClass().equals(frame.eClass())
					) {
				return frame;
			}		
		}
		
		EObject appropriateObject = MetamodelFactory.eINSTANCE.create(vd.eClass());
		if (appropriateObject instanceof FrameDef) {
			FrameDef result = (FrameDef) appropriateObject;			
			result.setImportedUid(vd.getUid());
			result.setInImportedModule(moduleOfVd);
			if (vd instanceof ElementDef) {
				result.setName(((ElementDef) vd).getName());
			}
			return result;
		}
		
		return null;
	}
	
	private ElementDef getStubObjectForBuiltIn(List<ElementDef> stubPool, ElementDef builtIn) {
		
		for (ElementDef o : stubPool) {
			if (o.eClass().equals(builtIn.eClass()) &&
					builtIn.getName().equals(o.getName())) {
				return o;
			}
		}
		
		EObject appropriateObject = MetamodelFactory.eINSTANCE.create(builtIn.eClass());
		if (appropriateObject instanceof ElementDef) {
			ElementDef result = (ElementDef) appropriateObject;			
			result.setName(builtIn.getName());
			return result;
		}
		
		return null;
	}
	
	public void restoreTreeDependencies() {
		for (Iterator<Node> it = this.nodesIterator(); it.hasNext(); ) {
			Node node = it.next();
//			System.err.println(" |> Module " + node.module.getName() + " processing start");
			restoreModuleDependencies(node.module);
		}
	}
	
	public void separateModuleDependencies(ModuleDef module) {
		LinkedList<ModelEntity> lst = new LinkedList<ModelEntity>();
		LinkedList<ElementDef> stubPool = new LinkedList<ElementDef>();
		lst.add(module);
		separateModuleDependencies(module, lst, module, stubPool);
	}
	
	
	/**
	 * Restores references, dependencies between modules' elements
	 * 
	 * Module A (imports Module B)
	 *  Type AType
	 *   Attribute B : BType
	 * Module B
	 *  Type BType
	 * 
	 * Internal representation is follow:
	 * 
	 * Module A (imports MODULE_B_STUB, B_TYPE_STUB)
	 *  Type AType
	 *   Attribute B : B_TYPE_STUB
	 *  MODULE_B_STUB (name = "Module B")
	 *  B_TYPE_STUB (in_imported_module = "Module B", in_imported_uid = "__uid of BType__")
	 * 
	 * Method iterates over the Module (over all it's objects, not only root objects) and
	 * seeks objects, which are in "imports" section of the module. Then, it seeks appropriate
	 * object for stub object and replaces stub object with real one.
	 * 
	 */
	
	public void restoreModuleDependencies(ModuleDef module) {		
		LinkedList<ModelEntity> lst = new LinkedList<ModelEntity>();
		lst.add(module);
		restoreModuleDependencies(module, lst, module);
		
		//manually clear imports section from stub objects
		//after dependencies restoration
		//this deletes stub objects, because we don't need it any more
		//modules which current module imports remains in the imports section
		List<ModelEntity> itemsToRemove = new LinkedList<ModelEntity>();
		for (Iterator<FrameDef> it = module.getImports().iterator(); it.hasNext(); ) {
			FrameDef frame = it.next();
			if (!(frame instanceof ModuleDef)) {
				itemsToRemove.add(frame);
			}
		}
		module.getImports().removeAll(itemsToRemove);
	}
	
	
	
	private void separateModuleDependencies(ModuleDef rootModule, List<ModelEntity> alreadyProcessedList, EObject eo, List<ElementDef> stubPool) {

		if (debug) {
			System.out.println(" // START PROCESSING OBJECT = " + eo);
			System.out.println(" ECLASS : " + eo.eClass());
		}
		
		eo.eSetDeliver(false);
		
		for (Iterator<EStructuralFeature> it = eo.eClass().getEAllStructuralFeatures().iterator(); it.hasNext(); ) {

			EStructuralFeature feature = it.next();
			Object obj = null;
			
			if (debug) {
				System.out.println(" --- feature : " + feature.getName());
			}
			
			if (feature == MetamodelPackage.eINSTANCE.getModuleDef_Imports()) {
				obj = new LinkedList((Collection) eo.eGet(feature));
			} else {
				obj = eo.eGet(feature);	
			}
					

			if (debug) {
				System.out.println(" value = " + obj);
			}
			
			if (obj != null) {

				if (obj instanceof List && !((List) obj).isEmpty()) {				
										
					for (ListIterator iter = ((List) obj).listIterator(); iter.hasNext(); ) {
						EObject eobj = (EObject) iter.next();
						if (eobj instanceof ModelEntity) {
							
							String moduleOfObject = ifInOtherModule(rootModule.getName(), (ModelEntity) eobj);
							if (null != moduleOfObject) {
								//System.err.println( ((ElementDef) eobj).getName() + "," + moduleOfObject);
								FrameDef stubObjectToSet = getStubObject(rootModule, (ModelEntity) eobj, moduleOfObject);
								if (null != stubObjectToSet) {
									//System.err.println("stub : " + stubObjectToSet);
									iter.set(stubObjectToSet);									
									rootModule.getImports().add(stubObjectToSet);
								}
							} else if (eobj instanceof ElementDef && 
										(
											BuiltInTypes.isBuiltInByName(((ElementDef) eobj).getName()) ||
										PredefinedObjects.isPredefinedByName(((ElementDef) eobj).getName())
										
										)
									){

									ElementDef stubObjectToSet = getStubObjectForBuiltIn(stubPool, (ElementDef) eobj);
									if (null != stubObjectToSet) {

										if (debug) {
											System.out.println(" > Found predefined object  " + eobj);
											System.out.println("   for object : " + eo);
											System.out.println("   predefined object is = " + stubObjectToSet);								
										}
										
										iter.set(stubObjectToSet);
										stubPool.add(stubObjectToSet);
									}
								
								
							} else if (!alreadyProcessedList.contains(eobj)) {
								alreadyProcessedList.add((ModelEntity) eobj);						
								separateModuleDependencies(rootModule, alreadyProcessedList, (EObject) eobj, stubPool);
							}		
						}
																		
					}


				} else if (obj instanceof ModelEntity) {

					String moduleOfObject = ifInOtherModule(rootModule.getName(), (ModelEntity) obj);
					if (null != moduleOfObject) {
						FrameDef stubObjectToSet = getStubObject(rootModule, (ModelEntity) obj, moduleOfObject);
						if (null != stubObjectToSet) {
							eo.eSet(feature, stubObjectToSet);
							rootModule.getImports().add(stubObjectToSet);
						}
					} else if (obj instanceof ElementDef &&
							(
								BuiltInTypes.isBuiltInByName(((ElementDef) obj).getName()) ||
								PredefinedObjects.isPredefinedByName(((ElementDef) obj).getName())
							)
						) {
							ElementDef stubObjectToSet = getStubObjectForBuiltIn(stubPool, (ElementDef) obj);
							if (null != stubObjectToSet) {								
								eo.eSet(feature, stubObjectToSet);								
								stubPool.add(stubObjectToSet);
							}
						
					} else if (!alreadyProcessedList.contains(obj)) {
						alreadyProcessedList.add((ModelEntity) obj);	
						separateModuleDependencies(rootModule, alreadyProcessedList, (EObject) obj, stubPool);
					}
				}

			}
		}
		
		eo.eSetDeliver(true);
		
	}
	
	/**
	 * Restores module dependencies recursively.
	 * 
	 * Method is recursive. It takes EObject and iterates over all it's features. 
	 * For each feature value if this value isn't processed yet (alreadyProcessedList holds such
	 * elements), then method called recursively on this feature value.
	 * 
	 * While iteration, if object from other module appears, then it replaced for real one 
	 * and method isn't calls recursively again.
	 * 
	 * @param rootModule Module to restore dependencies
	 * @param alreadyProcessedList List that holds objects, which are already processed by method. This
	 * list eliminates cycles in recursive calls 
	 * @param eo Current object to process
	 */
	private void restoreModuleDependencies(ModuleDef rootModule, List<ModelEntity> alreadyProcessedList, EObject eo) {

		eo.eSetDeliver(false);
		
		if (debug) {
			System.out.println(" // START PROCESSING OBJECT = " + eo);
			System.out.println(" ECLASS : " + eo.eClass());
		}
		for (EStructuralFeature feature : eo.eClass().getEAllStructuralFeatures()) {
			
			if (debug) {
				System.out.println(" --- feature : " + feature.getName());
			}
			
			if (feature == MetamodelPackage.eINSTANCE.getModuleDef_Imports()) continue;			
			Object obj = eo.eGet(feature);
			

			
			if (debug) {
				System.out.println(" value = " + obj);
			}
			
			if (obj != null) {

				if (obj instanceof List && !((List) obj).isEmpty()) {				
										
					for (ListIterator iter = ((List) obj).listIterator(); iter.hasNext(); ) {
						EObject eobj = (EObject) iter.next();
						if (eobj instanceof ModelEntity && !(eobj instanceof ModuleDef)) {
							if (rootModule.getImports().contains(eobj)) {
								//System.err.println(eobj);
								ModelEntity vd = rootModule.getImports().get(
										rootModule.getImports().lastIndexOf(eobj));
								ModelEntity realObject = findModelEntityInModule(
										vd.getInImportedModule(), 
										vd.getImportedUid());
								if (realObject != null) {
									//System.err.println("[real :"+ realObject);
									iter.set(realObject);									
								}
							} else if (eobj instanceof TypeDef
									&& BuiltInTypes.isBuiltInByName(((TypeDef) eobj).getName())) {
								TypeDef predefinedType = BuiltInTypes.getTypeByName(((TypeDef) eobj).getName());
								iter.set(predefinedType);
							} else if (eobj instanceof ElementDef 
									&& PredefinedObjects.isPredefinedByName(((ElementDef) eobj).getName())) {
								ElementDef predefinedObject = PredefinedObjects.getByName(((ElementDef) eobj).getName());
								iter.set(predefinedObject);
							} else if (!alreadyProcessedList.contains(eobj)) {
								alreadyProcessedList.add((ModelEntity) eobj);
								restoreModuleDependencies(rootModule, alreadyProcessedList, (EObject) eobj);
							}		
						}
																		
					}


				} else if (obj instanceof ModelEntity && !(obj instanceof ModuleDef)) {
//					System.err.println(" --- " + obj);
//					System.err.println(rootModule.getImports());
					if (rootModule.getImports().contains(obj)) {
						ModelEntity vd = rootModule.getImports().get(
								rootModule.getImports().lastIndexOf(obj));
						ModelEntity realObject = findModelEntityInModule(
								vd.getInImportedModule(), 
								vd.getImportedUid());
						eo.eSet(feature, realObject);											
					} if (obj instanceof TypeDef 
							&& BuiltInTypes.isBuiltInByName(((TypeDef) obj).getName())) {					
						TypeDef predefinedType = BuiltInTypes.getTypeByName(((TypeDef) obj).getName());
						if (debug) {
							System.out.println(" > Found built in type " + obj);
							System.out.println("   for object : " + eo);
							System.out.println("   predefined object type for it = " + predefinedType);								
						}
						eo.eSet(feature, predefinedType);
					} else if (obj instanceof ElementDef 
							&& PredefinedObjects.isPredefinedByName(((ElementDef) obj).getName())) {
						ElementDef predefinedObject = PredefinedObjects.getByName(((ElementDef) obj).getName());
						eo.eSet(feature, predefinedObject);
					} else if (!alreadyProcessedList.contains(obj)) {
						alreadyProcessedList.add((ModelEntity) obj);
						restoreModuleDependencies(rootModule, alreadyProcessedList, (EObject) obj);
					}
				}

			}
		}
		
		eo.eSetDeliver(true);
		
		if (debug) {
			System.out.println(" // FINISHED PROCESSING OBJECT = " + eo);
		}
		
	}
	
	
}
