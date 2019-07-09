```
$ docker build -t gcr.io/gcp-evgdim-test/gcp-test:0.0.1 . --build-arg JAR_FILE=target/gcp-0.0.1-SNAPSHOT.jar
```

```
$ docker images
```

```
$ docker run -d -p 9082:8080 evgdim/gcp-test:0.0.1
```

```
$ docker logs 1f7e74ee00d61330c07baaf16a970d44a5a73fe38d55c6a5d8010bb26b90b114
```

```
$ gcloud auth print-access-token

$ docker login -u oauth2accesstoken -p "ya29.GltABzJTPLMBPsDbCjVR1boLBhNmJz3dezPMIMts07kWiqqZ21YhDTYG6rNxDtO4N6U8jRCJmnWb6-JSvfSynhqIlOv3_ttUZgiJND8PEH57qZeGaS3EykQkN_5R" https://gcr.io
```
alternative

```
cat gcp-evgdim-test-d0f056181927.json | docker login -u _json_key --password-stdin https://gcr.io
```


```
$ gcloud components install docker-credential-gcr
```


# GAE
```
$ gcloud app deploy app.yaml
```