#!/system/bin/sh
busybox mount -o remount,rw -t auto /system
if [ -e /system/etc/hosts.mayday ] 
then
mv /system/etc/hosts /system/etc/hosts.ashin
mv /system/etc/hosts.mayday /system/etc/hosts
fi

sleep 2
