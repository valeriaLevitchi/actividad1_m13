package com.example.proyecto;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class Mainentrar extends AppCompatActivity {
    TextView registrarse1;
    Button bt_boton;
    EditText et_usuario;
    EditText et_contrasena;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //codigo SSl para evitar cualquier error
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
        //conexiones
        setContentView(R.layout.activity1_main);
          bt_boton =findViewById(R.id.boton111);
          et_usuario=findViewById(R.id.usuario12);
          et_contrasena=findViewById(R.id.contrasena12);
        registrarse1= (TextView) findViewById(R.id.registrarse);
        registrarse1.setOnClickListener(new View.OnClickListener() {
            //para pasar de una activity a otra
            @Override
            public void onClick(View v) {
                Intent intentReg =new Intent(Mainentrar.this,MainActivity1.class);
                Mainentrar.this.startActivity(intentReg);
            }
        });

        bt_boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrar();
            }
        });

    }
    public void mostrar(){

        String nombre = et_usuario.getText().toString();
        String passe = et_contrasena.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "https://192.168.19.39/proyecto/comprobrar.php?nombre=" + nombre + "&contrasena=" + passe, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Obtener el valor booleano "hayRegistros" de la respuesta JSON
                            boolean hayRegistros = response.getBoolean("registros");

                            if(hayRegistros){
                                Toast.makeText(Mainentrar.this, "Si existe" + passe, Toast.LENGTH_SHORT).show();

                                Intent intento =new Intent(Mainentrar.this,Mainempleados.class);
                                Mainentrar.this.startActivity(intento);
                            }else{
                                Toast.makeText(Mainentrar.this, "No existe" + passe, Toast.LENGTH_SHORT).show();
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