package com.b07.store.customer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.R;
import com.b07.database.DatabaseSelectHelper;
import com.b07.exceptions.AuthenticationException;
import com.b07.inventory.Item;
import com.b07.inventory.ItemImpl;
import com.b07.inventory.ItemTypes;
import com.b07.store.LogoutButtonController;
import com.b07.store.ShoppingCart;
import com.b07.users.Customer;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Eric
 */
public class CustomerUIActivity extends AppCompatActivity {

  private void renderShop(ShoppingCart cart) {
    int count = 1;
    for (ItemTypes itemType : ItemTypes.values()) {
      String itemIdName = "customer_itemname" + count;
      String priceIdName = "customer_itemprice" + count;
      String frameIdName = "customer_frame" + count;
      String itemLayoutIdName = "customer_item" + count;

      int itemId = getResources().getIdentifier(itemIdName, "id", getPackageName());
      int priceId = getResources().getIdentifier(priceIdName, "id", getPackageName());
      int frameId = getResources().getIdentifier(frameIdName, "id", getPackageName());
      int itemLayoutId = getResources().getIdentifier(itemLayoutIdName, "id", getPackageName());

      FrameLayout frameLayout = findViewById(frameId);
      LinearLayout itemLayout = findViewById(itemLayoutId);
      TextView itemTextView = findViewById(itemId);
      TextView priceTextView = findViewById(priceId);

      List<Item> allItems = DatabaseSelectHelper.getAllItems(this);
      BigDecimal price = BigDecimal.ZERO;

      Item item = null;
      for (Item i : allItems) {
        if (i.getName().equals(itemType.name())) {
          item = i;
          price = i.getPrice();
          break;
        }
      }
      priceTextView.setText(price.toString());

      String itemName = (itemType.name().substring(0, 1).toUpperCase() + itemType.name()
          .substring(1).toLowerCase()).replace("_", " ");
      itemTextView.setText(itemName);

      if (item != null && DatabaseSelectHelper.getInventoryQuantity(item.getId(), this) > 0) {
        itemLayout
            .setOnClickListener(new ItemLayoutController(this, (ItemImpl) item, itemName, cart));
      } else {
        frameLayout.setVisibility(View.GONE);
      }

      count++;
    }
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.customer);

    TextView greeting = findViewById(R.id.employee_greeting);
    ImageView logout = findViewById(R.id.customer_logout);
    ImageView cartButton = findViewById(R.id.customer_cart);

    Intent intent = getIntent();
    Customer customer = (Customer) intent.getSerializableExtra("user");
    String greetingText = "Hi " + customer.getName() + ",";
    ShoppingCart cart = null;

    try {
      cart = new ShoppingCart(customer);
    } catch (AuthenticationException ignored) {
      Log.e("Authentication Exception in CustomerUIActivity.java",
          "FROM: new ShoppingCart(customer)");
    }

    greeting.setText(greetingText);
    logout.setOnClickListener(new LogoutButtonController(this));
    cartButton.setOnClickListener(new CartButtonController(this, cart));

    renderShop(cart);
  }

  @Override
  public void onBackPressed() {
    return;
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 1 && resultCode == RESULT_OK) {
      ShoppingCart cart = (ShoppingCart) data.getSerializableExtra("cart");
      Log.e("UPDATED SHOPPINGCART", cart.getItemMap().toString());
      ImageView cartButton = findViewById(R.id.customer_cart);
      cartButton.setOnClickListener(new CartButtonController(this, cart));
      renderShop(cart);
    }
  }
}
