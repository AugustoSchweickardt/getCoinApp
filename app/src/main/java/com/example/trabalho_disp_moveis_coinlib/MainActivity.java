package com.example.trabalho_disp_moveis_coinlib;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RequestQueue mQueue;
    private TextView displayValor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button botaoBitcoin = findViewById(R.id.botaoBitcoin);
        Button botaoEth = findViewById(R.id.botaoEthereum);

        botaoBitcoin.setOnClickListener(this);
        botaoEth.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String requestBitcoin = "https://coinlib.io/api/v1/coin?key=c93b5d04e26caeb1&pref=BRL&symbol=BTC";
        String requestEthereum = "https://coinlib.io/api/v1/coin?key=c93b5d04e26caeb1&pref=BRL&symbol=ETH";

        mQueue = Volley.newRequestQueue(this);

        switch (v.getId()) {
            case R.id.botaoBitcoin:
                parseJson(requestBitcoin);
                break;

            case R.id.botaoEthereum:
                parseJson(requestEthereum);
                break;

            default:
                break;
        }
    }

    private void parseJson(String url) {

        displayValor = findViewById(R.id.displayValor);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String price = response.getString("price");
                            double pricing = Double.parseDouble(price);
                            DecimalFormat format = new DecimalFormat("0.##");

                            displayValor.setText("R$ " + format.format(pricing));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                displayValor.setText("Request\nError");
            }
        });

        mQueue.add(request);
    }
}