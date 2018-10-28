#!/system/bin/sh
export PATH=/system/bin:$PATH

mount -o rw,remount /system

mount -o rw,remount /data

cp -p -R  /system/xbin/Key/0/atmel-maxtouch.kl /system/usr/keylayout/atmel-maxtouch.kl

chmod -R 0644 /system/usr/keylayout/atmel-maxtouch.kl

sleep 1

reboot

