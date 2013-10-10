package uk.co.abbazaba.localprojectsrpgclient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TokenGridAdapter extends BaseAdapter {
	
	static class ViewHolder{
		String id;		
		TokenView tokenView;
		
		 @Override
		 public String toString() {
			 return "ViewHolder [id="+id+", tokenView=[id=" + tokenView.getId() + ", token="+tokenView.getToken().text + "]]";
		 }
		
		
	}
	
	private List<TokenView> tokenViews;
	
	private List<Token> mTokens;
	private Context mContext;
	private LayoutInflater mInflater;

	public TokenGridAdapter(Context context, HashMap<String, Token> tokens){
		mTokens = new ArrayList<Token>();
		tokenViews = new ArrayList<TokenView>();
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
		for(Entry<String, Token> entry : tokens.entrySet()){
			Token daToken = entry.getValue();
			mTokens.add(daToken);
			tokenViews.add(new TokenView(mContext,daToken));
		}
		
		//mTokens = tokens;
		
	}
	
	public void updateToken(String tokenId){
		for (int i =0; i<mTokens.size();i++){
			Token daToken = mTokens.get(i);
			if (daToken._id == tokenId){
				daToken.viewReference.updateView();
			}
		}
	}
	
	@Override
	public int getCount() {
		return mTokens.size();
	}

	@Override
	public Object getItem(int position) {
		return mTokens.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;	
		Token currToken = mTokens.get(position);
		TokenView daTokenView = tokenViews.get(position);
		
		if(v == null){
			View testView = parent.findViewById(position);
			if(testView !=null){
				v = testView;
			}else{
				v = mInflater.inflate(R.layout.token_grid_token, null);
				v.setId(position);			
				ViewGroup insertPoint = (ViewGroup)v.findViewById(R.id.insert_point);	
				if(daTokenView.getParent()!=null){
					ViewGroup currentParent = (ViewGroup)(daTokenView.getParent());
					currentParent.removeView(daTokenView);
				}
				insertPoint.addView(daTokenView, 0, new ViewGroup.LayoutParams(R.dimen.token_box_width, R.dimen.token_box_height));
				currToken.viewReference = daTokenView;
			}
			
		}
			
			
			
		return v;
	}

}
