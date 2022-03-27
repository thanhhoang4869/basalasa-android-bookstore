exports.constant = {
    BOOK_STATUS: {
        AVAILABLE: 1,
        OUT_OF_STOCK: 0,
        ON_SALE: 2
    },

    ORDER_STATUS: {
        PENDING: 1,
        PREPARING: 2,
        SHIPPING: 3,
        DONE: 0
    },
}

exports.database = {
    url: 'mongodb+srv://basalasa:mobiledev@basalasa.wcann.mongodb.net/basalasa?retryWrites=true&w=majority'
};