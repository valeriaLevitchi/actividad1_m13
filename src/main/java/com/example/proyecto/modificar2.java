package com.example.proyecto;

import android.content.Intent;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class modificar2 extends AppCompatActivity {
    String valor ;
    Button boton;
    EditText dn1 , nomb, apell, puesto, tele, desc;
    String dni, nombre, apellido, puestos, celular, descrip;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modificar2);
        Intent it = getIntent();
        valor = it.getStringExtra("dni");
        dn1 = findViewById(R.id.et_dni);
        nomb = findViewById(R.id.et_nombre);
        apell= findViewById(R.id.et_apellido);
        puesto = findViewById(R.id.et_puesto);
        tele = findViewById(R.id.et_telefono);
        desc = findViewById(R.id.et_descripcion);
        boton =  findViewById(R.id.et_boton);
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
        select();
        
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alterar();
            }
        });

    }

    public void select(){
        String url = "https://192.168.19.39/proyecto/selectempleados.php?dni="+valor;
        RequestQueue que = Volley.newRequestQueue(this);
        JsonObjectRequest queq = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Obtener el valor booleano "hayRegistros" de la respuesta JSON
                        try {
                             dni = response.getString("dni");
                            dn1.setText(dni);
                            nombre = response.getString("nombre");
                            nomb.setText(nombre);
                             apellido = response.getString("apellido");
                            apell.setText(apellido);
                             puestos = response.getString("puesto");
                            puesto.setText(puestos);
                             celular = response.getString("telefono");
                            tele.setText(celular);
                             descrip = response.getString("descripcion");
                            desc.setText(descrip);


                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }


                        // Recorrer el JSONArray y agregar cada elemento al StringBuilder




                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar errores de la solicitud
                        error.printStackTrace();
                    }
                });

        que.add(queq);



    }

    public void alterar (){
        dni = dn1.getText().toString();
        nombre = nomb.getText().toString();
        apellido= apell.getText().toString();
        puestos= puesto.getText().toString();
        celular = tele.getText().toString();
        descrip = desc.getText().toString();
        StringRequest sr = new StringRequest(Request.Method.POST, "https://192.168.19.39/proyecto/alterempleados.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("datos bien")) {
                    Toast.makeText(modificar2.this, "datos bien", Toast.LENGTH_SHORT).show();

                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(modificar2.this , error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>params = new HashMap<String, String>();
                params.put("dniv" , valor);
                params.put("dni", dni);
                params.put("nombre", nombre);
                params.put("apellido", apellido);
                params.put("puesto", puestos);
                params.put("telefono", celular);
                params.put("descripcion", descrip);
                return params;

            }
        };
        RequestQueue rq = Volley.newRequestQueue(modificar2.this);
        rq.add(sr);
    }
}
