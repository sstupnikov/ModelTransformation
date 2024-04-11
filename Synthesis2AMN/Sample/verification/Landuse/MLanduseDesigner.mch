/* MLanduseDesigner
 * Author: ssa
 * Creation date: 4/9/2024
 */
MACHINE
    MLanduseDesigner

SEES
	LanduseDesign_Context

OPERATIONS
	getVarieties(av) = 
	PRE
		av : ext_VarietySelector
	THEN
	    skip 
	END;    
    
    
	selectAgricultureVarieties(av, anagriculture) = 
	PRE
		av : ext_LanduseDesigner &
		anagriculture : ext_Crop
	THEN
		skip
	END;

	assignVariety(av, afield) = 
	PRE
		av : ext_LanduseDesigner &
		afield : ext_Site
	THEN
		skip
	END

END
