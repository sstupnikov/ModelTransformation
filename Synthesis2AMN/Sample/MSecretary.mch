MACHINE MSecretary

SEES
	FundingAgency_structure_Context

OPERATIONS
	number_of_experts <-- search_for_experts(av) = 
	PRE
		av : ext_Secretary
	THEN
		ANY number_of_experts1 WHERE
			number_of_experts1 : INTEGER
		THEN
			number_of_experts := number_of_experts1
		END
	END;

	dispatch(av, revi) = 
	PRE
		av : ext_Secretary &
		revi : ext_Review
	THEN
		skip
	END

END