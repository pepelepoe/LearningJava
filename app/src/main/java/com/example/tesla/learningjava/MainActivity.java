package com.example.tesla.learningjava;

        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.util.Log;
        import android.view.View;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.widget.Button;
        import android.widget.TextView;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.MalformedURLException;
        import java.net.URL;
        import java.io.*;
        import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    private TextView tvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        tvData = (TextView) findViewById(R.id.textHello);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JSONTask().execute("https://jsonparsingdemo-cec5b.firebaseapp.com/jsonData/moviesDemoItem.txt");
            }
        });
    }

    public class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream(); //store data in stream object

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null)
                    connection.disconnect();
                try {
                    if (reader != null)
                        reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return  null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            tvData.setText(result);
        }
    }

//    public void getURL() {
//        System.out.println("here");
//       try {
//           URL url = new URL("https://www.android.com/");
//
//           BufferedReader inputStream = new BufferedReader(
//                   new InputStreamReader(url.openStream()));
//
//           String line = "";
//           while (line != null) {
//               line = inputStream.readLine();
//               System.out.println(line);
//           }
//           inputStream.close();
//       } catch (IOException e) {
//           System.out.println("IOException: " + e.getMessage());
//       }
//    }

//    private void readStream(InputStream in) {
//        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//        StringBuilder result = new StringBuilder();
//        String line;
//        try {
//            while((line = reader.readLine()) != null) {
//                result.append(line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println(result.toString());
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
