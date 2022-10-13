# OpenScoring
Prototype of credit scoring system for VTB API Hackathon 


<!-- TOC -->
* [Documentation here](./docs/)
<!-- TOC -->

## TODO list
- [x] Services emulating Open banking API (couple routes for getting account and balances information). Must be provided by bank organization.
- [x] Auth service. Must be provided by bank organization
- [x] Data extraction service. Should get bearer token and then request and ingest information about client account/balances/transactions etc.
- [X] APi gateway. Should contain business logic of our process: get token after user authentication, save loan applications,
use "Data extraction service" to get data, that will be used by recommendation service.
- [X] Credit rating service / recommendation service. It is also not a task of the project. 
Needed only for prototype demonstration

## Deploying

### Prerequisites
You must have **Docker** and **docker-compose** installed on your computer.

### Running services

To deploy all services you need [docker-compose](./docker-compose.yml) file from this repository.

Open terminal in directory with this file and run:
> docker-compose up -d

This command will launch all services in the background. 
