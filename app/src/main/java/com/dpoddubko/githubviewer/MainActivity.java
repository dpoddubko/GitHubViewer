package com.dpoddubko.githubviewer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.dpoddubko.githubviewer.data.model.UserRepo;
import com.dpoddubko.githubviewer.data.remote.GitApi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.loginText)
    EditText loginText;
    @BindView(R.id.button_refresh)
    Button button_refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.bind(this);
        button_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = loginText.getText().toString();
                Call<List<UserRepo>> call = GitApi.Factory.getInstance().getRepos(user);
                call.enqueue(new Callback<List<UserRepo>>() {
                    @Override
                    public void onResponse(Call<List<UserRepo>> call, Response<List<UserRepo>> response) {
                        String[] repos = new String[response.body().size()];
                        for (int i = 0; i < response.body().size(); i++) {
                            repos[i] = response.body().get(i).getName().toString();
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
                                android.R.layout.simple_list_item_1, repos);
                        listView.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<List<UserRepo>> call, Throwable t) {
                    }
                });
            }
        });
    }
}
