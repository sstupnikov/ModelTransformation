/* Site
 * Author: ssa
 * Creation date: 4/9/2024
 */
MACHINE
    Site

SEES
	LanduseDesign_Context

VARIABLES
	soilType,
	grownCultivar

INVARIANT
	soilType : (ext_Site --> ext_SoilType) &
	grownCultivar : (ext_Site --> ext_Cultivar)

INITIALISATION
	ANY asoilType WHERE
		asoilType : (ext_Site --> ext_SoilType)
	THEN
		soilType := asoilType
	END
	||
	ANY agrownCultivar WHERE
		agrownCultivar : (ext_Site --> ext_Cultivar)
	THEN
		grownCultivar := agrownCultivar
	END

OPERATIONS
	set_soilType(sav) = 
	PRE
		sav : (ext_Site +-> ext_SoilType)
	THEN
		soilType := (soilType <+ sav)
	END;

	set_grownCultivar(sav) = 
	PRE
		sav : (ext_Site +-> ext_Cultivar)
	THEN
		grownCultivar := (grownCultivar <+ sav)
	END

END
