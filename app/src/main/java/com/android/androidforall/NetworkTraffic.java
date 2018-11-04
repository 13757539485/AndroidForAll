package com.android.androidforall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings.System;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;

public class NetworkTraffic extends TextView {
    private static final int KILOBYTE = 1024;
    public static final int MASK_DOWN = 2;
    public static final int MASK_PERIOD = -65536;
    public static final int MASK_UP = 1;
    private static DecimalFormat decimalFormat = new DecimalFormat("##0.#");
    public static boolean mEnable;
    public static boolean mStart;
    private String Byte;
    private int GB;
    private int KB;
    private int KEY_LONG;
    private int KEY_STRING;
    private int MB;
    public String STATUS_BAR_NETWORK_TRAFFIC_STYLE;
    private long lastUpdateTime;
    private boolean mAttached;
    private ConnectivityManager mConnectivityService;
//    private String mDown;
    private final BroadcastReceiver mIntentReceiver;
    private Runnable mRunnable;
    private int mState;
    private Handler mTrafficHandler;
//    private String mUp;
    private boolean mVpnConnected;
    private boolean shouldHide;
    private long totalRxBytes;
    private long totalTxBytes;
    private int txtSizeMulti;
    private int txtSizeSingle;

    static {
        decimalFormat.setMaximumIntegerDigits(4);
        decimalFormat.setMaximumFractionDigits(1);
        mStart = false;
    }

    public NetworkTraffic(Context var1) {
        this(var1, (AttributeSet)null);
    }

    public NetworkTraffic(Context var1, AttributeSet var2) {
        this(var1, var2, 0);
    }

    public NetworkTraffic(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        this.STATUS_BAR_NETWORK_TRAFFIC_STYLE = "status_bar_network_traffic_style";
        this.KEY_LONG = 0;
        this.KEY_STRING = 1;
        this.Byte = "B";
        this.mState = 0;
        this.KB = 1024;
        this.MB = this.KB * this.KB;
        this.GB = this.MB * this.KB;
       /* this.mUp = " ▲";
        this.mDown = " ▼";*/
        this.mTrafficHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message var1) {
                long var2 = SystemClock.elapsedRealtime() - NetworkTraffic.this.lastUpdateTime;
                long var4 = var2;
                if ((double)var2 < (double)NetworkTraffic.getInterval(NetworkTraffic.this.mState) * 0.95D) {
                    if (var1.what != 1) {
                        return false;
                    }

                    var4 = var2;
                    if (var2 < 1L) {
                        var4 = 9223372036854775807L;
                    }
                }

                NetworkTraffic.this.lastUpdateTime = SystemClock.elapsedRealtime();
                long var6 = TrafficStats.getTotalRxBytes();
                long var8 = TrafficStats.getTotalTxBytes();
                long var10 = var6 - NetworkTraffic.this.totalRxBytes;
                long var12 = var8 - NetworkTraffic.this.totalTxBytes;
                long var14 = var12;
                var2 = var10;
                if (NetworkTraffic.this.mVpnConnected) {
                    var2 = var10 / 2L;
                    var14 = var12 / 2L;
                }

                String var21 = "";
                NetworkTraffic var16;
                boolean var17;
                if (NetworkTraffic.isSet(NetworkTraffic.this.mState, 1)) {
                    var21 = formatOutput(var4, var14, "B/s") ;
                    var16 = NetworkTraffic.this;
                    if (var14 == 0L) {
                        var17 = true;
                    } else {
                        var17 = false;
                    }

                    var16.shouldHide = var17;
                }

                int var18;
                if (!NetworkTraffic.isSet(NetworkTraffic.this.mState, 3)) {
                    var18 = NetworkTraffic.this.txtSizeSingle;
                } else {
                    var21 = var21 + "\n";
                    var18 = NetworkTraffic.this.txtSizeMulti;
                    var16 = NetworkTraffic.this;
                    if (var2 == 0L && var14 == 0L) {
                        var17 = true;
                    } else {
                        var17 = false;
                    }

                    var16.shouldHide = var17;
                }

                String var24 = var21;
                NetworkTraffic var22;
                if (NetworkTraffic.isSet(NetworkTraffic.this.mState, 2)) {
                    var24 = var21 + formatOutput(var4, var2, "B/s") ;
                    var22 = NetworkTraffic.this;
                    if (var2 == 0L) {
                        var17 = true;
                    } else {
                        var17 = false;
                    }

                    var22.shouldHide = var17;
                }

                NetworkTraffic.this.shouldHide = false;
                if (!var24.contentEquals(NetworkTraffic.this.getText()) && !NetworkTraffic.this.shouldHide) {
                    SpannableString var23;
                    if (var18 == NetworkTraffic.this.txtSizeMulti) {
//                        var18 = var24.indexOf(NetworkTraffic.this.mUp);
//                        int var19 = var24.indexOf(NetworkTraffic.this.mDown);
                        int var20 = var24.indexOf("\n");
                        var23 = new SpannableString(var24);
//                        var23.setSpan(new AbsoluteSizeSpan(NetworkTraffic.this.txtSizeMulti), 0, var18, 33);
//                        var23.setSpan(new AbsoluteSizeSpan((int)((double)NetworkTraffic.this.txtSizeMulti * 0.7D)), var18, var20, 33);
//                        var23.setSpan(new NetworkTrafficSpan(-0.4D), var18, var20, 33);
//                        var23.setSpan(new AbsoluteSizeSpan(NetworkTraffic.this.txtSizeMulti), var20, var19, 33);
//                        var23.setSpan(new AbsoluteSizeSpan((int)((double)NetworkTraffic.this.txtSizeMulti * 0.7D)), var19, var24.length(), 33);
//                        var23.setSpan(new NetworkTrafficSpan(0.4D), var19, var24.length(), 33);
                        NetworkTraffic.this.setText(var23);
                    } else {
                        if (NetworkTraffic.isSet(NetworkTraffic.this.mState, 2)) {
//                            var21 = NetworkTraffic.this.mDown;
                        } else {
//                            var21 = NetworkTraffic.this.mUp;
                        }

                        var18 = var24.indexOf(var21);
                        var23 = new SpannableString(var24);
//                        var23.setSpan(new AbsoluteSizeSpan(NetworkTraffic.this.txtSizeSingle), 0, var18, 33);
//                        var23.setSpan(new AbsoluteSizeSpan((int)((double)NetworkTraffic.this.txtSizeMulti * 0.9D)), var18, var24.length(), 33);
//                        var23.setSpan(new NetworkTrafficSpan(0.25D), var18, var24.length(), 33);
                        NetworkTraffic.this.setText(var23);
                    }
                }

                var22 = NetworkTraffic.this;
                byte var25;
                if (!NetworkTraffic.this.shouldHide && NetworkTraffic.this.mState != 0) {
                    var25 = 0;
                } else {
                    var25 = 8;
                }

                var22.setVisibility(var25);
                NetworkTraffic.this.totalRxBytes = var6;
                NetworkTraffic.this.totalTxBytes = var8;
                NetworkTraffic.this.clearHandlerCallbacks();
                NetworkTraffic.this.mTrafficHandler.postDelayed(NetworkTraffic.this.mRunnable, (long)NetworkTraffic.getInterval(NetworkTraffic.this.mState));
                return false;
            }
        }) ;
        this.mRunnable = new Runnable() {
            public void run() {
                NetworkTraffic.this.mTrafficHandler.sendEmptyMessage(0);
            }
        };
        this.mIntentReceiver = new BroadcastReceiver() {
            public void onReceive(Context var1, Intent var2) {
                String var3 = var2.getAction();
                if (var3 != null && var3.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                    NetworkTraffic.this.updateSettings();
                }

            }
        };
        //Resources var4 = var1.getResources();
        this.mConnectivityService = (ConnectivityManager)var1.getSystemService(Context.CONNECTIVITY_SERVICE);
        this.txtSizeSingle = 38;
        this.txtSizeMulti = 19;
        (new NetworkTraffic.SettingsObserver(new Handler())).observe();
        this.updateSettings();
    }

    private void clearHandlerCallbacks() {
        this.mTrafficHandler.removeCallbacks(this.mRunnable);
        this.mTrafficHandler.removeMessages(0);
        this.mTrafficHandler.removeMessages(1);
    }

    private boolean getConnectAvailable() {
        ConnectivityManager var1 = (ConnectivityManager)this.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo var3;
        if (var1 != null) {
            var3 = var1.getActiveNetworkInfo();
        } else {
            var3 = null;
        }

        boolean var2;
        if (var3 != null) {
            var2 = true;
        } else {
            var2 = false;
        }

        return var2;
    }

    private static int getInterval(int var0) {
        var0 >>>= 16;
        if (var0 < 250 || var0 > 32750) {
            var0 = 1000;
        }

        return var0;
    }

    private static boolean isSet(int var0, int var1) {
        boolean var2;
        if ((var0 & var1) == var1) {
            var2 = true;
        } else {
            var2 = false;
        }

        return var2;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!this.mAttached) {
            this.mAttached = true;
            IntentFilter var1 = new IntentFilter();
            var1.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            this.getContext().registerReceiver(this.mIntentReceiver, var1, (String)null, this.getHandler());
        }

        this.updateSettings();
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mAttached) {
            this.getContext().unregisterReceiver(this.mIntentReceiver);
            this.mAttached = false;
        }

    }

    public void updateSettings() {
        this.mState = System.getInt(this.getContext().getContentResolver(), this.STATUS_BAR_NETWORK_TRAFFIC_STYLE, 3);
        this.MB = this.KB * this.KB;
        this.GB = this.MB * this.KB;
        if (!isSet(this.mState, 1) && !isSet(this.mState, 2)) {
            this.setVisibility(View.GONE);
            this.clearHandlerCallbacks();
        } else if (this.getConnectAvailable() && this.mAttached) {
            NetworkInfo var1 = this.mConnectivityService.getNetworkInfo(17);
            if (var1 != null && var1.isConnected()) {
                this.mVpnConnected = true;
            } else {
                this.mVpnConnected = false;
            }

            this.totalRxBytes = TrafficStats.getTotalRxBytes();
            this.totalTxBytes = TrafficStats.getTotalTxBytes();
            this.lastUpdateTime = SystemClock.elapsedRealtime();
            this.mTrafficHandler.sendEmptyMessage(1);
        }

    }

    private String formatOutput(long var1, long var3, String var5) {
        var1 = (long)((float)var3 / ((float)var1 / 1000.0F));
        if (var1 < (long)NetworkTraffic.this.KB) {
            var5 = NetworkTraffic.decimalFormat.format(var1) + var5;
        } else if (var1 < (long)NetworkTraffic.this.MB) {
            var5 = NetworkTraffic.decimalFormat.format((double)((float)var1 / (float)NetworkTraffic.this.KB)) + 'K' + var5;
        } else if (var1 < (long)NetworkTraffic.this.GB) {
            var5 = NetworkTraffic.decimalFormat.format((double)((float)var1 / (float)NetworkTraffic.this.MB)) + 'M' + var5;
        } else {
            var5 = NetworkTraffic.decimalFormat.format((double)((float)var1 / (float)NetworkTraffic.this.GB)) + 'G' + var5;
        }

        return var5;
    }

    class SettingsObserver extends ContentObserver {
        SettingsObserver(Handler var2) {
            super(var2);
        }

        void observe() {
            NetworkTraffic.this.getContext().getContentResolver().registerContentObserver(System.getUriFor(NetworkTraffic.this.STATUS_BAR_NETWORK_TRAFFIC_STYLE), false, this);
        }

        public void onChange(boolean var1) {
            NetworkTraffic.this.updateSettings();
        }
    }
}
