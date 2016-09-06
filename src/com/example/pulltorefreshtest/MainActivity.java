package com.example.pulltorefreshtest;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.pulltorefreshtest.RefreshableView.PullToRefreshListener;

public class MainActivity extends Activity {

	RefreshableView refreshableView;
	ListView listView;
	ArrayAdapter<String> adapter;
	String[] items = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
			"L" };
	private Handler mHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		mHandler = new Handler() {
		};
		refreshableView = (RefreshableView) findViewById(R.id.refreshable_view);
		listView = (ListView) findViewById(R.id.list_view);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, items);
		listView.setAdapter(adapter);
		refreshableView.setOnRefreshListener(new PullToRefreshListener() {
			@Override
			public void onRefresh() {
				new Thread(new Runnable() {

					@Override
					public void run() {
						// try {
						// Thread.sleep(5000);
						// } catch (InterruptedException e) {
						// e.printStackTrace();
						// }
						new MyTask().execute();
						// refreshableView.finishRefreshing();
					}
				}).start();
			}
		}, 0);
	}

	class MyTask extends AsyncTask<Void, Integer, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			 String[] items = { "A", "A", "A", "A", "E", "F", "G", "H",
					"I", "J", "K", "L" };

			return items;
		}

		@Override
		protected void onPostExecute(final String[] items) {
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					adapter = new ArrayAdapter<String>(MainActivity.this,
							android.R.layout.simple_list_item_1, items);
					listView.setAdapter(adapter);
				}
			});
			refreshableView.finishRefreshing();
		}

	}

}
