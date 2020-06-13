MACHINE MDispatcher

SEES
	FundingAgency_structure_Context

OPERATIONS
	get_experts(av) = 
	PRE
		av : ext_Dispatcher
	THEN
		skip
	END;

	check_expert(av) = 
	PRE
		av : ext_Dispatcher
	THEN
		skip
	END;

	number_of_experts <-- count_experts(av) = 
	PRE
		av : ext_Dispatcher
	THEN
		ANY number_of_experts1 WHERE
			number_of_experts1 : INTEGER
		THEN
			number_of_experts := number_of_experts1
		END
	END;

	appoint(av, revi) = 
	PRE
		av : ext_Dispatcher &
		revi : ext_Evaluation
	THEN
		skip
	END

END