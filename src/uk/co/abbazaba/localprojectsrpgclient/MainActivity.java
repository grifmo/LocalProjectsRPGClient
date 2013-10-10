package uk.co.abbazaba.localprojectsrpgclient;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterViewFlipper;

import android.widget.GridView;


import com.kodart.httpzoid.*;


public class MainActivity extends Activity {
	public static String HOST_NAME = "http://lprpgapi.herokuapp.com/";
	public static String TOKEN_ENDPOINT = "tokens/";
	public static String QUESTION_ENDPOINT = "questions/";
	
	private int currentQuestion = 0;
	private List<Question> questions;
	
	
	private HashMap<String, Token> tokens;
	private GridView resultView;
	private TokenGridAdapter resultAdapter;
	private QuestionViewAdapter questionAdapter;
	private AdapterViewFlipper questionFlipper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_flip); //TODO create opening layout to start with.
		init();
		
	}
	
	
	
	protected void init(){
		questions = new ArrayList<Question>();
		loadTokens();
	}
	
	public class AnswerClickListener implements OnClickListener {
		
		String answer;
		public AnswerClickListener(String daAnswer){
			answer = daAnswer;
		}
		@Override
		public void onClick(View v) {
			Log.d("Answer", "the answer is " + answer);
			Log.d("Answer", "Looping through tokens in question");
			
			for(int i=0; i<questions.get(currentQuestion).results.size();i++){				
				boolean updateWorthy = false;
				Result itRes = questions.get(currentQuestion).results.get(i);				
				Log.d("Answer", "result = yes:"+itRes.yes + ", no:"+itRes.no + ", token: " + itRes.token);
				if(tokens.containsKey(itRes.token)){
					Token currToken = tokens.get(itRes.token);
					Log.d("Answer", "found token " + itRes.token + " in tokenlist, title = " +currToken.text);
					if(answer=="yes"){						
						currToken.score += itRes.yes;
						if(itRes.yes != 0){
							updateWorthy = true;
						}
					}else if(answer=="no"){
						currToken.score += itRes.no;
						if(itRes.no != 0){
							updateWorthy = true;
						}
					}
					if(updateWorthy){
						TokenView tokenView = currToken.viewReference;
						if(tokenView!=null){
							Log.d("daToken", "updating view for " + currToken.text);
							tokenView.updateView();
						}else{
							Log.d("daToken", "no view found for token "+ currToken.text);
						}
					}
					
					
					
				}else{
					Log.d("Answer", "token " + itRes.token + "in not found in tokenlist. Update the database");
				}				
			}			
			goNextQuestion();
		}
		
	}
	
	

	private void loadTokens(){
		tokens = new HashMap<String,Token>();
		Http http = HttpFactory.create(this);
		Log.d("API", "thread before get = " + android.os.Process.getThreadPriority(android.os.Process.myTid()));
		Log.d("API", "getting tokens from: " + HOST_NAME + TOKEN_ENDPOINT);
		http.get(HOST_NAME + TOKEN_ENDPOINT).handler(new ResponseHandler<Token[]>() {
	        @Override
	        public void success(Token[] daTokens, HttpResponse response) {
	        	Log.d("API", "thread on success handler = " + android.os.Process.getThreadPriority(android.os.Process.myTid()));
	        	for(int i=0; i<daTokens.length; i++){
	        		Log.d("API", "adding Token " + daTokens[i].text);
	        		tokens.put(daTokens[i]._id, daTokens[i]);
	        		
	        	}	
	        	addTokenViews();
	        	loadQuestions();
	        	
	        }
	    }).send();
	}
	private void loadQuestions(){
		Http http = HttpFactory.create(this);
		http.get(HOST_NAME + QUESTION_ENDPOINT).handler(new ResponseHandler<Question[]>() {
	        @Override
	        public void success(Question[] daQuestions, HttpResponse response) {
	        	for(int i=0;i<daQuestions.length;i++){
	        		questions.add(daQuestions[i]);
	        	}
	        	
	        	addQuestionViews();
	        }

	    }).send();
	}
	
	private void addQuestionViews(){
		questionFlipper = (AdapterViewFlipper)findViewById(R.id.questionflipper);
		
		questionAdapter = new QuestionViewAdapter(this, questions);
		questionAdapter.setYesHandler(new AnswerClickListener("yes"));
		questionAdapter.setNoHandler(new AnswerClickListener("no"));
		questionFlipper.setAdapter(questionAdapter);
		questionFlipper.setInAnimation(this, R.animator.flipin);
		questionFlipper.setOutAnimation(this, R.animator.flipout);
		questionFlipper.setAutoStart(false);
		
		
	}
	
	private void addTokenViews(){
		
		resultAdapter = new TokenGridAdapter(this, tokens);
		resultView = (GridView)findViewById(R.id.resultsview);
		resultView.setAdapter(resultAdapter);
		
	}
	
	private void goNextQuestion() {
			
		if(currentQuestion< questions.size()-1){			
			questionFlipper.showNext();
			currentQuestion++;
		}else{
			endGame();
		}
		
		
	}
	
	private void endGame(){
		Intent intentEnd = new Intent(getApplicationContext(), OutroActivity.class);
		startActivityForResult(intentEnd,0);
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if(resultCode == 0) {
	        finish();
	    }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
