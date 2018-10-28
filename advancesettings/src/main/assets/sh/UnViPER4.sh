#!/system/bin/sh
busybox mount -o remount,rw -t auto /system
if [ -e /system/app/ViPER4Android/ViPER4Android.apk ] 
then
mv /system/etc/audio_effects.conf /system/etc/audio_effects.ashin
mv /system/etc/audio_effects.mayday /system/etc/audio_effects.conf
mv /system/vendor/etc/audio_effects.conf /system/vendor/etc/audio_effects.ashin
mv /system/vendor/etc/audio_effects.mayday /system/vendor/etc/audio_effects.conf
mv /system/app/ViPER4Android/ViPER4Android.apk /system/app/ViPER4Android/ViPER4Android.bak
fi

sleep 2
reboot