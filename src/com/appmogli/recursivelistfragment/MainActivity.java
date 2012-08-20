package com.appmogli.recursivelistfragment;

import java.io.File;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;

public class MainActivity extends FragmentActivity {

	private RecursiveListFragment recursiveFragment = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		recursiveFragment = (RecursiveListFragment) getSupportFragmentManager()
				.findFragmentById(R.id.activity_main_recursive_list_fragment);
		File root = Environment.getExternalStorageDirectory();
		recursiveFragment.setData(root);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	public void onBackPressed() {
		if(recursiveFragment.canNavigate()) {
			recursiveFragment.pop();
			return;
		}
		super.onBackPressed();
	}

}
