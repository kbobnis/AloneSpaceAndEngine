package com.kprojekt.alonespace.activities.android;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kprojekt.alonespace.R;
import com.kprojekt.alonespace.data.Core;
import com.kprojekt.alonespace.data.shipParts.ShipPartItem;

/**
 * 
 */
public class ShipPartsActivity extends Activity
{
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );

		setContentView( R.layout.shipparts );

		ExpandableListView mExpandableList = (ExpandableListView)findViewById( R.id.expandable_list );

		mExpandableList.setAdapter( new ShipPartsAdapter( ShipPartsActivity.this, Core.player.ship.parts ) );

	}
}

class ShipPartsAdapter extends BaseExpandableListAdapter
{

	private LayoutInflater inflater;
	private ArrayList<ShipPartItem> mParent;

	public ShipPartsAdapter( Context context, ArrayList<ShipPartItem> parent )
	{
		mParent = parent;
		inflater = LayoutInflater.from( context );
	}

	@Override
	public int getGroupCount()
	{
		return mParent.size();
	}

	@Override
	public int getChildrenCount( int i )
	{
		return mParent.get( i ).getModifiers().size();
	}

	//gets the title of each parent/group
	public Object getGroup( int i )
	{
		return mParent.get( i ).getName();
	}

	@Override
	//gets the name of each item
	public Object getChild( int i, int i1 )
	{
		return mParent.get( i ).getModifiers().get( i1 );
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

		TextView textView = (TextView)view.findViewById( R.id.list_item_text_view );
		Button button = (Button)view.findViewById( R.id.button );
		final int n = i;
		button.setOnClickListener( new OnClickListener()
		{

			@Override
			public void onClick( View arg0 )
			{
				Toast.makeText( inflater.getContext(), getGroup( n ).toString(), Toast.LENGTH_SHORT ).show();
			}
		} );
		textView.setText( getGroup( i ).toString() );

		return view;
	}

	@Override
	public View getChildView( int i, int i1, boolean b, View view, ViewGroup viewGroup )
	{
		if( view == null )
		{
			view = inflater.inflate( R.layout.list_item_child, viewGroup, false );
		}

		TextView textView = (TextView)view.findViewById( R.id.list_item_text_child );
		textView.setText( mParent.get( i ).getModifiers().get( i1 ).getName() );

		return view;
	}

	@Override
	public boolean isChildSelectable( int i, int i1 )
	{
		return true;
	}
}
