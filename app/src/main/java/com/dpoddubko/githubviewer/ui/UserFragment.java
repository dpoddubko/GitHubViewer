package com.dpoddubko.githubviewer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.dpoddubko.githubviewer.R;
import com.dpoddubko.githubviewer.data.model.UserRepo;
import com.dpoddubko.githubviewer.data.remote.GitApi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFragment extends Fragment {
    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.loginText)
    EditText loginText;
    @BindView(R.id.button_refresh)
    Button button_refresh;

    public static UserFragment newInstance() {
        return new UserFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.button_refresh)
    public void onClick(View view) {
        final String user = loginText.getText().toString();
        Call<List<UserRepo>> call = GitApi.Factory.getInstance().getRepos(user);
        call.enqueue(new Callback<List<UserRepo>>() {
            @Override
            public void onResponse(Call<List<UserRepo>> call, Response<List<UserRepo>> response) {
                try {
                    response.body().size();
                    Intent intent = PageActivity.newIntent(getActivity(), user);
                    startActivity(intent);
                } catch (NullPointerException e) {
                    Toast.makeText(getActivity(), "User with this name doesn't exist", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<UserRepo>> call, Throwable t) {
            }
        });
    }
}
