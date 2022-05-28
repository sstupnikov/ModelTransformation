/* HospitalAM
 * Author: ssa
 * Creation date: 5/25/2022
 */
MACHINE
    HospitalAM

SEES ContextRDF

DEFINITIONS
    STRING_TYPE == seq(0..255)


OPERATIONS

res <--  patientsWithTumorOfStageIIIa =
BEGIN
		ANY res1 WHERE
			res1: POW(struct(name: STRING_TYPE))
		THEN
			res := res1
		END    
END

END
