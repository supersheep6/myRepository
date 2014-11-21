package com.zzy.threadpooldemo.fragment;

import com.zzy.threadpooldemo.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GirdFragment extends Fragment {

	private GridView gv_grid;
	private String[] imageUrls=new String[100];
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView=inflater.inflate(R.layout.grid_fragment, container,false);
		gv_grid=(GridView)rootView.findViewById(R.id.gv_grid);
		gv_grid.setAdapter(new GridAdapter());
		return rootView;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	private class GridAdapter extends BaseAdapter{

		private LayoutInflater inflater;
		
		public GridAdapter(){
			inflater=LayoutInflater.from(getActivity());
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
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view=convertView;
			final ViewHolder holder;
			if(view==null){
				view=inflater.inflate(R.layout.item_grid_image, parent, false);
				holder=new ViewHolder();
				holder.imageView=(ImageView)view.findViewById(R.id.iv_image);
				view.setTag(holder);
			}else{
				holder=(ViewHolder)view.getTag();
			}
			holder.imageView.setImageResource(R.drawable.ic_launcher);
			return view;
		}
		
	}
	private static class ViewHolder{
		ImageView imageView;
	}
}
