package kr.mindwing.camp_sms;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import kr.mindwing.camp_sms.lib.MessageData;

class SmsConversationsViewHolder extends RecyclerView.ViewHolder {

    private TextView tvConversation;
    private TextView tvDate;
    private int myNumber;
    private static int number;

    public SmsConversationsViewHolder(View smsConversationView) {
        super(smsConversationView);

        myNumber = number;
        number = number + 1;

        tvConversation = (TextView) smsConversationView
                .findViewById(R.id.conversation);
        tvDate = (TextView) smsConversationView
                .findViewById(R.id.date);
    }

    public void updateContent(MessageData messageData) {
        int layoutPos = getLayoutPosition();

        // TODO myNumber 를 덧붙여서 ViewHolder 가 어떻게 재활용되는지 확인해보세요.
        String date = String.format("[%d, %d] %s", layoutPos, 0, messageData.getDateString());

        tvConversation.setText(messageData.getBody());
        tvDate.setText(date);
    }
}
