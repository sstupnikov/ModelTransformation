MACHINE Teaches

INCLUDES
	Teacher

INVARIANT
	!(tt).((tt : ext_Teacher =>
		(degree(tt) /= PhD =>
			card({ss | ss : ext_Student &
					(tt |-> ss) : av}) <= 5)))

END