#!/system/bin/sh
export PATH=/system/bin:$PATH

mount -o rw,remount /system

busybox sed -i 's/qemu.hw.mainkeys=//' /system/build.prop

sleep 1

reboot