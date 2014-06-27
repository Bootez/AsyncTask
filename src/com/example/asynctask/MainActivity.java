package com.example.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
    private static final String TAG = "MyAsycTask";
    private TextView message;
    private Button open;
    private EditText url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        message = (TextView) findViewById(R.id.message);
        url = (EditText) findViewById(R.id.url);
        open = (Button) findViewById(R.id.open);
        open.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                MyAsyncTask myAsyncTask = new MyAsyncTask(MainActivity.this);
                myAsyncTask.execute();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    class MyAsyncTask extends AsyncTask<Void, Integer, String> {
        // 可变长的输入参数，与AsyncTask.exucute()对应
        ProgressDialog pdialog;

        @SuppressWarnings("deprecation")
        public MyAsyncTask(Context context) {
            pdialog = new ProgressDialog(context, 0);
            pdialog.setButton("cancel", new DialogInterface.OnClickListener() {
                public void onClick(final DialogInterface dialog, final int i) {
                    dialog.cancel();
                }
            });
            pdialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface dialog) {
                    finish();
                }
            });
            pdialog.setCancelable(true);
            pdialog.setMax(100);
            pdialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pdialog.show();

        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                for (int i = 0; i <= 100; i += 10) {
                    Log.i(TAG, "i= " + i);
                    Thread.sleep(1000);
                    publishProgress(i);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onCancelled() {
            Log.i(TAG, "Canceled!");
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(String result) {
            message.setText(result);
            pdialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute!");
            message.setText(R.string.task_started);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.i(TAG, "onProgressUpdate...");
            System.out.println("" + values[0]);
            message.setText("" + values[0]);
            pdialog.setProgress(values[0]);
        }

    }
}
