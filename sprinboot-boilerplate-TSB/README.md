# springboot  boilerplate

## What is included ?
- project structure
  - config
  - controllers
  - services
  - sidecars
- sidecar
- prometheus
- Liveness and Readiness Probes
- unittest
- graalvm


## How to use
1. Clone <REPO> myAwesomeApp
2. Delete .git
3. git init .
4. push to your repo

## metrics
curl "http://localhost:8080/actuator/prometheus"

## probes
curl http://localhost:8080/actuator/health/liveness
curl curl http://localhost:8080/actuator/health/readiness

## Sidecars
- TBD