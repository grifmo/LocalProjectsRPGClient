package uk.co.abbazaba.localprojectsrpgclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class IntroActivity extends Activity {
	private Button introStartButton;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro_layout); //TODO create opening layout to start with.
		init();
		
	}

	private void init() {
		introStartButton = (Button)this.findViewById(R.id.intro_button);
		introStartButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startGame();
				
			}

			private void startGame() {
				Intent intentMain = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(intentMain);
				Log.i("Content "," Main layout ");
				
			}
		});
		
	}
}
