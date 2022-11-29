# EksamensBesvarelse

## del 3: 

### Oppgave 1:

Det jeg måtte gjøre for å få workflowen til å fungere:
1: Siden min hovedbranch heter master og ikke main, måtte jeg endre restriksjonene for event i docker.yml
2: Legge til docker konto i github repository secrets

grunnen til at workflowen feilet:
Den feilet fordi den ikke klarte å logge inn.

### Oppgave 2:

Gikk ut ifra at jeg skulle oppgradere til java 11, ikke gå tilbake til eldre version

når jeg kjørte docker imaget med multi stage så kjørte docker workflowen
Men når jeg fjernet setup av SDK og build med maven så sluttet workflowen å kjøre grunnet "ikke finner file"
Jeg misstenker at det var provided feil path i "file" (det var ./Dockerfile) med context "."
Når jeg bare fjernet path vairablen regner jeg med den bruker default, og da kjører docker
workflowen mye ryddigere uten unødvendige steg.

