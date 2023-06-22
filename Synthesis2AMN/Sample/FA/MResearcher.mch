MACHINE MResearcher

SEES
	FundingAgency_structure_Context

OPERATIONS
	ss <-- ee(av, ii, bb, pp, st) = 
	PRE
		av : ext_Researcher &
		ii : INTEGER &
		bb : BOOL &
		pp : ext_Person &
		st : POW(ext_Person)
	THEN
		ANY ss1 WHERE
			ss1 : seq((0 .. 255))
		THEN
			ss := ss1
		END
	END

END