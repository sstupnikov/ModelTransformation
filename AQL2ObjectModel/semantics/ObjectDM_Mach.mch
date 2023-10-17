/* ObjectDM_Mach
 * Author: ssa
 * Creation date: 10/13/2023
 */
MACHINE
    ObjectDM_Mach

SEES
    Context
    
OPERATIONS
    
    update1(cls, attr, exp, cond) =
    PRE cls: STRING_Type & attr: NAT & exp: INT --> INT & cond: NAT --> BOOL
    THEN
        skip
    END

END
