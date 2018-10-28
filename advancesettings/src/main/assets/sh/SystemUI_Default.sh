#!/system/bin/sh
busybox mount -o remount,rw -t auto /system
if [ -e /system/priv-app/MiuiSystemUI/MiuiSystemUI.mayday ]
then
mv /system/priv-app/MiuiSystemUI/MiuiSystemUI.apk /system/priv-app/MiuiSystemUI/MiuiSystemUI.ashin
mv /system/priv-app/MiuiSystemUI/MiuiSystemUI.mayday /system/priv-app/MiuiSystemUI/MiuiSystemUI.apk
fi
sleep 2
busybox killall com.android.systemui
busybox killall com.miui.home
umount /system
exit