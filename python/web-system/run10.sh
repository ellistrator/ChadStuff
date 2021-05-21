#!/bin/bash
for i in {0..9}
do
    sh foo.sh 800$i &
done
