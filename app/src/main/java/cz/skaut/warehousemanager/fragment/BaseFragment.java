package cz.skaut.warehousemanager.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cz.skaut.warehousemanager.WarehouseApplication;

public abstract class BaseFragment extends Fragment {

	private Unbinder unbinder;

	protected final SharedPreferences prefs = WarehouseApplication.getPrefs();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		return inflater.inflate(getFragmentLayout(), container, false);
	}

	@LayoutRes
	protected abstract int getFragmentLayout();

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		unbinder = ButterKnife.bind(this, view);
		showUpButton();
		setSubtitle("");
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
	}

	protected void showUpButton() {
		ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayShowHomeEnabled(true);
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setHomeButtonEnabled(true);
		}
	}

	protected void hideUpButton() {
		ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayShowHomeEnabled(false);
			actionBar.setDisplayHomeAsUpEnabled(false);
			actionBar.setHomeButtonEnabled(false);
		}
	}

	protected void setSubtitle(String text) {
		ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
		if (actionBar != null) {
			actionBar.setSubtitle(text);
		}
	}

	protected void setTitle(String text) {
		getActivity().setTitle(text);
	}

	protected void setTitle(@StringRes int resId) {
		getActivity().setTitle(resId);
	}
}
