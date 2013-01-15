package com.kprojekt.alonespace.activities.android;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

import com.kprojekt.alonespace.R;
import com.kprojekt.alonespace.data.Core;
import com.kprojekt.alonespace.data.model.Ship;

/**
 * @author Krzysiek Bobnis
 */
public class ShipPartsActivity extends Activity
{
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );

		setContentView( R.layout.shipparts );

		ExpandableListView mExpandableList = (ExpandableListView)findViewById( R.id.expandable_list );

		mExpandableList.setAdapter( new ShipPartsAdapter( ShipPartsActivity.this, Core.loggedProfile.getShip() ) );

	}
}

class ShipPartsAdapter extends BaseExpandableListAdapter
{

	private LayoutInflater inflater;
	private final Ship ship;

	public ShipPartsAdapter( Context context, Ship ship )
	{
		this.ship = ship;
		inflater = LayoutInflater.from( context );
	}

	@Override
	public int getGroupCount()
	{
		return 0; //return this.ship.getCategories().size();
	}

	@Override
	public int getChildrenCount( int i )
	{
		return 0; //return this.ship.getCategories().get( i ).getParts().size();
	}

	//gets the title of each parent/group
	public Object getGroup( int i )
	{
		return new Object(); //return mParent.get( i ).get( i );
	}

	@Override
	//gets the name of each item
	public Object getChild( int i, int i1 )
	{
		return new Object(); //return mParent.get( i ).get( i1 );
	}

	@Override
	public long getGroupId( int i )
	{
		return i;
	}

	@Override
	public long getChildId( int i, int i1 )
	{
		return i1;
	}

	@Override
	public boolean hasStableIds()
	{
		return true;
	}

	@Override
	public View getGroupView( int i, boolean b, View view, ViewGroup viewGroup )
	{

		if( view == null )
		{
			view = inflater.inflate( R.layout.list_item_parent, viewGroup, false );
		}

		//TextView textView = (TextView)view.findViewById( R.id.list_item_text_view );

		return view;
	}

	@Override
	public View getChildView( int i, int i1, boolean b, View view, ViewGroup viewGroup )
	{
		if( view == null )
		{
			view = inflater.inflate( R.layout.list_item_child, viewGroup, false );
		}

		//TextView textView = (TextView)view.findViewById( R.id.list_item_text_child );
		//textView.setText( mParent.get( i ).get( i1 ) );

		return view;
	}

	@Override
	public boolean isChildSelectable( int i, int i1 )
	{
		return true;
	}
}
