package pl.molsztyn.meetyourmeal.business.dao;

import pl.molsztyn.meetyourmeal.domain.Cart;

public interface CartDAO {
    Cart createCart(Cart emptyCart);
    void updateCart(Cart cartToUpdate);
}
