package com.dpoddubko.githubviewer.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dpoddubko.githubviewer.R;
import com.dpoddubko.githubviewer.data.model.Directory;
import com.dpoddubko.githubviewer.data.remote.GitApi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DirectoryFragment extends Fragment {
    private static final String ARG_FULL_NAME = "arg_full_name";
    private String user;
    private String repo;
    @BindView(R.id.list_view)
    ListView listView;

    public static DirectoryFragment newInstance(String fullName) {
        Bundle args = new Bundle();
        args.putString(ARG_FULL_NAME, fullName);
        DirectoryFragment fragment = new DirectoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String fullName = getArguments().getString(ARG_FULL_NAME);
        String[] parts = fullName.split("/");
        user = parts[0];
        repo = parts[1];
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.pager_fragment, null);
        ButterKnife.bind(this, view);
        Call<List<Directory>> call = GitApi.Factory.getInstance().getDir(user, repo);
        call.enqueue(new Callback<List<Directory>>() {
            @Override
            public void onResponse(Call<List<Directory>> call, Response<List<Directory>> response) {
                int size = response.body().size();
                String[] repos = new String[size];
                for (int i = 0; i < size; i++) {
                    repos[i] = response.body().get(i).getName().toString();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                        android.R.layout.simple_list_item_1, repos);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Directory>> call, Throwable t) {
            }
        });

        return view;
    }
}
