MACHINE FundingAgency_Context

SETS
	AVAL;
	OID

ABSTRACT_CONSTANTS
	Obj,
	self

PROPERTIES
	Obj : POW(AVAL) &
	self : (Obj >->> OID)

END