cat config.txt | sed -e "s/#.*//" | sed -e "/^\s*$/d" |
(
    read i
    echo $i
    while read -r line || [[ -n $line ]];
    do
        host=$( echo $line | awk '{ print $1 }' )

        echo $host
        ssh vxp161830@$host killall -u vxp161830 &
        sleep 1
    done
   
)


echo "Cleanup complete"
