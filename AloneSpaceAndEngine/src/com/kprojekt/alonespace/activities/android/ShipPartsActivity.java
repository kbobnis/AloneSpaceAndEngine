package com.kprojekt.alonespace.activities.android;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kprojekt.alonespace.R;
import com.kprojekt.alonespace.data.Core;
import com.kprojekt.alonespace.data.model.Ship;
import com.kprojekt.alonespace.data.model.ShipPart;
import com.kprojekt.alonespace.data.model.ShipPartCategory;

public class ShipPartsActivity extends ExpandableListActivity
{

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		ExpandableListView expandableListView = getExpandableListView();
		expandableListView.setDividerHeight( 2 );
		expandableListView.setGroupIndicator( null );
		expandableListView.setClickable( true );

		List<ShipPartCategory> groupItems = Core.loggedProfile.getShip().getCategories();
		ArrayList<ArrayList<ShipPart>> childItems = new ArrayList<ArrayList<ShipPart>>();
		for( ShipPartCategory cat : groupItems )
		{
			ArrayList<ShipPart> rzecz = new ArrayList<ShipPart>();
			for( ShipPart shipPart : Core.loggedProfile.getShip().getPartsOfCategory( cat ) )
			{
				rzecz.add( shipPart );
			}
			childItems.add( rzecz );
		}
		AssetManager assets = getAssets();
		Bitmap categoryEmptyBmp = null;
		try
		{
			InputStream open = assets.open( "gfx/category_empty.png" );
			categoryEmptyBmp = BitmapFactory.decodeStream( open );
		}
		catch( IOException e )
		{
			// TODO @Krzysiek logger
			e.printStackTrace();
		}

		NewAdapter newAdapter = new NewAdapter( groupItems, childItems, Core.loggedProfile.getShip(), categoryEmptyBmp );
		newAdapter.setInflater( (LayoutInflater)getSystemService( Context.LAYOUT_INFLATER_SERVICE ), this );
		expandableListView.setAdapter( newAdapter );
		expandableListView.setOnChildClickListener( this );

	}

	@Override
	public boolean onCreateOptionsMenu( Menu menu )
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate( R.menu.activity_my_expandable_list, menu );
		return true;
	}

	@Override
	public boolean onChildClick( ExpandableListView parent, View v, int groupPosition, int childPosition, long id )
	{
		Toast.makeText( this, "Clicked On Child", Toast.LENGTH_SHORT ).show();
		return true;
	}

}

class NewAdapter extends BaseExpandableListAdapter
{

	public List<ShipPartCategory> groupItem;
	public ArrayList<ShipPart> tempChild;
	public ArrayList<ArrayList<ShipPart>> Childtem;
	public LayoutInflater minflater;
	public Activity activity;
	private final Ship ship;
	private final Bitmap categoryEmptyBmp;

	public NewAdapter( List<ShipPartCategory> grList, ArrayList<ArrayList<ShipPart>> childItem, Ship ship,
			Bitmap categoryEmptyBmp )
	{
		groupItem = grList;
		this.Childtem = childItem;
		this.ship = ship;
		this.categoryEmptyBmp = categoryEmptyBmp;
	}

	public void setInflater( LayoutInflater mInflater, Activity act )
	{
		this.minflater = mInflater;
		activity = act;
	}

	@Override
	public Object getChild( int groupPosition, int childPosition )
	{
		return null;
	}

	@Override
	public long getChildId( int groupPosition, int childPosition )
	{
		return 0;
	}

	@Override
	public View getChildView( int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent )
	{
		tempChild = Childtem.get( groupPosition );
		TextView text = null;
		if( convertView == null )
		{
			convertView = minflater.inflate( R.layout.mychildgrouprow, null );
		}
		text = (TextView)convertView.findViewById( R.id.textView1 );
		final ShipPart shipPart = tempChild.get( childPosition );
		text.setText( Core.locale.get( shipPart.getName() ) );

		ImageView imageView = (ImageView)convertView.findViewById( R.id.childImage );
		imageView.setImageDrawable( shipPart.getBitmap() );

		convertView.setOnClickListener( new OnClickListener()
		{
			@Override
			public void onClick( View v )
			{
				ship.equipPart( shipPart );
				notifyDataSetChanged();
			}
		} );
		return convertView;
	}

	@Override
	public int getChildrenCount( int groupPosition )
	{
		return Childtem.get( groupPosition ).size();
	}

	@Override
	public Object getGroup( int groupPosition )
	{
		return null;
	}

	@Override
	public int getGroupCount()
	{
		return groupItem.size();
	}

	@Override
	public void onGroupCollapsed( int groupPosition )
	{
		super.onGroupCollapsed( groupPosition );
	}

	@Override
	public void onGroupExpanded( int groupPosition )
	{
		super.onGroupExpanded( groupPosition );
	}

	@Override
	public long getGroupId( int groupPosition )
	{
		return 0;
	}

	@Override
	public View getGroupView( int groupPosition, boolean isExpanded, View convertView, ViewGroup parent )
	{
		if( convertView == null )
		{
			convertView = minflater.inflate( R.layout.mygrouprow, null );
		}
		TextView v = (TextView)convertView.findViewById( R.id.checkedTextView );

		ShipPartCategory shipPartCategory = groupItem.get( groupPosition );

		v.setText( Core.locale.get( shipPartCategory.getName() ) );

		TextView tv = (TextView)(convertView.findViewById( R.id.textView12 ));
		ImageView emptyPart = (ImageView)(convertView.findViewById( R.id.childImage ));

		ShipPart eq = this.ship.getEquippedPartOfCategory( shipPartCategory );
		if( eq != null )
		{
			tv.setText( Core.locale.get( eq.getName() ) );
			emptyPart.setImageDrawable( eq.getBitmap() );
		}
		else
		{
			tv.setText( Core.locale.get( "no.shipPart.equipped" ) );
			emptyPart.setImageBitmap( categoryEmptyBmp );
		}

		return convertView;
	}

	@Override
	public boolean hasStableIds()
	{
		return false;
	}

	@Override
	public boolean isChildSelectable( int groupPosition, int childPosition )
	{
		return false;
	}

}
