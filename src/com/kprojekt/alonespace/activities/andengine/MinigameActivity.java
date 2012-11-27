package com.kprojekt.alonespace.activities.andengine;

import java.io.IOException;
import java.io.InputStream;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.debug.Debug;

import com.kprojekt.alonespace.data.Core;
import com.kprojekt.alonespace.data.minigame.Sector;
import com.kprojekt.alonespace.data.minigame.Ship;

public class MinigameActivity extends SimpleBaseGameActivity

{
	public static ScreenOrientation orientation = ScreenOrientation.PORTRAIT_SENSOR;

	private Camera camera;
	private TextureRegion shipTextureRegion;

	private int shipXInSector;
	private int shipYInSector;
	private int sectorX;
	private int sectorY;

	@Override
	public EngineOptions onCreateEngineOptions()
	{
		this.camera = new Camera( 0, 0, Core.width, Core.height );
		this.shipXInSector = this.getIntent().getExtras().getInt( "shipXInSector" );
		this.shipYInSector = this.getIntent().getExtras().getInt( "shipYInSector" );
		this.sectorX = this.getIntent().getExtras().getInt( "sectorX" );
		this.sectorY = this.getIntent().getExtras().getInt( "sectorY" );

		return new EngineOptions( Core.fullScreen, Core.orientation, Core.ratioResPolicy, camera );
	}

	@Override
	public void onCreateResources()
	{
		try
		{
			BitmapTexture shipTexture = new BitmapTexture( this.getTextureManager(), new IInputStreamOpener()
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
			Debug.e( e );
		}
	}

	@Override
	public Scene onCreateScene()
	{
		Scene scene = new Scene();

		Sector sector = new Sector( Core.getSectorData( this.sectorX, this.sectorY ) );
		final Ship ship = new Ship( this.shipXInSector, this.shipYInSector, this.shipTextureRegion,
				this.getVertexBufferObjectManager() );

		scene.attachChild( sector );
		scene.setOnSceneTouchListener( ship );

		scene.attachChild( ship );

		return scene;
	}

}
