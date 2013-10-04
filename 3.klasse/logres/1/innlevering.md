Øving 1 Logikk og Resonnerende Systemer
=========================================

_Tibor Vucovic, Arve Nygård_

1. Hva er en Turing-test?
-------------------------
En Turning test tester om en datamaskin greier å imitere menneskets oppførsel  til den grad at et gitt menneske oppfatter maskinen som et medmenneske. Testen blir gjennomført ved at et menneske kommuniserer med en maskin og annet et menneske, ved hjelp av tastatur og skjerm slik at den ikke kan skille dem fysisk fra hverandre. Hvis mennesket, på slutten av testen, ikke greier å avgjøre om det kommuniserte med en maskin eller et menneske, har maskinen bestått testen.

2. Hva er forholdet mellom å tenke rasjonelt og å handle rasjonelt?
-------------------------------------------------------------------
Å tenke rasjonelt innebærer å tenke “logisk” eller det å komme fram til nye logiske sannheter utifra tidligere antatte sannheter ved logikk (dedusere seg frem).
Å handle rasjonelt innebærer å handle slik at man makisimaliserer oppnåelsen av satte mål.

3. Hva handler Tarski’s `theory of reference` om?
-------------------------------------------------
Alfred Tarski’s theory of reference handler om hvordan vi kan se sammenhengen mellom logiske objekt er og objekter fra den virkelige verden

4.Beskriv rasjonalitet. Hvordan er det definert?
------------------------------------------------
Rasjonalitet er det som er i samsvar med fornuften og kan definers som besittelse eller bruk av logikk eller fornuft. 

5.  Robot krysser veien
-----------------------
###a) Helikopter faller ned på roboten. Er roboten rasjonell?

Robotens handlinger var rasjonelle hvis den handlet slik at den maksimaliserte oppnåelsen av sitt mål, som i dette tilfellet vil si å unngå kollisjoner. Robotens sensorer gjør den ikke istand til å oppfatte ting som kommer ovenifra, så dens mangel på handling for å unngå en kollisjon med et fallende objekt gjør den ikke irrasjonell på grunn av dette. Utifra robotens funksjoner og sensorer handlet den rasjonelt.

###b) Grønt lys, bil krasjer inn i roboten. Er roboten rasjonell?
I dette tilfelle skal roboten kunne ha oppfattet sine omgivelser til den grad at en rasjonell handling ville ha unngått kollisjonen. Dette gjør roboten irrasjonel i dette tilfellet.

6. Støvsuger
------------
### a) Kan en enkel refleks-agent være rasjonell i disse omgivelsene?
Rasjonalitet i dette tilfellet er å oppnå flest mulig poeng ved å unngå felst mulig minuspoeng for flytting og samtidig også sanke poeng for rengjøring. En agent som ikke tar hennsyn til minuspoeng for flytting, vil ikke være rasjonell siden den ikke maksimaliserer poengsummen sin. En simpel “refleks agent” vil ikke kunne ha noen form for “minne” om det den har vasket et sted før, og vil muligens flytte til same poisjon flere ganger og slik få unødvendige minuspoeng. Altså vil dette ikke være rasjonelt.

### b) Kan en refleks-agent med tilstand være rasjonell i disse omgivelsene? 
En agent med en tilstand kan holde orden på om den trenger å skifte blokk eller om den allerede har skiftet blokk, slik at den kan få maks mulig poengsum. Dette gjør den rasjonell i dette miljøet (enviromet).

##### .
### c) Anta nå at refleks-agenten (uten tilstand) kan oppfatte ren/skitten statusen til begge plassene samtidig. Kan denne agenten være rasjonell? Design en agent-funksjon

Hvis agenten kan oppfatte tilstadnen av begge ruter (A og B) samtidig kan den få maksimal poengsum og derfor er den også rasjonell.  

```
function SIMPLE-REFLEX-VACUUM-AGENT (location, statusA, statusB)
	if location = a:
		if statusA = dirty: return Suck
		else: return GoRight
	else:
		if statusB = dirty: return Suck
		else: return GoLeft 	
```

7. Støvsuger-omgivelser. Beskriv omgivelsene vha. egenskaper fra kap. 2.3.2.
----------------------------------------------------------------------------
Miljøet i støvsugereksemplet er:

- Delvis observerbart fordi agenten ikke vent noe om den andre blokken og må flytte seg for å sjekke.
- Deterministisk fordi neste tilstand er kun avhengig av nåværende tilstand og agentens handling samtidig som at handlingen har et garantert utfall.
- Diskret fordi det finnes et endelig antall tilstander for de to blokkene.
- Statisk siden det eneste som påvirker blokkene er selve agenten.
- Kjent fordi agenten opfatter blokkenes status, altså oppfatter om blokkene er skitne eller rene.

8. Diskuter fordeler og bakdeler ved de fire grunnleggende agent-typene:
------------------------------------------------------------------------

### a) Enkel Refleks-agent

- Fungerer kun på det som er nåværende input og klarer ikke å huske tidligere input.
- Fungerer kun i et fult observerbart miljø.

### b) Model-basert agent.

- Fungerer på dynamisk miljø som kan være delvis observerbart.
- Avhengig av å ha en kjent verden kodet inni seg, altså en modellering.

### c) Målbasert agent

- Fungerer i situasjoner der det ikke er nok å vite hvilken tilstand verden er i for å ta de mest optimale besluttningene.
- Kan være veldig kompliserte 

### d) Utility-based agents

- Kan skille mellom nivåer av effektivitet i måloppnåelsen
- Kan velge mellom motstridende mål
