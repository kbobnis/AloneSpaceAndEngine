package com.kprojekt.alonespace.activities.andengine;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import com.kprojekt.alonespace.data.Core;

/**
 * 
 */
public class ShowShipActivity extends SimpleBaseGameActivity
{

	@Override
	public EngineOptions onCreateEngineOptions()
	{
		Camera camera = new Camera( 0, 0, Core.width, Core.height );
		return new EngineOptions( false, MinigameActivity.orientation, MinigameActivity.resPolicy, camera );
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
