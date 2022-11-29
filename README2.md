Gikk ut ifra at jeg skulle oppgradere til java 11, ikke gå tilbake til eldre version

når jeg kjørte docker imaget med multi stage så kjørte docker workflowen
Men når jeg fjernet setup av SDK og build med maven så sluttet workflowen å kjøre grunnet "ikke finner file"
Jeg misstenker at det var provided feil path i "file" (det var ./Dockerfile) med context "."
Når jeg bare fjernet path vairablen regner jeg med den bruker default, og da kjører docker
workflowen mye ryddigere uten unødvendige steg.


TEST CHANGE