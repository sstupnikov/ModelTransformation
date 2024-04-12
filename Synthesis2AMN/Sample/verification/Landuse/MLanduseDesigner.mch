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
    
    
	searchCropCultivars(av, acrop) = 
	PRE
		av : ext_LanduseDesigner &
		acrop : ext_Crop
	THEN
		skip
	END;

	selectCultivar(av, asite) = 
	PRE
		av : ext_LanduseDesigner &
		asite : ext_Site
	THEN
		skip
	END

END
