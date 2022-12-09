.PHONY: clean package build push

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
