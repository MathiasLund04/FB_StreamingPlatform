1. hvorfor må der ikke ligge sql i controlleren
For at adskille ansvaret, så man ikke skal gentage sig flere gange hvis man har flere controllere.
(Controlleren modtagere input fra UI og sender det videre til service/Repository hvor logikken bliver håndteret.)

2. Hvor ligger validering og hvorfor?
Validering ligger i Service-laget, så forretningsreglerne ligger et sted, og UI skal ikke gøre andet end at modtage input,sende det til service og retunere et svar.

3. hvad er fordelen ved repository interfaces
Det gør så koden er er uafhænging af databasen, så man næmt kan teste eller udskifte implementeringer.

5. nævn to eksempler på srp fra jeres kode
DbConfig: har kun ansvar for database forbindelsen.
Movie: har kun ansvar for at vise film som data.

7. hvad er fordelen ved preparedstatement
8. PreparedStatement forhindrer SQL injection og kan give bedre performance, fordi forespørgslen kan genbruges.
