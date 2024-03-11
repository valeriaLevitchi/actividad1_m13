package com.example.proyecto;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity1 extends AppCompatActivity {
    //declarar variables
    EditText usuario;
    EditText contrasena;
    EditText nombre;
    EditText apellidos;
    EditText telefono;
    Button boton;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrarse);
        //conecar
    usuario= findViewById(R.id.usuario1);
    contrasena = findViewById(R.id.contrasena1);
    nombre = findViewById(R.id.nombre1);
    apellidos= findViewById(R.id.apellidos1);
    telefono =findViewById(R.id.telefono1);
    boton = findViewById(R.id.boton11);
    //SSl para evitar posibles errores
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
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertar();

            }
        });

    }

    public void insertar (){
        //obtener datos
        String usuarios = usuario.getText().toString();
        String contraseña = contrasena.getText().toString();
        String nombre2 =nombre.getText().toString();
        String apellidos2 =apellidos.getText().toString();
        String telefono2 =telefono.getText().toString();
        //conecatar php
        StringRequest sr = new StringRequest(Request.Method.POST, "https://192.168.19.39/proyecto/insert.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("Los datos han sido guardado correctamente")) {
                    Toast.makeText(MainActivity1.this, "Los datos han sido guardado correctamente", Toast.LENGTH_SHORT).show();

                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity1.this , error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }){
            //pasar datos php
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>params = new HashMap<String, String>();
                params.put("usuario", usuarios);
                params.put("contrasena", contraseña);
                params.put("nombre",nombre2);
                params.put("apellidos",apellidos2);
                params.put("telefono",telefono2);
                return params;

            }
        };
        RequestQueue rq = Volley.newRequestQueue(MainActivity1.this);
        rq.add(sr);
    }


}
