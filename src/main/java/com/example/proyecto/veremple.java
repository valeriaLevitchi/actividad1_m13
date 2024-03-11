package com.example.proyecto;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class veremple extends AppCompatActivity {
    ListView list;
    adapter adp ;
    emple emple;
    List<String> emp ;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityempleados);
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                    }
            };

            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);

            // Inicializar RequestQueue de Volley con HurlStack personalizado
            RequestQueue requestQueue = Volley.newRequestQueue(this, new HurlStack(null, sslContext.getSocketFactory()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        list=findViewById(R.id.listView);




        mostrardatos();
    }
    public void mostrardatos(){
        String url = "https://192.168.19.39/proyecto/sectemple.php";

        // Crea una nueva cola de solicitudes HTTP utilizando Volley
        RequestQueue queue = Volley.newRequestQueue(veremple.this);

        // Realiza una solicitud GET al servidor para obtener los datos
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Parsea los datos JSON y los agrega a la lista
                            List<String> dataList = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                dataList.add(response.getString(i));
                            }

                            // Crea un adaptador para la lista y establece los datos
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(list.getContext(),
                                    android.R.layout.simple_list_item_1, dataList);
                            list.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Muestra un mensaje de error si no se pueden obtener los datos
                Toast.makeText(list.getContext(), "Error al obtener los datos", Toast.LENGTH_SHORT).show();
            }
        });

        // Agrega la solicitud a la cola
        queue.add(jsonArrayRequest);
    }
}
