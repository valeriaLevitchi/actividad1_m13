package com.example.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class MainModificar extends AppCompatActivity {
    Button dni;
    EditText dn;

    String valor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modificar);
        dni = findViewById(R.id.botonsito);
        dn = findViewById(R.id.edittext);

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


        dni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprobrar2();
            }
        });




    }



    public void comprobrar2(){


        String dni3 = dn.getText().toString();
        valor = dni3;
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "https://192.168.19.39/proyecto/comprobraremple.php?dni=" + dni3, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Obtener el valor booleano "hayRegistros" de la respuesta JSON
                            boolean hayRegistros = response.getBoolean("registros");
//añadir paso de activity , comprueba si el usuario existe y si exite pasa a otro ctivity
                            if(hayRegistros){
                                Toast.makeText(MainModificar.this, "Si existe" , Toast.LENGTH_SHORT).show();
                                Intent intentq =new Intent(MainModificar.this,modificar2.class);
                                intentq.putExtra("dni" , dni3);
                                MainModificar.this.startActivity(intentq);

                            }else{
                                Toast.makeText(MainModificar.this, "No existe" + dni3 , Toast.LENGTH_SHORT).show();
                            }





                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar errores de la solicitud
                        error.printStackTrace();
                    }
                });

        queue.add(request);

    }
}
