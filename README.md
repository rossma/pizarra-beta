# pizarra (Beta)
Spanish Lessons from the whiteboard - Beta Edition

## Intro

This application is used to showcase the Play Framework along with these other libraries:
* [Scala](http://scala-lang.org)
* [Vue](https://vuejs.org)
* [Webpack](https://webpack.github.io)
* [Bootstrap](http://www.bootstrap.com)
* [Slick](http://www.slick.typesafe.com)

## Installation

This application uses Play 2.5 and Vue.js. 
* Install SBT
* Install Node.js
* Run `npm install`

## Running

### Dev Mode

* Run: `sbt ~run`
* Run: `webpack --watch`
* Go to [localhost:9000](http://localhost:9000)

### Prod Mode

* Run `sbt testProd`

Deployment:

* Produce executable via `sbt clean dist`
* Extract `unzip target/universal/pizarra-beta-1.0.0.zip`
* Run `pizarra-beta-1.0.0/bin/pizarra-beta -Dhttp.port=9000 -Dconfig.resource=prod.conf`
