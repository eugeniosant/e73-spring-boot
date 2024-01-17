# Configurable Widget
The project contains a very simple MFE (simple-mfe) for Entando 7.3

## Prerequisites
1. Docker account
2. attach ent to an Entando platform (e.g. ent attach-kubeconfig config-file)

## Build and publish steps  
1. ent bundle pack 
2. ent bundle publish (push image on docker hub)
3. ent bundle deploy
4. ent bundle install

how create CR from docker images

ent bundle generate-cr --image=my-org/my-bundle

See https://developer.entando.com for more information.

## History
v0.0.1 Basic React MFE
v0.0.2 Add publicpath config to handle static assets in Entando 