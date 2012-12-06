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
		Camera camera = new Camera( 0, 0, Core.metersToPixels( Core.widthInMeters ),
				Core.metersToPixels( Core.heightInMeters ) );
		return new EngineOptions( Core.fullScreen, Core.orientation, Core.ratioResPolicy, camera );
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
