CREATE TABLE inventory_item (
    product_id UUID PRIMARY KEY,
    available_quantity INT NOT NULL,
    reserved_quantity INT NOT NULL DEFAULT 0,
    version BIGINT NOT NULL
);

CREATE TABLE inventory_reservation (
    reservation_id UUID PRIMARY KEY,
    order_id UUID NOT NULL,
    product_id UUID NOT NULL,
    quantity INT NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL,

    CONSTRAINT uq_inventory_reservation_order
        UNIQUE (order_id, product_id)
);

CREATE INDEX idx_inventory_reservation_order
    ON inventory_reservation(order_id);
