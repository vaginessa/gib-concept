package io.github.alamo18.updater.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.github.alamo18.updater.R;
import io.github.alamo18.updater.model.json.Repo;
import io.github.alamo18.updater.utils.LoadJSONTask;

/**
 * Created by alamo with his pizza on 5/9/2017.
 */


public class RepoTestActivity extends AppCompatActivity implements LoadJSONTask.Listener, AdapterView.OnItemClickListener {

    private ListView mListView;

    public static final String URL = "https://gist.githubusercontent.com/alamo18/3222f298b315e39df55afe8b0154a650/raw/94a082c04d6059a7b5a2f4eec1a9909688df2364/gistfile1.txt";

    private List<HashMap<String, String>> mRepoMapList = new ArrayList<>();

    private static final String KEY_NAME = "name";
    private static final String KEY_URL = "url";
    private static final String KEY_DEV = "dev";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_test);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mListView = findViewById(R.id.list_view);
        mListView.setOnItemClickListener(this);
        new LoadJSONTask(this).execute(URL);
    }

    @Override
    public void onLoaded(List<Repo> repoList) {

        for (Repo repolist : repoList) {

            HashMap<String, String> map = new HashMap<>();

            map.put(KEY_NAME, repolist.getName());
            map.put(KEY_URL, repolist.getUrl());
            map.put(KEY_DEV, repolist.getDev());

            mRepoMapList.add(map);
        }

        loadListView();
    }

    @Override
    public void onError() {
        Toast.makeText(this, "Check your internet connection bish :3", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this, mRepoMapList.get(i).get(KEY_NAME),Toast.LENGTH_LONG).show();
    }

    private void loadListView() {
        ListAdapter adapter = new SimpleAdapter(RepoTestActivity.this, mRepoMapList, R.layout.list_item,
                new String[] { KEY_NAME, KEY_URL, KEY_DEV },
                new int[] { R.id.name,R.id.url, R.id.dev });

        mListView.setAdapter(adapter);

    }
}
