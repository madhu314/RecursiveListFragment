package com.appmogli.recursivelistfragment;

import java.io.File;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class RecursiveListFragment extends Fragment {

	private ListView listView;
	private File root;
	private FileListAdapter adapter = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		listView = (ListView) inflater.inflate(R.layout.recursive_list_fragment, container);
		setUpListview();
		return listView;
	}
	
	private void setUpListview() {
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position,
					long id) {
				FileListAdapter adapter = (FileListAdapter)adapterView.getAdapter();
				File file = (File) (adapter).getItem(position);
				boolean pushed = adapter.pushRoot(file);
				if(!pushed) {
					//this is file intent, give a system intent
					Intent viewIntent = new Intent();
					viewIntent.setAction(Intent.ACTION_VIEW);
					String extension = MimeTypeMap.getFileExtensionFromUrl(file.getAbsolutePath());
					if(MimeTypeMap.getSingleton().hasExtension(extension)) {
						String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
						viewIntent.setDataAndType(Uri.fromFile(file), mimeType);
						try {
							startActivity(viewIntent);
						} catch(ActivityNotFoundException ane) {
							//ignore
						}

					}
					
				}
				
			}
		});
		
	}
	
	@Override
	public void onDestroy() {
		if(listView != null) {
			listView.setOnItemClickListener(null);
		}
		super.onDestroy();
	}

	public void pop() {
		adapter.pop();
	}
	
	public Object root() {
		return null;
	}

	public void setData(File root) {
		this.root = root;
		//now set the adapter
		adapter = new FileListAdapter(getActivity(), root); 
		listView.setAdapter(adapter);
	}

	public boolean canNavigate() {
		return !adapter.isStackEmpty();
	}
}
