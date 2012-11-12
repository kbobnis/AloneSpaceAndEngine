package com.kprojekt.alonespace.activities.andengine;

import java.io.IOException;
import java.io.InputStream;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;

import android.os.Bundle;

import com.kprojekt.alonespace.data.Core;

public class ChooseSectorActivity extends SimpleBaseGameActivity
{

	private TextureRegion shipTextureRegion;
	private ShipOnSector shipOnSector;
	private SectorBehindShip sectorBehindShip;

	@Override
	protected void onCreate( Bundle pSavedInstanceState )
	{
		super.onCreate( pSavedInstanceState );
	}

	@Override
	public EngineOptions onCreateEngineOptions()
	{
		Camera camera = new Camera( 0, 0, MinigameActivity.CAMERA_WIDTH, MinigameActivity.CAMERA_HEIGHT );
		return new EngineOptions( false, MinigameActivity.orientation, MinigameActivity.resPolicy, camera );
	}

	@Override
	protected void onCreateResources()
	{
		BitmapTexture shipTexture;
		try
		{
			shipTexture = new BitmapTexture( this.getTextureManager(), new IInputStreamOpener()
			{
				@Override
				public InputStream open() throws IOException
				{
					return getAssets().open( "gfx/ship.png" );
				}
			} );
			shipTexture.load();
			this.shipTextureRegion = TextureRegionFactory.extractFromTexture( shipTexture );
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}
	}

	@Override
	protected Scene onCreateScene()
	{
		Scene scene = new Scene();

		shipOnSector = new ShipOnSector( this.shipTextureRegion, Core.ship.sectorX, Core.ship.sectorY );
		sectorBehindShip = new SectorBehindShip( Core.ship.sectorX, Core.ship.sectorY );

		return scene;
	}
}
