MACHINE Expert

SEES
	FundingAgency_structure_Context


EXTENDS
	Researcher

VARIABLES
	area_of_expertise,
	expert

INVARIANT
	expert : POW(ext_Expert) &
	area_of_expertise : (ext_Expert --> seq((0 .. 255))) &
	expert <: researcher

INITIALISATION
	ANY aarea_of_expertise WHERE
		aarea_of_expertise : (ext_Expert --> seq((0 .. 255)))
	THEN
		area_of_expertise := aarea_of_expertise
	END
	||
	expert := {}

OPERATIONS
	set_area_of_expertise(sav) = 
	PRE
		sav : (ext_Expert +-> seq((0 .. 255)))
	THEN
		area_of_expertise := (area_of_expertise <+ sav)
	END

END