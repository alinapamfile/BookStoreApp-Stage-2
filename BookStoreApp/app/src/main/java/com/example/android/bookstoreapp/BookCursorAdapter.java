package com.example.android.bookstoreapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bookstoreapp.data.BookContract.BookEntry;

/**
 * Adapter for a list that uses a cursor of book data as its data source
 */
public class BookCursorAdapter extends CursorAdapter {

    /**
     * Constructs a new {@link BookCursorAdapter}
     *
     * @param context The context.
     * @param cursor  The cursor from which to get data.
     */
    public BookCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    /**
     * @param context app context
     * @param cursor  The cursor from which to get the data.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * This method binds the book data to the given list item layout
     *
     * @param view    Existing view, returned earlier by newView() methos
     * @param context app context
     * @param cursor  The cursor from which to get the data.
     */
    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView priceTextView = (TextView) view.findViewById(R.id.price);
        final TextView quantityTextView = (TextView) view.findViewById(R.id.quantity);
        final Button saleButton = (Button) view.findViewById(R.id.sale_button);

        //Read the book attributes from the Cursor for the current book
        String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        float price = cursor.getFloat(cursor.getColumnIndexOrThrow("price"));
        String priceString = "$" + price;
        final int quantityInt = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
        String quantityString = String.valueOf(quantityInt);

        //Update the TextViews with the attributes for the current book
        nameTextView.setText(name);
        priceTextView.setText(priceString);
        quantityTextView.setText(quantityString);

        //Set listener on the sale button
        saleButton.setOnClickListener(buttonClickListener);
    }

    // Click listener for the sale button
    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Get parent of the buton (the LiniarLayout)
            View parentRow = (View) view.getParent();
            // Get the parent of parentRow (the RelativeLayout, which is the list item)
            View parentParentRow = (View) parentRow.getParent();
            // Get the parent of the list item (the list view)
            ListView listView = (ListView) parentParentRow.getParent();

            // Identify the quantity text view
            TextView quantityTextView = (TextView) parentRow.findViewById(R.id.quantity);

            // Get the position of the list item
            final int position = listView.getPositionForView(parentParentRow);
            // and the id
            long id = getItemId(position);
            // Determine the initial quantity
            int oldQuantity = Integer.parseInt(quantityTextView.getText().toString());
            // If it equals 0, display a toast
            if (oldQuantity == 0) {
                Toast.makeText(view.getContext(), R.string.sold_out, Toast.LENGTH_LONG).show();
            } else {
                // Else, determine the new quantity, by subtracting 1
                int newQuantity = oldQuantity - 1;
                String quantityString = String.valueOf(newQuantity);
                // Display the new quantity
                quantityTextView.setText(quantityString);
                ContentValues values = new ContentValues();
                values.put(BookEntry.COLUMN_BOOK_QUANTITY, newQuantity);
                // Update the database
                int rowsUpdated = view.getContext().getContentResolver().update(BookEntry.CONTENT_URI, values, "_ID = ?", new String[]{String.valueOf(id)});
            }
        }
    };
}
