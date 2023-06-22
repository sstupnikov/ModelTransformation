MACHINE Employee

SEES
	FundingAgency_structure_Context

VARIABLES
	first_name,
	last_name,
	employee

INVARIANT
	employee : POW(ext_Employee) &
	first_name : (ext_Employee --> seq((0 .. 255))) &
	last_name : (ext_Employee --> seq((0 .. 255)))

INITIALISATION
	ANY afirst_name WHERE
		afirst_name : (ext_Employee --> seq((0 .. 255)))
	THEN
		first_name := afirst_name
	END
	||
	ANY alast_name WHERE
		alast_name : (ext_Employee --> seq((0 .. 255)))
	THEN
		last_name := alast_name
	END
	||
	employee := {}

OPERATIONS
	set_first_name(sav) = 
	PRE
		sav : (ext_Employee +-> seq((0 .. 255)))
	THEN
		first_name := (first_name <+ sav)
	END;

	set_last_name(sav) = 
	PRE
		sav : (ext_Employee +-> seq((0 .. 255)))
	THEN
		last_name := (last_name <+ sav)
	END

END