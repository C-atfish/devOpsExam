# EksamensBesvarelse

Bonusoppgave: Jeg har ikke brukt Cloud9 til utvikling, så jeg har ikke fått denne feilmeldingen selv, men etter litt
undersøking så tror jeg årsaken er:

## Del 1
1: Hva er utfordringene med dagens systemutviklingsprosess - og hvordan vil innføring av DevOps kunne være med 
   på å løse disse? Hvilke DevOps prinsipper blir brutt? 

2: En vanlig respons på mange feil under release av ny funksjonalitet er å gjøre det mindre hyppig, og samtidig
forsøke å legge på mer kontroll og QA. Hva er problemet med dette ut ifra et DevOps perspektiv, og hva kan være en bedre tilnærming?

1 og 2:
Utfordringen med nåværende utviklingprosess er at frekvensen på deployments er veldig lav. Med veldig lite kvalitetssikring og mye waste.
De har en lang prosess både på å bygge prosjektet, manuelt teste det, og unødvendige steg rundt å sende det til en egen avdeling, 
som deretter manuelt deployer på AWS.

Denne prosessen fører til veldig sjelden utrulling av ny funksjonalitet, og krever at alle er ferdig med oppgaven sin feilfritt innen
første mandagen i kvartalet, ellers blir deployment forsinket eller funksjonaliteten må vente til neste kvartal. 

Om man sammenligner det med motparten som er å Devopsifye det til å ha en hyppigere frekvens på deployments med automatiske tester. 
Man kan sette opp en Release branch som man kan pushe til flere ganger om dagen, med restriksjoner i Workflowen som bare godtar pushes
om de automatiske testene har kjørt grønnt. På denne måten kan man oppdatere med mer begrenset oppgavestørrelse så fort man er ferdig
med oppgaven eller når de skulle ønske selv.

Dette vil spare masse tid og penger for bedriften, det vil være en mye høyere terskel for kvalitets-sikring når vi kan ha kontroll på koden
i workflowen. Skulle bugs eventuelt oppstå så kan men lett rulle tilbake og fikse problemet, og release det igjen med en gang.
Brukere vil få en mye bedre opplevelse, med oftere forbedret funksjonalitet i løsningen.

Ved å automatisere tester og legge opp en bedre arbeidsflyt, vil vi fjerne den falske tryggheten man kan få av manuelle tester.
Det vil skape en mye bedre flyt i prosessen, hvor resultatet har fjernet masse waste rundt venting på teams, færre context switches og
mindre venting på delvis fullført arbeid. Det vil overføre mye av OPS ansvaret til utviklerene, som fører til færre overleveringer
av koden fra start til slutt.



3: Teamet overleverer kode til en annen avdelng som har ansvar for drift - hva er utfordringen med dette ut ifra et DevOps perspektiv, 
   og hvilke gevinster kan man få ved at team han ansvar for både drift- og utvikling?

3:
Om utvikleren har ansvar for både drift og utvikling vil man fjerne skillet mellom disse. Tid er veldig verdifult, og det å ha unødvendige 
overleveringer vil føre til mye bortkastet tid. Ett team kan sitte å måtte vente på at de andre må bli ferdig med en oppgave og lignende.
Det å ha ansvar på hele prosessen fra start til slutt ender da med å jobbe ekstremt effektivt.


4: Å release kode ofte kan også by på utfordringer. Beskriv hvilke- og hvordan vi kan bruke DevOps prinsipper til å redusere eller
   fjerne risiko ved hyppige leveraner

4: Det å release kode ofte kan by på utfordringer om ikke pipelinen er god nok. For at dette skal fungere bra så må det være
få overleveringer av koden, god struktur på arbeidsflyten og ett godt versions kontroll system satt opp som tar seg av bracnhes og workkflow.
Det er viktig at release branchen alltid er i en fungerende state, så det må være ett kvalitets system med automatiske tester og pull requester
som fjerner muligheten til å pushe ødelagt kode så godt som mulig til release.

## Del 2

### Oppgave 3

For å
1: Forhindre at noen skal klare å pushe kode direkte til Main Branchen,
2: Bare merge feature branch om status checks har passed
3: For å merge til main med en pull request som krever minst en godkjenning, kan man gå til:

Github Repo -> Settings -> Branches 
-> Add Branch Protection Rule -> Legg til Main(Master) Branchen 
-> Under "Protect matching branches" så sjekker du av kommende bokser:
- [X] Require Pull Request before Merging
- [X] Require Approvals 
-> Required number of approvals before merging: > 0
- [X] Require status checks to pass before merging


## Del 3: 

### Oppgave 1:

Det jeg måtte gjøre for å få workflowen til å fungere:
1: Siden min hovedbranch heter master og ikke main, måtte jeg endre restriksjonene for event i docker.yml
2: Legge til docker konto i github repository secrets

Grunnen til at workflowen feilet:
Den feilet fordi den ikke klarte å logge inn/Fant ikke noen DockerHub Konto i Secrets.

### Oppgave 2:

Gikk ut ifra at jeg skulle oppgradere til java 11, ikke gå tilbake til eldre Java version.

Når jeg kjørte docker imaget med multi stage så kjørte docker workflowen.

Men når jeg skulle rydde opp ved å fjerne setup av SDK og build med maven så sluttet workflowen å kjøre grunnet "ikke finner file"
Jeg spesifiserte riktig path i docker filen og workflow filen , og da kjører docker
workflowen mye ryddigere uten unødvendige steg.



### Oppgave 3

Jeg lagde en ny workflow for å pushe til ECR istedenfor å slette docker workflowen. 
Derfor om de noensinne vil bruke docker hub igjen så kan de enkelt bytte tilbake/Legge til workflowen.
Se workflows/ecr.yml for push til ECR.

Så endret jeg slik at docker kan kjøres manuelt om ønskelig, men den pusher bare til ECR under push og pull til main

For å pushe til et annet ECR repo, trengs det bare å gå til "ECR" på AWS, Gå inn på ditt Repo,
Der står det build commands for å replace bunnen av ecr.yml

Videre har jeg lagt til commit hash som tag til ecr.

## Del 4

### Oppgave 2

Jeg prøvde en del forskjellige ting for å forstå CloudWatch bedre, som gjorde at jeg endte opp med veldig mange
"test metrics" som ikke er i bruk lengre.
De relevante metricene heter:
- carts_checked_out
- number_of_active_carts
- carts_value

## Del 5

### Oppgave 1

Since we dont have a backend, we are using a local terraform.state file.
Which means that when the VM in the workflow runs the code, it will create
a new state file everytime, and then also try to create the bucket, since the terraform state
is not aware of the existing bucket.

This got fixed by adding a backend in the provider.tf that reffers to existing bucket,
then importing the bucket with terraform CLI: 

```
terraform import aws_s3_bucket.analyticsbucket analytics-1023
```
Now the state file is saved in a bucket and keeps track of the state of all our components for us, so 
it won't try to create duplicates of the elements in the infrastructure.
