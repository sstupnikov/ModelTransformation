MACHINE           AbstractWines

SEES

WinesContext

OPERATIONS

include_Wine(ind, color, flavor, sugar)=
PRE ind: Ind & color: WineColor & flavor: WineFlavor & sugar: WineSugar
THEN
 skip
END;
  
include_IceWine(ind, color, flavor, sugar)=
PRE ind: Ind & color: WineColor & flavor: WineFlavor & sugar: WineSugar
THEN
 skip
END;

include_DessertWine(ind, color, flavor, sugar)=
PRE ind: Ind & color: WineColor & flavor: WineFlavor & sugar: WineSugar
THEN
 skip
END;

set_hasColor(ind, val)=
PRE ind: Ind & val: WineColor
THEN
 skip
END;

set_hasSugar(ind, val)=
PRE ind: Ind & val: WineSugar
THEN
 skip
END;


set_hasFlavor(ind, val)=
PRE ind: Ind & val: WineFlavor
THEN
 skip
END



END
