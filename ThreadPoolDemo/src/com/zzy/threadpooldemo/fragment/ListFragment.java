package com.zzy.threadpooldemo.fragment;

import com.zzy.threadpooldemo.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ListFragment extends Fragment {

	private View rootView;
	private ListView lv_list;
	private String [] imageUrls=new String[100];

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootView = inflater.inflate(R.layout.list_fragment, container, false);
		lv_list = (ListView) rootView.findViewById(R.id.lv_list);
		lv_list.setAdapter(new imageListAdapter());
		return rootView;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	private class imageListAdapter extends BaseAdapter {

		private LayoutInflater inflater=LayoutInflater.from(getActivity());
		
		public imageListAdapter(){
			
		}
		
		@Override
		public int getCount() {
			return imageUrls.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view=convertView;
			final ViewHolder holder;
			if(view==null){
				view=inflater.inflate(R.layout.item_list_image, parent,false);
				holder=new ViewHolder();
				holder.text=(TextView)view.findViewById(R.id.text);
				holder.image=(ImageView)view.findViewById(R.id.image);
				view.setTag(holder);
			}else{
				holder=(ViewHolder)view.getTag();
			}
			holder.text.setText("ͼƬ"+position);
			holder.image.setImageResource(R.drawable.ic_launcher);
			return view;
		}

	}

	private static class ViewHolder {
		TextView text;
		ImageView image;
	}

}
