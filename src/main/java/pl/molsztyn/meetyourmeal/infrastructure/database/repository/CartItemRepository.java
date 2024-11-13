package pl.molsztyn.meetyourmeal.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.molsztyn.meetyourmeal.business.dao.CartItemDAO;
import pl.molsztyn.meetyourmeal.domain.CartItem;
import pl.molsztyn.meetyourmeal.infrastructure.database.entity.CartItemEntity;
import pl.molsztyn.meetyourmeal.infrastructure.database.repository.jpa.CartItemJpaRepository;
import pl.molsztyn.meetyourmeal.infrastructure.database.repository.mapper.CartItemEntityMapper;

@Repository
@AllArgsConstructor
public class CartItemRepository implements CartItemDAO {

    private final CartItemJpaRepository cartItemJpaRepository;

    private final CartItemEntityMapper cartItemEntityMapper;

    @Override
    public void createCartItem(CartItem cartItem) {
        CartItemEntity cartItemToSave = cartItemEntityMapper.mapToEntity(cartItem);
        cartItemJpaRepository.saveAndFlush(cartItemToSave);
    }

    @Override
    public void deleteCartItemById(Integer cartItemId) {
        cartItemJpaRepository.deleteById(cartItemId);
    }
}
