package com.kprojekt.alonespace.activities.andengine;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;

import android.os.Bundle;

import com.kprojekt.alonespace.data.Core;
import com.kprojekt.alonespace.data.Player;
import com.kprojekt.alonespace.data.chooseSector.ShipOnSector;
import com.kprojekt.alonespace.data.chooseSector.Star;

public class ChooseSectorActivity extends SimpleBaseGameActivity
{

	private TextureRegion shipTextureRegion;
	private Camera camera;
	private TextureRegion star1TR;
	private TextureRegion star2TR;
	private TextureRegion star3TR;
	private int sectorX;
	private int sectorY;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		this.sectorX = this.getIntent().getExtras().getInt( "sectorX" );
		this.sectorY = this.getIntent().getExtras().getInt( "sectorY" );
	}

	@Override
	public EngineOptions onCreateEngineOptions()
	{
		this.camera = new Camera( 0, 0, Core.metersToPixels( Core.widthInMeters ),
				Core.metersToPixels( Core.heightInMeters ) );
		return new EngineOptions( Core.fullScreen, Core.orientation, Core.ratioResPolicy, camera );
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

			BitmapTexture star1Text = new BitmapTexture( this.getTextureManager(), new IInputStreamOpener()
			{

				@Override
				public InputStream open() throws IOException
				{
					return getAssets().open( "gfx/stars/star.png" );
				}
			} );
			star1Text.load();
			star1TR = TextureRegionFactory.extractFromTexture( star1Text );

			BitmapTexture star2Text = new BitmapTexture( this.getTextureManager(), new IInputStreamOpener()
			{

				@Override
				public InputStream open() throws IOException
				{
					return getAssets().open( "gfx/stars/star2.png" );
				}
			} );
			star2Text.load();
			star2TR = TextureRegionFactory.extractFromTexture( star2Text );

			BitmapTexture star3Text = new BitmapTexture( this.getTextureManager(), new IInputStreamOpener()
			{

				@Override
				public InputStream open() throws IOException
				{
					return getAssets().open( "gfx/stars/star3.png" );
				}
			} );
			star3Text.load();
			star3TR = TextureRegionFactory.extractFromTexture( star3Text );
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
		List<Star> stars = new ArrayList<Star>();

		final ShipOnSector shipOnSector = new ShipOnSector( this.shipTextureRegion, this.sectorX, this.sectorY,
				this.getVertexBufferObjectManager(), this.camera, stars );

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
