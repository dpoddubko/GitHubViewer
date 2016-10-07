package com.dpoddubko.githubviewer.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class DirectoryActivity extends SingleFragmentActivity {
    private static final String EXTRA_FULL_NAME = "extra_full_name";

    @Override
    protected Fragment createFragment() {
        String fullName = (String) getIntent().getSerializableExtra(EXTRA_FULL_NAME);
        return DirectoryFragment.newInstance(fullName);
    }

    public static Intent newIntent(Context context, String fullName) {
        Intent intent = new Intent(context, DirectoryActivity.class);
        intent.putExtra(EXTRA_FULL_NAME, fullName);
        return intent;
    }

}