MACHINE Teacher

SEES
	FundingAgency_structure_Context

INCLUDES
	Student,
	Person,
	Symmetric,
	Proposal,
	Evaluation

EXTENDS
	Specialist

VARIABLES
	teaches,
	teacher,
	teachesAM

INVARIANT
	teacher : POW(ext_Teacher) &
	teachesAM : POW(((teacher \/ proposal) <-> (student \/ evaluation))) &
	teachesAM = {av | av : ((teacher \/ proposal) <-> (student \/ evaluation)) &
		!(tt).((tt : ext_Teacher =>
			(degree(tt) /= PhD =>
				card({ss | ss : ext_Student &
						(tt |-> ss) : av}) <= 5))) &
		av : symmetric &
		!(rr).((rr : (student \/ evaluation) =>
			card({dd | dd : (teacher \/ proposal) &
					(dd |-> rr) : av}) >= 3 &
				card({dd | dd : (teacher \/ proposal) &
					(dd |-> rr) : av}) <= 10)) &
		!(dd).((dd : (teacher \/ proposal) =>
			card({rr | rr : (student \/ evaluation) &
					(dd |-> rr) : av}) >= 1 &
				card({rr | rr : (student \/ evaluation) &
					(dd |-> rr) : av}) <= 2))} &
	teaches : (ext_Teacher --> POW(ext_Student)) &
	{pair | pair : (ext_Teacher * ext_Student) &
		#(dd, rr).(dd : ext_Teacher &
			rr : ext_Student &
			(dd |-> rr) = pair &
			rr : teaches(dd))} : teachesAM &
	!(av).((av : ext_Teacher =>
		card(teaches(av)) <= 15)) &
	{pair | pair : (ext_Teacher * ext_Student) &
		#(dd, rr).(dd : ext_Teacher &
			rr : ext_Student &
			(dd |-> rr) = pair &
			rr : teaches(dd))} = {pair | pair : (ext_Student * ext_Teacher) &
		#(dd, rr).(dd : ext_Student &
			rr : ext_Teacher &
			(dd |-> rr) = pair &
			rr : isTaughtBy(dd))}~ &
	teacher <: person

INITIALISATION
	ANY ateaches WHERE
		ateaches : (ext_Teacher --> POW(ext_Student))
	THEN
		teaches := ateaches
	END
	||
	teacher := {}
	||
	teachesAM := {}

OPERATIONS
	set_teaches(sav) = 
	PRE
		sav : (ext_Teacher +-> POW(ext_Student))
	THEN
		teaches := (teaches <+ sav)
	END

END