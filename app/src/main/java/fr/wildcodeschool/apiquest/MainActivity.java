package fr.wildcodeschool.apiquest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private final static String API_KEY = "b48c0b27c0fa33fd62c937d43e971f41";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Crée une file d'attente pour les requêtes vers l'API
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // URL de la requête vers l'API
        String url = "http://api.openweathermap.org/data/2.5/forecast?q=Toulouse,FR&appid=" + API_KEY;

        // Création de la requête vers l'API, ajout des écouteurs pour les réponses et erreurs possibles
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // TODO : traiter la réponse
                        try {
                            JSONArray list = response.getJSONArray("list");
                            for (int i = 0; i < list.length(); i++) {

                                JSONObject weatherInfo = (JSONObject) list.get(i);
                                JSONArray weather = (JSONArray) weatherInfo.get("weather");
                                for (int j = 0; j < weather.length(); j++) {
                                    JSONObject weatherinfo = (JSONObject) weather.get(j);
                                    String description = weatherinfo.getString("description");
                                    Toast.makeText(MainActivity.this, description, Toast.LENGTH_SHORT).show();
                                    TextView result = findViewById(R.id.tv_result);
                                    result.append(description);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Afficher l'erreur
                        Log.d("VOLLEY_ERROR", "onErrorResponse: " + error.getMessage());
                    }
                }
        );

        // On ajoute la requête à la file d'attente
        requestQueue.add(jsonObjectRequest);
    }
}
