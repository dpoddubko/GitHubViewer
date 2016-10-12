package com.dpoddubko.githubviewer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dpoddubko.githubviewer.ListViewAdapter;
import com.dpoddubko.githubviewer.R;
import com.dpoddubko.githubviewer.RowItem;
import com.dpoddubko.githubviewer.data.model.Directory;
import com.dpoddubko.githubviewer.data.remote.GitApi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DirectoryFragment extends Fragment {
    private static final String ARG_FULL_NAME = "arg_full_name";
    private static final String ARG_PATH = "arg_path";
    private String user;
    private String repo;
    private String path;
    private String fullName;
    @BindView(R.id.list_view)
    ListView listView;

    public static DirectoryFragment newInstance(String fullName, String path) {
        Bundle args = new Bundle();
        args.putString(ARG_FULL_NAME, fullName);
        args.putString(ARG_PATH, path);
        DirectoryFragment fragment = new DirectoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullName = getArguments().getString(ARG_FULL_NAME);
        path = getArguments().getString(ARG_PATH);
        String[] parts = fullName.split("/");
        user = parts[0];
        repo = parts[1];
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.pager_fragment, null);
        ButterKnife.bind(this, view);
        Call<List<Directory>> call = GitApi.Factory.getInstance().getDir(user, repo, path);
        call.enqueue(new Callback<List<Directory>>() {
            @Override
            public void onResponse(Call<List<Directory>> call, Response<List<Directory>> response) {
                final List<RowItem> rowItems = new ArrayList<>();
                RowItem item;
                for (int i = 0; i < response.body().size(); i++) {
                    String name = response.body().get(i).getName().toString();
                    String type = response.body().get(i).getType().toString();
                    if ((type).equals("dir")) {
                        item = new RowItem(R.drawable.dir, name);
                    } else {
                        item = new RowItem(R.drawable.file, name);
                    }
                    rowItems.add(item);
                }
                ListViewAdapter adapter = new ListViewAdapter(getActivity(),
                        R.layout.list_item, rowItems);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View v, int position,
                                            long arg3) {
                        String locPath = path + "/" + rowItems.get(position).getTitle();
                        Intent intent = DirectoryActivity.newIntent(getActivity(), fullName, locPath);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Directory>> call, Throwable t) {
            }
        });

        return view;
    }
}
