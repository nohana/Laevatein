package com.laevatein.internal.ui.adapter;

import com.amalgam.content.ContextUtils;
import com.laevatein.R;
import com.laevatein.internal.entity.Item;
import com.laevatein.internal.entity.ItemViewResources;
import com.laevatein.internal.ui.helper.PhotoGridViewHelper;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

/**
 * @author KeithYokoma
 * @since 2014/03/24
 * @version 1.0.0
 * @hide
 */
public class AlbumPhotoAdapter extends CursorAdapter {
    private final ItemViewResources mResources;

    public AlbumPhotoAdapter(Context context, Cursor c, ItemViewResources resources) {
        super(context, c, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        mResources = resources;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = ContextUtils.getLayoutInflater(context);
        return inflater.inflate(mResources.getLayoutId(), parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        final Item item = Item.valueOf(cursor);

        ImageView thumbnail = (ImageView) view.findViewById(mResources.getImageViewId());
        thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoGridViewHelper.callPreview(context, item);
            }
        });
        CheckBox check = (CheckBox) view.findViewById(mResources.getCheckBoxId());
        Picasso.with(context).load(item.buildContentUri())
                .resizeDimen(R.dimen.gridItemImageWidth, R.dimen.gridItemImageHeight)
                .centerCrop()
                .into(thumbnail);
    }

}
