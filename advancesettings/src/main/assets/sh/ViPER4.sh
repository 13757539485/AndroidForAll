#!/system/bin/sh
busybox mount -o remount,rw -t auto /system
if [ -e /system/app/ViPER4Android/ViPER4Android.bak ] 
then
mv /system/etc/audio_effects.conf /system/etc/audio_effects.mayday
mv /system/etc/audio_effects.ashin /system/etc/audio_effects.conf
mv /system/vendor/etc/audio_effects.conf /system/vendor/etc/audio_effects.mayday
mv /system/vendor/etc/audio_effects.ashin /system/vendor/etc/audio_effects.conf
mv /system/app/ViPER4Android/ViPER4Android.bak /system/app/ViPER4Android/ViPER4Android.apk
fi

sleep 2
reboot