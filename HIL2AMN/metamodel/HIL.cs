SYNTAXDEF hil
FOR <http://www.ibm.com/HIL/>
START Program

IMPORTS{
	HIL.HILType:<http://www.ibm.com/HIL/Type/>
	HIL.Expression:<http://www.ibm.com/HIL/Expression/>
}


OPTIONS {
	reloadGeneratorModel = "true";	
	
	tokenspace = "1";
	defaultTokenName = "IDENTIFIER";
	memoize = "true";
	usePredefinedTokens = "false";
	//resolveProxyElementsAfterParsing = "false";
	
	disableTokenSorting = "true";

}

TOKENS {
	DEFINE SL_COMMENT $'//'(~('\n'|'\r'|'\uffff'))* $;

	DEFINE WHITESPACE $(' '|'\t'|'\f')$;
	DEFINE LINEBREAKS $('\r\n'|'\r'|'\n')$;
	
	DEFINE IDENTIFIER $('a'..'z' |  'A'..'Z' | '_') ('a'..'z' |  'A'..'Z' | '0'..'9' | '_')*$;

	DEFINE RELATION_SYMBOL $('<>' | '=' )$;
	
	DEFINE DECIMAL_INTEGER_LITERAL $('0'|('-')?('1'..'9')('0'..'9')*)$;	
	
	DEFINE DECIMAL_REAL_LITERAL $('-')? (('1'..'9') ('0'..'9')* | '0') '.' ('0'..'9')+ ('E' ('-')? ('0'|('1'..'9')('0'..'9')*))?$;
		
    DEFINE STRING_LITERAL $'"'('\\'('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')|('\\''u'('0'..'9'|'a'..'f'|'A'..'F')('0'..'9'|'a'..'f'|'A'..'F')('0'..'9'|'a'..'f'|'A'..'F')('0'..'9'|'a'..'f'|'A'..'F'))|'\\'('0'..'7')|~('\\'|'"'))*'"'$;		 
}


TOKENSTYLES {
	"SL_COMMENT" COLOR #000080, ITALIC;
	"STRING_LITERAL" COLOR #2A00FF;
	"IDENTIFIER" COLOR #000000;
	"RELATION_SYMBOL" COLOR #800080;

}

RULES {
	Program ::= (statement)+ ;
	Declaration ::= "declare" name[IDENTIFIER] ":" type ";";
		
	Insert ::=		
		"insert" "into"	into
		"select" "[" select ("," select)* "]" 
		"from" fromClause ("," fromClause)*
		("where" where)? 
		";"
	;
	
	CreateLink ::=	
		"create" "link" name[IDENTIFIER] "as"   
		"select" "[" select ("," select)* "]"
		"from" fromClause ("," fromClause)*
		"match" "using" match ("," match)*
		("check" (check)+ )?
		(grouping)?
		(cardinality)?
		";"
	;
	
	NamedPredicate ::= name[IDENTIFIER] ":" predicate;
	IfThen ::= "if" ifPred "then" thenPred;
	LabeledExp ::= label[IDENTIFIER] ":" exp;
	Grouping ::= 
		"group" "on" groupOn ("," groupOn)* "keep" "links" "having" having
			("if" "none" "then" groupingPolicy[] "all")?
	;
	Cardinality ::= "cardinality"  left kind[] right;
	
	// HIL.Type
	HIL.HILType.IntType ::= "int";
	HIL.HILType.StringType ::= "string";
	HIL.HILType.DoubleType ::= "double";
	HIL.HILType.BooleanType ::= "boolean";	
	HIL.HILType.RecordType ::= "["  element  ("," element)* "]";
	HIL.HILType.RecordElement ::= label[IDENTIFIER] ":" type;
	HIL.HILType.SetType ::= "set" type;
	HIL.HILType.IndexType ::= "fmap" fromType "to" toType;
	HIL.HILType.FunctionType ::= "function" fromType ("," fromType)* "to" toType;	
 	
	// HIL.Expression
	HIL.Expression.AliasedEntity ::= exp alias[IDENTIFIER]; 
	HIL.Expression.IntegerLiteral ::= value[DECIMAL_INTEGER_LITERAL];
	HIL.Expression.BooleanLiteral ::= value["true":"false"];
	HIL.Expression.DoubleLiteral ::= value[DECIMAL_REAL_LITERAL];
	HIL.Expression.StringLiteral ::= value[STRING_LITERAL];
	HIL.Expression.Identifier ::= value[IDENTIFIER];
	HIL.Expression.BracketExp ::= "(" exp ")";
	HIL.Expression.RecordExp ::= "[" exp ("," exp)* "]";
	HIL.Expression.RecordExpElement ::= label[IDENTIFIER] ":" value;
	HIL.Expression.RecordProjection ::= recordId[IDENTIFIER] #0 "." #0 elementId[IDENTIFIER];
	HIL.Expression.DomExp ::= "dom" fmapId[IDENTIFIER];
	HIL.Expression.LookupExp ::= indexId[IDENTIFIER] "!" exp;
	HIL.Expression.FunctionCallExp ::= functionId[IDENTIFIER] "(" (parameter ("," parameter)*)? ")" ; 
	
	@Operator(type="primitive", weight="4", superclass="Predicate")
	HIL.Expression.UDFBooleanFunctionCall ::= functionId[IDENTIFIER] "(" (parameter ("," parameter)*)? ")" ;
		
	@Operator(type="primitive", weight="4", superclass="Predicate")	
	HIL.Expression.BinaryPredicate ::= "(" left sign[RELATION_SYMBOL] right ")";

	@Operator(type="primitive", weight="4", superclass="Predicate")	
	HIL.Expression.BracketPredicate ::= "(" pred ")";


	@Operator(type="unary_prefix", weight="2", superclass="Predicate")
	HIL.Expression.NotPredicate ::= "not" pred; 
	
	@Operator(type="binary_left_associative",weight="1", superclass="Predicate")
	HIL.Expression.AndPredicate ::= pred "and" pred;	
}