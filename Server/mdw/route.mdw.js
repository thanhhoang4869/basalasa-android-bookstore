import accountRoutes from '../route/account.route.js'
import categoryRoutes from '../route/category.route.js'
import bookRoutes from '../route/book.route.js'
import searchRoutes from '../route/search.route.js'
import cartRoutes from '../route/cart.route.js'
import sellerRoutes from '../route/seller.route.js'
import adminRoutes from '../route/admin.route.js'
import orderRoutes from '../route/order.route.js'
import commentRoutes from '../route/comment.route.js'

export default function(app) {
    app.use('/admin', adminRoutes)
    app.use('/account', accountRoutes)
    app.use('/category', categoryRoutes)
    app.use('/book', bookRoutes)
    app.use('/search', searchRoutes)
    app.use('/cart', cartRoutes)
    app.use('/seller', sellerRoutes)
    app.use('/order',orderRoutes)
    app.use('/comment',commentRoutes)
}