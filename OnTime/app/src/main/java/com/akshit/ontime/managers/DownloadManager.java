package com.akshit.ontime.managers;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import com.akshit.ontime.util.AppContext;

public class DownloadManager {

    private static DownloadManager instance;

    private DownloadManager() {

    }

    public static synchronized DownloadManager getInstance() {
        if (instance == null) {
            instance = new DownloadManager();
        }
        return instance;
    }

    public void downloadFile(final String url, final String fileName, final String fileExtension) {
        android.app.DownloadManager downloadmanager = (android.app.DownloadManager) AppContext.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);

        android.app.DownloadManager.Request request = new android.app.DownloadManager.Request(uri);
        request.setTitle(fileName);
        request.setDescription("Downloading");
        request.setNotificationVisibility(android.app.DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        downloadmanager.enqueue(request);
    }
}
