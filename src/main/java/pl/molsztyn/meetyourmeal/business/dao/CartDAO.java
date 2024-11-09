package pl.molsztyn.meetyourmeal.business.dao;

import pl.molsztyn.meetyourmeal.domain.Cart;

import java.util.Optional;

public interface CartDAO {
    Cart createCart(Cart emptyCart);
    void updateCart(Cart cartToUpdate);

    void deleteCartById(Integer cartId);

    Optional<Cart> findCartById(Integer cartId);
}
