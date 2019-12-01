package com.b07.store.customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.R;
import com.b07.inventory.Item;
import com.b07.inventory.ItemTypes;
import com.b07.inventory.MemberItemTypes;
import com.b07.store.ShoppingCart;
import java.math.BigDecimal;
import java.util.HashMap;

/**
 * @author Eric
 */
public class ShoppingCartActivity extends AppCompatActivity {

  public void renderCart(ShoppingCart cart) {
    int count = 1;
    HashMap<Item, Integer> itemMap = cart.getItemMap();

    for (Item item : itemMap.keySet()) {
      String itemNameIdName = "cart_itemname" + count;
      String priceIdName = "cart_itemprice" + count;
      String removeButtonIdName = "cart_removebutton" + count;
      String quantityIdName = "cart_itemquantity" + count;
      String emptyItemLayoutIdName = "cart_item" + count;

      int itemNameId = getResources().getIdentifier(itemNameIdName, "id", getPackageName());
      int priceId = getResources().getIdentifier(priceIdName, "id", getPackageName());
      int removeButtonId = getResources().getIdentifier(removeButtonIdName, "id", getPackageName());
      int quantityId = getResources().getIdentifier(quantityIdName, "id", getPackageName());
      int emptyItemLayoutId = getResources()
          .getIdentifier(emptyItemLayoutIdName, "id", getPackageName());

      TextView itemTextView = findViewById(itemNameId);
      TextView priceTextView = findViewById(priceId);
      TextView quantityTextView = findViewById(quantityId);
      ImageButton removeButton = findViewById(removeButtonId);

      Integer amountPerItem = itemMap.get(item);
      BigDecimal pricePerItem = item.getPrice();
      BigDecimal price = pricePerItem.multiply(new BigDecimal(amountPerItem.toString()));
      String formattedItemName = (item.getName().substring(0, 1).toUpperCase() + item.getName()
          .substring(1).toLowerCase()).replace("_", " ");

      priceTextView.setText(price.toString());
      itemTextView.setText(formattedItemName);
      quantityTextView.setText(amountPerItem.toString());

      removeButton
          .setOnClickListener(new RemoveFromCartController(this, cart, item, priceId, quantityId,
              emptyItemLayoutId));

      count++;
    }

    if (count != ItemTypes.values().length + MemberItemTypes.values().length + 1) {
      while (count != ItemTypes.values().length + MemberItemTypes.values().length + 1) {
        String emptyItemLayoutId = "cart_item" + count;
        int emptyItemLayoutIdName = getResources()
            .getIdentifier(emptyItemLayoutId, "id", getPackageName());
        LinearLayout emptyItemLayout = findViewById(emptyItemLayoutIdName);
        emptyItemLayout.setVisibility(View.GONE);
        count++;
      }
    }
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.shopping_cart);

    TextView totalPrice = findViewById(R.id.cart_totalprice);
    Intent intent = getIntent();
    ShoppingCart cart = (ShoppingCart) intent.getSerializableExtra("cart");

    Button saveButton = findViewById(R.id.cart_save_button);
    saveButton.setOnClickListener(new SaveButtonController(this, cart));

    Button restoreButton = findViewById(R.id.cart_restore_button);
    restoreButton.setOnClickListener(new RestoreButtonController(this));

    BigDecimal total = cart.getTotal().multiply(cart.getTaxRate());
    total = total.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    totalPrice.setText(total.toString());
    renderCart(cart);

  }
}
