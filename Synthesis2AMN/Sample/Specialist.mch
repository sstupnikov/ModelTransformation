MACHINE Specialist

SEES
	FundingAgency_structure_Context

EXTENDS
	Employee

VARIABLES
	research_field,
	degree,
	field_of_expertise,
	specialist

INVARIANT
	specialist : POW(ext_Specialist) &
	research_field : (ext_Specialist --> Specialist_research_field_Enum) &
	degree : (ext_Specialist --> Specialist_degree_Enum) &
	field_of_expertise : (ext_Specialist --> seq((0 .. 255))) &
	specialist <: employee

INITIALISATION
	ANY aresearch_field WHERE
		aresearch_field : (ext_Specialist --> Specialist_research_field_Enum)
	THEN
		research_field := aresearch_field
	END
	||
	ANY adegree WHERE
		adegree : (ext_Specialist --> Specialist_degree_Enum)
	THEN
		degree := adegree
	END
	||
	ANY afield_of_expertise WHERE
		afield_of_expertise : (ext_Specialist --> seq((0 .. 255)))
	THEN
		field_of_expertise := afield_of_expertise
	END
	||
	specialist := {}

OPERATIONS
	set_research_field(sav) = 
	PRE
		sav : (ext_Specialist +-> Specialist_research_field_Enum)
	THEN
		research_field := (research_field <+ sav)
	END;

	set_degree(sav) = 
	PRE
		sav : (ext_Specialist +-> Specialist_degree_Enum)
	THEN
		degree := (degree <+ sav)
	END;

	set_field_of_expertise(sav) = 
	PRE
		sav : (ext_Specialist +-> seq((0 .. 255)))
	THEN
		field_of_expertise := (field_of_expertise <+ sav)
	END

END