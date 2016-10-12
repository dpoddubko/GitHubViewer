package com.dpoddubko.githubviewer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dpoddubko.githubviewer.R;
import com.dpoddubko.githubviewer.data.model.UserRepo;
import com.dpoddubko.githubviewer.data.remote.GitApi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PageFragment extends Fragment {
    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    static final String ARGUMENT_USER_NAME = "arg_page_name";
    private String name;
    @BindView(R.id.list_view)
    ListView listView;

    static PageFragment newInstance(int page, String name) {
        PageFragment pageFragment = new PageFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        arguments.putString(ARGUMENT_USER_NAME, name);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = getArguments().getString(ARGUMENT_USER_NAME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.pager_fragment, null);
        ButterKnife.bind(this, view);
        Call<List<UserRepo>> call = getCall(getArguments().getInt(ARGUMENT_PAGE_NUMBER));
        call.enqueue(new Callback<List<UserRepo>>() {
            @Override
            public void onResponse(Call<List<UserRepo>> call, Response<List<UserRepo>> response) {
                String[] repos = new String[response.body().size()];
                for (int i = 0; i < response.body().size(); i++) {
                    repos[i] = response.body().get(i).getFullName().toString();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                        android.R.layout.simple_list_item_1, repos);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View v, int position,
                                            long arg3) {
                        String fullName = (String) adapter.getItemAtPosition(position);
                        Intent intent = DirectoryActivity.newIntent(getActivity(), fullName, "");
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<UserRepo>> call, Throwable t) {
            }
        });

        return view;
    }

    public Call<List<UserRepo>> getCall(int position) {
        GitApi instance = GitApi.Factory.getInstance();
        if (position == 0)
            return instance.getRepos(name);
        return instance.getStars(name);
    }
}