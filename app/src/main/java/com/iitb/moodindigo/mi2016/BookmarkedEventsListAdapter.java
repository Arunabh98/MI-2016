package com.iitb.moodindigo.mi2016;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iitb.moodindigo.mi2016.ServerConnection.GsonModels;

import java.util.List;

/**
 * Created by sajalnarang on 16/12/16.
 */

public class BookmarkedEventsListAdapter extends RecyclerView.Adapter<BookmarkedEventsListAdapter.ViewHolder> {
    private List<GsonModels.Event> eventList;
    private Context context;
    private ItemCLickListener itemCLickListener;

    public BookmarkedEventsListAdapter(List<GsonModels.Event> eventList, ItemCLickListener itemCLickListener) {
        this.eventList = eventList;
        this.itemCLickListener = itemCLickListener;
    }

    @Override
    public BookmarkedEventsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View userView = inflater.inflate(R.layout.bookmarked_event_list_row, parent, false);
        final BookmarkedEventsListAdapter.ViewHolder userViewHolder = new BookmarkedEventsListAdapter.ViewHolder(userView);
        userView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCLickListener.onItemClick(v, userViewHolder.getAdapterPosition());
            }
        });
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(final BookmarkedEventsListAdapter.ViewHolder holder, int position) {
        final GsonModels.Event selectedEvent = eventList.get(position);
        holder.eventName.setText(selectedEvent.getTitle());
        holder.eventVenue.setText(selectedEvent.getLocation());
        holder.eventDescripiton.setText(selectedEvent.getShort_des());
        String time = String.valueOf(selectedEvent.getTime());
        if (time.length() == 3 || time.length() == 7)
            time = "0" + time;
        if (time.length() == 4)
            holder.eventTime.setText(time.substring(0, 2) + ":" + time.substring(2, 4));
        if (time.length() == 8)
            holder.eventTime.setText(time.substring(0, 2) + ":" + time.substring(2, 4) + " - " + time.substring(4, 6) + ":" + time.substring(6, 8));
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapFragment mapFragment = new MapFragment(selectedEvent);
                FragmentManager manager = ((FragmentActivity) context).getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.relativelayout_for_fragment, mapFragment, mapFragment.getTag());
                transaction.commit();
            }
        };
        holder.venueIcon.setOnClickListener(onClickListener);
        holder.eventVenue.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        if (eventList == null) {
            return 0;
        } else {
            return eventList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView eventName;
        private TextView eventVenue;
        private TextView eventTime;
        private TextView eventDescripiton;
        private ImageView venueIcon;
        private ImageView timeIcon;

        public ViewHolder(View itemView) {
            super(itemView);

            eventName = (TextView) itemView.findViewById(R.id.event_name);
            eventVenue = (TextView) itemView.findViewById(R.id.event_venue);
            eventDescripiton = (TextView) itemView.findViewById(R.id.event_description);
            eventTime = (TextView) itemView.findViewById(R.id.event_time);
            venueIcon = (ImageView) itemView.findViewById(R.id.venue_icon);
            timeIcon = (ImageView) itemView.findViewById(R.id.time_icon);
        }
    }
}
