/*
 * Copyright shanki. All rights reserved.
 */

grammar ErGrammar;

CARDINALITY             : 'ONE' | 'MANY';

ID                      : [a-zA-Z] (ID_LETTER | DIGIT)* ;
fragment ID_LETTER	: 'a'..'z' | 'A'..'Z' | '_' ;
fragment DIGIT		: '0'..'9' ;

INTEGER                 : '-'? DIGIT+;
DECIMAL                 : '-'? DIGIT+ '.' DIGIT+
                        | '-'? '.' DIGIT+
                        ;

WS                      : [ \t\r\n]+ -> skip;

STRING                  : '"' (ESC | .)*? '"';
fragment ESC            : '\\' [btnr"\\] ;

text                    : text_id
                        | text_string
                        ;

text_id                 : value=ID;
text_string             : value=STRING;

number                  : number_integer
                        | number_decimal
                        ;

number_integer          : value=INTEGER;
number_decimal          : value=DECIMAL;




model                   : (statement ';')* ;

statement               : entity_set_definition
                        | association_definition
						| generalization_definition
                        ;

entity_set_definition   : 'entity set' name=text '{' attribute* '}';
attribute               : value=text;

association_definition 				: 'association' name=text? 'between' '{' side* '}' '{' attribute* '}' ;
generalization_definition 			: 'generalization' name=text? 'from' from=text 'to' to=text;
side                    			: part=text CARDINALITY? ('as' role=text)?;