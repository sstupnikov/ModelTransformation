/* ObjectDM_Mach_GraphExt
 * Author: ssa
 * Creation date: 10/23/2023
 */
MACHINE
    ObjectDM_Mach_GraphExt

SEES
    Context
    
OPERATIONS
    
    deleteVertex(attr, cond) =
    PRE attr: NAT & cond: INT --> BOOL
    THEN
        skip
    END

END
