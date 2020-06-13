MACHINE Evaluation

SEES
	FundingAgency_structure_Context

VARIABLES
	subm_ref,
	by_specialist,
	evaluation

INVARIANT
	evaluation : POW(ext_Evaluation) &
	subm_ref : (ext_Evaluation --> ext_Submission) &
	by_specialist : (ext_Evaluation --> ext_Expert)

INITIALISATION
	ANY asubm_ref WHERE
		asubm_ref : (ext_Evaluation --> ext_Submission)
	THEN
		subm_ref := asubm_ref
	END
	||
	ANY aby_specialist WHERE
		aby_specialist : (ext_Evaluation --> ext_Expert)
	THEN
		by_specialist := aby_specialist
	END
	||
	evaluation := {}

OPERATIONS
	set_subm_ref(sav) = 
	PRE
		sav : (ext_Evaluation +-> ext_Submission)
	THEN
		subm_ref := (subm_ref <+ sav)
	END;

	set_by_specialist(sav) = 
	PRE
		sav : (ext_Evaluation +-> ext_Expert)
	THEN
		by_specialist := (by_specialist <+ sav)
	END

END