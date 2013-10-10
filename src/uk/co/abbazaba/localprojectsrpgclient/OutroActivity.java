package uk.co.abbazaba.localprojectsrpgclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


//This class handles the end activity, just telling the user that everything is finished.
public class OutroActivity extends Activity {
	private Button outroEndButton;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.outro_layout); 
		init();
		
	}

	private void init() {
		outroEndButton = (Button)this.findViewById(R.id.outro_button);
		outroEndButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				endGame();
				
			}

			private void endGame() {
				setResult(0);
				finish();
				
			}
		});
		
	}
}
