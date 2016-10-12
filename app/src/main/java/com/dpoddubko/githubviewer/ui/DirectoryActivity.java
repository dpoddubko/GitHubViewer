package com.dpoddubko.githubviewer.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class DirectoryActivity extends SingleFragmentActivity {
    private static final String EXTRA_FULL_NAME = "extra_full_name";
    private static final String EXTRA_PATH = "extra_path";

    @Override
    protected Fragment createFragment() {
        String fullName = (String) getIntent().getSerializableExtra(EXTRA_FULL_NAME);
        String path = (String) getIntent().getSerializableExtra(EXTRA_PATH);
        return DirectoryFragment.newInstance(fullName, path);
    }

    public static Intent newIntent(Context context, String fullName, String path) {
        Intent intent = new Intent(context, DirectoryActivity.class);
        intent.putExtra(EXTRA_FULL_NAME, fullName);
        intent.putExtra(EXTRA_PATH, path);
        return intent;
    }

}