package com.example.sunshine.widgets.recycler_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sunshine.R;
import com.example.sunshine.data.one_call_api.RawDataEntry;

import java.util.List;
import java.util.function.BiConsumer;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
  private int itemsNum;
  private List<RawDataEntry> itemsData;
  private ItemClickListener adapterItemClickListener;
  public RecyclerViewAdapter(List<RawDataEntry> itemsData, ItemClickListener listener){
    this.itemsData = itemsData;
    this.itemsNum = this.itemsData.size();
    this.adapterItemClickListener = listener;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    Context context = parent.getContext();
    LayoutInflater inflater = LayoutInflater.from(context);
    View view = inflater.inflate(R.layout.layout_list_item, parent, false);
    //my layout, the view, attaching to the parent immediately.
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.bind(itemsData.get(position));
  }

  @Override
  public int getItemCount() {
    return itemsNum;
  }

  public class ViewHolder extends RecyclerView.ViewHolder{
    ImageView icon;
    TextView date;
    TextView description;
    TextView state;
    TextView temp;
    TextView tempUnit;
    View parentView;
    public ViewHolder(@NonNull View parentView) {
      super(parentView);
      //click listener...
      date = parentView.findViewById(R.id.item_tv_date);
      state = parentView.findViewById(R.id.item_tv_state);
      description = parentView.findViewById(R.id.item_tv_description);
      temp = parentView.findViewById(R.id.item_tv_temp);
      tempUnit = parentView.findViewById(R.id.item_tv_tempUnit);
      icon = parentView.findViewById(R.id.item_image_icon);
      this.parentView = parentView;
      this.parentView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          adapterItemClickListener.ItemClick((int)Math.pow(getAdapterPosition(), 2));
        }
      });
    }
    public void bind(RawDataEntry item){
      this.date.setText("date: " + item.getDate());
      this.state.setText("state: " + item.getState());
      this.description.setText("description:\n  " + item.getDescription());
      this.temp.setText(String.valueOf(item.getTemp()));
      String tempUnitChar = "K";
      switch (item.getTempUnit()){
        case CELSIUS:
          tempUnitChar = "C";
          break;
        case FAHRENHEIT:
          tempUnitChar = "F";
          break;
      }
      this.tempUnit.setText(tempUnitChar);
      this.icon.setImageResource(item.getIconId());
    }
  }
  public interface ItemClickListener{
    public void ItemClick(int position);
  }
}
