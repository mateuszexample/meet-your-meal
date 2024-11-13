package pl.molsztyn.meetyourmeal.business.dao;

import pl.molsztyn.meetyourmeal.domain.CartItem;

public interface CartItemDAO {
    void createCartItem(CartItem cartItem);

    void deleteCartItemById(Integer cartItemId);
}
