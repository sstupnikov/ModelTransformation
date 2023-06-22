MACHINE Dispatcher

SEES
	FundingAgency_structure_Context

VARIABLES
	experts,
	experts_got,
	relevant_experts,
	dispatcher

INVARIANT
	dispatcher : POW(ext_Dispatcher) &
	experts : (ext_Dispatcher --> POW(ext_Specialist)) &
	experts_got : (ext_Dispatcher --> BOOL) &
	relevant_experts : (ext_Dispatcher --> POW(ext_Specialist))

INITIALISATION
	ANY aexperts WHERE
		aexperts : (ext_Dispatcher --> POW(ext_Specialist))&
		!(av).((av : ext_Dispatcher =>
			aexperts(av) = {}))
	THEN
		experts := aexperts
	END
	||
	ANY aexperts_got WHERE
		aexperts_got : (ext_Dispatcher --> BOOL)&
		!(av).((av : ext_Dispatcher =>
			aexperts_got(av) = FALSE))
	THEN
		experts_got := aexperts_got
	END
	||
	ANY arelevant_experts WHERE
		arelevant_experts : (ext_Dispatcher --> POW(ext_Specialist))&
		!(av).((av : ext_Dispatcher =>
			arelevant_experts(av) = {}))
	THEN
		relevant_experts := arelevant_experts
	END
	||
	dispatcher := {}

OPERATIONS
	set_experts(sav) = 
	PRE
		sav : (ext_Dispatcher +-> POW(ext_Specialist))
	THEN
		experts := (experts <+ sav)
	END;

	set_experts_got(sav) = 
	PRE
		sav : (ext_Dispatcher +-> BOOL)
	THEN
		experts_got := (experts_got <+ sav)
	END;

	set_relevant_experts(sav) = 
	PRE
		sav : (ext_Dispatcher +-> POW(ext_Specialist))
	THEN
		relevant_experts := (relevant_experts <+ sav)
	END

END