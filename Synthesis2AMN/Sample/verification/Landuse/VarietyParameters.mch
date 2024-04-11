/* VarietyParameters
 * Author: ssa
 * Creation date: 4/9/2024
 */
MACHINE
    VarietyParameters

SEES
	LanduseDesign_Context

VARIABLES
	variety,
	soil,
	yield,
	cost,
	varietyParameters

INVARIANT
	varietyParameters : POW(ext_VarietyParameters) &
	variety : (ext_VarietyParameters --> ext_Variety) &
	soil : (ext_VarietyParameters --> ext_Soil) &
	yield : (ext_VarietyParameters --> INTEGER) &
	cost : (ext_VarietyParameters --> INTEGER)

INITIALISATION
	ANY avariety WHERE
		avariety : (ext_VarietyParameters --> ext_Variety)
	THEN
		variety := avariety
	END
	||
	ANY asoil WHERE
		asoil : (ext_VarietyParameters --> ext_Soil)
	THEN
		soil := asoil
	END
	||
	ANY ayield WHERE
		ayield : (ext_VarietyParameters --> INTEGER)
	THEN
		yield := ayield
	END
	||
	ANY acost WHERE
		acost : (ext_VarietyParameters --> INTEGER)
	THEN
		cost := acost
	END
	||
	varietyParameters := {}

OPERATIONS
	set_variety(sav) = 
	PRE
		sav : (ext_VarietyParameters +-> ext_Variety)
	THEN
		variety := (variety <+ sav)
	END;

	set_soil(sav) = 
	PRE
		sav : (ext_VarietyParameters +-> ext_Soil)
	THEN
		soil := (soil <+ sav)
	END;

	set_yield(sav) = 
	PRE
		sav : (ext_VarietyParameters +-> INTEGER)
	THEN
		yield := (yield <+ sav)
	END;

	set_cost(sav) = 
	PRE
		sav : (ext_VarietyParameters +-> INTEGER)
	THEN
		cost := (cost <+ sav)
	END

END
