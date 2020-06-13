MACHINE Symmetric

SEES
	FundingAgency_structure_Context

INCLUDES
	Person

VARIABLES
	symmetric

INVARIANT
	!(s1, s2).((s1 : ext_Person &
			s2 : ext_Person =>
		((s1 |-> s2) : av =>
			(s2 |-> s1) : av)))

INITIALISATION
	symmetric := {}

END