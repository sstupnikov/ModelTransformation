@SuppressWarnings
SYNTAXDEF syn
FOR <http://synthesis.ipi.ac.ru/Synthesis/>

START 

SchemaDef, ModuleDef, Synthesis.Formulae.Formula
//FrameDef, ADTDef, ClassDef, FunctionDef, 
//Synthesis.Scripts.ScriptDef,
//Synthesis.Ontology.ConceptDef, 
//Synthesis.Programs.Program, Synthesis.Programs.Rule

IMPORTS {
	Synthesis.Values:<http://synthesis.ipi.ac.ru/Synthesis/Values/>
	Synthesis.BuiltInTypes:<http://synthesis.ipi.ac.ru/Synthesis/BuiltInTypes/>
	Synthesis.Scripts:<http://synthesis.ipi.ac.ru/Synthesis/Scripts/>
	Synthesis.Ontology:<http://synthesis.ipi.ac.ru/Synthesis/Ontology/>
	Synthesis.Formulae:<http://synthesis.ipi.ac.ru/Synthesis/Formulae/>
	Synthesis.Programs:<http://synthesis.ipi.ac.ru/Synthesis/Programs/>	
}

OPTIONS {
	//reloadGeneratorModel = "true";	
	
	tokenspace = "1";
	defaultTokenName = "IDENTIFIER";
	memoize = "true";
	usePredefinedTokens = "false";
	//resolveProxyElementsAfterParsing = "false";

}



TOKENS {
	DEFINE SL_COMMENT $'//'(~('\n'|'\r'|'\uffff'))* $;
	DEFINE ML_COMMENT $'/*'.*'*/'$;

	DEFINE WHITESPACE $(' '|'\t'|'\f')$;
	DEFINE LINEBREAKS $('\r\n'|'\r'|'\n')$;
	
	DEFINE RELATION_SYMBOL $('<' | '<=' | '>=' | '>' | '<>' | '=' | 'lt' | 'le' | 'ge' | 'gt' | 'ne' | 'eq')$;		

	DEFINE IDENTIFIER $('a'..'z' |  'A'..'Z' | '_') ('a'..'z' |  'A'..'Z' | '0'..'9' | '_')*$;

	DEFINE CHARACTER_LITERAL $'\''('\\'('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')|('\\''u'('0'..'9'|'a'..'f'|'A'..'F')('0'..'9'|'a'..'f'|'A'..'F')('0'..'9'|'a'..'f'|'A'..'F')('0'..'9'|'a'..'f'|'A'..'F'))|('\\'('0'..'3')('0'..'7')('0'..'7')|'\\'('0'..'7')('0'..'7')|'\\'('0'..'7'))|~('\''|'\\'))'\''$;
	
	DEFINE DECIMAL_INTEGER_LITERAL $('0'|('-')?('1'..'9')('0'..'9')*)$;	
	
	DEFINE DECIMAL_REAL_LITERAL $('-')? (('1'..'9') ('0'..'9')* | '0') '.' ('0'..'9')+ ('E' ('-')? ('0'|('1'..'9')('0'..'9')*))?$;
	
	DEFINE PLUS_MINUS $ '+' | '-' $;	
	DEFINE MULTIPLICATIVE_OPERATOR $ '*' | '%' $;
	DEFINE NAVIGATION_OPERATOR $'.'$;
	
    DEFINE STRING_LITERAL $'"'('\\'('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')|('\\''u'('0'..'9'|'a'..'f'|'A'..'F')('0'..'9'|'a'..'f'|'A'..'F')('0'..'9'|'a'..'f'|'A'..'F')('0'..'9'|'a'..'f'|'A'..'F'))|'\\'('0'..'7')|~('\\'|'"'))*'"'$;		 
}


TOKENSTYLES {
	"ML_COMMENT" COLOR #008000, ITALIC;
	"SL_COMMENT" COLOR #000080, ITALIC;
	"STRING_LITERAL" COLOR #2A00FF;
	"IDENTIFIER" COLOR #000000;
	"RELATION_SYMBOL" COLOR #800080;


	"schema" COLOR #7F0055, BOLD;
}


RULES {

	SchemaDef ::=
	(
		"{" name[] ";" (metaframe)? "in" ":" "schema" ";" 
		("params" ":" "{" parameters ("," parameters)* "}" ";")?
		("frame" ":" containedFrames ("," containedFrames)* ";")?
		("type" ":" containedTypes ("," containedTypes)* ";")?
		("class_specification" ":" containedClasses ("," containedClasses)* ";")?
		("function" ":" containedFunctions ("," containedFunctions)* ";")?
						
		"}"
		
		(modules)*
		
	|
		
		"{" concretizationOf[] (":" name[])? ";" "in" ":" "schema" ";"
		"actual_params" ":" "{" actualParameters ("," actualParameters)* "}"  ";"
		"}"
	)
	;

	ModuleDef ::= 
	(
		"{" name[]  ";" (metaframe)? "in" ":" "module" ("," classes[])* ";" 
		("params" ":" "{" parameters ("," parameters)* "}" ";")?
		
		("import" ":"  imports[] ";")?		
		("module_level" ":" moduleLayer[] ";")?
		("schema" ":"  schema[] ";")?
	
		("frame" ":" containedFrames ("," containedFrames)* ";")?
		("type" ":" containedTypes ("," containedTypes)* ";")?
		("class_specification" ":" containedClasses ("," containedClasses)* ";")?
		("function" ":" containedFunctions ("," containedFunctions)* ";")?		
		"}"
	|
		"{" concretizationOf[] (":" name[])? ";" "in" ":" "module" ";"
		"actual_params" ":" "{" actualParameters ("," actualParameters)* "}"  ";"
		"}"
	)	
	;	

	ActualParameterDef ::=
		parameter[] "/" (containedValue | value[] "metavalue" "in" ":" "frame" ";" "end") 					
	;
	
	ADTDef ::=
		"{" name[]  ";" (metaframe)? "in" ":" "type" ("," classes[])* ";"
		("params" ":" "{" parameters ("," parameters)* "}" ";")?
		("supertypes" ":"  supertypes[] ("," supertypes[])* ";")?    
		(attributes)*
		"}"
		|
		"{" concretizationOf[] (":" name[])? ";" "in" ":" "type" ";"
		"actual_params" ":" "{" actualParameters ("," actualParameters)* "}"  ";"
		"}"		
	;
	
	// Here attribute has two metaslots - one for builtin metaproperties and one for other metaproperties.	
	AttributeDef ::=
		name[] ":" ( type[] | containedType ) ";"
		( "metaslot"
			("card_numb" ":" cardinality[DECIMAL_INTEGER_LITERAL] ";")?
			("init" ":"  initialValue ";")?
			("in" ":"  classes[] ("," classes[])* ";")?
			("inverse" ":" inverseAttribute[] ";")?
		  "end"
		)?
		(metaslot)?
	;
	
	ClassDef ::=
		"{" name[]  ";" (metaframe)? "in" ":" "class" ("," classes[])* ";"
		("params" ":" "{" parameters ("," parameters)* "}" ";")?
		("superclass" ":"  superclasses[] ("," superclasses[])* ";")?
		("class_section" ":" (typeOfValue[] | containedTypeOfValue) ";")?		
		("instance_section" ":" (instanceType[] | containedInstanceType) ";")?    
		"}"	
		|
		"{" concretizationOf[] (":" name[])? ";" "in" ":" "class" ";"
		"actual_params" ":" "{" actualParameters ("," actualParameters)* "}"  ";"
		"}"			
	;

	FunctionDef ::=
		"{" (name[]  ";")? (metaframe)? "in" ":" ("function" | "predicate") ("," classes[])* ";"
			("params" ":" "{" parameters ("," parameters)* "}" ";")?
			("{" "predicative" ":" "{" predicativeSpec:Synthesis.Formulae.Formula "}" "}" ";")?
			(implementation)?
		"}"		
	;	

	InvariantDef ::=
		"{" (name[]  ";")? (metaframe)? "in" ":" "invariant" ";"
			("{" "predicative" ":" "{" predicativeSpec:Synthesis.Formulae.Formula "}" "}" ";")?
		"}"		
	;	

	ParameterDef ::=
		(parameterKind[PLUS_MINUS])? name[] "/" ( type[] | containedType )   (metavalue)?
	;

	MetaclassDef ::=
		"{" name[] ";" (metaframe)? "in" ":" "metaclass" ("," classes[])* ";"
		("params" ":" "{" parameters ("," parameters)* "}" ";")?
		("superclass" ":"  superclasses[] ("," superclasses[])* ";")?
		("class_section" ":" (typeOfValue[] | containedTypeOfValue) ";")?
		("instance_section" ":" (instanceType[] | containedInstanceType) ";")?
		("instance_instance_section" ":" (instInstType[] | containedInstInstType) ";")?		
		"}"
		|
		"{" concretizationOf[] (":" name[])? ";" "in" ":" "metaclass" ";"
		"actual_params" ":" "{" actualParameters ("," actualParameters)* "}"  ";"
		"}"		
	;

	AssociationMetaclassDef ::=
		"{" name[] ";" (metaframe)? "in" ":" "association" "," "metaclass" ("," classes[])* ";"
		("params" ":" "{" parameters ("," parameters)* "}" ";")?
		("superclass" ":"  superclasses[] ("," superclasses[])* ";")?
		("inverse" ":" inverse[] ";")?
		("association_type" ":" "{" "{" minCard[DECIMAL_INTEGER_LITERAL] "," maxCard[DECIMAL_INTEGER_LITERAL] "}"  
								","  "{" minInverseCard[DECIMAL_INTEGER_LITERAL] "," maxInverseCard[DECIMAL_INTEGER_LITERAL] "}" 
		                         "}" ";" )?
		("domain" ":" domain[] ("," domain[])*  ";")?
		("range" ":" range[] ("," range[])* ";" )?
		("class_section" ":" (typeOfValue[] | containedTypeOfValue) ";")?
		("instance_section" ":" (instanceType[] | containedInstanceType) ";")?
		("instance_instance_section" ":" (instInstType[] | containedInstInstType) ";")?
		"}"
		|
		"{" concretizationOf[] (":" name[])? ";" "in" ":" "association" "," "metaclass" ";"
		"actual_params" ":" "{" actualParameters ("," actualParameters)* "}"  ";"
		"}"		
	;

	FrameDef ::=
		"{" (name[] ";" )? 
		(metaframe)?
		("in" ":" classes[] ("," classes[])* ";")?
		("params" ":" "{" parameters ("," parameters)* "}" ";")?
		(slots)*
		"}"
		|
		"{" concretizationOf[] (":" name[])? ";" "in" ":" "frame" ";"
		"actual_params" ":" "{" actualParameters ("," actualParameters)* "}"  ";"
		"}"
		|
		"metaframe" (slots)* "end"
		|
		"metaslot" (slots)* "end"	
		|
		"metavalue" (slots)* "end"	
	;

	SlotDef ::=
		(name[])? ":" 
		(containedValues | values[] "metavalue" "in" ":" "frame" ";" "end")
		("," (containedValues | values[] "metavalue" "in" ":" "frame" ";" "end"))* 
		 ";"
		(metaslot)?
	;

// Ontology

	Ontology.ConceptDef ::=
		"{" name[]  ";" (metaframe)? "in" ":" "concept" ("," classes[])* ";"		
		("metaframe"  ("def" ":"  definition[STRING_LITERAL] ";")? "end")?
		("params" ":" "{" parameters ("," parameters)* "}" ";")?		
		("supertypes" ":"  supertypes[] ("," supertypes[])* ";")?    
		(attributes)*
		"}"		
		|
		"{" concretizationOf[] (":" name[])? ";" "in" ":" "concept" ";"
		"actual_params" ":" "{" actualParameters ("," actualParameters)* "}"  ";"
		"}"			
	;	

// Scripts

	Scripts.ScriptDef ::=
		"{" name[] ";" (metaframe)? "in" ":" "type" "," "script" ("," classes[])* ";"
		("params" ":" "{" parameters ("," parameters)* "}" ";")?
		("supertypes" ":"  supertypes[] ("," supertypes[])* ";")? 
		(attributes)*
		("states" ":" states ("," states)* ";")?
		("gates" ":" gates ("," gates)* ";")?
		("transitions" ":" transitions ("," transitions)* ";")?				
		"}"
		|
		"{" concretizationOf[] (":" name[])? ";" "in" ":" "script" ";"
		"actual_params" ":" "{" actualParameters ("," actualParameters)* "}"  ";"
		"}"	
	;	

	Scripts.StateDef ::=
		"{" name[] ";"
		("token" ":" "{" tokenIdentifier[] ":" (tokenType[] | containedTokenType ) "}" ";" )?
		("initial" ":" "{" initialFactor[DECIMAL_INTEGER_LITERAL] "," initialValue "}" ";")?
		"}"
	;
	
	Scripts.TransitionDef ::=
		"{"	name[] ";"
		"from" ":"  from[] ("," from[])? ";"
		("bind_from" ":" bindFrom ("," bindFrom)?  ";")?
		"to" ":" to[] ("," to[])* ";"
		("bind_to" ":" bindTo ("," bindTo)?  ";")?
		("conditions" ":" conditions "," ( conditions )* ";")?
		"action" ":" action ";"
 		"}"
	;
	
	Scripts.StateBindingDef ::=
		"{" state[] ("," parameter[])?
		("," factor[DECIMAL_INTEGER_LITERAL])? ("," condition)?
		"}"
	;

// Built-In Types

	Synthesis.BuiltInTypes.IntegerDef ::=
		(	"integer" 
		|	"{" "integer" ";"  
				("precision" ":" precision[DECIMAL_INTEGER_LITERAL]  ";" 
					("scale" ":" scale[DECIMAL_INTEGER_LITERAL]  ";" )?
					("base" ":" base[DECIMAL_INTEGER_LITERAL]  ";" )?  
				)?
			"}"
		)
	;

	Synthesis.BuiltInTypes.RealDef ::=
		(	"real" 
		|	"{" "real" ";"  
				("precision" ":" precision[DECIMAL_INTEGER_LITERAL]  ";")?
			"}"
		)
	;

	Synthesis.BuiltInTypes.BooleanDef ::=
		"Boolean"
	;

	Synthesis.BuiltInTypes.StringDef ::=
		(	"string" 
		|	"{" "string" ";"  ("length" ":" length[DECIMAL_INTEGER_LITERAL]  ";" )?  "}"
		)
	;

	Synthesis.BuiltInTypes.EnumDef ::=
		"{" "enum" ";"  "enum_list" ":" "{" enumList[IDENTIFIER] ( ";" enumList[IDENTIFIER] )*  "}"  "}"
	;

	Synthesis.BuiltInTypes.RangeDef ::=
		"{" "range" ";"  "l_bound" ":" lowerBound ";" "u_bound" ":" upperBound ";" 
		"type_of_element" ":" (rangedType[] | containedRangedType) ";"  "}"
	;

	Synthesis.BuiltInTypes.CollectionDef ::=
		"{" "collection" ";" ("in" ":"  nonobject["nonobject" : ""] ";" )?  
		"type_of_element" ":" (ofType[] | containedOfType) ";"  "}"
	;

	Synthesis.BuiltInTypes.SetDef ::=
		"{" "set" ";" ("in" ":"  nonobject["nonobject" : ""] ";" )?  
		"type_of_element" ":" (ofType[] | containedOfType) ";"  "}"
	;

	Synthesis.BuiltInTypes.BagDef ::=
		"{" "bag" ";" ("in" ":"  nonobject["nonobject" : ""] ";" )?  
		"type_of_element" ":" (ofType[] | containedOfType) ";"  "}"
	;
	
	Synthesis.BuiltInTypes.SequenceDef ::=
		"{" "sequence" ";" ("in" ":"  nonobject["nonobject" : ""] ";" )?  
		"type_of_element" ":" (ofType[] | containedOfType) ";" 
		("length" ":" length[DECIMAL_INTEGER_LITERAL]  ";" )? "}"
	;	
	
	Synthesis.BuiltInTypes.ArrayDef ::=
		"{" "array" ";" ("in" ":"  nonobject["nonobject" : ""] ";" )?
		"index" ":" index ("," index)* ";"  
		"type_of_element" ":" (ofType[] | containedOfType) ";" "}"
	;
	
	Synthesis.BuiltInTypes.UnionDef ::=
		"{" "union" ";"	"type_of_label" ":" tagType ";" 
		(elements)* "}"			
	;	
	
	Synthesis.BuiltInTypes.UnionElementDef ::=
		tag ":" (type[] | containedType) ";"		
	;	
	
	Synthesis.BuiltInTypes.ProductDef ::=
		"{" "product" ";" 
		(elements)* "}"			
	;	
	
	Synthesis.BuiltInTypes.ProductElementDef ::=
		name[] ":" (type[] | containedType) ";"					
	;		
	
	Synthesis.BuiltInTypes.TimeDef ::=
		"{" "time" ";" ("in" ":"  nonobject["nonobject" : ""] ";" )? 
		    "from" ":" "{" from[] (";" precision[DECIMAL_INTEGER_LITERAL])? "}" ";"  
		    "to" ":" "{" to[] "}" ";" 
		"}"
	;

	Synthesis.BuiltInTypes.IntervalDef ::=
		"{" "interval" ";" ("in" ":"  nonobject["nonobject" : ""] ";" )? 
		    "from" ":" "{" from[] (";" precision[DECIMAL_INTEGER_LITERAL])? "}" ";"  
		    "to" ":" "{" to[] "}" ";" 
		"}"
	;

	Synthesis.BuiltInTypes.TimeSliceDef ::=
		"{" "timeslice" ";" ("in" ":"  nonobject["nonobject" : ""] ";" )? 
		    "from" ":" "{" from[] (";" precision[DECIMAL_INTEGER_LITERAL])? "}" ";"  
		    "to" ":" "{" to[] "}" ";" 
		"}"
	;

// Programs
	Synthesis.Programs.LogicProgram ::=
		"{" (variable ("," variable)*)? "{" program "}" "}"
	;

	@Operator(type="binary_left_associative",weight="1", superclass="Program")
	Synthesis.Programs.ProgramSequence ::=
		programs ";" programs
	;

	@Operator(type="primitive",weight="2", superclass="Program")
	Synthesis.Programs.Block ::=
		"begin" program "end"
	;

	@Operator(type="primitive",weight="2", superclass="Program")	
	Synthesis.Programs.RuleList ::=
		rules (rules)*
	;	

	Synthesis.Programs.Rule ::=
		(head)? ":-" (body)? "."
	;

	Synthesis.Programs.SimpleRuleBody ::=
		formula
	;

	Synthesis.Programs.ConditionalRuleBody ::=
		"if" condition "then" program
	;

	Synthesis.Programs.IterativeRuleBody ::=
		"while" condition "do" program
	;
	
// Formulae
	@Operator(type="binary_left_associative",weight="9", superclass="Formula")
	Synthesis.Formulae.Implication ::= 
		formula "->" formula
	;

	@Operator(type="binary_left_associative",weight="10", superclass="Formula")
	Synthesis.Formulae.Equivalence ::= 
		formula "<->" formula
	;

	@Operator(type="binary_left_associative",weight="11", superclass="Formula")
	Synthesis.Formulae.Disjunction ::= 
		formula "|" formula
	;
	
	@Operator(type="binary_left_associative",weight="12", superclass="Formula")
	Synthesis.Formulae.Conjunction ::= 
		formula "&" formula
	;
	
	@Operator(type="unary_prefix", weight="13", superclass="Formula")
	Synthesis.Formulae.Negation ::= 
		"^" formula
	;

	@Operator(type="primitive", weight="15", superclass="Formula")
	Synthesis.Formulae.Atom ::=
		symbol[] "(" (terms ("," terms)* )? ")"
	;

	// Every relation have to be embraced by parenthesises
	@Operator(type="primitive", weight="15", superclass="Formula")
	Synthesis.Formulae.RelationPredicate ::=
		"(" terms symbol[RELATION_SYMBOL] terms ")"
	;
		
	@Operator(type="primitive", weight="15", superclass="Formula")
	Synthesis.Formulae.BracketFormula ::=
		"(" formula ")"
	;	

	@Operator(type="primitive", weight="15", superclass="Formula")
	Synthesis.Formulae.UniversallyQuantifiedFormula ::=
		"all" variables ("," variables)* "(" formula ")"
	;	

	@Operator(type="primitive", weight="15", superclass="Formula")
	Synthesis.Formulae.ExistentiallyQuantifiedFormula ::=
		"ex" variables ("," variables)* "(" formula ")"
	;		

	@Operator(type="binary_left_associative",weight="16", superclass="ValueDef")
	Synthesis.Formulae.AdditiveOpCall ::= 
		terms name[PLUS_MINUS] terms
	;

	@Operator(type="binary_left_associative",weight="17", superclass="ValueDef")
	Synthesis.Formulae.MultiplicativeOpCall ::= 
		terms name[MULTIPLICATIVE_OPERATOR] terms
	;

	@Operator(type="unary_postfix", weight="18", superclass="ValueDef")
	Synthesis.Formulae.NavigationPath ::= 
		terms #0 "." #0 terms (#0 "." #0 terms)* 
	;	

	// Referring to poststate is made by tilda here.
	@Operator(type="primitive", weight="20", superclass="ValueDef")
	Synthesis.Formulae.Variable ::=
		name[] ("/"  (type[] | containedType) )? refersToPoststate["~":""] 			
	;

	// Referring to poststate is made by tilda here.
	@Operator(type="primitive", weight="20", superclass="ValueDef")
	Synthesis.Formulae.FunctionCall ::=
		name[] "(" ( terms ("," terms)* )? ")" refersToPoststate["~":""]		
	;

	@Operator(type="primitive", weight="20", superclass="ValueDef")
	Synthesis.Formulae.BracketValue ::=
		"(" terms ")" 			
	;

	@Operator(type="primitive", weight="20", superclass="ValueDef")
	Synthesis.Formulae.CollectionComprehension ::=
		"{" variables ("," variables)* "|" formula "}" 			
	;
		
// Values
	@Operator(type="primitive", weight="20", superclass="ValueDef")
	Synthesis.Values.BooleanValueDef ::=
		value["true":"false"]
	;

	@Operator(type="primitive", weight="20", superclass="ValueDef")
	Synthesis.Values.IntValueDef ::=
		value[DECIMAL_INTEGER_LITERAL]
	;	

	@Operator(type="primitive", weight="20", superclass="ValueDef")
	Synthesis.Values.RealValueDef ::=
		value[DECIMAL_REAL_LITERAL]
	;	

	@Operator(type="primitive", weight="20", superclass="ValueDef")
	Synthesis.Values.StringValueDef ::=
		value[STRING_LITERAL]
	;	

	@Operator(type="primitive", weight="20", superclass="ValueDef")
	Synthesis.Values.SetValueDef ::=
	(	"{(" ")}" 
	|	"{("	
			(	containedContents ("," containedContents)*   
			//| 	contents[] ("," contents[])*
			)  
	    ")}"
	)
	;

	@Operator(type="primitive", weight="20", superclass="ValueDef")
	Synthesis.Values.BagValueDef ::=
	(	"{[" "]}"
	|	"{["  
			(	containedContents ("," containedContents)*   
			//| 	contents[] ("," contents[])*
			)  
		"]}"
	)
	;

	@Operator(type="primitive", weight="20", superclass="ValueDef")
	Synthesis.Values.SequenceValueDef ::=
	(	"[" "]"
	|	"["
			(	containedContents ("," containedContents)*   
			//| 	contents[] ("," contents[])*
			)   
		"]"
	)
	;

	@Operator(type="primitive", weight="20", superclass="ValueDef")
	Synthesis.Values.TimeConstantDef ::=
		"moment"  "(" 
		(	year[DECIMAL_INTEGER_LITERAL] "#" month[DECIMAL_INTEGER_LITERAL] "#" day[DECIMAL_INTEGER_LITERAL]
			( hour[DECIMAL_INTEGER_LITERAL] ":" minute[DECIMAL_INTEGER_LITERAL] ":" second[DECIMAL_INTEGER_LITERAL] (":" fraction[DECIMAL_INTEGER_LITERAL])? )? 
		|	hour[DECIMAL_INTEGER_LITERAL] ":" minute[DECIMAL_INTEGER_LITERAL] ":" second[DECIMAL_INTEGER_LITERAL] (":" fraction[DECIMAL_INTEGER_LITERAL])?
		) 
		")"
	;

	@Operator(type="primitive", weight="20", superclass="ValueDef")
	Synthesis.Values.IntervalConstantDef ::=
		"interval"  "(" 
		(	year[DECIMAL_INTEGER_LITERAL] "#" month[DECIMAL_INTEGER_LITERAL] 
		|	day[DECIMAL_INTEGER_LITERAL] (hour[DECIMAL_INTEGER_LITERAL] ":" minute[DECIMAL_INTEGER_LITERAL] ":" second[DECIMAL_INTEGER_LITERAL] (":" fraction[DECIMAL_INTEGER_LITERAL])?)?
		|	hour[DECIMAL_INTEGER_LITERAL] ":" minute[DECIMAL_INTEGER_LITERAL] ":" second[DECIMAL_INTEGER_LITERAL] (":" fraction[DECIMAL_INTEGER_LITERAL])?
		) 
		")"
	;
	
	@Operator(type="primitive", weight="20", superclass="ValueDef")
	Synthesis.Values.TimeSliceConstantDef ::=
		"timeslice" "(" (begin "," end | "," end | begin "," ) ")"
	;
	
// Registration
	
	// Attributes have to have unique names - in reduct taken AttributeDef is resolved by name
	Registration.ReductDef ::=
		//reducedType[] "[" taking[] ("," taking[])* "]"
		reducedType[] "[" elements ("," elements)* "]"
	;		
	
	Registration.ReductElementDef ::=
		( name[] ("/" (type[] | containedType))? ":" path #0 ("." #0 path)*  
		| path
		)
	;
	
	Registration.ReductPathElementDef ::=
		attribute[] ("/" (type[] | containedType))?
	;
}

