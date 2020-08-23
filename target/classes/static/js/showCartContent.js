function showCartContent() {
    fetch('http://localhost:8000/orders/fetch/')
        .then((res) => res.json())
        .then((json) => {
            if (json.length === 0) {
                $('#cart-contents').prepend(`<div class="dropdown-item text-center">Cart Empty!</div>`);
            } else {
                json.forEach((order) => $('#cart-contents')
                    .prepend(`<div class="dropdown-item text-center">${order.productName} x${order.quantity}</div>`));
            }
        });
}