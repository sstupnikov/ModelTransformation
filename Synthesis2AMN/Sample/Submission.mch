MACHINE Submission

SEES
	FundingAgency_structure_Context

VARIABLES
	field

INVARIANT
	field : (ext_Submission --> seq((0 .. 255)))

INITIALISATION
	ANY afield WHERE
		afield : (ext_Submission --> seq((0 .. 255)))
	THEN
		field := afield
	END

OPERATIONS
	set_field(sav) = 
	PRE
		sav : (ext_Submission +-> seq((0 .. 255)))
	THEN
		field := (field <+ sav)
	END

END