package com.zzy.hotfix;

import android.app.Application;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.interfaces.BetaPatchListener;
import com.tencent.bugly.beta.upgrade.UpgradeStateListener;

import java.util.Locale;

/**
 * @author zzy
 * @date 2018/9/6
 */

public class HotfixAdapter {
    private static final String TAG = "HotfixAdapter";

    public static void init(final Application application, final boolean isDebug){
//        setStrictMode();
        // 设置是否开启热更新能力，默认为true
        Beta.enableHotfix = true;
        // 设置是否自动下载补丁
        Beta.canAutoDownloadPatch = true;
        // 设置是否提示用户重启
        Beta.canNotifyUserRestart = true;
        // 设置是否自动合成补丁
        Beta.canAutoPatch = true;

        /**
         *  全量升级状态回调
         */
        Beta.upgradeStateListener = new UpgradeStateListener() {
            @Override
            public void onUpgradeFailed(boolean b) {
                Log.e(TAG,"onUpgradeFailed");
            }

            @Override
            public void onUpgradeSuccess(boolean b) {
                Log.e(TAG,"onUpgradeSuccess");
            }

            @Override
            public void onUpgradeNoVersion(boolean b) {
                Log.e(TAG,"onUpgradeNoVersion");
                if(isDebug)
                    Toast.makeText(application, "最新版本", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUpgrading(boolean b) {
                Log.e(TAG,"onUpgrading");
                if(isDebug)
                    Toast.makeText(application, "onUpgrading", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDownloadCompleted(boolean b) {
                Log.e(TAG,"onDownloadCompleted");
            }
        };

        /**
         * 补丁回调接口，可以监听补丁接收、下载、合成的回调
         */
        Beta.betaPatchListener = new BetaPatchListener() {
            @Override
            public void onPatchReceived(String patchFileUrl) {
                Log.e(TAG,"onPatchReceived");
                if(isDebug)
                    Toast.makeText(application, patchFileUrl, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDownloadReceived(long savedLength, long totalLength) {
                Log.e(TAG,"onDownloadReceived");
                if(isDebug)
                    Toast.makeText(application, String.format(Locale.getDefault(),
                        "%s %d%%",
                        Beta.strNotificationDownloading,
                        (int) (totalLength == 0 ? 0 : savedLength * 100 / totalLength)), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDownloadSuccess(String patchFilePath) {
                Log.e(TAG,"onDownloadSuccess");
                if(isDebug)
                    Toast.makeText(application, patchFilePath, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDownloadFailure(String msg) {
                Log.e(TAG,"onDownloadFailure");
                if(isDebug)
                    Toast.makeText(application, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onApplySuccess(String msg) {
                Log.e(TAG,"onApplySuccess");
                if(isDebug)
                    Toast.makeText(application, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onApplyFailure(String msg) {
                Log.e(TAG,"onApplyFailure");
                if(isDebug)
                    Toast.makeText(application, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPatchRollback() {
                Log.e(TAG,"onPatchRollback");
                if(isDebug)
                    Toast.makeText(application, "onPatchRollback", Toast.LENGTH_SHORT).show();
            }
        };
        Bugly.init(application, Constants.APP_ID, isDebug);
    }

    private static void setStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
    }

    public static void install(){
        Beta.installTinker();
    }

    public static void checkUpgrade(){
        Beta.checkUpgrade();
    }
}
