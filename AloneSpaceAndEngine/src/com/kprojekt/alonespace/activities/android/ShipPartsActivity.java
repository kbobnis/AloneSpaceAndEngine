package com.kprojekt.alonespace.activities.android;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kprojekt.alonespace.R;
import com.kprojekt.alonespace.data.Core;
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

		ArrayList<String> groupItems = new ArrayList<String>();
		ArrayList<ArrayList<String>> childItems = new ArrayList<ArrayList<String>>();
		for( ShipPartCategory cat : Core.loggedProfile.getShip().getCategories() )
		{
			groupItems.add( cat.getName() );
			ArrayList<String> rzecz = new ArrayList<String>();
			for( ShipPart shipPart : Core.loggedProfile.getShip().getPartsOfCategory( cat.getId() ) )
			{
				rzecz.add( shipPart.getName() );
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

class NewAdapter extends BaseExpandableListAdapter
{

	public ArrayList<String> groupItem, tempChild;
	public ArrayList<ArrayList<String>> Childtem;
	public LayoutInflater minflater;
	public Activity activity;

	public NewAdapter( ArrayList<String> grList, ArrayList<ArrayList<String>> childItem )
	{
		groupItem = grList;
		this.Childtem = childItem;
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
		tempChild = (ArrayList<String>)Childtem.get( groupPosition );
		TextView text = null;
		if( convertView == null )
		{
			convertView = minflater.inflate( R.layout.mychildgrouprow, null );
		}
		text = (TextView)convertView.findViewById( R.id.textView1 );
		text.setText( Core.locale.get( tempChild.get( childPosition ) ) );
		convertView.setOnClickListener( new OnClickListener()
		{
			@Override
			public void onClick( View v )
			{
				Toast.makeText( activity, tempChild.get( childPosition ), Toast.LENGTH_SHORT ).show();
			}
		} );
		return convertView;
	}

	@Override
	public int getChildrenCount( int groupPosition )
	{
		return ((ArrayList<String>)Childtem.get( groupPosition )).size();
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
		((CheckedTextView)convertView).setText( Core.locale.get( groupItem.get( groupPosition ) ) );
		((CheckedTextView)convertView).setChecked( isExpanded );
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
