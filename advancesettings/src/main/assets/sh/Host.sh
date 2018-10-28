#!/system/bin/sh
busybox mount -o remount,rw -t auto /system
if [ -e /system/etc/hosts.ashin ]
then
mv /system/etc/hosts /system/etc/hosts.mayday
mv /system/etc/hosts.ashin /system/etc/hosts
fi

sleep 2