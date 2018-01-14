package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    private int quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        quantity = 1;
    }

    /**
     * This method is called when the order button is clicked
     */
    public void submitOrder(View view) {
        String name = ((EditText) findViewById(R.id.name)).getText().toString();
        boolean hasWhippedCream = ((CheckBox) findViewById(R.id.whippedCream)).isChecked();
        boolean hasChocolate = ((CheckBox) findViewById(R.id.chocolate)).isChecked();
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, name, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for " + name);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method is called when the '<b>+</b>' button is clicked
     */
    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, R.string.warning_100_coffee_limit, Toast.LENGTH_SHORT).show();
            return;
        }
        quantity++;
        display(quantity);
    }

    /**
     * This method is called when the '<b>-</b>' button is clicked
     */
    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, R.string.warning_1_coffee_limit, Toast.LENGTH_SHORT).show();
            return;
        }
        quantity--;
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     *
     * @param number int: Quantity of coffees
     */
    private void display(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * Calculates the total order price
     *
     * @param hasWhippedCream boolean: Has Whipped Cream been added to the order
     * @param hasChocolate    boolean: Has Chocolate been added to the order
     * @return
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int coffeePrice = 5, whippedCreamPrice = 1, chocolatePrice = 2;
        int price = coffeePrice + (hasWhippedCream ? whippedCreamPrice : 0) + (hasChocolate ? chocolatePrice : 0);
        price = price * quantity;
        return price;
    }

    /**
     * Creates the order summary adding more info
     *
     * @param price           int: Total price of the order
     * @param name            String: Name of the customer
     * @param hasWhippedCream boolean: Has Whipped Cream been added to the order
     * @param hasChocolate    boolean: Has Chocolate been added to the order
     * @return String: The message to be printed with all the information
     */
    private String createOrderSummary(int price, String name, boolean hasWhippedCream, boolean hasChocolate) {
        // TODO: 1/14/2018 Add price conversion as per LOCALE
        String message = getString(R.string.order_summary, name, hasWhippedCream, hasChocolate, quantity, price);
        message += getString(R.string.thank_you) + '\n';
        return message;
    }
}
