package kr.mindwing.camp_sms;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import kr.mindwing.camp_sms.lib.AddressInfo;
import kr.mindwing.camp_sms.lib.MessageData;

public class SmsConversationsAdapter extends
        RecyclerView.Adapter<SmsConversationsViewHolder> {

    private static final int CONVER_FROM = 0;
    private static final int CONVER_TO = 1;

    private ArrayList<MessageData> conversations;

    public SmsConversationsAdapter(ArrayList<MessageData> _conversations) {
        conversations = _conversations;
    }

    @Override
    public int getItemCount() {
        return conversations.size();
    }

    @Override
    public void onBindViewHolder(SmsConversationsViewHolder holder, int position) {
        holder.updateContent(conversations.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        boolean isReceived = conversations.get(position).isReceived();

        return isReceived ? CONVER_FROM : CONVER_TO;
    }

    @Override
    public SmsConversationsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        LayoutInflater layoutInflater = LayoutInflater
                .from(parent.getContext());

        // TODO 새로운 나인패치 대화풍선을 만들어보세요.
        if (viewType == CONVER_FROM) {
            view = layoutInflater.inflate(R.layout.listitem_conversation_from, parent, false);

        } else {
            view = layoutInflater.inflate(R.layout.listitem_conversation_to, parent, false);
        }

        SmsConversationsViewHolder viewHolder = new SmsConversationsViewHolder(view);

        return viewHolder;
    }

    public void notifyMessageAdded(AddressInfo addressInfo, String message) {
        MessageData msgData = MessageData.buildSentFromScratch(addressInfo, message, System.currentTimeMillis());

        conversations.add(msgData);
        notifyItemInserted(conversations.size() - 1);
    }
}
