package com.kprojekt.alonespace.activities.andengine;

import java.io.IOException;
import java.io.InputStream;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;

import com.kprojekt.alonespace.data.Core;
import com.kprojekt.alonespace.data.chooseSector.ShipOnSector;

public class ChooseSectorActivity extends SimpleBaseGameActivity
{

	private TextureRegion shipTextureRegion;
	private ShipOnSector shipOnSector;
	private Camera camera;

	@Override
	public EngineOptions onCreateEngineOptions()
	{
		this.camera = new Camera( 0, 0, Core.width, Core.height );
		return new EngineOptions( Core.fullScreen, MinigameActivity.orientation, MinigameActivity.resPolicy, camera );
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
			throw new RuntimeException( e );
		}
	}

	@Override
	protected Scene onCreateScene()
	{
		Scene scene = new Scene();

		shipOnSector = new ShipOnSector( this.shipTextureRegion, Core.ship.sectorX, Core.ship.sectorY,
				this.getVertexBufferObjectManager(), this.camera );

		this.getEngine().registerUpdateHandler( new IUpdateHandler()
		{
			@Override
			public void reset()
			{
			}

			@Override
			public void onUpdate( float pSecondsElapsed )
			{
				shipOnSector.onUpdateHandle( pSecondsElapsed );
			}
		} );

		scene.attachChild( shipOnSector );

		return scene;
	}
}
