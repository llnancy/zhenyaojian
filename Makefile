.PHONY: clean package build push docker-compose docker-rm-f

IMAGE=sunchaserlilu/zhenyaojian
VERSION=latest

clean:
	mvn clean

package:
	mvn package

build:
	docker build -t ${IMAGE}:${VERSION} .

push:
	docker push ${IMAGE}:${VERSION}

docker-compose:
	docker-compose -f docker-compose.yml up -d

docker-rm-f:
	docker rm -f zhenyaojian
