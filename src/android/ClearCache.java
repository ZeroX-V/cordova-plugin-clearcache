package com.chinact.mobile.plugin.clearcache;

import android.app.Activity;
import android.os.Environment;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;

public class ClearCache extends CordovaPlugin {

    private static Activity cordovaActivity;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        cordovaActivity = cordova.getActivity();
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("getCaches")) {
            this.getCaches(callbackContext);
            return true;
        } else if (action.equals("clearCaches")) {
            this.clearCaches(callbackContext);
            return true;
        }
        return false;
    }

    private void getCaches(CallbackContext callbackContext) {
        long size = getFolderSize(cordovaActivity.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            size += getFolderSize(cordovaActivity.getExternalCacheDir());
        callbackContext.success(String.valueOf(size));
    }

    private void clearCaches(CallbackContext callbackContext) {
        deleteFolder(cordovaActivity.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteFolder(cordovaActivity.getExternalCacheDir());
            cordovaActivity.deleteDatabase("webview.db");
            cordovaActivity.deleteDatabase("webviewCache.db");
        }
        callbackContext.success("true");
    }

    private long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory())
                    size += getFolderSize(fileList[i]);
                else
                    size += fileList[i].length();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    private boolean deleteFolder(File folder) {
        if (folder != null && folder.isDirectory()) {
            String[] folderList = folder.list();
            for (int i = 0; i < folderList.length; i++)
                return deleteFolder(new File(folder, folderList[i]));
        }
        return folder.delete();
    }

}