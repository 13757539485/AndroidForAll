#!/system/bin/sh
export PATH=/system/bin:$PATH

mount -o rw,remount /system

busybox sed -i 's/qemu.hw.mainkeys=//' /system/build.prop

echo "qemu.hw.mainkeys=0"  >> /system/build.prop

cp -p -a -R  /system/xbin/ashin/* /system/media/theme/default/framework-res

chmod -R 0644 /system/media/theme/default/framework-res

sleep 1

reboot