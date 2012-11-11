package com.kprojekt.alonespace.activities.andengine;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.os.Bundle;

/**
 * 
 */
public class ChooseSectorActivity extends SimpleBaseGameActivity
{

	@Override
	protected void onCreate( Bundle pSavedInstanceState )
	{
		super.onCreate( pSavedInstanceState );

	}

	@Override
	public EngineOptions onCreateEngineOptions()
	{
		Camera camera = new Camera( 0, 0, MinigameActivity.CAMERA_WIDTH, MinigameActivity.CAMERA_HEIGHT );
		return new EngineOptions( false, ScreenOrientation.LANDSCAPE_SENSOR, new RatioResolutionPolicy(
				MinigameActivity.CAMERA_WIDTH, MinigameActivity.CAMERA_HEIGHT ), camera );
	}

	@Override
	protected void onCreateResources()
	{

	}

	@Override
	protected Scene onCreateScene()
	{
		return new Scene();
	}
}
