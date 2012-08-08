#!/bin/bash

PACKAGE=./target/wookler.tar.gz


LIBFILE=`find . -name wookler*.jar`
tar -czvf $PACKAGE target/lib/ $LIBFILE target/classes/config/*.xml runserver.sh


