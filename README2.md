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

### Oppgave 3

Jeg lagde en ny workflow for å pushe til ECR istedenfor å slette docker workflowne.
Se workflows/ecr.yml
Så endret jeg slik at docker kan kjøres manuelt om ønskelig, men den pusher til ECR under push og pull til main

For å pushe til et annet ECR repo, trengs det bare å gå til "ECR" på AWS, inn på ditt repo
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

ligger også timer metric i "class, exception, method"


## del 5

babe change resource to data


### Oppgave 1

something something the bucket was not added to the terraform state file
It tried to create it over and over because it did not have a backend to save fetch info
This got fixed by importing the bucket with terraform CLI: 

```
terraform import aws_s3_bucket.analyticsbucket analytics-1023
```

ok