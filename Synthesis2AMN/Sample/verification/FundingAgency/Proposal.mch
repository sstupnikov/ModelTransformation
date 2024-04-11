MACHINE Proposal

SEES
	FundingAgency_structure_Context

VARIABLES
	area,
	proposal

INVARIANT
	proposal : POW(ext_Proposal) &
	area : (ext_Proposal --> seq((0 .. 255)))

INITIALISATION
	ANY aarea WHERE
		aarea : (ext_Proposal --> seq((0 .. 255)))
	THEN
		area := aarea
	END
	||
	proposal := {}

OPERATIONS
	set_area(sav) = 
	PRE
		sav : (ext_Proposal +-> seq((0 .. 255)))
	THEN
		area := (area <+ sav)
	END

END