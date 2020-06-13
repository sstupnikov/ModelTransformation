MACHINE Person

SEES
	FundingAgency_structure_Context

VARIABLES
	name,
	person

INVARIANT
	person : POW(ext_Person) &
	name : (ext_Person --> seq((0 .. 255)))

INITIALISATION
	ANY aname WHERE
		aname : (ext_Person --> seq((0 .. 255)))
	THEN
		name := aname
	END
	||
	person := {}

OPERATIONS
	set_name(sav) = 
	PRE
		sav : (ext_Person +-> seq((0 .. 255)))
	THEN
		name := (name <+ sav)
	END

END