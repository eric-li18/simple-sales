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
import com.b07.users.User;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Eric
 */
public class CustomerUIActivity extends AppCompatActivity {

  private void renderShop(User user, ShoppingCart cart) {
    int count = 1;
    for (ItemTypes itemType : ItemTypes.values()) {
      String itemIdName = "customer_itemname" + count;
      String priceIdName = "customer_itemprice" + count;
      int itemId = getResources().getIdentifier(itemIdName, "id", getPackageName());
      int priceId = getResources().getIdentifier(priceIdName, "id", getPackageName());

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

      String itemFrameId = "customer_frame" + count;
      int frameId = getResources().getIdentifier(itemFrameId, "id", getPackageName());
      FrameLayout itemFrameLayout = findViewById(frameId);

      String itemLayoutName = "customer_item" + count;
      int customerItemId = getResources().getIdentifier(itemLayoutName, "id", getPackageName());
      LinearLayout itemLayout = findViewById(customerItemId);

      String itemName = (itemType.name().substring(0, 1).toUpperCase() + itemType.name()
          .substring(1).toLowerCase()).replace("_", " ");
      itemTextView.setText(itemName);

      if (item != null) {
        int quantity = DatabaseSelectHelper.getInventoryQuantity(item.getId(), this);

        itemLayout.setOnClickListener(
            new ItemLayoutController(this, (ItemImpl) item, quantity, itemName, cart));

      } else {
        itemFrameLayout.setVisibility(View.GONE);
      }
//      HashMap<Item, Integer> itemMap = DatabaseSelectHelper.getInventory(this).getItemMap();
//
//      if (itemMap != null && itemMap.containsKey(item) && itemMap.get(item) != null) {
//        if (itemMap.get(item).compareTo(Integer.valueOf(0)) > 0) {
//          itemLayout.setOnClickListener(
//              new ItemLayoutController(this, (ItemImpl) item, itemMap.get(item).intValue(),
//                  itemName, user, cart));
//        }
//      } else {
//        itemFrameLayout.setVisibility(View.GONE);
//      }

      count++;
    }
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.customer);

    TextView greeting = findViewById(R.id.employee_greeting);
    ImageView logout = findViewById(R.id.customer_logout);
    Intent intent = getIntent();
    Customer customer = (Customer) intent.getSerializableExtra("user");
    String greetingText = "Hi " + customer.getName() + ",";
    ShoppingCart cart = null;

    try {
      cart = new ShoppingCart(customer);
    } catch (AuthenticationException ignored) {
      Log.e("AUTHENTICATION EXCEPTION CustomerUIActivity.java", "new ShoppingCart(customer)");
    }

    greeting.setText(greetingText);
    logout.setOnClickListener(new LogoutButtonController(this));

    renderShop(customer, cart);
  }

  @Override
  public void onBackPressed() {
    return;
  }
}
