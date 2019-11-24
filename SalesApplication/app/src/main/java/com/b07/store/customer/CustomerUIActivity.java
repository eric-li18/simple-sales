package com.b07.store.customer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.R;
import com.b07.database.DatabaseInsertHelper;
import com.b07.database.DatabaseSelectHelper;
import com.b07.inventory.Item;
import com.b07.inventory.ItemTypes;
import com.b07.store.LogoutButtonController;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Eric
 */
public class CustomerUIActivity extends AppCompatActivity {

  private void renderShop() {
    int count = 1;
    for (ItemTypes item : ItemTypes.values()) {
      String itemIdName = "customer_itemname" + count;
      String priceIdName = "customer_itemprice" + count;
      int itemId = getResources().getIdentifier(itemIdName, "id", getPackageName());
      int priceId = getResources().getIdentifier(priceIdName, "id", getPackageName());

      TextView itemTextView = findViewById(itemId);
      TextView priceTextView = findViewById(priceId);
      List<Item> allItems = DatabaseSelectHelper.getAllItems(this);
      BigDecimal price = BigDecimal.ZERO;
      for (Item i : allItems) {
        if (i.getName().equals(item.name())) {
          price = i.getPrice();
          break;
        }
      }
      String itemName = (item.name().substring(0, 1).toUpperCase() + item.name().substring(1)
          .toLowerCase()).replace("_", " ");
      itemTextView.setText(itemName);
      priceTextView.setText(price.toString());
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
    String greetingText = "Hi " + intent.getStringExtra("name") + ",";
    greeting.setText(greetingText);
    logout.setOnClickListener(new LogoutButtonController(this));
    float count = 1.00f;
    for (ItemTypes i : ItemTypes.values()) {
      DatabaseInsertHelper.insertItem(i.name(), new BigDecimal(count), this);
      count += 1.00f;
    }
    renderShop();
  }

  @Override
  public void onBackPressed() {
    return;
  }
}
