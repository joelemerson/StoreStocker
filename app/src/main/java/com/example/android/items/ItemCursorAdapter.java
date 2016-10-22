/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.items;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.example.android.items.data.ItemContract;
import com.example.android.items.data.ItemContract.ItemEntry;

/**
 * {@link ItemCursorAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of items data as its data source. This adapter knows
 * how to create list items for each row of items in the {@link Cursor}.
 */

public class ItemCursorAdapter extends CursorAdapter {

    /**
     * Constructs a new {@link ItemCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */

    public ItemCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml

        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * This method binds the item data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current item can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        final int itemId = cursor.getInt(cursor.getColumnIndexOrThrow(ItemEntry._ID));
        final int itemQty = cursor.getInt(cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_QUANTITY));

        // Find individual views that we want to modify in the list item layout

        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView descriptionTextView = (TextView) view.findViewById(R.id.description);
        TextView priceTextView = (TextView) view.findViewById(R.id.price);
        TextView quantityTextView = (TextView) view.findViewById(R.id.quantity);

        // Find the columns of items attributes that we're interested in

        int nameColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_NAME);
        int descriptionColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_DESCRIPTION);
        int priceColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_QUANTITY);

        // Read the item attributes from the Cursor for the current item

        String itemName = cursor.getString(nameColumnIndex);
        String itemDescription = cursor.getString(descriptionColumnIndex);
        String itemPrice = cursor.getString(priceColumnIndex);
        String itemQuantity = cursor.getString(quantityColumnIndex);

        // If the item description is empty string or null, then use some default text
        // that says "Unknown Description", so the TextView isn't blank.

        if (TextUtils.isEmpty(itemDescription)) {
            itemDescription = context.getString(R.string.unknown_description);
        }

        // Update the TextViews with the attributes for the current item

        nameTextView.setText(itemName);
        descriptionTextView.setText(itemDescription);
        priceTextView.setText(itemPrice);
        quantityTextView.setText(itemQuantity);

        //Process a sale when the button is clicked and subtract one from the current inventory

        Button saleButton = (Button) view.findViewById(R.id.salebutton);
        saleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                if (itemQty > 0) {
                    int mItemQty;
                    mItemQty = (itemQty - 1);
                    values.put(ItemContract.ItemEntry.COLUMN_ITEM_QUANTITY, mItemQty);
                    Uri uri = ContentUris.withAppendedId(ItemContract.ItemEntry.CONTENT_URI, itemId);
                    context.getContentResolver().update(uri, values, null, null);
                }
                context.getContentResolver().notifyChange(ItemContract.ItemEntry.CONTENT_URI, null);
            }
        });
    }
}

