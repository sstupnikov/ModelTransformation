/* LanduseDesign_Context
 * Author: ssa
 * Creation date: 4/9/2024
 */
MACHINE
    LanduseDesign_Context
SETS
	AVAL;
	OID

ABSTRACT_CONSTANTS
	Obj,
	self,
	
	extp_Entity,
	ext_Entity,
	extp_SoilType,
	ext_SoilType,
	extp_Crop,
	ext_Crop,
	extp_Cultivar,
	ext_Cultivar,
	extp_Site,
	ext_Site,
	extp_LanduseDesigner,
	ext_LanduseDesigner,
	
	extp_Soil,
	ext_Soil,
	extp_Agriculture,
	ext_Agriculture,
	extp_Variety,
	ext_Variety,
	extp_Field,
	ext_Field,
	extp_VarietyParameters,
	ext_VarietyParameters,
	extp_VarietySelector,
	ext_VarietySelector	

PROPERTIES
	Obj : POW(AVAL) &
	self : (Obj >->> OID) &
	extp_Entity : POW(Obj) &
	ext_Entity : POW(Obj) &
	extp_Entity <: ext_Entity &
	extp_SoilType : POW(Obj) &
	ext_SoilType : POW(Obj) &
	extp_SoilType <: ext_SoilType &
	extp_Crop : POW(Obj) &
	ext_Crop : POW(Obj) &
	extp_Crop <: ext_Crop &
	extp_Cultivar : POW(Obj) &
	ext_Cultivar : POW(Obj) &
	extp_Cultivar <: ext_Cultivar &
	extp_Site : POW(Obj) &
	ext_Site : POW(Obj) &
	extp_Site <: ext_Site &
	extp_LanduseDesigner : POW(Obj) &
	ext_LanduseDesigner : POW(Obj) &
	extp_LanduseDesigner <: ext_LanduseDesigner &
	(extp_Entity /\ extp_SoilType) = {} &
	(extp_Entity /\ extp_Crop) = {} &
	(extp_Entity /\ extp_Cultivar) = {} &
	(extp_Entity /\ extp_Site) = {} &
	(extp_Entity /\ extp_LanduseDesigner) = {} &
	(extp_SoilType /\ extp_Crop) = {} &
	(extp_SoilType /\ extp_Cultivar) = {} &
	(extp_SoilType /\ extp_Site) = {} &
	(extp_SoilType /\ extp_LanduseDesigner) = {} &
	(extp_Crop /\ extp_Cultivar) = {} &
	(extp_Crop /\ extp_Site) = {} &
	(extp_Crop /\ extp_LanduseDesigner) = {} &
	(extp_Cultivar /\ extp_Site) = {} &
	(extp_Cultivar /\ extp_LanduseDesigner) = {} &
	(extp_Site /\ extp_LanduseDesigner) = {} &
	ext_SoilType <<: ext_Entity &
	ext_Crop <<: ext_Entity &
	ext_Cultivar <<: ext_Entity &


	extp_Soil : POW(Obj) & 
	ext_Soil : POW(Obj) &
	extp_Soil <: ext_Soil &
	extp_Agriculture : POW(Obj) &
	ext_Agriculture : POW(Obj) &
	extp_Agriculture <: ext_Agriculture &
	extp_Variety : POW(Obj) &
	ext_Variety : POW(Obj) &
	extp_Variety <: ext_Variety &
	extp_Field : POW(Obj) &
	ext_Field : POW(Obj) &
	extp_Field <: ext_Field &
	extp_VarietyParameters : POW(Obj) &
	ext_VarietyParameters : POW(Obj) &
	extp_VarietyParameters <: ext_VarietyParameters &
	extp_VarietySelector : POW(Obj) &
	ext_VarietySelector : POW(Obj) &
	extp_VarietySelector <: ext_VarietySelector &
	(extp_Soil /\ extp_Agriculture) = {} &
	(extp_Soil /\ extp_Variety) = {} &
	(extp_Soil /\ extp_Field) = {} &
	(extp_Soil /\ extp_VarietyParameters) = {} &
	(extp_Soil /\ extp_VarietySelector) = {} &
	(extp_Agriculture /\ extp_Variety) = {} &
	(extp_Agriculture /\ extp_Field) = {} &
	(extp_Agriculture /\ extp_VarietyParameters) = {} &
	(extp_Agriculture /\ extp_VarietySelector) = {} &
	(extp_Variety /\ extp_Field) = {} &
	(extp_Variety /\ extp_VarietyParameters) = {} &
	(extp_Variety /\ extp_VarietySelector) = {} &
	(extp_Field /\ extp_VarietyParameters) = {} &
	(extp_Field /\ extp_VarietySelector) = {} &
	(extp_VarietyParameters /\ extp_VarietySelector) = {} &
	
	extp_Soil = extp_SoilType &
	ext_Soil = ext_SoilType &
	extp_Crop = extp_Agriculture &
	ext_Crop = ext_Agriculture &
	extp_Cultivar = extp_Variety &
	ext_Cultivar = ext_Variety &	
	extp_Site = extp_Field &	
	ext_Site = ext_Field &		
	extp_LanduseDesigner = extp_VarietySelector &		
	ext_LanduseDesigner = ext_VarietySelector 		
END