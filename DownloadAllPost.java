package com.status.black.status.lover.extra;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.AsyncTask;


import com.status.black.status.lover.model.Photo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class DownloadAllPost extends AsyncTask<String, String, String> {
//new DownloadAllPost(MainActivity.this,photo.app_icon_int).execute();
    Context context;
    ArrayList<String> data;
    Photo insta = Photo.getInstance();
    File myDir;

    public DownloadAllPost(Context c, ArrayList<String> nn) {
        context = c;
        data = nn;
    }

    @Override
    protected String doInBackground(String... strings) {

        int count;

        try {

            myDir = new ContextWrapper(context).getDir("Interstitial",0);

            if (!myDir.exists()) {
                myDir.mkdirs();
            }

            String name;

            for (int i = 0; i < data.size(); i++) {

                name = data.get(i);
                String savePath = myDir.getPath() + File.separator + name;

                if(!new File(savePath).exists())
                {
                    URL url = new URL(insta.banner_pre+name);
                    URLConnection conexion = url.openConnection();
                    conexion.connect();

                    InputStream input = new BufferedInputStream(url.openStream());
                    OutputStream output = new FileOutputStream(new File(savePath));

                    byte data[] = new byte[1024];

                    long total = 0;

                    while ((count = input.read(data)) != -1) {
                        total += count;

                        output.write(data, 0, count);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }

                insta.app_path_int.add(savePath);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

    }
}
