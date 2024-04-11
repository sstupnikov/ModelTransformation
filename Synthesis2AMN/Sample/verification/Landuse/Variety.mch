/* Variety
 * Author: ssa
 * Creation date: 4/9/2024
 */
MACHINE
    Variety

SEES
	LanduseDesign_Context

VARIABLES
	varietyName,
	agriculture,
	soils,
	price,
	varieties

INVARIANT
	varieties : POW(ext_Variety) &
	varietyName : (ext_Variety --> seq((0 .. 255))) &
	agriculture : (ext_Variety --> ext_Agriculture) &
	soils : (ext_Variety --> POW(ext_Soil)) &
	price : (ext_Variety --> INTEGER)

INITIALISATION
	ANY avarietyName WHERE
		avarietyName : (ext_Variety --> seq((0 .. 255)))
	THEN
		varietyName := avarietyName
	END
	||
	ANY aagriculture WHERE
		aagriculture : (ext_Variety --> ext_Agriculture)
	THEN
		agriculture := aagriculture
	END
	||
	ANY asoils WHERE
		asoils : (ext_Variety --> POW(ext_Soil))&
		!(av).((av : ext_Variety =>
			asoils(av) = {}))
	THEN
		soils := asoils
	END
	||
	ANY aprice WHERE
		aprice : (ext_Variety --> INTEGER)
	THEN
		price := aprice
	END
	||
	varieties := {}

OPERATIONS
	set_varietyName(sav) = 
	PRE
		sav : (ext_Variety +-> seq((0 .. 255)))
	THEN
		varietyName := (varietyName <+ sav)
	END;

	set_agriculture(sav) = 
	PRE
		sav : (ext_Variety +-> ext_Agriculture)
	THEN
		agriculture := (agriculture <+ sav)
	END;

	set_soils(sav) = 
	PRE
		sav : (ext_Variety +-> POW(ext_Soil))
	THEN
		soils := (soils <+ sav)
	END;

	set_price(sav) = 
	PRE
		sav : (ext_Variety +-> INTEGER)
	THEN
		price := (price <+ sav)
	END

END
