/* Agriculture
 * Author: ssa
 * Creation date: 4/9/2024
 */
MACHINE
    Agriculture
SEES
	LanduseDesign_Context

VARIABLES
	agricultureName

INVARIANT
	agricultureName : (ext_Agriculture --> seq((0 .. 255)))

INITIALISATION
	ANY aagricultureName WHERE
		aagricultureName : (ext_Agriculture --> seq((0 .. 255)))
	THEN
		agricultureName := aagricultureName
	END

OPERATIONS
	set_agricultureName(sav) = 
	PRE
		sav : (ext_Agriculture +-> seq((0 .. 255)))
	THEN
		agricultureName := (agricultureName <+ sav)
	END

END