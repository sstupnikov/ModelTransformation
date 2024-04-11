MACHINE Researcher

SEES
	FundingAgency_structure_Context

EXTENDS
	Person

VARIABLES
	research_area,
	researcher

INVARIANT
	researcher : POW(ext_Researcher) &
	research_area : (ext_Researcher --> Researcher_research_area_Enum) &
	researcher <: person

INITIALISATION
	ANY aresearch_area WHERE
		aresearch_area : (ext_Researcher --> Researcher_research_area_Enum)&
		!(av).((av : ext_Researcher =>
			aresearch_area(av) = biology))
	THEN
		research_area := aresearch_area
	END
	||
	researcher := {}

OPERATIONS
	set_research_area(sav) = 
	PRE
		sav : (ext_Researcher +-> Researcher_research_area_Enum)
	THEN
		research_area := (research_area <+ sav)
	END

END