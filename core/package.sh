#!/bin/bash

PACKAGE=./target/wookler.tar.gz


LIBFILE=`find . -name wookler*.jar`
tar -czvf $PACKAGE target/lib/ $LIBFILE runserver.sh


