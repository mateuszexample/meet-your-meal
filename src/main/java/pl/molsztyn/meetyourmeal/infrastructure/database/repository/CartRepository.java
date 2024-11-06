package pl.molsztyn.meetyourmeal.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.molsztyn.meetyourmeal.business.dao.CartDAO;
import pl.molsztyn.meetyourmeal.domain.Cart;
import pl.molsztyn.meetyourmeal.infrastructure.database.entity.CartEntity;
import pl.molsztyn.meetyourmeal.infrastructure.database.repository.jpa.CartJpaRepository;
import pl.molsztyn.meetyourmeal.infrastructure.database.repository.mapper.CartEntityMapper;

@Repository
@AllArgsConstructor
public class CartRepository implements CartDAO {

    private final CartJpaRepository cartJpaRepository;

    private final CartEntityMapper cartEntityMapper;

    @Override
    public Cart createCart(Cart cart) {
        CartEntity cartToSave = cartEntityMapper.mapToEntity(cart);
        CartEntity cartSaved = cartJpaRepository.saveAndFlush(cartToSave);
        return cartEntityMapper.mapFromEntity(cartSaved);
    }

    @Override
    public void updateCart(Cart cartToUpdate) {
        CartEntity cartToSave = cartEntityMapper.mapToEntity(cartToUpdate);
        cartJpaRepository.saveAndFlush(cartToSave);
    }

    @Override
    public void deleteCartById(Integer cartId) {
        cartJpaRepository.deleteById(cartId);
    }
}
