/* AbstractSoil
 * Author: ssa
 * Creation date: 12/27/2023
 */
MACHINE
    AbstractSoil

SEES

    SoilContext

OPERATIONS

include_Observation(ind, results, foi, op)=
PRE ind: Obj & results: POW(Obj) & foi: Obj & op: Obj
THEN
 skip
END;
  
include_CropClass(ind, results, foi, op)=
PRE ind: Obj & results: POW(Obj) & foi: Obj & op: Obj
THEN
 skip
END;

set_hasResult(ind, val)=
PRE ind: Obj & val: POW(Obj)
THEN
 skip
END;

set_featureOfInterest(ind, val)=
PRE ind: Obj & val: Obj
THEN
 skip
END;


set_observedProperty(ind, val)=
PRE ind: Obj & val: Obj
THEN
 skip
END


END
