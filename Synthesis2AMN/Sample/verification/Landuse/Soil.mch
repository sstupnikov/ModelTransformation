/* Soil
 * Author: ssa
 * Creation date: 4/9/2024
 */
MACHINE
    Soil

SEES
	LanduseDesign_Context

VARIABLES
	soilName

INVARIANT
	soilName : (ext_Soil --> seq((0 .. 255)))

INITIALISATION
	ANY asoilName WHERE
		asoilName : (ext_Soil --> seq((0 .. 255)))
	THEN
		soilName := asoilName
	END

OPERATIONS
	set_soilName(sav) = 
	PRE
		sav : (ext_Soil +-> seq((0 .. 255)))
	THEN
		soilName := (soilName <+ sav)
	END

END

