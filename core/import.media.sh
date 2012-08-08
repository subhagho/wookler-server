#!/bin/bash

curl -i -X POST http://localhost:8090/rest/import/process?entity=video -vF"file=@VIDEO.csv"
curl -i -X POST http://localhost:8090/rest/import/process?entity=creative -vF"file=@CREATIVE.csv"
curl -i -X POST http://localhost:8090/rest/import/process?entity=sequence -vF"file=@SEQUENCE.csv"
curl -i -X POST http://localhost:8090/rest/import/process?entity=tag -vF"file=@TAG.csv"
curl -i -X POST http://localhost:8090/rest/import/process?entity=producthistory -vF"file=@PRODUCTHISTORY.csv"
curl -i -X POST http://localhost:8090/rest/import/process?entity=profile -vF"file=@PROFILE.csv"
curl -i -X POST http://localhost:8090/rest/import/process?entity=contribution -vF"file=@CONTRIBUTION.csv"
curl -i -X POST http://localhost:8090/rest/import/process?entity=subscription -vF"file=@SUBSCRIPTION.csv"
curl -i -X POST http://localhost:8090/rest/import/process?entity=notification -vF"file=@NOTIFICATION.csv"
curl -i -X POST http://localhost:8090/rest/import/process?entity=activity -vF"file=@ACTIVITY.csv"

