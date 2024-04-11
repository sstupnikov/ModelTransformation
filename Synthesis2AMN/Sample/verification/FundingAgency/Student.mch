MACHINE Student

SEES
	FundingAgency_structure_Context

INCLUDES
	Person

VARIABLES
	isTaughtBy,
	grade,
	student

INVARIANT
	student : POW(ext_Student) &
	isTaughtBy : (ext_Student --> POW(ext_Teacher)) &
	grade : (ext_Student --> INTEGER) &
	student <: person

INITIALISATION
	ANY aisTaughtBy WHERE
		aisTaughtBy : (ext_Student --> POW(ext_Teacher))
	THEN
		isTaughtBy := aisTaughtBy
	END
	||
	ANY agrade WHERE
		agrade : (ext_Student --> INTEGER)&
		!(av).((av : ext_Student =>
			agrade(av) = 1))
	THEN
		grade := agrade
	END
	||
	student := {}

OPERATIONS
	set_isTaughtBy(sav) = 
	PRE
		sav : (ext_Student +-> POW(ext_Teacher))
	THEN
		isTaughtBy := (isTaughtBy <+ sav)
	END;

	set_grade(sav) = 
	PRE
		sav : (ext_Student +-> INTEGER)
	THEN
		grade := (grade <+ sav)
	END

END