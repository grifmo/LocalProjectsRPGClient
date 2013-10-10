package uk.co.abbazaba.localprojectsrpgclient;


import java.util.List;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class QuestionViewAdapter extends BaseAdapter {
	
	static class ViewHolder{
		TextView questionTitle;
		TextView questionText;
		Button yesButton;
		Button noButton;
	}
	
	private List<Question> mQuestions;
	private Context mContext;
	private LayoutInflater mInflater;
	private MainActivity.AnswerClickListener yesListenerRef;
	private MainActivity.AnswerClickListener noListenerRef;

	public QuestionViewAdapter(Context context, List<Question> questions){
		mQuestions = questions;
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
	}
	
	public void setYesHandler(MainActivity.AnswerClickListener daListener){
		yesListenerRef = daListener;
	}
	public void setNoHandler(MainActivity.AnswerClickListener daListener){
		noListenerRef = daListener;
	}
	
	@Override
	public int getCount() {
		return mQuestions.size();
	}

	@Override
	public Object getItem(int position) {
		return mQuestions.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
ViewHolder holder;
		
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.question_view, null);
			holder = new ViewHolder();
			holder.questionTitle = (TextView)convertView.findViewById(R.id.question_title);
			holder.questionText = (TextView)convertView.findViewById(R.id.question_text);
			holder.yesButton = (Button)convertView.findViewById(R.id.question_yes_button);
			
			holder.noButton = (Button)convertView.findViewById(R.id.question_no_button);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		holder.questionTitle.setText(mContext.getString(R.string.question_title) + (position+1));
		holder.questionText.setText(mQuestions.get(position).text);
		if(yesListenerRef != null){
			holder.yesButton.setOnClickListener(yesListenerRef);
			
		}
		if(noListenerRef != null){
			holder.noButton.setOnClickListener(noListenerRef);
			
		}
		return convertView;
	}

}
