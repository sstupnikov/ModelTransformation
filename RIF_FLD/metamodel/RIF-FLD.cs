SYNTAXDEF rifps
FOR <http://www.emftext.org/language/RIF_FLD>
START Document

OPTIONS {
	//reloadGeneratorModel = "true";
	
	tokenspace = "1";
	//defaultTokenName = "IDENTIFIER";
	memoize = "true";
	usePredefinedTokens = "false";	
	
}

TOKENS{
	DEFINE WHITESPACE $(' '|'\t'|'\f')$;
	DEFINE LINEBREAKS $('\r\n'|'\r'|'\n')$;
	
	DEFINE IDENTIFIER $('a'..'z' |  'A'..'Z' | '_' | '-' | ':') ('a'..'z' |  'A'..'Z' | '0'..'9' | '_' | '-' | ':')*$;

	DEFINE CHARACTER_LITERAL $'\''('\\'('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')|('\\''u'('0'..'9'|'a'..'f'|'A'..'F')('0'..'9'|'a'..'f'|'A'..'F')('0'..'9'|'a'..'f'|'A'..'F')('0'..'9'|'a'..'f'|'A'..'F'))|('\\'('0'..'3')('0'..'7')('0'..'7')|'\\'('0'..'7')('0'..'7')|'\\'('0'..'7'))|~('\''|'\\'))'\''$;
	DEFINE STRING_LITERAL $'"'('\\'('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')|('\\''u'('0'..'9'|'a'..'f'|'A'..'F')('0'..'9'|'a'..'f'|'A'..'F')('0'..'9'|'a'..'f'|'A'..'F')('0'..'9'|'a'..'f'|'A'..'F'))|'\\'('0'..'7')|~('\\'|'"'))*'"'$;
	DEFINE DECIMAL_INTEGER_LITERAL $('0'|('-')?('1'..'9')('0'..'9')*)$;
	DEFINE DECIMAL_REAL_LITERAL $('-')? (('1'..'9') ('0'..'9')* | '0') '.' ('0'..'9')+ ('E' ('-')? ('0'|('1'..'9')('0'..'9')*))?$;

	DEFINE QUANTIFIER $ 'Forall' | 'Exists' $;	
	DEFINE CONNECTIVE $ 'And' | 'Or' $;
	
}


TOKENSTYLES {
	"STRING_LITERAL" COLOR #2A00FF;
	"IDENTIFIER" COLOR #000000;
}

RULES {
	Document ::= "Document" "(" group? ")";

	Group ::= "Group" "(" content* ")";

	Quantified ::= symbol[QUANTIFIER] var+ "(" termula ")";
	
	Var ::= "?" #0 name;

	Name ::= stringRepr[IDENTIFIER];
	
	Implies ::= "(*IMPLIES*)" left? ":-" right ;
	
	Compound ::= symbol[CONNECTIVE] #0 "(" termula* ")";
	
	Member ::= "(" left "#" right ")";
	
	Equal ::= "(" left "=" right ")";
	
	WeakConstraint ::= ":~" termula;
	
	Neg ::= "Neg" termula;
	
	Naf ::= "Naf" termula;
	
	Frame ::= "(*FRAME*)" termula #0 "[" assignment* "]"; 

	Remote ::= "(*REMOTE*)" termula #0 "@" #0 moduleRef;

	PropertyAssignment ::= property  "->"  value;
		
	Constant ::= 
		( value[STRING_LITERAL] #0 "^^" #0 symbolSpace 
		| value[IDENTIFIER]
		)
	;
		
	IRI ::= stringRepr[IDENTIFIER];	

	External ::= "External" #0 "(" termula ")";
	
	PositionalAtom ::= "(*P-ATOM*)" termula #0 "(" arg* ")";
	
	NamedArgumentAtom ::= "(*NA-ATOM*)" termula #0 "(" arg* ")";
	
	NamedArgument ::=  name "->"  value;
	
	
}