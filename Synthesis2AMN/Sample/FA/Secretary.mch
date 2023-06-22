MACHINE Secretary

SEES
	FundingAgency_structure_Context

VARIABLES
	available_experts,
	secretary

INVARIANT
	secretary : POW(ext_Secretary) &
	available_experts : (ext_Secretary --> POW(ext_Expert))

INITIALISATION
	ANY aavailable_experts WHERE
		aavailable_experts : (ext_Secretary --> POW(ext_Expert))&
		!(av).((av : ext_Secretary =>
			aavailable_experts(av) = {}))
	THEN
		available_experts := aavailable_experts
	END
	||
	secretary := {}

OPERATIONS
	set_available_experts(sav) = 
	PRE
		sav : (ext_Secretary +-> POW(ext_Expert))
	THEN
		available_experts := (available_experts <+ sav)
	END

END