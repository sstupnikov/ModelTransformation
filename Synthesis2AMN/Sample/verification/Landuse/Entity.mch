/* Entity
 * Author: ssa
 * Creation date: 4/9/2024
 */
MACHINE
    Entity

SEES
	LanduseDesign_Context

VARIABLES
	name

INVARIANT
	name : (ext_Entity --> seq((0 .. 255)))

INITIALISATION
	ANY aname WHERE
		aname : (ext_Entity --> seq((0 .. 255)))
	THEN
		name := aname
	END

OPERATIONS
	set_name(sav) = 
	PRE
		sav : (ext_Entity +-> seq((0 .. 255)))
	THEN
		name := (name <+ sav)
	END

END