package entity;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Item> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public List<Item> getItems() {
        return items;
    }

    // Lấy 1 món hàng ra dựa trên ID
    private Item getItemById(int id) {
        for (Item i : items) {
            if (i.getProduct().getId() == id) {
                return i;
            }
        }
        return null;
    }

    // Logic quan trọng: Thêm sản phẩm vào giỏ
    public void addItem(Item t) {
        Item m = getItemById(t.getProduct().getId());
        if (m != null) {
            // Nếu sản phẩm đã có trong giỏ, chỉ tăng số lượng
            m.setQuantity(m.getQuantity() + t.getQuantity());
        } else {
            // Nếu chưa có, thêm mới hoàn toàn
            items.add(t);
        }
    }

    // Xóa sản phẩm khỏi giỏ
    public void removeItem(int id) {
        if (getItemById(id) != null) {
            items.remove(getItemById(id));
        }
    }

    // Tính tổng tiền của cả giỏ hàng
    public double getTotalMoney() {
        double t = 0;
        for (Item i : items) {
            t += (i.getQuantity() * i.getPrice());
        }
        return t;
    }
}