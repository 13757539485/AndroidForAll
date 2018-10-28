#! /vendor/bin/sh

	echo 95 > /proc/sys/kernel/sched_upmigrate
	echo 85 > /proc/sys/kernel/sched_downmigrate
	echo 90 > /proc/sys/kernel/sched_spill_load
 echo 1 > /proc/sys/kernel/sched_prefer_sync_wakee_to_waker
 echo 40 > /proc/sys/kernel/sched_init_task_load
	echo 3000000 > /proc/sys/kernel/sched_freq_inc_notify
	echo 1280000 > /sys/devices/system/cpu/cpu0/cpufreq/scaling_min_freq
	echo 1180000 > /sys/devices/system/cpu/cpu4/cpufreq/scaling_min_freq
 echo "0:1324800" > /sys/module/cpu_boost/parameters/input_boost_freq
	echo "18000 1480000:198000" > /sys/devices/system/cpu/cpu0/cpufreq/interactive/above_hispeed_delay
	echo "18000 1180000:78000 1280000:18000 1480000:98000 1680000:138000" > /sys/devices/system/cpu/cpu4/cpufreq/interactive/above_hispeed_delay
	echo "80 1580000:90" > /sys/devices/system/cpu/cpu0/cpufreq/interactive/target_loads
	echo "80 1680000:90" > /sys/devices/system/cpu/cpu4/cpufreq/interactive/target_loads
	echo 38000 > /sys/devices/system/cpu/cpu0/cpufreq/interactive/min_sample_time
	echo 38000 > /sys/devices/system/cpu/cpu4/cpufreq/interactive/min_sample_time
	echo 1380000 > /sys/devices/system/cpu/cpu0/cpufreq/interactive/hispeed_freq
	echo 1280000 > /sys/devices/system/cpu/cpu4/cpufreq/interactive/hispeed_freq
	echo 0 > /sys/devices/system/cpu/cpu0/cpufreq/interactive/boostpulse_duration
	echo 0 > /sys/devices/system/cpu/cpu0/cpufreq/interactive/boost
	echo 0 > /sys/devices/system/cpu/cpu4/cpufreq/interactive/boostpulse_duration
	echo 0 > /sys/devices/system/cpu/cpu4/cpufreq/interactive/boost
 echo 0 > /sys/module/msm_performance/parameters/touchboost
	echo 0 > /sys/devices/system/cpu/cpu0/cpufreq/interactive/ignore_hispeed_on_notif
	echo 0 > /sys/devices/system/cpu/cpu0/cpufreq/interactive/enable_prediction
	echo 0 > /sys/devices/system/cpu/cpu4/cpufreq/interactive/ignore_hispeed_on_notif
	echo 0 > /sys/devices/system/cpu/cpu4/cpufreq/interactive/enable_prediction
	
	        # By Mayday(Ashin).
	        # XiaoMi 6
	        # AsROM
	        # disable thermal bcl hotplug to switch governor
        echo 0 > /sys/module/msm_thermal/core_control/enabled
       	echo N > /sys/module/msm_thermal/parameters/enabled

# Charge Battery
 busybox mount -o remount,rw -t auto /system
 ln -s /sbin/su /system/xbin/su
	chmod 644 /sys/class/power_supply/battery/constant_charge_current_max 
	echo 3600000 > /sys/class/power_supply/battery/constant_charge_current_max

# Enable input boost configuration
	echo 2500 > /sys/module/cpu_boost/parameters/input_boost_ms
 echo 512 > /sys/block/sda/queue/read_ahead_kb
 echo 256 > /sys/block/sda/queue/nr_requests
 echo 0 > /sys/block/sda/queue/iostats
 echo 1 > /sys/block/sda/queue/scheduler
 echo 0 > /sys/block/sda/queue/iosched/slice_idle
 
        echo 2-3 > /dev/cpuset/background/cpus
        echo 0-3 > /dev/cpuset/system-background/cpus
        echo 4-7 > /dev/cpuset/foreground/boost/cpus
        echo 0-3,4-7 > /dev/cpuset/foreground/cpus
       	chmod 644 /dev/cpuset/top-app/cpus
        echo 0-3,4-7 > /dev/cpuset/top-app/cpus
        echo 0 > /proc/sys/kernel/sched_boost
	
        # Enable bus-dcvs
        for cpubw in /sys/class/devfreq/*qcom,cpubw*
        do
            echo "bw_hwmon" > $cpubw/governor
            echo 50 > $cpubw/polling_interval
            echo 1525 > $cpubw/min_freq
            echo "3143 5859 11863 13763" > $cpubw/bw_hwmon/mbps_zones
            echo 4 > $cpubw/bw_hwmon/sample_ms
            echo 34 > $cpubw/bw_hwmon/io_percent
            echo 20 > $cpubw/bw_hwmon/hist_memory
            echo 10 > $cpubw/bw_hwmon/hyst_length
            echo 0 > $cpubw/bw_hwmon/low_power_ceil_mbps
            echo 34 > $cpubw/bw_hwmon/low_power_io_percent
            echo 20 > $cpubw/bw_hwmon/low_power_delay
            echo 0 > $cpubw/bw_hwmon/guard_band_mbps
            echo 250 > $cpubw/bw_hwmon/up_scale
            echo 1600 > $cpubw/bw_hwmon/idle_mbps
        done

        for memlat in /sys/class/devfreq/*qcom,memlat-cpu*
        do
            echo "mem_latency" > $memlat/governor
            echo 10 > $memlat/polling_interval
            echo 400 > $memlat/mem_latency/ratio_ceil
        done
        echo "cpufreq" > /sys/class/devfreq/soc:qcom,mincpubw/governor
	if [ -f /sys/devices/soc0/soc_id ]; then
		soc_id=`cat /sys/devices/soc0/soc_id`
	else
		soc_id=`cat /sys/devices/system/soc/soc0/id`
	fi

	if [ -f /sys/devices/soc0/hw_platform ]; then
		hw_platform=`cat /sys/devices/soc0/hw_platform`
	else
		hw_platform=`cat /sys/devices/system/soc/soc0/hw_platform`
	fi

	if [ -f /sys/devices/soc0/platform_version ]; then
		platform_version=`cat /sys/devices/soc0/platform_version`
		platform_major_version=$((10#${platform_version}>>16))
	fi

	if [ -f /sys/devices/soc0/platform_subtype_id ]; then
		platform_subtype_id=`cat /sys/devices/soc0/platform_subtype_id`
	fi

	case "$soc_id" in
		"292") #msm8998 apq8098_latv
		# Start Host based Touch processing
		case "$hw_platform" in
		"QRD")
			case "$platform_subtype_id" in
				"0")
					start_hbtp
					;;
				"16")
					if [ $platform_major_version -lt 6 ]; then
						start_hbtp
					fi
					;;
			esac

			;;
		esac
	    ;;
	esac

# Post-setup services
case "$target" in
    "msm8994" | "msm8992" | "msm8996" | "msm8998" | "sdm660" | "apq8098_latv" | "sdm845")
        setprop sys.post_boot.parsed 1
    ;;
esac

	        # By Mayday(Ashin).
	        # XiaoMi 6
	        # AsROM