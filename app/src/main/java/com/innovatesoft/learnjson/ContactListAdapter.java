package com.innovatesoft.learnjson;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Monim on 9/9/2018.
 */
public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    private ArrayList<HashMap<String, String>> contacts;
    private Context context;

    public ContactListAdapter(Context ctx, ArrayList<HashMap<String, String>> list) {
        context = ctx;
        contacts = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameText.setText(contacts.get(position).get("name"));
        holder.emailText.setText(contacts.get(position).get("email"));
        holder.genderText.setText(contacts.get(position).get("gender"));
        holder.mobileText.setText("Mobile: ");
        holder.mobileText.append(contacts.get(position).get("mobile"));
        holder.homeText.setText("Home: ");
        holder.homeText.append(contacts.get(position).get("home"));
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameText, genderText, emailText, mobileText, homeText;

        public ViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.nameText);
            genderText = itemView.findViewById(R.id.genderText);
            emailText = itemView.findViewById(R.id.emailText);
            mobileText = itemView.findViewById(R.id.mobileText);
            homeText = itemView.findViewById(R.id.homeText);
        }
    }

}
