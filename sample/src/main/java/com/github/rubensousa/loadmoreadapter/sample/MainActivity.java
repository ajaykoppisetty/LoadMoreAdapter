package com.github.rubensousa.loadmoreadapter.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.github.rubensousa.loadmoreadapter.LoadMoreAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoadMoreAdapter.OnLoadMoreListener {

    private CustomAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CustomAdapter(mRecyclerView);
        mAdapter.setOnLoadMoreListener(this);

        if (savedInstanceState == null) {
            mAdapter.addData(getData(0));
        } else {
            mAdapter.restoreState(savedInstanceState);
            if (mAdapter.isLoading()) {
                onLoadMore(mAdapter.getItemCount());
            }
        }

        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mAdapter.saveState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter = null;
    }

    @Override
    public void onLoadMore(final int offset) {
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mAdapter != null) {
                    // Call this when you've finished loading data.
                    // Make sure to call it before adding the new data
                    mAdapter.setLoading(false);
                    mAdapter.addData(getData(offset));
                }
            }
        }, 1500);
    }

    private List<String> getData(int offset) {
        List<String> data = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            data.add((offset + i) + "");
        }

        return data;
    }
}
