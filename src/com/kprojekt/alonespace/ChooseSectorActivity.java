package com.kprojekt.alonespace;

import org.andengine.engine.options.EngineOptions;
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
		int playerName = this.getIntent().getExtras().getInt( "cityPos" );
	}

	@Override
	public EngineOptions onCreateEngineOptions()
	{
		return null;
	}

	@Override
	protected void onCreateResources()
	{

	}

	@Override
	protected Scene onCreateScene()
	{
		return null;
	}

}
