package cz.skaut.warehousemanager.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;


abstract class RecyclerViewHolder extends RecyclerView.ViewHolder {

	RecyclerViewHolder(View view) {
		super(view);
		ButterKnife.bind(this, view);
	}

	public abstract View getAnimatedView();
}
