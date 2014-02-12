
% Arve Nygård
% 10.02.2014


First sets
----------

FIRST(A): {a} + FIRST(D) + FIRST(F)
				= {a, b, c, d, e, f, $\varepsilon$}

FIRST(B): {b}

FIRST(C): {b, $\varepsilon$}

FIRST(D): FIRST(E) + {d}
				= {e} - {$\varepsilon} FIRST(B) + {d}
				= {b, d, e, $\varepsilon$}

FIRST(E): {e, $\varepsilon$}

FIRST(F): {c, f}


Follow sets
-----------
FOLLOW(A): $

FOLLOW(B): FOLLOW(D) + FOLLOW(A) + FIRST(E)
         = {e, $}

FOLLOW(C): FOLLOW(B) + FOLLOW(C) = FOLLOW(B)
         = {e, $}

FOLLOW(D): FOLLOW(A) 
         = {$}

FOLLOW(E): FIRST(B) + FIRST(F)
         = {b, c, f}

FOLLOW(F): FOLLOW(A) + FOLLOW(D)
         = {$}


|   |               FIRST               |    FOLLOW    |
|---+-----------------------------------+--------------|
| A | {a, b, c, d, e, f, $\varepsilon$} | {$}          |
| B | {b}                               | {e, $}       |
| C | {b, $\varepsilon$}                | {e, $}       |
| D | {b, d, e, $\varepsilon$}          | {$}          |
| E | {e, $\varepsilon$}                | {b, c, f}    |
| F |	{c, f}                            | {$}          |

  : `FIRST` and `FOLLOW` sets