package cz.skaut.warehousemanager.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import cz.skaut.warehousemanager.R;
import cz.skaut.warehousemanager.entity.Item;
import timber.log.Timber;

public class InventorizeAdapter extends RecyclerViewAdapter<Item, InventorizeAdapter.InventoryViewHolder> {

	private final Set<Item> inventorizedItems;

	public InventorizeAdapter(List<Item> data) {
		// create new ArrayList to break RealmList connection
		// TODO: try to remove this
		super(new ArrayList<>(data));
		inventorizedItems = new HashSet<>();
	}

	@Override
	public InventoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_inventorize, parent, false);
		return new InventoryViewHolder(v);
	}

	@Override
	public void onBindViewHolder(InventoryViewHolder holder, final int position) {
		super.onBindViewHolder(holder, position);
		final Item item = getItem(position);
		holder.itemNameText.setText(item.getName());
		holder.inventorizeCheckbox.setOnClickListener(view -> inventorize(item));
	}

	@Override
	public void setData(List<Item> data) {
		// create new ArrayList to break RealmList connection
		super.setData(new ArrayList<>(data));
	}

	public void inventorize(String code) {
		// remove letters from the beginning of code
		long itemId = Long.valueOf(code.substring(2));

		// find the item in list
		Item item = null;
		int i = 0;
		for (; i < data.size(); i++) {
			if (data.get(i).getId() == itemId) {
				item = data.get(i);
				break;
			}
		}

		if (item != null) {
			inventorize(item, i);
		} else {
			Timber.d("Code " + code + " not found");
		}
	}
	// bylo public
	private void inventorize(Item item) {
		int position = data.lastIndexOf(item);
		inventorize(item, position);
	}

	private void inventorize(Item item, int position) {
		data.remove(item);
		inventorizedItems.add(item);
		notifyItemRemoved(position);
	}

	public Set<Item> getInventorizedItems() {
		return inventorizedItems;
	}

	@Override
	public long getItemId(int position) {
		return data.get(position).getId();
	}

	class InventoryViewHolder extends RecyclerViewHolder {

		@BindView(R.id.itemListInventorize) TextView itemNameText;
		@BindView(R.id.inventorizeCheckbox) CheckBox inventorizeCheckbox;

		// bylo public
		InventoryViewHolder(View base) {
			super(base);
		}

		@Override
		public View getAnimatedView() {
			return null;
		}
	}
}
