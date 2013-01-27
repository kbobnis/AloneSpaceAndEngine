package com.kprojekt.alonespace;

import java.util.ArrayList;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.kprojekt.alonespace.data.Core;
import com.kprojekt.alonespace.data.model.ShipPart;
import com.kprojekt.alonespace.data.model.ShipPartCategory;

public class MyExpandableListActivity extends ExpandableListActivity
{

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		ExpandableListView expandableListView = getExpandableListView();
		expandableListView.setDividerHeight( 2 );
		expandableListView.setGroupIndicator( null );
		expandableListView.setClickable( true );

		ArrayList<String> groupItems = new ArrayList<String>();
		ArrayList<ArrayList<String>> childItems = new ArrayList<ArrayList<String>>();
		for( ShipPartCategory cat : Core.loggedProfile.getShip().getCategories() )
		{
			groupItems.add( Core.locale.get( cat.getName() ) );
			ArrayList<String> rzecz = new ArrayList<String>();
			for( ShipPart shipPart : Core.loggedProfile.getShip().getPartsOfCategory( cat.getId() ) )
			{
				rzecz.add( Core.locale.get( shipPart.getName() ) );
			}
			childItems.add( rzecz );
		}

		NewAdapter newAdapter = new NewAdapter( groupItems, childItems );
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
