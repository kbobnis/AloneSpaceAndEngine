package com.kprojekt.alonespace.activities.android;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.kprojekt.alonespace.R;
import com.kprojekt.alonespace.data.Core;
import com.kprojekt.alonespace.data.model.Ship;
import com.kprojekt.alonespace.data.model.ShipPartCategory;

/**
 * @author Krzysiek Bobnis
 */
public class ShipPartsActivity extends Activity implements OnChildClickListener, OnItemClickListener
{
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );

		setContentView( R.layout.shipparts );

		ExpandableListView mExpandableList = (ExpandableListView)findViewById( R.id.expandable_list );

		mExpandableList.setAdapter( new ShipPartsAdapter( ShipPartsActivity.this, Core.loggedProfile.getShip() ) );
		mExpandableList.setClickable( true );
		mExpandableList.setOnChildClickListener( this );
		mExpandableList.setOnItemClickListener( this );
	}

	@Override
	public boolean onChildClick( ExpandableListView parent, View v, int groupPosition, int childPosition, long id )
	{
		Toast.makeText( ShipPartsActivity.this, "dotknales " + groupPosition, Toast.LENGTH_SHORT ).show();
		return false;
	}

	@Override
	public void onItemClick( AdapterView<?> arg0, View arg1, int arg2, long arg3 )
	{
		Toast.makeText( this, "kliknales: " + arg2, Toast.LENGTH_SHORT ).show();
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
		return this.ship.getCategories().size();
	}

	@Override
	public int getChildrenCount( int i )
	{
		return this.ship.getPartsOfCategory( this.ship.getCategories().get( i ).getId() ).size();
	}

	//gets the title of each parent/group
	public Object getGroup( int i )
	{
		return this.ship.getCategories().get( i ).getName();
	}

	@Override
	//gets the name of each item
	public String getChild( int i, int i1 )
	{
		return this.ship.getPartsOfCategory( this.ship.getCategories().get( i ).getId() ).get( i1 ).getName();//return mParent.get( i ).get( i1 );
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

		TextView textView = (TextView)view.findViewById( R.id.parent_1_partName );
		ShipPartCategory shipPartCategory = this.ship.getCategories().get( i );
		String name = shipPartCategory.getName();
		textView.setText( Core.locale.get( name ) );
		return view;
	}

	@Override
	public View getChildView( int i, int i1, boolean b, View view, ViewGroup viewGroup )
	{
		if( view == null )
		{
			view = inflater.inflate( R.layout.list_item_child, viewGroup, false );

		}

		//@+id/child_partName
		TextView textView = (TextView)view.findViewById( R.id.child_partName );
		textView.setText( this.ship.getPartsOfCategory( this.ship.getCategories().get( i ).getId() ).get( i1 ).getName() );

		return view;
	}

	@Override
	public boolean isChildSelectable( int i, int i1 )
	{
		return true;
	}

}
