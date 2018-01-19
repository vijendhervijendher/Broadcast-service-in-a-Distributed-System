#!/bin/bash


# Change this to your netid
netid=vxp161830

#
# Root directory of your project
PROJDIR=$HOME/CS6378/Project1

#
# This assumes your config file is named "config.txt"
# and is located in your project directory
#
CONFIG=$PROJDIR/config.txt

#
# Directory your java classes are in
#
BINDIR=$PROJDIR

#
# Your main project class
#
PROG=BroadcastService

n=1

cat $CONFIG | sed -e "s/#.*//" | sed -e "/^\s*$/d" |
(
    read i
    echo $i
    while read line 
    do
        host=$( echo $line | awk '{ print $1 }' )

        ssh $netid@$host java -cp $BINDIR/$PROG $n &

        n=$(( n + 1 ))
    done
   
)


