package com.example.proyecto;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class MainRegistrar extends AppCompatActivity {
    TextView dni;
    TextView nombre;
    TextView apellidos;
    TextView puesto;
    TextView telefono;
    TextView descripcion;
    Button boton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar);
        dni = findViewById(R.id.vl_dni);
        nombre =findViewById(R.id.vl_nombre);
        apellidos=findViewById(R.id.vl_apellido);
        puesto=findViewById(R.id.vl_puesto);
        telefono =findViewById(R.id.vl_telefono);
        descripcion=findViewById(R.id.vl_descripcion);
        boton =findViewById(R.id.vl_boton);
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
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertar2();
            }
        });
    }

    public void insertar2 (){
        String dni1= dni.getText().toString();
        String nombre1 = nombre.getText().toString();
        String apellido1= apellidos.getText().toString();
        String puesto1 = puesto.getText().toString();
        String telefono1 = telefono.getText().toString();
        String descripcion1 = descripcion.getText().toString();
        StringRequest sr = new StringRequest(Request.Method.POST, "https://192.168.19.39/proyecto/insertarempleados.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("datos bien")) {
                    Toast.makeText(MainRegistrar.this, "datos bien", Toast.LENGTH_SHORT).show();

                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainRegistrar.this , error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>params = new HashMap<String, String>();
                params.put("dni", dni1);
                params.put("nombre",nombre1);
                params.put("apellido",apellido1);
                params.put("puesto",puesto1);
                params.put("telefono",telefono1);
                params.put("descripcion",descripcion1);
                return params;

            }
        };
        RequestQueue rq = Volley.newRequestQueue(MainRegistrar.this);
        rq.add(sr);
    }

}
