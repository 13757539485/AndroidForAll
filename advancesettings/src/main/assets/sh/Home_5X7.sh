#!/system/bin/sh
export PATH=/system/bin:$PATH

mount -o rw,remount /system

cp -p -a -R  /system/xbin/Home/Home_5X7/* /system/media/theme/default/com.miui.home

busybox chmod -R 0644 /system/media/theme/default/com.miui.home

sleep 2
busybox killall com.android.systemui
busybox killall com.miui.home
umount /system
exit