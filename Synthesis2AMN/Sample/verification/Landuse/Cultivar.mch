/* Cultivar
 * Author: ssa
 * Creation date: 4/9/2024
 */
MACHINE
    Cultivar

SEES
	LanduseDesign_Context

EXTENDS
	Entity

VARIABLES
	crop,
	soilTypes,
	cultivars

INVARIANT
	cultivars : POW(ext_Cultivar) &
	crop : (ext_Cultivar --> ext_Crop) &
	soilTypes : (ext_Cultivar --> POW(ext_SoilType))

INITIALISATION
	ANY acrop WHERE
		acrop : (ext_Cultivar --> ext_Crop)
	THEN
		crop := acrop
	END
	||
	ANY asoilTypes WHERE
		asoilTypes : (ext_Cultivar --> POW(ext_SoilType))
	THEN
		soilTypes := asoilTypes
	END
	||
	cultivars := {}

OPERATIONS
	set_crop(sav) = 
	PRE
		sav : (ext_Cultivar +-> ext_Crop)
	THEN
		crop := (crop <+ sav)
	END;

	set_soilTypes(sav) = 
	PRE
		sav : (ext_Cultivar +-> POW(ext_SoilType))
	THEN
		soilTypes := (soilTypes <+ sav)
	END

END
