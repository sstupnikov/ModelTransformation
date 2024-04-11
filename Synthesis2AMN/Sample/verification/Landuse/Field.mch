/* Field
 * Author: ssa
 * Creation date: 4/9/2024
 */
MACHINE
    Field

SEES
	LanduseDesign_Context

VARIABLES
	fieldSoil,
	cultivatedVariety

INVARIANT
	fieldSoil : (ext_Field --> ext_Soil) &
	cultivatedVariety : (ext_Field --> ext_Variety)

INITIALISATION
	ANY afieldSoil WHERE
		afieldSoil : (ext_Field --> ext_Soil)
	THEN
		fieldSoil := afieldSoil
	END
	||
	ANY acultivatedVariety WHERE
		acultivatedVariety : (ext_Field --> ext_Variety)
	THEN
		cultivatedVariety := acultivatedVariety
	END

OPERATIONS
	set_fieldSoil(sav) = 
	PRE
		sav : (ext_Field +-> ext_Soil)
	THEN
		fieldSoil := (fieldSoil <+ sav)
	END;

	set_cultivatedVariety(sav) = 
	PRE
		sav : (ext_Field +-> ext_Variety)
	THEN
		cultivatedVariety := (cultivatedVariety <+ sav)
	END

END
