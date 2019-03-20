package com.yyc.rexiufudemo;

import android.content.Context;
import android.support.annotation.Keep;
import android.util.Log;

import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixApplication;
import com.taobao.sophix.SophixEntry;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;
/**
 * Sophix入口类，专门用于初始化Sophix，不应包含任何业务逻辑。
 * 此类必须继承自SophixApplication，onCreate方法不需要实现。
 * 此类不应与项目中的其他类有任何互相调用的逻辑，必须完全做到隔离。
 * AndroidManifest中设置application为此类，而SophixEntry中设为原先Application类。
 * 注意原先Application里不需要再重复初始化Sophix，并且需要避免混淆原先Application类。
 * 如有其它自定义改造，请咨询官方后妥善处理。
 */
public class SophixStubApplication extends SophixApplication {
    private final String TAG = "SophixStubApplication";
    // 此处SophixEntry应指定真正的Application，并且保证RealApplicationStub类名不被混淆。
    @Keep
    @SophixEntry(MyApplication.class)
    static class RealApplicationStub {}
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//         如果需要使用MultiDex，需要在此处调用。
//         MultiDex.install(this);
        initSophix();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //热修复
        SophixManager.getInstance().queryAndLoadNewPatch();
    }

    private void initSophix() {
        String appVersion = "1.0.0";
        try {
            appVersion = this.getPackageManager()
                             .getPackageInfo(this.getPackageName(), 0)
                             .versionName;
        } catch (Exception e) {
        }
        final SophixManager instance = SophixManager.getInstance();
        instance.setContext(this)
                .setAppVersion(appVersion)
                .setSecretMetaData("25878602-1", "1d9827e147601eae84ecaf2e1159c4a8"
                        , "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCr+QFwUO6MLoeEk31pP+Ig8f93ejPTQwHCRGzHuhX6UseZ9leLYbDcULX5dDaXsT12yBDR6pFjovnPm7Ct8ov9DANst33vg0tKjUF8+iSl8DCpmK4Z11ndb5zbd3my1QKe3cNVGTNPVAmzl9Y/FpyZARYpgatRELx99MdHDcChbaqp6bvJ8oFDEyVa6WM8DH3ptcMEdIIcyhqrNswbbYysEyUiYvxMBcLbyoufrYO/8mWj6kKKKZF62fJDFnKfJZp7kdcSiAL4TmtTk9DgbhnpiTt3/5oX39+aK4pHITPrfy4Y4rgUrI1NFIRfmgLrpPSP2MEvR7cfTMotxfAyvAPTAgMBAAECggEAGwXDnhx5f3mIPeLXIASzya19EQ8YuaebzSTnWvL4pR8A0kaNNw+gtN3R2mLSeT+WguWpDvMm6VsgmejC+HJWYOrUVu72UP52hD5x/etoetKlMuEU6maWbl8tbKb3boxeHwyFtweAsoRoCdpgBgXNkZVEID2J5/ETTU97SMT2cecBlNaGVhpzzN/wejOAmU9YMsIR1Qljl/3vOtypIYTezz9wsJl6iF2HV272YedmA0aKn59qF1a/HH9iGJYslLI8rv1PPJl2Gqg8wVGJx4ChV/qUmnSQeK047vJ/JfViCGgABMjAf362rnd4HUT5Pg8iA66lU0OPXTU1dBLvQwx+QQKBgQDc9bCwlrl/zLbDX7y+FQjyi73F6Rzg/gSX+AHiBiEHe3/9Fu7QC5bsFgAr6PHomNciZv0/0QDK1JW3rVH9OD8S1+pisBtFr8Ro/1c4WqJdSGIg3xmYnzyCtKnGPjLstZMEBg5LL7+MmXPJI37IB68PrkbOiguICU4z1lfxnopR0QKBgQDHPpYjGVl5ZlunL4dDCa5wUVVlFxX8RPeL7YdydM7S8kV/ct0mY4haDhLRcqx+ZhX+uNK3ewpJk6jYF/khjhuk/grWCJ2xy6NUkwYTv6hqbgtL/dj03JRoRx5G4eN9Tzp10yvMZaqTF86B8Nr7LPATP4V+Qca7xRaicOA2z5BgYwKBgBW6v7aODA4KZsrN5nTXArs/jNkitKlXscH0LNM6gRMITSzpjXC+QwhSnY+z595U8Ys7j5owBqDryRF4JQD/AAztWOp9oSD4SRt+SOiM2TzNOoE7D3xX5I6CD9QkF8P5k0yo/8zbFD5SdPWJ3RT01H15SRNUtg+ZwqwMXzeuvLGRAoGBAI0+bfqFzusDx/d19wJsC5At6EaYngpO7dnxUfKuoavi+aeGywWay59jmK2ICIY2YmNHrIFLpRhRqAFAxWinMCu/t/jQ9NGElc2vyEAZvw9r4XBGjRXQhRiDShBftAAyOTQrtkHY1Jof51IwZ1xmF+7dOgouIO8doZUYRao1bJkXAoGBAIsiRvFLGdimancTVDrZojgXdhhiTTjxngDfEePFUEAAck8ZRirZ8Usor5V8RsnGm+cg4DSr2MMSMXI/2zG6JzQ4RzVa56k5x4coydxp2Xv+FXnPhvxStDibPPY3XFrBU3iAWb1+EHGL32o7bULh/KPWp6BJaw9X2K4RekEN2V0j")
                .setEnableDebug(true)
                .setEnableFullLog()
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            Log.i(TAG, "sophix load patch success!");
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 如果需要在后台重启，建议此处用SharePreference保存状态。
                            Log.i(TAG, "sophix preload patch success. restart app to make effect.");
                        }
                    }
                }).initialize();
    }
}