package uk.co.abbazaba.localprojectsrpgclient;
import android.view.animation.BounceInterpolator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;

import android.util.Log;
import android.view.View;

public class TokenView extends View implements AnimatorUpdateListener {
	private Token token;
	private Paint bgPaint;
	private Paint strokePaint;
	private Paint textPaintSC;
	private Paint textPaintPoints;
	private float textWidthSC;
	private float textWidthPoints;
	private float boxWidth;
	private float boxHeight;
	private float fontSize;
	
	private String pointsText;
	
	private boolean inited = false;
	
	public TokenView(Context context, Token daToken) {
		super(context);
		token = daToken;
		Log.d("TokenView", "constructing new tokenView for " + token.text);
		init();
	}
	// set up the drawing objects for the token view
	private void init(){
		Log.d("TokenView", "initing token " + token.text);
		Resources r = this.getResources();
		fontSize = r.getDimension(R.dimen.token_shortcode_fontsize);
		boxWidth = r.getDimension(R.dimen.token_box_width);
		boxHeight = r.getDimension(R.dimen.token_box_height);
		bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		String colorString = "#FF"+token.color;
		//Log.d("TokenView", "setting tokencolor to "+colorString);
		bgPaint.setColor(Color.parseColor(colorString));
		bgPaint.setStyle(Paint.Style.FILL);
		//Log.d("TokenView", "bgcoloer set to "+ bgPaint.getColor());
		
		strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		strokePaint.setColor(0xFFFFFFFF);
		strokePaint.setStyle(Paint.Style.STROKE);
		strokePaint.setStrokeWidth(10);
		
		
		
		textPaintSC = new Paint(Paint.ANTI_ALIAS_FLAG);
		textPaintSC.setColor(r.getColor(R.color.text_color));
		textPaintSC.setFakeBoldText(true);
		textPaintSC.setSubpixelText(true);
		textPaintSC.setTextSize(fontSize);
		textPaintSC.setTextAlign(Align.LEFT);
		textWidthSC = textPaintSC.measureText(token.shortcode);
		
		
		textPaintPoints = new Paint(Paint.ANTI_ALIAS_FLAG);
		textPaintPoints.setColor(r.getColor(R.color.text_color));
		textPaintPoints.setFakeBoldText(true);
		textPaintPoints.setSubpixelText(true);
		textPaintPoints.setTextSize(fontSize);
		textPaintPoints.setTextAlign(Align.LEFT);
		
		inited = true;
		updateDrawElements();
	}
	
	protected void updateDrawElements(){
		Log.d("TokenView", "token " + token.text + " score = " + token.score);
		pointsText = Integer.toString(token.score); 
		textWidthPoints = textPaintPoints.measureText(pointsText);
	}
	
	public void updateView(){
		Log.d("TokenView", "token " + token.text + " recieved updateView command");
		updateDrawElements();
		ValueAnimator anim = ValueAnimator.ofInt(100,255);
		anim.addUpdateListener(this);
		anim.setDuration(150);
		anim.setRepeatCount(10);
		anim.setRepeatMode(ValueAnimator.REVERSE);
		//anim.setInterpolator(new BounceInterpolator());
		anim.start();
	}
	
	@Override
	protected void onDraw(Canvas canvas){
		
		
		
		canvas.drawRect(0, 0, boxWidth, boxHeight, bgPaint);
		canvas.drawRect(0, 0, boxWidth, boxHeight, strokePaint);
		
		canvas.drawText(token.shortcode, boxWidth/2-textWidthSC/2, (fontSize+10), textPaintSC);
		canvas.drawText(pointsText, boxWidth/2-textWidthPoints/2, boxHeight-15, textPaintSC);
		
	}
	
	
	@Override
	protected void onAttachedToWindow(){
		Log.d("TokenView", "tokenView for " + token.text + " attached to window");
	}
	@Override
	public void onAnimationUpdate(ValueAnimator animation) {
		Integer alpha = (Integer)animation.getAnimatedValue();
		//strokePaint.setAlpha(alpha);
		strokePaint.setARGB(255, 255, alpha, alpha);
		this.requestLayout();
		
	}
	
	public Token getToken(){
		return token;
	}
	

}
