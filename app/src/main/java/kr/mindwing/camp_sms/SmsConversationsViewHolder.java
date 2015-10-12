package kr.mindwing.camp_sms;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import kr.mindwing.camp_sms.lib.MessageData;

class SmsConversationsViewHolder extends RecyclerView.ViewHolder {

    private TextView tvConversation;
    private TextView tvDate;

    public SmsConversationsViewHolder(View smsConversationView) {
        super(smsConversationView);

        tvConversation = (TextView) smsConversationView
                .findViewById(R.id.conversation);
        tvDate = (TextView) smsConversationView
                .findViewById(R.id.date);
    }

    public void updateContent(MessageData messageData) {
        tvConversation.setText(messageData.getBody());
        tvDate.setText(messageData.getDateString());
    }
}
