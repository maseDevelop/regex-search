#Regular Expression Compiler/Searcher

Returns a line of text if a regular expression is found on that line 

## Defined Grammar

E -> D

E -> D|E

D -> C

D -> TC

C -> T

T -> F*

T -> F?

F -> \v

F -> (E)

F -> v 

F -> .

**Usage:**

java  REcompile *Regex String* | java REsearch Text *File to Search*
