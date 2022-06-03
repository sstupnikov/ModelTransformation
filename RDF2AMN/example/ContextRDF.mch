/* ContextRDF
 * Author: ssa
 * Creation date: 5/25/2022
 */
MACHINE
    ContextRDF

SETS    

    // RDF Simple Interpretation 
    IR; // Resources 
    IRI // Resource Identifiers
    
DEFINITIONS
    STRING_TYPE == seq(0..255)    
    
ABSTRACT_CONSTANTS 
    
    IS_IR, // IRIs for Resources         
    IL_String,    // IRIs for String Values  
    
    NN // Quite large number to be the upper border of integer identifiers

PROPERTIES
       
    IR /= {} & 
    IS_IR: IRI +-> IR & IS_IR: FIN(IS_IR) &
    IL_String: IRI +-> STRING_TYPE & IL_String: FIN(IL_String) &
    
    NN: INT
    
    

END
