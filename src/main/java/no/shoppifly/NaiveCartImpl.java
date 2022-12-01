package no.shoppifly;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
class NaiveCartImpl implements CartService {


    MeterRegistry meterRegistry;

    @Autowired
    public NaiveCartImpl(MeterRegistry meterRegistry){
        this.meterRegistry = meterRegistry;

    }


    private final Map<String, Cart> shoppingCarts = new HashMap<>();

    @Override
    public Cart getCart(String id) {
        return shoppingCarts.get(id);
    }

    @Override
    public Cart update(Cart cart) {
        if (cart.getId() == null) {
            cart.setId(UUID.randomUUID().toString());
        }
        shoppingCarts.put(cart.getId(), cart);

        Gauge.builder("number_of_active_carts", shoppingCarts, b -> b.values().size()).register(meterRegistry);

        Gauge.builder("carts_value", shoppingCarts, b -> b.values().stream()
                .flatMap(c -> c.getItems().stream()
                        .map(i -> i.getUnitPrice() * i.getQty()))
                .reduce(0f, Float::sum) ).register(meterRegistry);


    //    meterRegistry.gauge("carts_value", total());
        return shoppingCarts.put(cart.getId(), cart);
    }

    @Override
    public String checkout(Cart cart) {
        shoppingCarts.remove(cart.getId());
        meterRegistry.counter("carts_checked_out").increment();
        Gauge.builder("number_of_active_carts", shoppingCarts, b -> b.values().size()).register(meterRegistry);
        meterRegistry.gauge("carts_value", total());

        Gauge.builder("carts_value", shoppingCarts, b -> b.values().stream()
                .flatMap(c -> c.getItems().stream()
                        .map(i -> i.getUnitPrice() * i.getQty()))
                .reduce(0f, Float::sum) ).register(meterRegistry);


        return UUID.randomUUID().toString();
    }

    @Override
    public List<String> getAllsCarts() {


        return new ArrayList<>(shoppingCarts.keySet());


    }

    // @author Jim; I'm so proud of this one, took me one week to figure out !!!
    public float total() {


        return shoppingCarts.values().stream()
                .flatMap(c -> c.getItems().stream()
                        .map(i -> i.getUnitPrice() * i.getQty()))
                .reduce(0f, Float::sum);
    }
}
