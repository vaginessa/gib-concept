package io.github.alamo18.updater.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.Arrays;

import io.github.alamo18.updater.R;
import io.github.alamo18.updater.model.json.JSONResponse;
import io.github.alamo18.updater.model.json.Repo;
import io.github.alamo18.updater.ui.adapter.PackageAdapter;
import io.github.alamo18.updater.utils.RequestInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alamo with his pizza on 7/9/2017.
 */

public class TestRepoListActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private ArrayList<Repo> data;
    private PackageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();
    }

    private void initViews(){
        recyclerView = findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PackageAdapter();
        recyclerView.setAdapter(adapter);
        loadJSON();
    }

    private void loadJSON(){
        Retrofit retrofit = new Retrofit.Builder()
                // repo json
                .baseUrl("https://gist.githubusercontent.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<JSONResponse> call = request.getJSON();
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();
                data = new ArrayList<>(Arrays.asList(jsonResponse != null ? jsonResponse.getRepolist() : new Repo[0]));
                adapter.setRepoList(data);
                adapter.setLoading(false);
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable died) {
                adapter.setError(getString(R.string.cannot_load_packages));
                adapter.setLoading(false);
            }
        });
    }
}
