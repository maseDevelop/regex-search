# Regular Expression Compiler/Searcher

Returns a line of text if a regular expression is found on that line 

### Defined Grammar

E -> D

D -> C

D -> C|D

C -> T

C -> TC

T-> F

T -> F*

T -> F?

F -> \v

F -> (E)

F -> v 

F -> .

v -> Approved literal 

**Usage:**

java  REcompile *Regex String* | java REsearch Text *File to Search*
