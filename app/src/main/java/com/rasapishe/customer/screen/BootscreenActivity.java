package com.rasapishe.customer.screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.rasapishe.boom.customer.R;

/**
 * Created by Mohammad reza khodabandeh.
 */
public class BootscreenActivity extends Activity {

	public static Activity boot_screen;
	private String str;


	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle icicle) {


		super.onCreate(icicle);
		setContentView(R.layout.activity_boot_screen);
		boot_screen = this;
		final TextView textViewAppName = (TextView) findViewById(R.id.bootScreenTextview);
		textViewAppName.setVisibility(View.INVISIBLE);
		final Animation myFadeOutAnimation = AnimationUtils.loadAnimation(this, R.anim.boot_screen_animation);
		final Animation myFadeOutAnimationSecond = AnimationUtils.loadAnimation(this, R.anim.boot_screen_animation);


		ImageView ImageViewLogo = (ImageView) findViewById(R.id.splashScreen);
        TextView textViewBankName = (TextView) findViewById(R.id.textviewBankName);
		ImageViewLogo.startAnimation(myFadeOutAnimation);
        textViewBankName.startAnimation(myFadeOutAnimation);




        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
		        /* Create an Intent that will start the Menu-Activity. */
				textViewAppName.setVisibility(View.VISIBLE);
				textViewAppName.startAnimation(myFadeOutAnimationSecond);

			}
		}, 3000);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {

				Intent mainIntent = new Intent(BootscreenActivity.this, HomeActivity.class);
				BootscreenActivity.this.startActivity(mainIntent);
				BootscreenActivity.this.finish();


			}
		}, (3000 * 2));
	}
}

