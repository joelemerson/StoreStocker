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
package com.example.android.items.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * API Contract for the Store Stocker app.
 */
public final class ItemContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private ItemContract() {
    }

    /**
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website.  A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     */
    public static final String CONTENT_AUTHORITY = "com.example.android.items";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Possible path (appended to base content URI for possible URI's)
     */
    public static final String PATH_ITEMS = "items";

    /**
     * Inner class that defines constant values for the items database table.
     * Each entry in the table represents a single item.
     */
    public static final class ItemEntry implements BaseColumns {

        /**
         * The content URI to access the item data in the provider
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ITEMS);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of items.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEMS;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single item.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEMS;

        /**
         * Name of database table for items
         */
        public final static String TABLE_NAME = "items";

        /**
         * Unique ID number for the items (only for use in the database table).
         * <p>
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Name of the item.
         * <p>
         * Type: TEXT
         */
        public final static String COLUMN_ITEM_NAME = "name";

        /**
         * Description of the item.
         * <p>
         * Type: TEXT
         */
        public final static String COLUMN_ITEM_DESCRIPTION = "description";

        /**
         * Status of the item.
         * <p>
         * The only possible values are {@link #STATUS_IN_STOCK}, {@link #STATUS_OUT_OF_STOCK},
         * or {@link #STATUS_ON_ORDER}.
         * <p>
         * Type: INTEGER
         */
        public final static String COLUMN_ITEM_STATUS = "status";

        /**
         * Quantity of the item.
         * <p>
         * Type: INTEGER
         */
        public final static String COLUMN_ITEM_QUANTITY = "quantity";

        /**
         * Price of the item.
         * <p>
         * Type: REAL
         */
        public final static String COLUMN_ITEM_PRICE = "price";

        /**
         * Image of the item.
         * <p>
         * Type: TEXT
         */
        public final static String COLUMN_ITEM_IMAGE = "image";

        /**
         * Possible values for the status of the item.
         */
        public static final int STATUS_IN_STOCK = 0;
        public static final int STATUS_OUT_OF_STOCK = 1;
        public static final int STATUS_ON_ORDER = 2;

        /**
         * Returns whether or not the given status is {@link #STATUS_IN_STOCK}, {@link #STATUS_OUT_OF_STOCK},
         * or {@link #STATUS_ON_ORDER}.
         */
        public static boolean isValidStatus(int status) {
            if (status == STATUS_IN_STOCK || status == STATUS_OUT_OF_STOCK || status == STATUS_ON_ORDER) {
                return true;
            }
            return false;
        }

        /**
         * Name of the manufacturer_label.
         * <p>
         * Type: TEXT
         */
        public final static String COLUMN_ITEM_MANUFACTURER = "MANUFACTURER";
    }

}

