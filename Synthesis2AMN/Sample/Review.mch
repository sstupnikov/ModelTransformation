MACHINE Review

SEES
	FundingAgency_structure_Context

VARIABLES
	for_proposal,
	by_expert,
	review

INVARIANT
	review : POW(ext_Review) &
	for_proposal : (ext_Review --> ext_Proposal) &
	by_expert : (ext_Review --> ext_Expert)

INITIALISATION
	ANY afor_proposal WHERE
		afor_proposal : (ext_Review --> ext_Proposal)
	THEN
		for_proposal := afor_proposal
	END
	||
	ANY aby_expert WHERE
		aby_expert : (ext_Review --> ext_Expert)
	THEN
		by_expert := aby_expert
	END
	||
	review := {}

OPERATIONS
	set_for_proposal(sav) = 
	PRE
		sav : (ext_Review +-> ext_Proposal)
	THEN
		for_proposal := (for_proposal <+ sav)
	END;

	set_by_expert(sav) = 
	PRE
		sav : (ext_Review +-> ext_Expert)
	THEN
		by_expert := (by_expert <+ sav)
	END

END