package com.example.proyecto;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class MainEliminar extends AppCompatActivity {
    EditText dniborrar;
    Button borrardni;
    TextView mensaje;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eliminar);
        dniborrar = findViewById(R.id.vl_borrar);
        borrardni = findViewById(R.id.vl_bottonborrar);
        mensaje = findViewById(R.id.mensaje10);
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
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
      borrardni.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              borrar();
          }
      });

    }

    public void borrar(){
        String dniborrar1 = dniborrar.getText().toString();

        StringRequest sr = new StringRequest(Request.Method.POST, "https://192.168.19.39/proyecto/borrar.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                    Toast.makeText(MainEliminar.this, "usuario eliminado correctamente", Toast.LENGTH_SHORT).show();

                }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainEliminar.this , error.getMessage(), Toast.LENGTH_SHORT).show();
                mensaje.setText(error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>params = new HashMap<String, String>();
                params.put("dni", dniborrar1);

                return params;

            }
        };
        RequestQueue rq = Volley.newRequestQueue(MainEliminar.this);
        rq.add(sr);
    }
}